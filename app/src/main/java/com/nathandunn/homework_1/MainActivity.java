package com.nathandunn.homework_1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private int red, green, blue;
    private Button colorButton, switchUIButton;
    private EditText colorText;
    private TextView rgbTextView, hexTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        colorButton = findViewById(R.id.color_button);
        switchUIButton = findViewById(R.id.switchUI);

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeTextColor();
                displayTextColor();
            }
        });

        switchUIButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(MainActivity.this, DoodleActivity.class));
            }
        });

    }

    private void changeTextColor(){
        Random generator = new Random();
        red = generator.nextInt(255);
        green = generator.nextInt(255);
        blue = generator.nextInt(255);

        colorText = findViewById(R.id.color_edit_text);
        colorText.setTextColor(Color.rgb(red, green, blue));
    }

    private void displayTextColor(){
        rgbTextView = findViewById(R.id.rgbText);
        hexTextView = findViewById(R.id.hexText);

        String rgbValue = String.format("rgb: (%d, %d, %d)", red, green, blue);
        String hexValue = String.format("hex: #%02x%02x%02x", red, green, blue);
        rgbTextView.setText(rgbValue);
        hexTextView.setText(hexValue);

    }
}
