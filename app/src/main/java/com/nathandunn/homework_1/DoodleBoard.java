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



public class DoodleBoard extends View {


    private Bitmap bitmap;
    private Canvas canvas;
    private Path path = new Path();
    private Paint tool = new Paint();

    final float ALPHA = 1.0f;
    private float xpos = 0, ypos = 0;


    public DoodleBoard(Context con, AttributeSet attSet){
        super(con, attSet);

        //set attributes of the tool that draws to the canvas
        tool.setARGB(255,255,0,0);
        tool.setStyle(Paint.Style.STROKE);
        tool.setStrokeWidth(15.0f);
        tool.setAntiAlias(true);
        tool.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onSizeChanged(int w, int h, int prevW, int prevH) {
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawPath(path, tool);
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
            path.quadTo(xpos, ypos,(x + xpos)/2, (y + ypos)/2);
            xpos = x;
            ypos = y;
        }
        invalidate();
    }

    private void actionDown(float x, float y){
        xpos = x;
        ypos = y;
        path.moveTo(x, y);
        invalidate();
    }

    private void actionUp(){
        path.moveTo(xpos, ypos);
        invalidate();
    }

    public void clearDoodle(){
        path.rewind();
        invalidate();
    }

    public void setDoodleColor(int color){
        tool.setColor(color);
    }

}
