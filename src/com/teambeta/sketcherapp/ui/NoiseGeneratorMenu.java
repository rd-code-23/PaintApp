package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.Main;
import com.teambeta.sketcherapp.ui.DrawArea;
import com.teambeta.sketcherapp.ui.MainUI;

import javax.swing.*;

public class NoiseGeneratorMenu {

    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Generate random noise on the canvas? \n\n WARNING: " +
            "This will overwrite the entire canvas";
    private static final String DIALOG_WINDOW_TITLE = "Confirm Noise";

    /*
        Constructor to access the drawArea
     */
    public NoiseGeneratorMenu(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * Show the dialog window to confirm current noise generation.
     */
    public void showWindow() {
        int dialogWindow = JOptionPane.YES_NO_OPTION;
        int dialogOption = JOptionPane.showConfirmDialog(null,
                DIALOG_MESSAGE, DIALOG_WINDOW_TITLE, dialogWindow);

        if (dialogOption == JOptionPane.YES_OPTION) {
            drawArea.colouredNoiseGenerator();
        }
    }

}