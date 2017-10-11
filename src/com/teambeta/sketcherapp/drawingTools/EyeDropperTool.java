package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.ui.MainUI;
import com.teambeta.sketcherapp.ui.WidthChanger;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The EyeDropperTool class implements the drawing behavior for when the Eye Dropper tool has been selected
 */
public class EyeDropperTool extends DrawingTool {

    // Directly update to width changer MainUI object upon mouse press.
    private WidthChanger toolWidthChanger;

    /**
     * Constructor.
     * 
     * @param widthChanger to change the width of the tool.
     */
    public EyeDropperTool(WidthChanger widthChanger) {
        toolWidthChanger = widthChanger;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        Color colorAtPoint = new Color(layers[0].getRGB(e.getX(), e.getY())); // Pull from layer 0 by default.
        toolWidthChanger.updateEyeDropperTextField(colorAtPoint); // Update directly to UI element.

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
