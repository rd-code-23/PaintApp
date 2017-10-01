package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
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
    private int sizeInPixels;
    private Color color;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public EraserTool(DrawArea drawArea) {
        color = drawArea.getBackground();
        sizeInPixels = 20;
        currentX = 0;
        currentY = 0;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        //draw a path that follows your mouse while the mouse is being dragged
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);
        currentX = e.getX();
        currentY = e.getY();
        // draw line if graphics context not null
        layer1Graphics.fillRect(currentX, currentY, sizeInPixels, sizeInPixels);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);

        currentY = e.getY();
        //draw a point where the mouse was clicked
        layer1Graphics.fillRect(currentX, currentY, sizeInPixels, sizeInPixels);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        //set the coordinates to the current point when the mouse is pressed
        currentX = e.getX();
        currentY = e.getY();
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
