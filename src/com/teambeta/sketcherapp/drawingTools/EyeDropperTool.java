package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.MainUI;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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
    public void onDrag(BufferedImage canvas, ImageLayer currentlySelectedLayer, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, ImageLayer currentlySelectedLayer) {

    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, ImageLayer currentlySelectedLayer) {

    }

    //TODO: Refactor static reference to a better handler.
    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e, ImageLayer currentlySelectedLayer) {
        Color colorAtPoint = new Color(layers[0].getRGB(e.getX(), e.getY())); // Pull from layer 0 by default.
        MainUI.getWidthChanger().updateEyeDropperTextField(colorAtPoint); // Update directly to UI element.
        MainUI.getColorChooser().setColorFromEyeDropper(colorAtPoint); // Update to the color chooser.
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
