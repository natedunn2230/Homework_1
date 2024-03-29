package com.nathandunn.homework_1;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import java.util.Random;

import me.priyesh.chroma.ChromaDialog;
import me.priyesh.chroma.ColorMode;
import me.priyesh.chroma.ColorSelectListener;

/**
 * This class instantiates the doodle board
 */
public class DoodleActivity extends AppCompatActivity {
    private Button homeButton, clearButton, colorButton, saveButton;
    private DoodleBoard doodleBoard;
    private ChromaDialog colorPicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doodle);

        //request permission to write to device
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);

        //buttons
        homeButton = findViewById(R.id.doodle_home_button);
        clearButton = findViewById(R.id.doodle_clear_button);
        colorButton = findViewById(R.id.doodle_color_change_button);
        saveButton = findViewById(R.id.doodle_save_button);

        //custom doodle board
        doodleBoard = findViewById(R.id.doodle_board);

        //color picker
        colorPicker = new ChromaDialog();


        // Return to the home screen
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                startActivity(new Intent(DoodleActivity.this, MainActivity.class));
            }
        });

        //clear the doodle board
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                doodleBoard.clearDoodle();
            }
        });

        // Display the color picker when the user clicks on the color button
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

        //save the image
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });

    }

    /**
     * Saves the doodle that the user drew
     */
    private void saveImage() {
        Bitmap bitMap = doodleBoard.getImage();
        FileOutputStream output;

        // random numbers to tag each new doodle
        Random generator = new Random();
        int bound = 10000;

        // base file directory
        File baseDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
        String imageFileName = String.format("doodle_%d.png", generator.nextInt(bound));

        try {

            if(!baseDir.exists()){
                baseDir.mkdirs();
            }

            //file to be saved
            File pictureFile = new File(baseDir, imageFileName);

            if (pictureFile.exists()){
                pictureFile.delete();
            }

            // save the bitmap image created from the users drawing
            output = new FileOutputStream(pictureFile);
            bitMap.compress(Bitmap.CompressFormat.PNG, 100, output);
            output.flush();
            output.close();
            Toast.makeText(getApplicationContext(), "Image saved", Toast.LENGTH_SHORT).show();

            //update the picture folder to display the newly saved doodle
            MediaScannerConnection.scanFile(getApplicationContext(), new String[]{pictureFile.getPath()}, null, new MediaScannerConnection.OnScanCompletedListener() {
                @Override
                public void onScanCompleted(String path, Uri uri) {
                    Log.d("image_scan", path);
                }
            });

        } catch (Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Cannot save image", Toast.LENGTH_SHORT).show();
        }
    }
}
