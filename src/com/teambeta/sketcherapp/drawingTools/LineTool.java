package com.teambeta.sketcherapp.drawingTools;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The LineTool class implements the drawing behavior for when the Line tool has been selected
 */
public class LineTool extends DrawingTool {
    private int currentY;
    private int currentX;
    private int lastX;
    private int lastY;
    private int sizeInPixels;
    private Color color;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public LineTool() {
        color = Color.black;
        sizeInPixels = 1;
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
    }

    @Override
    public void onDrag(Graphics2D graphics, MouseEvent e) {
        //for now this does nothing.
        //if time permits we can implement a preview of what the line would look like if the user released the mouse.
    }

    @Override
    public void onRelease(Graphics2D graphics, MouseEvent e) {
        //get the coordinates of where the user released the mouse
        currentX = e.getX();
        currentY = e.getY();
        //draw a line between the start and release points
        if (graphics != null) {
            // draw line if graphics context not null
            graphics.drawLine(lastX, lastY, currentX, currentY);
        }
        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public void onClick(Graphics2D graphics, MouseEvent e) {
        //do nothing
    }

    @Override
    public void onPress(Graphics2D graphics, MouseEvent e) {
        //set the coordinates to the current pixel clicked
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
