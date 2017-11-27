package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.DrawArea;
import com.teambeta.sketcherapp.ui.MainUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * NOTE:
 * The DrawArea class object must be passed into the tool to receive the background colour.
 * <p>
 * The EraserTool class implements the drawing behavior for when the Eraser tool has been selected
 */
public class EraserTool extends DrawingTool {
    private int currentX;
    private int currentY;
    private int lastX;
    private int lastY;
    private Color color;
    private static final Color transparentColor = new Color(0x00FFFFFF, true);
    private int eraserWidth;
    private Composite originalComposite;
    private final int DEFAULT_WIDTH_VALUE = 10;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public EraserTool() {
        eraserWidth = DEFAULT_WIDTH_VALUE;
        currentX = 0;
        currentY = 0;
        lastX = 0;
        lastX = 0;
        color = Color.white; // Default color until MainUI updates to the proper color.
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
    public void onDrag(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        //draw a path that follows your mouse while the mouse is being dragged
        currentX = e.getX();
        currentY = e.getY();
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        if (selectedLayer != null) {
            Graphics2D layerGraphics = initLayerGraphics(selectedLayer.getBufferedImage());
            layerGraphics.drawLine(lastX, lastY, currentX, currentY);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public void onRelease(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        Graphics2D graphics = (Graphics2D) canvas.getGraphics();
        graphics.setComposite(originalComposite);
    }

    @Override
    public void onClick(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        currentX = e.getX();
        currentY = e.getY();
    }

    @Override
    public void onMove(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {

    }

    @Override
    public void onPress(BufferedImage canvas, MouseEvent e,
                        LinkedList<ImageLayer> drawingLayers) {
        //set the coordinates to the current point when the mouse is pressed
        currentX = e.getX();
        currentY = e.getY();
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        if (selectedLayer != null) {
            Graphics2D layerGraphics = initLayerGraphics(selectedLayer.getBufferedImage());
            layerGraphics.fillOval(currentX - (eraserWidth / 2),
                    currentY - (eraserWidth / 2), eraserWidth, eraserWidth);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
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
     */
    private Graphics2D initLayerGraphics(BufferedImage layer) {
        Graphics2D layerGraphics = (Graphics2D) layer.getGraphics();
        originalComposite = layerGraphics.getComposite();
        layerGraphics.setComposite(AlphaComposite.Clear);
        layerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layerGraphics.setColor(transparentColor);
        layerGraphics.setStroke(new BasicStroke(getToolWidth(), BasicStroke.CAP_ROUND,    // End-cap style
                BasicStroke.CAP_BUTT));
        return layerGraphics;
    }

    public void setFillState(boolean fillState) {
    }
}