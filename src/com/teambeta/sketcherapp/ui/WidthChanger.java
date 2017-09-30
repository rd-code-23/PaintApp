package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;

public class WidthChanger {

    private static final int INITIAL_SLIDER_VALUE = 10;
    private static final int MINIMUM_SLIDER_VALUE = 0;
    private static final int MAXIMIM_SLIDER_VALUE = 100;
    private static final int SIZE_FONT_JLABEL = 24;

    JPanel sliderPanel = new JPanel();
    private JSlider widthSlider;
    private JLabel widthSliderLabel;
    private JTextField widthTextField;
    int currentWidthValue = 0;


    /**
     * sets up the panel to change width
     */
    public void renderPanel() {

        currentWidthValue = INITIAL_SLIDER_VALUE;
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));

        sliderPanel.setPreferredSize(new Dimension(10, 50));
        sliderPanel.setBackground(Color.DARK_GRAY);

        widthSliderLabel = new JLabel("Size");
        widthSliderLabel.setForeground(Color.red);
        widthSliderLabel.setFont(new Font("Serif", Font.PLAIN, SIZE_FONT_JLABEL));

        widthTextField = new JTextField("10", 3);

        widthSlider = new JSlider(JSlider.HORIZONTAL, MINIMUM_SLIDER_VALUE, MAXIMIM_SLIDER_VALUE, INITIAL_SLIDER_VALUE);
        widthSlider.setMinorTickSpacing(10);
        widthSlider.setPaintLabels(true);
        widthSlider.setMajorTickSpacing(25);
        widthSlider.setPaintTicks(true);

        widthSliderLabel.setMaximumSize(new Dimension(50, 40));
        widthSliderLabel.setPreferredSize(new Dimension(50, 40));
        widthSliderLabel.setMinimumSize(new Dimension(50, 40));
        sliderPanel.add(Box.createRigidArea(new Dimension(80, 40)));
        sliderPanel.add(widthSliderLabel);

        widthTextField.setMaximumSize(new Dimension(30, 40));
        widthTextField.setPreferredSize(new Dimension(30, 40));
        widthTextField.setMinimumSize(new Dimension(30, 40));
        sliderPanel.add(widthTextField);

        widthSlider.setMaximumSize(new Dimension(450, 40));
        widthSlider.setPreferredSize(new Dimension(450, 40));
        widthSlider.setMinimumSize(new Dimension(450, 40));
        sliderPanel.add(widthSlider);


    }

    public JTextField getWidthTextField() {
        return widthTextField;
    }

    public int getTextFieldValue() {

        return Integer.parseInt(widthTextField.getText());

    }

    public void setSizeTextField() {
        widthSlider.setValue(currentWidthValue);
    }


    public JSlider getWidthSlider() {
        return widthSlider;
    }


    public void changePenWidthSlider(int v) {
        widthSlider.setValue(v);
    }


    public void setPenWidthSliderLabel() {
        widthTextField.setText("" + currentWidthValue);
    }

    public void setCurrentWidthValue(int currentWidthValue) {
        this.currentWidthValue = currentWidthValue;
    }

    public int getCurrentWidthValue() {
        return widthSlider.getValue();
    }


    public JComponent getGUI() {
        return sliderPanel;

    }

    public void hidePanel() {
        sliderPanel.setVisible(false);
    }

    public void showPanel() {
        sliderPanel.setVisible(true);
    }
}
