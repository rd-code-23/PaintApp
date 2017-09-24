package com.teambeta.sketcherapp.model;

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

    public LineTool() {
        color = Color.black;
        sizeInPixels = 1;
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
    }

    @Override
    public void onDrag(Graphics2D graphics, MouseEvent e, int startX, int startY, int endX, int endY) {
        //for now this does nothing
        //if we have time we can implement a line preview
    }

    @Override
    public void onRelease(Graphics2D graphics, MouseEvent e, int startX, int startY, int endX, int endY) {
        //when the line tool is released draw the line
        if (graphics != null) {
            // draw line if graphics context not null
            graphics.drawLine(startX, startY, endX, endY);
        }
    }

    @Override
    public void onClick(Graphics2D graphics, MouseEvent e, int startX, int startY, int endX, int endY) {
        //do nothing
    }

    @Override
    public void onPress(Graphics2D graphics, MouseEvent e, int startX, int startY, int endX, int endY) {

    }

    @Override
    public Color getColor() {
        return color;
    }
}
