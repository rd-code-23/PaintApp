package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;

public class HueSaturationMenu {
    private DrawArea drawArea;
    private static final int VERTICAL_MARGIN = 10;
    private static final int PANEL_WIDTH = 330;
    private static final int PANEL_HEIGHT = 100;
    private static final float ZERO_OFFSET = 0f;
    private static final String HUE_LABEL = "Multiply Layer Hue:";
    private static final String SATURATION_LABEL = "Multiply Layer Saturation:";
    private static final String DEFAULT_HUE_FACTOR = "1.0";
    private static final String DEFAULT_SATURATION_FACTOR = "1.0";
    private static final String DIALOG_WARNING = "Only positive decimal numbers can be input";
    private static final String DIALOG_WINDOW_TITLE = "Hue and Saturation";

    /*
        Constructor to access the drawArea
     */
    public HueSaturationMenu(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    /**
     * Show the dialog window to confirm current layer hue and saturation transformation
     */
    public void showWindow() {

        JPanel transformationPanel = new JPanel();
        transformationPanel.setLayout(new BoxLayout(transformationPanel, BoxLayout.Y_AXIS));
        JLabel hueLabel = new JLabel(HUE_LABEL);
        JLabel saturationLabel = new JLabel(SATURATION_LABEL);
        JTextField hueInput = new JTextField(DEFAULT_HUE_FACTOR);
        JTextField saturationInput = new JTextField(DEFAULT_SATURATION_FACTOR);

        transformationPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        transformationPanel.setName(DIALOG_WINDOW_TITLE);
        transformationPanel.add(hueLabel);
        transformationPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN / 2));
        transformationPanel.add(hueInput);
        transformationPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN));
        transformationPanel.add(saturationLabel);
        transformationPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN / 2));
        transformationPanel.add(saturationInput);
        transformationPanel.add(Box.createVerticalStrut(VERTICAL_MARGIN * 2));

        // Escape = -1, OK = 0, Cancel = 2
        int panelOptionResult = JOptionPane.showConfirmDialog(null, transformationPanel,
                DIALOG_WINDOW_TITLE, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (panelOptionResult == JOptionPane.OK_OPTION) {

            float hueFactor = 0f;
            float saturationFactor = 0f;

            try {
                hueFactor = Float.parseFloat(hueInput.getText());
                saturationFactor = Float.parseFloat(saturationInput.getText());

                if (hueFactor < 0.0f || saturationFactor < 0.0f) {
                    throw new NumberFormatException();
                }

                drawArea.drawLayerHueScaling(hueFactor);
                drawArea.drawLayerSaturationScaling(saturationFactor);

            } catch (NumberFormatException illegalInputError) {
                JOptionPane.showMessageDialog(null, DIALOG_WARNING);
                return;
            }

        }

    }

}