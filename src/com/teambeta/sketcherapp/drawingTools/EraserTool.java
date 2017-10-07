package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

/**
 * NOTE:
 * The DrawArea class object must be passed into the tool to receive the background colour.
 *
 * The EraserTool class implements the drawing behavior for when the Eraser tool has been selected
 */
public class EraserTool extends DrawingTool {

    private int currentX;
    private int currentY;
    private int lastX;
    private int lastY;
    private Graphics2D layer1Graphics;
    private Color color;
    private int eraserWidth;
    private final int DEFAULT_WIDTH_VALUE = 10;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public EraserTool(DrawArea drawArea) {
        color = drawArea.getBackground();
        eraserWidth = DEFAULT_WIDTH_VALUE;
        currentX = 0;
        currentY = 0;
        lastX = 0;
        lastX = 0;
    }



    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        //draw a path that follows your mouse while the mouse is being dragged
        currentX = e.getX();
        currentY = e.getY();

        layer1Graphics.drawLine(lastX, lastY, currentX, currentY);
        DrawArea.drawLayersOntoCanvas(layers, canvas);

        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();

        layer1Graphics.fillOval(currentX - (eraserWidth/2),currentY - (eraserWidth/2), eraserWidth, eraserWidth);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        initLayer1Graphics(canvas, layers, e);
        //set the coordinates to the current point when the mouse is pressed
        currentX = e.getX();
        currentY = e.getY();
        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public int getToolWidth() {
        return eraserWidth;
    }

    @Override
    public void setToolWidth(int width) {
            eraserWidth = width;
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

    /**
     * Initialize the parameters required for layer1Graphics.
     *
     * @param canvas for drawing the line onto.
     * @param layers first layer by default is layers[0]
     * @param e      MouseEvent
     */
    private void initLayer1Graphics(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);
        layer1Graphics.setStroke(new BasicStroke(getToolWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
    }

}
