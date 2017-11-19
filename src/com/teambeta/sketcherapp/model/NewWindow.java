package com.teambeta.sketcherapp.model;

import javax.swing.JOptionPane;
import java.awt.*;

/**
 * This class is called when the user presses file > new, it asks the user for the canvas size
 * and will open a new window with that canvas size
 */
public class NewWindow {
    private static final String LOW_RES_FOUR_THREE = "4:3 :       800 x 600";
    private static final String HD = "HD:        1280 x 720";
    private static final String MID_RES_FOUR_THREE = "4:3 :       1600 x 1200";
    private static final String FULL_HD = "Full HD: 1920 x 1080";
    private static final String HIGH_RES_FOUR_THREE = "4:3 :       2048 x 1536";
    private static final String QHD = "QHD:      2560 x 1440";
    private static final String UHD = "UHD:      3840 x 2160";
    private static final String SELECT_MESSAGE = "Select the dimensions of the canvas (pixels)\n\n";
    private static final String WINDOW_TITLE = "Canvas Dimensions";

    /**
     * This method will ask the user what size they want the canvas to be
     */
    public static Dimension displayPrompt() {

        Object[] combinations = {
                LOW_RES_FOUR_THREE, HD, MID_RES_FOUR_THREE, FULL_HD, HIGH_RES_FOUR_THREE, QHD, UHD};

        String choice = (String) JOptionPane.showInputDialog(null,
                SELECT_MESSAGE, WINDOW_TITLE,
                JOptionPane.PLAIN_MESSAGE, null, combinations, FULL_HD);

        if (choice != null) {
            switch (choice) {
                case LOW_RES_FOUR_THREE:
                    return new Dimension(800, 600);
                case HD:
                    return new Dimension(1280, 720);
                case MID_RES_FOUR_THREE:
                    return new Dimension(1600, 1200);
                case FULL_HD:
                    return new Dimension(1920, 1080);
                case HIGH_RES_FOUR_THREE:
                    return new Dimension(2049, 1536);
                case QHD:
                    return new Dimension(2049, 1536);
                case UHD:
                    return new Dimension(3840, 2160);
            }
        }
        return null;
    }
}
