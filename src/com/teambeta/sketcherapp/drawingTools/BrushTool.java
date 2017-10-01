package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;

import java.awt.*;
import java.awt.event.MouseEvent;

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
    public void onDrag(Graphics2D graphics, MouseEvent e) {
        graphics.setColor(color);
        //draw a path that follows your mouse while the mouse is being dragged
        if (graphics != null) {
            currentX = e.getX();
            currentY = e.getY();


            graphics.setStroke(new BasicStroke(getBrushWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                    BasicStroke.JOIN_ROUND));

            graphics.drawLine(lastX, lastY, currentX, currentY);

            lastX = currentX;
            lastY = currentY;
        }
    }

    @Override
    public void onRelease(Graphics2D graphics, MouseEvent e) {
        graphics.setStroke(new BasicStroke(DEFAULT_STOKE_VALUE));
    }

    @Override
    public void onClick(Graphics2D graphics, MouseEvent e) {
        //draw a point where the mouse was clicked
        graphics.setColor(color);
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
     * getColor returns the current color the brush tool is set to.
     *
     * @return the current Color of the LineTool
     */
    @Override
    public Color getColor() {
        return ColorChooser.getColor();
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
