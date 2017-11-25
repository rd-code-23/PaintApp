package com.teambeta.sketcherapp.ui;

import javax.swing.*;

public class SaturationMenu {
    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Scale the current layer saturation to what factor?\n[0.0 - Infinity]";
    private static final String DIALOG_WARNING = "That is not a valid value";
    private static final String DIALOG_WINDOW_TITLE = "Change Saturation";

    /*
        Constructor to access the drawArea
     */
    public SaturationMenu(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * Show the dialog window to confirm current layer brightness change
     */
    public void showWindow() {
        String saturationInput;
        float scaleFactor = 0f;
        saturationInput = JOptionPane.showInputDialog(null, DIALOG_MESSAGE,
                DIALOG_WINDOW_TITLE, JOptionPane.QUESTION_MESSAGE);
        if (saturationInput == null) {
            return;
        }
        try {
            scaleFactor = Float.parseFloat(saturationInput);
            if (scaleFactor < 0.0f) {
                throw new Exception();
            }
            drawArea.drawLayerSaturationScaling(scaleFactor);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, DIALOG_WARNING);
        }
    }

}