package com.teambeta.sketcherapp.ui;

import javax.swing.*;

public class CheckerboardMenu {

    private DrawArea drawArea;
    private static final String DIALOG_MESSAGE = "Draw checkerboard pattern?\n\n" +
            "This will overwrite the entire canvas";
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

        if (dialogOption == JOptionPane.YES_OPTION) {
            try {
                int horizontalCount = Integer.parseInt(JOptionPane.showInputDialog(DIALOG_HORIZONTAL));
                int verticalCount = Integer.parseInt(JOptionPane.showInputDialog(DIALOG_VERTICAL));
                drawArea.drawCheckerPattern(horizontalCount, verticalCount);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, DIALOG_WARNING);
            }
        }

    }

}
