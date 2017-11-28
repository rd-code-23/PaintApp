package com.teambeta.sketcherapp.ui;

import javax.swing.*;

public class InvertMenu {
    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Invert all colours on the current layer?";
    private static final String DIALOG_WINDOW_TITLE = "Confirm Inversion";

    /*
        Constructor to access the drawArea
     */
    public InvertMenu(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * Show the dialog window to confirm current layer colour inversion.
     */
    public void showWindow() {
        int dialogWindow = JOptionPane.YES_NO_OPTION;
        int dialogOption = JOptionPane.showConfirmDialog(null,
                DIALOG_MESSAGE, DIALOG_WINDOW_TITLE, dialogWindow);
        if (dialogOption == JOptionPane.YES_OPTION) {
            drawArea.drawInvertCurrentLayerColours();
        }
    }
}