package com.teambeta.sketcherapp;

import com.teambeta.sketcherapp.ui.MainUI;

/**
 * Class to wrap all components together.
 */
public class Main {

    /**
     * Main program.
     *
     * @param args of user input
     */
    public static void main(String[] args) {
        System.setProperty("sun.java2d.opengl", "True");
        MainUI mainUI = new MainUI();
        mainUI.showGridBagLayoutDemo();
    }
}
