package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;

public class BrightnessMenu {
    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Scale the current layer brightness to what factor?\n[0.0 - Infinity]";
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
        brightnessInput = JOptionPane.showInputDialog(null, DIALOG_MESSAGE,
                DIALOG_WINDOW_TITLE, JOptionPane.QUESTION_MESSAGE);
        if (brightnessInput == null) {
            return;
        }
        try {
            scaleFactor = Float.parseFloat(brightnessInput);
            if (scaleFactor < 0.0f) {
                throw new Exception();
            }
            drawArea.rescaleOperation(scaleFactor, 0, null);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, DIALOG_WARNING);
        }
    }

}