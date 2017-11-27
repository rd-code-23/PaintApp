package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.MainUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * The EyeDropperTool class implements the drawing behavior for when the Eye Dropper tool has been selected
 */
public class EyeDropperTool extends DrawingTool {
    /**
     * Constructor.
     */
    public EyeDropperTool() {
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void onDrag(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onRelease(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onClick(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public void onMove(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {

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

    //TODO: Refactor static reference to a better handler.
    @Override
    public void onPress(BufferedImage canvas, MouseEvent e,
                        LinkedList<ImageLayer> drawingLayers) {
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        Color colorAtPoint = null; // Pull from layer 0 by default.
        if (selectedLayer != null) {
            colorAtPoint = new Color(selectedLayer.getBufferedImage()
                    .getRGB(e.getX(), e.getY()));
            MainUI.getColorChooser().setColorFromEyeDropper(colorAtPoint); // Update to the color chooser.
        }
    }

    @Override
    public int getToolWidth() {
        return 0;
    }

    @Override
    public void setToolWidth(int width) {
    }

    @Override
    public void setFillState(boolean fillState) {
    }
}