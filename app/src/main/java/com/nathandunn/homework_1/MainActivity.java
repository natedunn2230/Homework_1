package com.nathandunn.homework_1;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import java.util.Random;

/**
* Class handles the user interactions with the home screen
 */
public class MainActivity extends AppCompatActivity {

    //global variables to hold components of the home screen
    private int red, green, blue;
    private Button colorButton, doodleButton;
    private EditText colorText;
    private TextView rgbTextView, hexTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        colorButton = findViewById(R.id.main_color_button);
        doodleButton = findViewById(R.id.main_doodle_button);

        // change the text color and display the color values when color button is pressed
        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextColor();
                displayTextColor();
            }
        });

        //switch to Doodle board when switchUI button is pressed
        doodleButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, DoodleActivity.class));
            }
        });

    }

    /**
     * Changes the color of the text the user types into the textbox field
     */
    private void changeTextColor(){
        Random generator = new Random();
        red = generator.nextInt(255);
        green = generator.nextInt(255);
        blue = generator.nextInt(255);

        colorText = findViewById(R.id.main_color_edit_text);
        colorText.setTextColor(Color.rgb(red, green, blue));
    }

    /**
     * Displays the color values of the textbox
     */
    private void displayTextColor(){
        rgbTextView = findViewById(R.id.main_rgb_text);
        hexTextView = findViewById(R.id.main_hex_text);

        String rgbValue = String.format("rgb: (%d, %d, %d)", red, green, blue);
        String hexValue = String.format("hex: #%02x%02x%02x", red, green, blue);
        rgbTextView.setText(rgbValue);
        hexTextView.setText(hexValue);

    }
}
