package com.example.myapplication;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.app.TimePickerDialog;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Button buttonWater = findViewById(R.id.button_water);
        Button buttonFood = findViewById(R.id.button_food);
        Button buttonSetWaterSchedule = findViewById(R.id.button_set_water_schedule);
        Button buttonSetFoodSchedule = findViewById(R.id.button_set_food_schedule);

        buttonWater.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Water button clicked", Toast.LENGTH_SHORT).show();
            // Here you would add your code to dispense water
        });

        buttonFood.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Food button clicked", Toast.LENGTH_SHORT).show();
            // Here you would add your code to dispense food
        });

        buttonSetWaterSchedule.setOnClickListener(v -> showTimePickerDialog("water"));
        buttonSetFoodSchedule.setOnClickListener(v -> showTimePickerDialog("food"));
    }

    private void showTimePickerDialog(String type) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                (view, hourOfDay, minuteOfHour) -> setSchedule(type, hourOfDay, minuteOfHour),
                hour, minute, true);
        timePickerDialog.show();
    }

    private void setSchedule(String type, int hourOfDay, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("type", type);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, type.equals("water") ? 0 : 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            Toast.makeText(this, type + " schedule set for " + hourOfDay + ":" + minute, Toast.LENGTH_SHORT).show();
        }
    }
}
