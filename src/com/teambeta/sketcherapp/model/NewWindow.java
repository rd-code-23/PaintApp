package com.teambeta.sketcherapp.model;

import com.teambeta.sketcherapp.ui.MainUI;

import javax.swing.JOptionPane;

/**
 * This class is called when the user presses file > new, it asks the user for the canvas size
 * and will open a new window with that canvas size
 */
public class NewWindow {
    /**
     * This method will ask the user what size they want the canvas to be
     */
    public static void displayPrompt() {

        Object[] combinations = {
                "Select a canvas size",
                "4:3 :       800 x 600",
                "HD:        1280 x 720",
                "4:3 :       1600 x 1200",
                "Full HD: 1920 x 1080",
                "4:3 :       2048 x 1536",
                "QHD:      2560 x 1440",
                "UHD:      3840 x 2160"};

        String choice = (String) JOptionPane.showInputDialog(null,
                "Choose the resolution of the canvas\n", "Canvas resolution",
                JOptionPane.PLAIN_MESSAGE, null, combinations, "Select a canvas size");

        if (choice != null) {
            if (choice == "4:3 :       800 x 600") {
                MainUI.CANVAS_HEIGHT = 600;
                MainUI.CANVAS_WIDTH = 800;
            } else if (choice == "HD:        1280 x 720") {
                MainUI.CANVAS_HEIGHT = 720;
                MainUI.CANVAS_WIDTH = 1280;
            } else if (choice == "4:3 :       1600 x 1200") {
                MainUI.CANVAS_HEIGHT = 1200;
                MainUI.CANVAS_WIDTH = 1600;
            } else if (choice == "Full HD: 1920 x 1080") {
                MainUI.CANVAS_HEIGHT = 1080;
                MainUI.CANVAS_WIDTH = 1920;
            } else if (choice == "4:3 :       2048 x 1536") {
                MainUI.CANVAS_HEIGHT = 1536;
                MainUI.CANVAS_WIDTH = 2048;
            } else if (choice == "QHD:      2560 x 1440") {
                MainUI.CANVAS_HEIGHT = 1440;
                MainUI.CANVAS_WIDTH = 2560;
            } else if (choice == "UHD:      3840 x 2160") {
                MainUI.CANVAS_HEIGHT = 2160;
                MainUI.CANVAS_WIDTH = 3840;
            } else if (choice == "Select a canvas size") {
                JOptionPane.showOptionDialog(null, "Please select an option",
                        "Invalid selection", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        null, null);
            }
        }
    }

    public static void NewWindow() {
/**
 * I know, this is that same as the displayPropmt method, but I didn't know how to have the bottom bit there for this
 * class but have it not be there when it is used while we don't want a new window to open.
 */
        Object[] combinations = {
                "Select a canvas size",
                "4:3 :       800 x 600",
                "HD:        1280 x 720",
                "4:3 :       1600 x 1200",
                "Full HD: 1920 x 1080",
                "4:3 :       2048 x 1536",
                "QHD:      2560 x 1440",
                "UHD:      3840 x 2160"};

        String choice = (String) JOptionPane.showInputDialog(null,
                "Choose the resolution of the canvas\n", "Canvas resolution",
                JOptionPane.PLAIN_MESSAGE, null, combinations, "Select a canvas size");
/**
 * this loop will continue to ask the user to select a canvas size until they do
 */
        while (choice == "Select a canvas size") {
    JOptionPane.showOptionDialog(null, "Please select an option",
            "Invalid selection", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
            null, null);

    choice = (String) JOptionPane.showInputDialog(null,
                    "Choose the resolution of the canvas\n", "Canvas resolution",
                    JOptionPane.PLAIN_MESSAGE, null, combinations, "Select a canvas size");

}

    if (choice != null) {
            if (choice == "4:3 :       800 x 600") {
                MainUI.CANVAS_HEIGHT = 600;
                MainUI.CANVAS_WIDTH = 800;
            } else if (choice == "HD:        1280 x 720") {
                MainUI.CANVAS_HEIGHT = 720;
                MainUI.CANVAS_WIDTH = 1280;
            } else if (choice == "4:3 :       1600 x 1200") {
                MainUI.CANVAS_HEIGHT = 1200;
                MainUI.CANVAS_WIDTH = 1600;
            } else if (choice == "Full HD: 1920 x 1080") {
                MainUI.CANVAS_HEIGHT = 1080;
                MainUI.CANVAS_WIDTH = 1920;
            } else if (choice == "4:3 :       2048 x 1536") {
                MainUI.CANVAS_HEIGHT = 1536;
                MainUI.CANVAS_WIDTH = 2048;
            } else if (choice == "QHD:      2560 x 1440") {
                MainUI.CANVAS_HEIGHT = 1440;
                MainUI.CANVAS_WIDTH = 2560;
            } else if (choice == "UHD:      3840 x 2160") {
                MainUI.CANVAS_HEIGHT = 2160;
                MainUI.CANVAS_WIDTH = 3840;
            } else if (choice == "Select a canvas size") {
                JOptionPane.showOptionDialog(null, "Please select an option",
                        "Invalid selection", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null,
                        null, null);
            }

            System.setProperty("sun.java2d.opengl", "True");
            MainUI mainUI = new MainUI();
            mainUI.showGridBagLayoutDemo();
        }

    }

}
