package com.teambeta.sketcherapp.ui;

import javax.swing.*;

public class BrightnessMenu {
    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Change the brightness to what percentage?\n[0 - Infinity]";
    private static final String DIALOG_WARNING = "That is not a valid value";

    /*
        Constructor to access the drawArea
     */
    public BrightnessMenu(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * Show the dialog window to confirm current layer brightness change
     */
    public void showWindow() {
        String brightnessInput;
        float scaleFactor = 0f;
        brightnessInput = JOptionPane.showInputDialog(DIALOG_MESSAGE);
        if (brightnessInput == null) {
            return;
        }
        try {
            scaleFactor = Float.parseFloat(brightnessInput) / 100.0f;
            if (scaleFactor < 0.0f) {
                throw new Exception();
            }
            drawArea.rescaleOperation(scaleFactor, 0, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, DIALOG_WARNING);
        }
    }

}