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
    private final static double T_VAL_INCREMENT = 0.01;
    private final static double SCALING_FACTOR_REDUCER = 1.0/9.0;
    private final static double MINIMUM_SCALING_FACTOR = 1.025;

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
            writeParametricSpiral(scalingFactor, currentX, currentY, selectedLayer.getBufferedImage());
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
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

    public int getToolWidth() {
        return brushWidth;
    }

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
    private void writeParametricSpiral(double distalPowerFactor, int x_centre, int y_centre, BufferedImage layer) {
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
        layerGraphics.setStroke(new BasicStroke(getToolWidth() / 2f, BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));

        while (radius <= radius_max) {
            x =  Math.pow(t, distalPowerFactor) * Math.sin(t) + x_centre;
            y =  Math.pow(t, distalPowerFactor) * Math.cos(t) + y_centre;

            // Initial setting for first line
            if (x_prev == -1) {
                x_prev = x;
            }
            if (y_prev == -1) {
                y_prev = y;
            }

            // Range checking for radius computing and line drawing.
            if (! ((x < 0) && (x >= layer.getWidth()) && (y < 0) && (y >= layer.getHeight())) ) {
                try {
                    radius = Math.sqrt(Math.pow(x - x_centre, 2) + Math.pow(y - y_centre, 2));
                    layerGraphics.drawLine((int) x, (int) y, (int) x_prev, (int) y_prev);
                } catch (Exception e) {
                    // If the radius max was computed improperly because getDimension returns maximum dimensional value
                    // but the actual max dimension index is max dimension - 1
                }
            }

            t += T_VAL_INCREMENT;

            x_prev = x;
            y_prev = y;
        }
    }

}