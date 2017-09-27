package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * The SquareTool class implements the drawing behavior for when the Square tool has been selected
 *
 * Behaviour of the square tool:
 * - The longest side will take the length of the shortest side.
 * - The end-point relative to the init-point can be in any 4 quadrants.
 */
public class SquareTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private int initX;
    private int initY;
    private int drawWidthX;
    private int drawHeightY;
    private int xAxisMagnitudeDelta;
    private int yAxisMagnitudeDelta;
    private int sizeInPixels;
    private Color color;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public SquareTool() {
        registerObservers();
        color = Color.black;
        sizeInPixels = 1;
        initX = 0;
        initY = 0;
        currentX = 0;
        currentY = 0;
        drawWidthX = 0;
        drawHeightY = 0;
        xAxisMagnitudeDelta = 0;
        yAxisMagnitudeDelta = 0;
    }

    @Override
    public void onDrag(Graphics2D graphics, MouseEvent e) {
        /*
            If time permits we can implement a preview of what the square would look like if the
            user released the mouse. This would greatly aid ease of use as the square shape is determined
            by the shortest axis difference.
        */
        graphics.setColor(color);
    }


    @Override
    public void onRelease(Graphics2D graphics, MouseEvent e) {
        // Get the coordinates of where the user released the mouse.
        currentX = e.getX();
        currentY = e.getY();

        // Draw the square with the longest side as long as the shortest side.
        xAxisMagnitudeDelta = Math.abs(currentX - initX);
        yAxisMagnitudeDelta = Math.abs(currentY - initY);

        if (xAxisMagnitudeDelta > yAxisMagnitudeDelta) {
            drawWidthX = yAxisMagnitudeDelta;
            drawHeightY = yAxisMagnitudeDelta;
        }
        else {
            drawWidthX = xAxisMagnitudeDelta;
            drawHeightY = xAxisMagnitudeDelta;
        }

        // Handle cases where the square lies in a quadrant (with origin 0,0 at click) other than IV.
        if (currentY < initY) {
            initY -= drawHeightY;
        }
        if (currentX < initX) {
            initX -= drawWidthX;
        }

        // Draw the square as the user would expect.
        if (graphics != null) {
            // draw square if graphics context not null
            graphics.drawRect(initX, initY, drawWidthX, drawHeightY);
        }
    }

    @Override
    public void onClick(Graphics2D graphics, MouseEvent e) {
    }

    @Override
    public void onPress(Graphics2D graphics, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
        initX = currentX;
        initY = currentY;
    }

    /**
     * getColor returns the current color the square tool is set to.
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
