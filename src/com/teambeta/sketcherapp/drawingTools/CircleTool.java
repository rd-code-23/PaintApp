package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The CircleTool class implements the drawing behavior for when the Circle tool has been selected
 * <p>
 * Please note: CircleTool and SquareTool implement exactly the same code except for one single line
 * that actually draws the shape. In beta (for a working demo) this is fine, but look to create
 * a superclass that handles these square-boundable shapes in the future.
 * <p>
 * Behaviour of the circle tool:
 * - The longest side will take the length of the shortest side.
 * - The end-point relative to the init-point can be in any 4 quadrants.
 */
public class CircleTool extends DrawingTool {

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
    public CircleTool() {
        color = Color.black;
        registerObservers();
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
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        canvas.getGraphics().setColor(color);
    }


    @Override
    public  void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        // Get the coordinates of where the user released the mouse
        currentX = e.getX();
        currentY = e.getY();

        // Draw the circle with the longest side as long as the shortest side
        xAxisMagnitudeDelta = Math.abs(currentX - initX);
        yAxisMagnitudeDelta = Math.abs(currentY - initY);

        if (xAxisMagnitudeDelta > yAxisMagnitudeDelta) {
            drawWidthX = yAxisMagnitudeDelta;
            drawHeightY = yAxisMagnitudeDelta;
        } else {
            drawWidthX = xAxisMagnitudeDelta;
            drawHeightY = xAxisMagnitudeDelta;
        }

        // Handle cases where the circle lies in a quadrant (with origin 0,0 at click) other than IV
        if (currentY < initY) {
            initY -= drawHeightY;
        }
        if (currentX < initX) {
            initX -= drawWidthX;
        }

        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasGraphics.setColor(color);
        canvasGraphics.drawOval(initX, initY, drawWidthX, drawHeightY);
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onPress(Graphics2D graphics, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
        initX = currentX;
        initY = currentY;
    }

    /**
     * getColor returns the current color the circle tool is set to.
     *
     * @return the current Color of the CircleTool
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
