package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * The FanTool class implements the drawing behavior for when the Fan tool has been selected
 * <p>
 * NOTE: For nice looking "3D", draw using line width of 0.
 */
public class FanTool extends DrawingTool {
    private int x_start;
    private int y_start;
    private int x_current;
    private int y_current;
    private int lineWidth;
    private final int DEFAULT_STOKE_VALUE = 10;
    private Color lineColor;

    /**
     * Constructor.
     */
    public FanTool() {
        lineColor = Color.BLACK;
        x_start = 0;
        y_start = 0;
        x_current = 0;
        y_current = 0;
        lineWidth = DEFAULT_STOKE_VALUE;
        registerObservers();
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
    public Color getColor() {
        return ColorChooser.getColor();
    }

    @Override
    public void onDrag(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        x_current = e.getX();
        y_current = e.getY();
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        if (selectedLayer != null) {
            Graphics2D selectedLayerGraphics = initLayerGraphics(selectedLayer.getBufferedImage());
            selectedLayerGraphics.drawLine(x_start, y_start, x_current, y_current);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
    }

    @Override
    public void onRelease(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onClick(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onPress(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        x_start = e.getX();
        y_start = e.getY();
    }

    @Override
    public int getToolWidth() {
        return lineWidth;
    }

    @Override
    public void setToolWidth(int width) {
        lineWidth = width;
    }

    @Override
    public void setFillState(boolean fillState) {
    }

    /**
     * Add a new observer to ColorChooser. Selecting a color in ColorChooser will update the color in this class
     */
    private void registerObservers() {
        ColorChooser.addObserver(new GeneralObserver() {
            @Override
            public void update() {
                lineColor = ColorChooser.getColor();
            }
        });
    }

    /**
     * Initialize the parameters required for layer1Graphics.
     */
    private Graphics2D initLayerGraphics(BufferedImage layer) {
        Graphics2D layerGraphics = (Graphics2D) layer.getGraphics();
        layerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layerGraphics.setColor(lineColor);
        layerGraphics.setStroke(new BasicStroke(getToolWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
        return layerGraphics;
    }
}