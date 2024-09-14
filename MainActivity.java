package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;

public class MainActivity extends AppCompatActivity {

    private String esp8266IP = "http://192.168.243.91"; // Replace with your ESP8266's local IP
    private ExecutorService executorService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize ExecutorService
        executorService = Executors.newSingleThreadExecutor();

        // Find buttons by ID
        Button buttonWater = findViewById(R.id.button_water);
        Button buttonFood = findViewById(R.id.button_food);

        // Set onClick listeners for buttons
        buttonWater.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Water button clicked", Toast.LENGTH_SHORT).show();
            sendRequest("/blue/on");
        });

        buttonFood.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Food button clicked", Toast.LENGTH_SHORT).show();
            sendRequest("/red/on");
        });
    }

    // Function to send HTTP request to ESP8266
    private void sendRequest(String endpoint) {
        executorService.execute(() -> {
            try {
                URL url = new URL(esp8266IP + endpoint);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setConnectTimeout(5000); // 5 seconds connect timeout
                urlConnection.setReadTimeout(5000);    // 5 seconds read timeout
                urlConnection.connect();
                int responseCode = urlConnection.getResponseCode();

                runOnUiThread(() -> {
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        Toast.makeText(MainActivity.this, "Request successful!", Toast.LENGTH_SHORT).show();
                        Log.d("HTTP", "Request successful!");
                    } else {
                        Toast.makeText(MainActivity.this, "Request failed. Code: " + responseCode, Toast.LENGTH_SHORT).show();
                        Log.d("HTTP", "Request failed. Code: " + responseCode);
                    }
                });

                urlConnection.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    // Helper function to convert InputStream to String
    private String convertStreamToString(InputStream is) {
        Scanner scanner = new Scanner(is).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (executorService != null) {
            executorService.shutdown();
        }
    }
}
