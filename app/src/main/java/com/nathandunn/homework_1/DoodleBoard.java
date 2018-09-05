package com.nathandunn.homework_1;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;


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

    @Override
    protected void onSizeChanged(int w, int h, int prevW, int prevH) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    public void onDraw(Canvas canvas){
        for(Line line: lines){
            canvas.drawPath(line.getPath(), line.getTool());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
               actionMove(eventX, eventY);
                Log.d("touch_event:", "action_move");
                break;
            case MotionEvent.ACTION_DOWN:
                actionDown(eventX, eventY);
                Log.d("touch_event", "action_down");
                break;
            case MotionEvent.ACTION_UP:
                actionUp();
                Log.d("touch_event", "action_up");
                break;
        }

        Log.d("touch_coords", String.format("(%f, %f)", xpos, ypos));
        postInvalidate();
        return true;
    }

    private void actionMove(float x, float y){
        float diffX = Math.abs(x - xpos);
        float diffY = Math.abs(y - ypos);

        if (diffX >= ALPHA|| diffY >= ALPHA) {
            lines.get(currentLineIndex).getPath().quadTo(xpos, ypos,(x + xpos)/2, (y + ypos)/2);
            xpos = x;
            ypos = y;
        }
        invalidate();
    }

    private void actionDown(float x, float y){
        currentLineIndex++;
        lines.add(new Line(currentColor));
        xpos = x;
        ypos = y;
        lines.get(currentLineIndex).getPath().moveTo(x, y);
        invalidate();
        Log.d("lineIndex", currentLineIndex + "");
    }

    private void actionUp(){
        lines.get(currentLineIndex).getPath().moveTo(xpos, ypos);
        invalidate();
    }

    public void clearDoodle(){
        for(Line line: lines){
            line.getPath().rewind();
        }
        invalidate();
    }

    public void setDoodleColor(int color){
        currentColor = color;
    }

}
