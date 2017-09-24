package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


/**
 * Main UI class to wrap all GUI elements together.
 */
public class MainUI {
    private static final String CLEAR_BUTTON_TEXT = "Clear";
    private static final String BLACK_BUTTON_TEXT = "Pen";
    private JFrame mainFrame;

    private static final String APPLICATION_NAME = "Beta Sketcher";
    private static final int APPLICATION_WIDTH = 1920;
    private static final int APPLICATION_HEIGHT = 1080;

    private JButton clearButton;
    private JButton blackButton;
    private DrawArea drawArea;
    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                drawArea.clear();
            } else if (e.getSource() == blackButton) {
                drawArea.black();
            }
        }
    };

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
        Container mainContent = mainFrame.getContentPane();
        mainContent.setLayout(new BorderLayout());

        mainFrame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        mainFrame.getContentPane().setBackground(Color.DARK_GRAY);

        mainFrame.setLocationByPlatform(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        drawArea = new DrawArea();
        // ideally this should be in its own panel with a proper scale, not directly to mainContent
        mainContent.add(drawArea, BorderLayout.CENTER);

        clearButton = new JButton(CLEAR_BUTTON_TEXT);
        clearButton.addActionListener(actionListener);
        blackButton = new JButton(BLACK_BUTTON_TEXT);
        blackButton.addActionListener(actionListener);

        JPanel canvasTools = new JPanel();
        canvasTools.setBackground(Color.DARK_GRAY);
        canvasTools.add(clearButton);
        canvasTools.add(blackButton);

        mainContent.add(canvasTools, BorderLayout.WEST);
    }

    /**
     * Display GUI.
     */
    public void showGridBagLayoutDemo() {
        mainFrame.setLocationRelativeTo(null);  // positions GUI in center when opened
        mainFrame.setVisible(true);
    }
}
