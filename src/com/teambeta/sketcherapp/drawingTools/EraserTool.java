package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * NOTE:
 * 1. sizeInPixels does nothing so far.
 * 2. The DrawArea class object must be passed into the tool to receive the background colour.
 * <p>
 * The EraserTool class implements the drawing behavior for when the Eraser tool has been selected
 */
public class EraserTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private int lastX;
    private int lastY;
    private int sizeInPixels;
    private Color color;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public EraserTool(DrawArea drawArea) {
        color = drawArea.getBackground();
        sizeInPixels = 20;
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        //draw a path that follows your mouse while the mouse is being dragged
        Graphics canvasGraphics = canvas.getGraphics();
        if (canvasGraphics != null) {
            currentX = e.getX();
            currentY = e.getY();
            // draw line if graphics context not null
            canvasGraphics.drawLine(lastX, lastY, currentX, currentY);
            lastX = currentX;
            lastY = currentY;
        }
    }

    @Override
    public  void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onClick(Graphics2D graphics, MouseEvent e) {
        //draw a point where the mouse was clicked
        color = graphics.getBackground();
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
     * getColor returns the current color the canvas background was set to.
     *
     * @return the current Color of the LineTool
     */
    @Override
    public Color getColor() {
        return color;
    }
}
