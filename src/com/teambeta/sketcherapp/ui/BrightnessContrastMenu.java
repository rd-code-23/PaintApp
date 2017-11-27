package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;

public class BrightnessContrastMenu {
    private DrawArea drawArea;
    private static final int VERTICAL_MARGIN = 10;
    private static final int PANEL_WIDTH = 330;
    private static final int PANEL_HEIGHT = 100;
    private static final float ZERO_OFFSET = 0f;
    private static final String BRIGHTNESS_LABEL = "Multiply layer brightness:";
    private static final String CONTRAST_LABEL = "Multiply layer contrast:";
    private static final String DEFAULT_BRIGHTNESS_FACTOR = "1.0";
    private static final String DEFAULT_CONTRAST_FACTOR = "1.0";
    private static final String DIALOG_WARNING = "Only positive decimal numbers can be input";
    private static final String DIALOG_WINDOW_TITLE = "Brightness and Contrast";

    /*
        Constructor to access the drawArea
     */
    public BrightnessContrastMenu(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * Show the dialog window to confirm current layer brightness and contrast transformation
     */
    public void showWindow() {

        JPanel transformationPanel = new JPanel();
        transformationPanel.setLayout(new BoxLayout(transformationPanel, BoxLayout.Y_AXIS));
        JLabel brightnessLabel = new JLabel(BRIGHTNESS_LABEL);
        JLabel contrastLabel = new JLabel(CONTRAST_LABEL);
        JTextField brightnessInput = new JTextField(DEFAULT_BRIGHTNESS_FACTOR);
        JTextField contrastInput = new JTextField(DEFAULT_CONTRAST_FACTOR);

        transformationPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        transformationPanel.setName(DIALOG_WINDOW_TITLE);
        transformationPanel.add(brightnessLabel);
        transformationPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN / 2));
        transformationPanel.add(brightnessInput);
        transformationPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        transformationPanel.add(contrastLabel);
        transformationPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN / 2));
        transformationPanel.add(contrastInput);
        transformationPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN * 2));

        // Escape = -1, OK = 0, Cancel = 2
        int panelOptionResult = JOptionPane.showConfirmDialog(null, transformationPanel,
                DIALOG_WINDOW_TITLE, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (panelOptionResult == JOptionPane.OK_OPTION) {

            float brightnessFactor = 0f;
            float contrastFactor = 0f;

            try {
                brightnessFactor = Float.parseFloat(brightnessInput.getText());
                contrastFactor = Float.parseFloat(contrastInput.getText());

                if (brightnessFactor < 0.0f || contrastFactor < 0.0f) {
                    throw new NumberFormatException();
                }

                drawArea.rescaleOperation(contrastFactor, ZERO_OFFSET, null);
                drawArea.drawLayerBrightnessScaling(brightnessFactor);

            } catch (NumberFormatException illegalInputError) {
                JOptionPane.showMessageDialog(null, DIALOG_WARNING);
                return;
            }

        }

    }

}