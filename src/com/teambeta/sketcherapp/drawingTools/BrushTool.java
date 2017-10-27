package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * The BrushTool class implements the drawing behavior for when the Brush tool has been selected
 */
public class BrushTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private int lastX;
    private int lastY;
    private Color color;
    private int brushWidth;
    private final static int DEFAULT_STOKE_VALUE = 10;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public BrushTool() {
        color = Color.black;
        registerObservers();
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;
        brushWidth = DEFAULT_STOKE_VALUE;
    }

    @Override
    public void onDrag(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers, BufferedImage[] layers, MouseEvent e) {
        currentX = e.getX();
        currentY = e.getY();

        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        if (selectedLayer != null) {
            selectedLayer.getBufferedImage().getGraphics().drawLine(lastX, lastY, currentX, currentY);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
            Graphics2D selectedLayerGraphics = initLayerGraphics(selectedLayer.getBufferedImage());
            selectedLayerGraphics.drawLine(lastX, lastY, currentX, currentY);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
        lastX = currentX;
        lastY = currentY;
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
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        currentX = e.getX();
        currentY = e.getY();
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        if (selectedLayer != null) {
            Graphics2D selectedLayerGraphics = initLayerGraphics(selectedLayer.getBufferedImage());
            selectedLayerGraphics.fillOval(currentX - (brushWidth / 2), currentY - (brushWidth / 2),
                    brushWidth, brushWidth);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        // Set the coordinates to the current point when the mouse is pressed.
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

    /**
     * Initialize the parameters required for layer1Graphics.
     */
    private Graphics2D initLayerGraphics(BufferedImage layer) {
        Graphics2D layerGraphics;
        layerGraphics = (Graphics2D) layer.getGraphics();
        layerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layerGraphics.setColor(color);
        layerGraphics.setStroke(new BasicStroke(getToolWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
        return layerGraphics;
    }

    @Override
    public void setFillState(boolean fillState) {
    }
}