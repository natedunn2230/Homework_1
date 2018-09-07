package com.nathandunn.homework_1;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;


import java.util.ArrayList;

/**
 * The DoodleBoard class creates an interactive environment for the
 * user to draw pictures
 */
public class DoodleBoard extends View {

    ArrayList<Line> lines = new ArrayList<>();

    private Bitmap bitmap;
    private Canvas canvas;

    final float ALPHA = 1.0f;
    private float xpos = 0, ypos = 0;

    private int currentColor = -10000;
    private int currentLineIndex = -1;

    public DoodleBoard(Context con, AttributeSet attSet){
        super(con, attSet);

    }

    /**
     * Resizes the canvas
     * @param w current width of the canvas
     * @param h current height of the canvas
     * @param prevW previous width of the canvas (prior to size change, if any)
     * @param prevH previous height of the canvas (prior to size change, if any)
     */
    @Override
    protected void onSizeChanged(int w, int h, int prevW, int prevH) {
        super.onSizeChanged(w, h, prevW, prevH);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    /**
     * Draws to the canvas
     * @param canvas canvas to be drawn on
     */
    @Override
    public void onDraw(Canvas canvas){
        for(Line line: lines){
            canvas.drawPath(line.getPath(), line.getTool());
        }
    }

    /**
     * Handles the touch events emitted by the user.
     * @param event Type of event the user emits to the screen
     * @return always returns true if an event occurs
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        // Delegate tasks based off the users interactions with the doodle board
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
               actionMove(eventX, eventY);
                break;
            case MotionEvent.ACTION_DOWN:
                actionDown(eventX, eventY);
                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                break;
        }

        // invoke the draw call
        postInvalidate();
        return true;
    }

    /**
     * Draws a line in the path that the user drags their finger across the screen
     * @param x new x coordinate of the path
     * @param y new y coordinate of the path
     */
    private void actionMove(float x, float y){
        float diffX = Math.abs(x - xpos);
        float diffY = Math.abs(y - ypos);

        // if the tolerance is higher than alpha (1px, 1px) draw the new path
        if (diffX >= ALPHA|| diffY >= ALPHA) {
            lines.get(currentLineIndex).getPath().quadTo(xpos, ypos,(x + xpos)/2, (y + ypos)/2);
            xpos = x;
            ypos = y;
        }

        // invoke the draw call
        invalidate();
    }

    /**
     * Determines the location of the finger when pressed down on the screen
     * @param x new x coordinate where the user initially touches the screen
     * @param y new y coordinate where the user initially touches the screen
     */
    private void actionDown(float x, float y){
        currentLineIndex++;
        lines.add(new Line(currentColor));
        xpos = x;
        ypos = y;
        lines.get(currentLineIndex).getPath().moveTo(x, y);

        //invoke draw call
        invalidate();
    }

    /**
     * Determines the location of the users finger when it is lifted off the
     * screen
     */
    private void actionUp(){
        lines.get(currentLineIndex).getPath().moveTo(xpos, ypos);
        invalidate();
    }

    /**
     * Clears the doodle board
     */
    public void clearDoodle(){
        for(Line line: lines){
            line.getPath().rewind();
        }
        invalidate();
    }

    /**
     * Sets the current doodle color
     */
    public void setDoodleColor(int color){
        currentColor = color;
    }

    /**
     * Maps the foreground bitmap that the user drew to a white canvas and returns it
     * @return Bitmap to be saved to the device
     */
    public Bitmap getImage(){
        // settings to draw to bitmap
        this.setDrawingCacheEnabled(true);
        this.buildDrawingCache();

        //convert empty black background to a white background
        Bitmap foregroundBitMap = Bitmap.createBitmap(this.getDrawingCache());
        Bitmap completeBitmap = Bitmap.createBitmap(foregroundBitMap.getWidth(), foregroundBitMap.getHeight(), foregroundBitMap.getConfig());
        Canvas backgroundCanvas = new Canvas(completeBitmap);
        backgroundCanvas.drawColor(Color.WHITE);
        backgroundCanvas.drawBitmap(foregroundBitMap,  0 , 0, null);

        this.setDrawingCacheEnabled(false);

       return completeBitmap;
    }

}
