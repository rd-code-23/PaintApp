package com.teambeta.sketcherapp.model;

import com.teambeta.sketcherapp.Main;
import com.teambeta.sketcherapp.ui.DrawArea;
import com.teambeta.sketcherapp.ui.MainUI;

import javax.swing.*;

public class GreyscaleMenu {

    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Convert all current canvas elements to greyscale?";
    private static final String DIALOG_WINDOW_TITLE = "Confirm Greyscale";

    /*
        Constructor to access the drawArea
     */
    public GreyscaleMenu(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * Show the dialog window to confirm current canvas greyscale conversion.
     */
    public void showWindow() {
        int dialogWindow = JOptionPane.YES_NO_OPTION;
        int dialogOption = JOptionPane.showConfirmDialog(null,
                DIALOG_MESSAGE, DIALOG_WINDOW_TITLE, dialogWindow);

        if (dialogOption == JOptionPane.YES_OPTION) {
            drawArea.redrawToGreyscale();
        }
    }

}
