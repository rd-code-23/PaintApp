package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The EllipseTool class implements the drawing behavior for when the Ellipse tool has been selected
 * <p>
 * Behaviour of the ellipse tool:
 * - The end-point relative to the init-point can be in any 4 quadrants.
 * - Draw a circle when the shift button is held on mouse release.
 */
public class EllipseTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private int initX;
    private int initY;
    private int xAxisMagnitudeDelta;
    private int yAxisMagnitudeDelta;
    private int drawWidthX;
    private int drawHeightY;
    private int sizeInPixels;
    private Color color;
    private final int DEFAULT_STOKE_VALUE = 10;
    private int ellipseWidth;
    /**
     * The constructor sets the properties of the tool to their default values
     */
    public EllipseTool() {
        color = Color.black;
        registerObservers();
        sizeInPixels = 1;
        initX = 0;
        initY = 0;
        currentX = 0;
        currentY = 0;
        drawWidthX = 0;
        drawHeightY = 0;
        ellipseWidth = DEFAULT_STOKE_VALUE;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        canvas.getGraphics().setColor(color);
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        // Get the coordinates of where the user released the mouse.
        currentX = e.getX();
        currentY = e.getY();

        xAxisMagnitudeDelta = Math.abs(currentX - initX);
        yAxisMagnitudeDelta = Math.abs(currentY - initY);

        // Detect shift-down by the MouseEvent, e.
        if (e.isShiftDown()) {
            if (xAxisMagnitudeDelta > yAxisMagnitudeDelta) {
                drawWidthX = yAxisMagnitudeDelta;
                drawHeightY = yAxisMagnitudeDelta;
            } else {
                drawWidthX = xAxisMagnitudeDelta;
                drawHeightY= yAxisMagnitudeDelta;
            }
        } else {
            drawWidthX = xAxisMagnitudeDelta;
            drawHeightY = yAxisMagnitudeDelta;
        }

        // Handle cases where the ellipse lies in a quadrant (with origin 0,0 at click) other than IV.
        if (currentY < initY) {
            initY -= drawHeightY;
        }
        if (currentX < initX) {
            initX -= drawWidthX;
        }

        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setStroke(new BasicStroke(getToolWidth()));
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);

        layer1Graphics.drawOval(initX, initY, drawWidthX, drawHeightY);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();
        initX = currentX;
        initY = currentY;
    }

    @Override
    public int getToolWidth() {
       return ellipseWidth;
    }

    @Override
    public void setToolWidth(int width) {
    ellipseWidth = width;
    }

    /**
     * getColor returns the current color the ellipse tool is set to.
     *
     * @return the current Color of the ellipse
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
