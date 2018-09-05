package com.nathandunn.homework_1;

import android.graphics.Paint;
import android.graphics.Path;

public class Line {

    private Path path;
    private Paint tool;

    public Line(int color){
        path = new Path();
        tool = new Paint();

        //set attributes of the tool that draws to the canvas
        tool.setColor(color);
        tool.setStyle(Paint.Style.STROKE);
        tool.setStrokeWidth(15.0f);
        tool.setAntiAlias(true);
        tool.setStrokeJoin(Paint.Join.ROUND);
    }

    public Path getPath(){
        return this.path;
    }

    public Paint getTool(){
        return this.tool;
    }

}
