package com.teambeta.sketcherapp.drawingTools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The LineTool class implements the drawing behavior for when the Pen tool has been selected
 */
public class PenTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private int lastX;
    private int lastY;
    private int sizeInPixels;
    private Color color;
    private int penWidth;
    private final  int DEFAULT_STOKE_VALUE= 5;

    public int getPenWidth() {
        return penWidth;
    }

    public void setPenWidth(int penWidth) {
        this.penWidth = penWidth;
    }

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public PenTool() {
        color = Color.black;
        sizeInPixels = 1;
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
        penWidth = 10;
    }

    @Override
    public void onDrag(Graphics2D graphics, MouseEvent e) {
        //draw a path that follows your mouse while the mouse is being dragged
        if (graphics != null) {
            currentX = e.getX();
            currentY = e.getY();
            // draw line if graphics context not null

            graphics.setStroke(new BasicStroke(getPenWidth()));
            //
            graphics.drawLine(lastX, lastY, currentX, currentY);

            lastX = currentX;
            lastY = currentY;
        }
    }

    @Override
    public void onRelease(Graphics2D graphics, MouseEvent e) {
        graphics.setStroke(new BasicStroke( DEFAULT_STOKE_VALUE));
    }

    @Override
    public void onClick(Graphics2D graphics, MouseEvent e) {
        //draw a point where the mouse was clicked
        currentX = e.getX();
        currentY = e.getY();
        graphics.drawLine(currentX, currentY, currentX, currentY);
        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public void onPress(Graphics2D graphics, MouseEvent e) {
        //set the coordinates to the current point when the mouse is pressed
        currentX = e.getX();
        currentY = e.getY();
        lastX = currentX;
        lastY = currentY;
    }
    
    /**
     * getColor returns the current color the line tool is set to.
     *
     * @return the current Color of the LineTool
     */
    @Override
    public Color getColor() {
        return color;
    }
}
