package com.example.myapplication;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String type = intent.getStringExtra("type");
        if (type != null) {
            switch (type) {
                case "water":
                    Toast.makeText(context, "Time to dispense water!", Toast.LENGTH_SHORT).show();
                    // Add your code to dispense water here
                    break;
                case "food":
                    Toast.makeText(context, "Time to dispense food!", Toast.LENGTH_SHORT).show();
                    // Add your code to dispense food here
                    break;
            }
        }
    }
}
