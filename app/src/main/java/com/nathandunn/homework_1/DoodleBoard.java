package com.nathandunn.homework_1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class DoodleBoard extends View {

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
    public void onDraw(Canvas canvas){
        canvas.drawPath(path, tool);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
               action_move(eventX, eventY);
                Log.d("touch_event:", "action_move");
                break;
            case MotionEvent.ACTION_DOWN:
                action_down(eventX, eventY);
                Log.d("touch_event", "action_down");
                break;
            case MotionEvent.ACTION_UP:
                action_up();
                Log.d("touch_event", "action_up");
                break;
        }

        Log.d("touch_coords", String.format("(%f, %f)", xpos, ypos));
        postInvalidate();
        return true;
    }

    private void action_move(float x, float y){
        float diffX = Math.abs(x - xpos);
        float diffY = Math.abs(y - ypos);

        if (diffX >= ALPHA|| diffY >= ALPHA) {
            path.quadTo(xpos, ypos,(x + xpos)/2, (y + ypos)/2);
            xpos = x;
            ypos = y;
        }
        invalidate();
    }

    private void action_down(float x, float y){
        xpos = x;
        ypos = y;
        path.moveTo(x, y);
        invalidate();
    }

    private void action_up(){
        path.moveTo(xpos, ypos);
        invalidate();
    }

    public void clear_drawing(){
        path.rewind();
        invalidate();
    }


}
