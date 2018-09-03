package com.nathandunn.homework_1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DoodleActivity extends AppCompatActivity {
    private Button homeButton, clearButton;
    private DoodleBoard doodleBoard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);

        homeButton = findViewById(R.id.home_button);
        clearButton = findViewById(R.id.clear_button);
        doodleBoard = findViewById(R.id.doodle_board);

        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(DoodleActivity.this, MainActivity.class));
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                doodleBoard.clear_drawing();
            }
        });

    }
}
