package com.teambeta.sketcherapp.ui;

import javax.swing.*;

/**
 * Generate a checkerboard pattern on canvas.
 */
public class CheckerboardMenu {
    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Draw checkerboard pattern?\n" +
            "This will overwrite the selected layer";
    private static final String DIALOG_WINDOW_TITLE = "Confirm Checkerboard";
    private static final String DIALOG_HORIZONTAL = "Horizontal amount";
    private static final String DIALOG_VERTICAL = "Vertical amount";
    private static final String DIALOG_WARNING = "You cannot input a non-integer";

    /*
        Constructor to access the drawArea
     */
    public CheckerboardMenu(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * Show the dialog window to confirm current canvas greyscale conversion.
     */
    public void showWindow() {
        int dialogWindow = JOptionPane.YES_NO_OPTION;
        int dialogOption = JOptionPane.showConfirmDialog(null,
                DIALOG_MESSAGE, DIALOG_WINDOW_TITLE, dialogWindow);
        String horizontalInput;
        String verticalInput;

        int horizontalCount = 0;
        int verticalCount = 0;

        if (dialogOption == JOptionPane.YES_OPTION) {

            horizontalInput = JOptionPane.showInputDialog(DIALOG_HORIZONTAL);
            if (horizontalInput == null) {
                return; // Explicit quit on cancel or escape operation
            }
            try {
                horizontalCount = Integer.parseInt(horizontalInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, DIALOG_WARNING);
                return; // Explicit quit
            }

            verticalInput = JOptionPane.showInputDialog(DIALOG_VERTICAL);
            if (verticalInput == null) {
                return; // Explicit quit on cancel or escape operation
            }
            try {
                verticalCount = Integer.parseInt(verticalInput);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, DIALOG_WARNING);
                return; // Explicit quit
            }

            if (horizontalCount > 0 && verticalCount > 0) {
                drawArea.drawCheckerPattern(horizontalCount, verticalCount);
            }

        }
    }
}