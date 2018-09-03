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

    public DoodleBoard(Context con, AttributeSet attSet){
        super(con, attSet);

        tool.setARGB(255,255,0,0);
        tool.setStyle(Paint.Style.STROKE);
        tool.setStrokeWidth(7.0f);
        tool.setAntiAlias(true);
        tool.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.drawPath(path, tool);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float currentX = event.getX();
        float currentY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                path.moveTo(currentX, currentY);
                Log.d("touch_event:", "action_move");
                break;
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.lineTo(currentX, currentY);
                Log.d("touch_event", "action_down");
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                path.lineTo(currentX, currentY);
                path.reset();
                Log.d("touch_event", "action_up");
                break;
        }

        Log.d("touch_coords", String.format("(%f, %f)", currentX, currentY));
        postInvalidate();
        return true;
    }


}
