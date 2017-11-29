package com.teambeta.sketcherapp;

import com.teambeta.sketcherapp.ui.BetaSplashScreen;
import com.teambeta.sketcherapp.ui.MainUI;

import javax.swing.*;

/**
 * Class to wrap all components together.
 */
public class Main {
    private static final int DEFAULT_CANVAS_WIDTH = 1600;
    private static final int DEFAULT_CANVAS_HEIGHT = 900;
    private static final int SPLASH_DISPLAY_DURATION = 5000;
    private static final String DATABASE_EXCEPTION = "The database cannot be accessed.";
    private static final String MINIMUM_REQUIREMENT_EXCEPTION = "Your computer does not meet the minimum requirements.";

    /**
     * Main program.
     *
     * @param args of user input
     */
    public static void main(String[] args) throws InterruptedException {
        new BetaSplashScreen(SPLASH_DISPLAY_DURATION);

        Thread.sleep(SPLASH_DISPLAY_DURATION);
        try {
            System.setProperty("sun.java2d.opengl", "True");
            MainUI mainUI = new MainUI(DEFAULT_CANVAS_WIDTH, DEFAULT_CANVAS_HEIGHT);
            mainUI.displayUI();
            if(!MainUI.isDatabaseGood) {
                JOptionPane.showMessageDialog(null, DATABASE_EXCEPTION);
            }
        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, DATABASE_EXCEPTION);
            System.exit(-1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, MINIMUM_REQUIREMENT_EXCEPTION);
            System.exit(-1);
        }
    }
}