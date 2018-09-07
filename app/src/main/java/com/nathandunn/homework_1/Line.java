package com.nathandunn.homework_1;

import android.graphics.Paint;
import android.graphics.Path;

/**
 * Line holds segments of the users drawing
 */
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

    /**
     * Returns the path (series of x, y coordinates) of the segment of the doodle
     * @return specific path of the doodle
     */
    public Path getPath(){
        return this.path;
    }

    /**
     * Returns the tool (Object used to do the drawing) of the segment of the doodle
     * @return specific tool of the doodle
     */
    public Paint getTool(){
        return this.tool;
    }

}
