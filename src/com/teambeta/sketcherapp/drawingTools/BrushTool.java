package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The LineTool class implements the drawing behavior for when the Pen tool has been selected
 */
public class BrushTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private int lastX;
    private int lastY;
    private int sizeInPixels;
    private Color color;
    private int brushWidth;
    private final int DEFAULT_STOKE_VALUE = 1;

    public int getBrushWidth() {
        return brushWidth;
    }

    public void setBrushWidth(int brushWidth) {
        this.brushWidth = brushWidth;
    }

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public BrushTool() {
        color = Color.black;
        registerObservers();
        sizeInPixels = 1;
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
        brushWidth = 10;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        drawLine(canvas, layers, e);
    }


    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }


    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }


    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        //set the coordinates to the current point when the mouse is pressed
        currentX = e.getX();
        currentY = e.getY();
        lastX = currentX;
        lastY = currentY;
    }

    /**
     * getColor returns the current color the brush tool is set to.
     *
     * @return the current Color of the LineTool
     */
    @Override
    public Color getColor() {
        return ColorChooser.getColor();
    }

    /**
     * Draw a line on canvas. Used for line preview when dragging as well.
     *
     * @param canvas for drawing line onto.
     * @param layers first layer by default is layers[0]
     * @param e      MouseEvent
     */
    private void drawLine(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);

        layer1Graphics.setStroke(new BasicStroke(getBrushWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));

        //draw a point where the mouse was clicked
        currentX = e.getX();
        currentY = e.getY();

        layer1Graphics.drawLine(lastX, lastY, currentX, currentY);

        DrawArea.drawLayersOntoCanvas(layers, canvas);

        lastX = currentX;
        lastY = currentY;
    }

    /**
     * Add a new observer to ColorChooser. Selecting a color in ColorChooser will update the color in this class
     */
    private void registerObservers() {
        ColorChooser.addObserver(new GeneralObserver() {
            @Override
            public void update() {
                color = ColorChooser.getColor();
            }
        });
    }
}
