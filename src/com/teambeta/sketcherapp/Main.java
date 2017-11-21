package com.teambeta.sketcherapp;

import com.teambeta.sketcherapp.ui.MainUI;

/**
 * Class to wrap all components together.
 */
public class Main {
    private static final int DEFAULT_CANVAS_WIDTH = 1600;
    private static final int DEFAULT_CANVAS_HEIGHT = 900;
    /**
     * Main program.
     *
     * @param args of user input
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        MainUI mainUI = new MainUI(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT);
        mainUI.displayUI();
    }
}