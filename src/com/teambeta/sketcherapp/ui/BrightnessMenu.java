package com.teambeta.sketcherapp.ui;

import javax.swing.*;

public class BrightnessMenu {
    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Change the brightness by what percentage?\n(Numbers only)";
    private static final String DIALOG_WARNING = "That is not a valid value";
    private static final String DIALOG_WINDOW_TITLE = "Change Brightness";

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
            drawArea.rescaleOperation(scaleFactor, 0, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, DIALOG_WARNING);
        }
    }

}