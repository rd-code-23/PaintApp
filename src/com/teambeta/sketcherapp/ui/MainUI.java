package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;


/**
 * Main UI class to wrap all GUI elements together.
 */
public class MainUI {
    private JFrame mainFrame;

    private static final String APPLICATION_NAME = "Beta Sketcher";
    private static final int APPLICATION_WIDTH = 1800;
    private static final int APPLICATION_HEIGHT = 1000;

    /**
     * Constructor.
     */
    public MainUI() {
        prepareGUI();
    }

    /**
     * Build main GUI.
     */
    private void prepareGUI() {
        mainFrame = new JFrame(APPLICATION_NAME);
        mainFrame.setLayout(new BorderLayout());
        mainFrame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        mainFrame.getContentPane().setBackground(Color.DARK_GRAY);

        mainFrame.setLocationByPlatform(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /* Test label. String literal here should be extracted as a constant! */
        JLabel label = new JLabel("This is a basic GUI!", JLabel.CENTER);
        label.setForeground(Color.WHITE);

        mainFrame.add(label);
    }

    /**
     * Display GUI.
     */
    public void showGridBagLayoutDemo() {
        mainFrame.setLocationRelativeTo(null);  // positions GUI in center when opened
        mainFrame.setVisible(true);
    }
}