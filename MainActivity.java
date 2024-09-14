package com.example.myapplication; //Java packages help manage namespaces by grouping related classes and interfaces together.
//This ensures that classes with the same name can coexist in different packages without causing naming conflicts.
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override // @Override instructs the compiler that you intend to override a method in the superclass
    protected void onCreate(Bundle savedInstanceState) { //This means that MainActivity inherits the properties and behaviors (methods) of AppCompatActivity.
        super.onCreate(savedInstanceState); //This method is called when the user clicks on your app's icon.This method is required for every activity, as it sets the layout.
        EdgeToEdge.enable(this); //Edge-to-edge mode allows the content to be drawn behind system bars like the status bar and navigation bar, giving a more immersive experience.
        setContentView(R.layout.activity_main); //This method sets the UI layout for the activity. The layout file activity_main.xml is loaded and displayed on the screen.
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {  //a listener is set to adjust the padding of the main view
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());    //view so that the content doesn't overlap with system bars, making the UI responsive to different device configurations and system UI changes.
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Find buttons by ID
        Button buttonWater = findViewById(R.id.button_water);
        Button buttonFood = findViewById(R.id.button_food);

        // Set onClick listeners for buttons
        buttonWater.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Water button clicked", Toast.LENGTH_SHORT).show();
        });

        buttonFood.setOnClickListener(v -> {
            Toast.makeText(MainActivity.this, "Food button clicked", Toast.LENGTH_SHORT).show();
        });
    }
}
