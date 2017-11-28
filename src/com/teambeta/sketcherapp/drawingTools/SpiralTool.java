package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * The SpiralTool class implements the drawing behavior for when the Spiral tool has been selected
 */
public class SpiralTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private Color color;
    private int brushWidth;
    private final static int DEFAULT_STOKE_VALUE = 10;
    private final static int MAXIMUM_PIXEL_MARGIN = 15;
    private final static float LINE_WIDTH_REDUCER = 1.5f;
    private final static double T_VAL_INCREMENT = 0.025;
    private final static double SCALING_FACTOR_REDUCER = 1.0 / 8.0;
    private final static double MINIMUM_SCALING_FACTOR = 1.01;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public SpiralTool() {
        color = Color.black;
        registerObservers();
        currentX = 0;
        currentY = 0;
        brushWidth = DEFAULT_STOKE_VALUE;
    }

    @Override
    public void onDrag(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    /**
     * Get selected image layer.
     * 
     * @param drawingLayers of the canvas.
     * @return selected layer.
     */
    private ImageLayer getSelectedLayer(LinkedList<ImageLayer> drawingLayers) {
        //get the selected layer, this assumes there is only one selected layer.
        for (int i = 0; i < drawingLayers.size(); i++) {
            ImageLayer drawingLayer = drawingLayers.get(i);
            if (drawingLayer.isSelected()) {
                return drawingLayer;
            }
        }
        return null;
    }

    @Override
    public void onRelease(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onClick(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onPress(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        // Set the coordinates to the current point when the mouse is pressed.
        currentX = e.getX();
        currentY = e.getY();

        double scalingFactor = Math.pow(brushWidth, SCALING_FACTOR_REDUCER);

        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        if (selectedLayer != null) {
            if (!e.isAltDown()) {
                writeParametricSpiral(scalingFactor, currentX, currentY, selectedLayer.getBufferedImage(), false);
            } else {
                writeParametricSpiral(scalingFactor, currentX, currentY, selectedLayer.getBufferedImage(), true);
            }
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
    }

    @Override
    public void onMove(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {

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
     * Get the selected tool width.
     * 
     * @return brush width.
     */
    public int getToolWidth() {
        return brushWidth;
    }

    /**
     * Set the tool width.
     * 
     * @param brushWidth size for the brush width.
     */
    public void setToolWidth(int brushWidth) {
        this.brushWidth = brushWidth;
    }

    /**
     * Add a new observer to ColorChooser. Selecting a color in ColorChooser will update the color in this class.
     */
    private void registerObservers() {
        ColorChooser.addObserver(new GeneralObserver() {
            @Override
            public void update() {
                color = ColorChooser.getColor();
            }
        });
    }

    @Override
    public void setFillState(boolean fillState) {
    }

    /**
     * Write a parametric spiral curve to the given layer.
     *
     * @param distalPowerFactor The scaling factor to determine the rate of change of the radius away from the origin
     * @param x_centre the x-coordinate of the centre point
     * @param y_centre the y-coordinate of the centre point
     * @param layer the layer to draw on
     */
    private void writeParametricSpiral(double distalPowerFactor, int x_centre, int y_centre, BufferedImage layer, boolean flip) {
        // take arguments for the center point
        // default to the centre of the image if one of them isn't given
        // t-max is equal to the factor root of min(width, height)

        if (distalPowerFactor <= MINIMUM_SCALING_FACTOR) {
            distalPowerFactor = MINIMUM_SCALING_FACTOR;
        }

        double t = 0;
        double x;
        double y;
        double x_prev = -1;
        double y_prev = -1;
        double radius = 0;
        double radius_max = Math.sqrt(Math.pow(layer.getHeight(), 2) + Math.pow(layer.getWidth(), 2));

        Graphics2D layerGraphics = (Graphics2D) layer.getGraphics();
        layerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layerGraphics.setColor(color);
        layerGraphics.setStroke(new BasicStroke(getToolWidth() / LINE_WIDTH_REDUCER, BasicStroke.CAP_ROUND,
                BasicStroke.CAP_BUTT));

        while (radius <= radius_max) {
            if (!flip) {
                x = Math.pow(t, distalPowerFactor) * Math.sin(t) + x_centre;
                y = Math.pow(t, distalPowerFactor) * Math.cos(t) + y_centre;
            } else {
                x = Math.pow(t, distalPowerFactor) * Math.cos(t) + x_centre;
                y = Math.pow(t, distalPowerFactor) * Math.sin(t) + y_centre;
            }

            // Initial setting for first line
            if (x_prev == -1) {
                x_prev = x;
            }
            if (y_prev == -1) {
                y_prev = y;
            }

            radius = Math.sqrt(Math.pow(x - x_centre, 2) + Math.pow(y - y_centre, 2));

            if ( (x > 0) && (x < layer.getWidth()) && (y > 0) && (y < layer.getHeight())
                    ||
                        (
                            (x > -MAXIMUM_PIXEL_MARGIN) || (y > -MAXIMUM_PIXEL_MARGIN) ||
                            (x < layer.getWidth() + MAXIMUM_PIXEL_MARGIN) || (y < layer.getHeight() + MAXIMUM_PIXEL_MARGIN)
                        )
                    ) {
                    layerGraphics.drawLine((int) x, (int) y, (int) x_prev, (int) y_prev);
            }

            t += T_VAL_INCREMENT;

            x_prev = x;
            y_prev = y;
        }
    }

}