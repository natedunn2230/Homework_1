package com.nathandunn.homework_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;
import me.priyesh.chroma.ColorSelectListener;

public class DoodleActivity extends AppCompatActivity {
    private Button homeButton, clearButton, colorButton;
    private DoodleBoard doodleBoard;
    private ChromaDialog colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);

        //buttons
        homeButton = findViewById(R.id.home_button);
        clearButton = findViewById(R.id.clear_button);
        colorButton = findViewById(R.id.color_change_button);

        //custom doodle board
        doodleBoard = findViewById(R.id.doodle_board);

        //color picker
        colorPicker = new ChromaDialog();


        //button handlers
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(DoodleActivity.this, MainActivity.class));
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                doodleBoard.clearDoodle();
            }
        });

        colorButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                new ChromaDialog.Builder().initialColor(256^2).colorMode(ColorMode.RGB)
                    .onColorSelected(new ColorSelectListener() {
                        @Override
                        public void onColorSelected(int i) {
                            doodleBoard.setDoodleColor(i);
                        }
                    })
                    .create().show(getSupportFragmentManager(), "color_builder");
            }
        });



    }
}
