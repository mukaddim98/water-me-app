package com.example.waterme;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    String time = "null";
    Double soilMoisture = 0.0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("WMD/WMD-001");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                HashMap dataEntry = (HashMap) dataSnapshot.getValue();
                if(dataEntry!=null) {
                    Object uid = dataEntry.keySet().toArray()[0];
                    HashMap dataValues = (HashMap) dataEntry.get(uid);
                    time = dataValues.get("Time").toString();
                    soilMoisture = Double.parseDouble(dataValues.get("Soil Moisture").toString());
                    updateMessage();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    protected void updateMessage(){
        TextView messageText = (TextView)findViewById(R.id.messageText);
        TextView timeText = (TextView)findViewById(R.id.timeText);
        timeText.setText("Time: "+time);
        if(soilMoisture > 0) {
            messageText.setText("I am full!");
        }
        else{
            messageText.setText("I am thirsty!");
        }

    }
}