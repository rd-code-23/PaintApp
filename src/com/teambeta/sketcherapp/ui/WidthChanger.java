package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;

public class WidthChanger {

    private static final int INITIAL_SLIDER_VALUE = 10;
    private static final int MINIMUM_SLIDER_VALUE = 0;
    private static final int MAXIMIM_SLIDER_VALUE = 100;
    private static final int SIZE_FONT_JLABEL = 24;

    JPanel sliderPanel = new JPanel();
    private JSlider penWidthSlider;
    private JLabel penWidthSliderLabel;
    private JTextField sizeTextField;


    public void renderPanel() {


        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));

        sliderPanel.setPreferredSize(new Dimension(10, 50));
        sliderPanel.setBackground(Color.DARK_GRAY);

        penWidthSliderLabel = new JLabel("SIZE: " + INITIAL_SLIDER_VALUE);
        penWidthSliderLabel.setForeground(Color.red);
        penWidthSliderLabel.setFont(new Font("Serif", Font.PLAIN, SIZE_FONT_JLABEL));

        sizeTextField = new JTextField("10", 3);
        sizeTextField.setPreferredSize(new Dimension(2, 2));
        //   sizeTextField.setColumns(3);
        //   sizeTextField.setBounds(10,10,10,10);


        penWidthSlider = new JSlider(JSlider.HORIZONTAL, MINIMUM_SLIDER_VALUE, MAXIMIM_SLIDER_VALUE, INITIAL_SLIDER_VALUE);
        penWidthSlider.setMinorTickSpacing(5);
        penWidthSlider.setPaintLabels(true);
        penWidthSlider.setMajorTickSpacing(20);
        penWidthSlider.setPaintTicks(true);


        sliderPanel.add(penWidthSliderLabel);
        //  sliderPanel.add(Box.createHorizontalStrut(10));
        //     sliderPanel.add(Box.createRigidArea(new Dimension(2, 0)));
        //  sliderPanel.add(sizeTextField);
        sliderPanel.add(penWidthSlider);


    }

    public JSlider getPenWidthSlider() {
        return penWidthSlider;
    }

    public void setPenWidthSlider(JSlider penWidthSlider) {
        this.penWidthSlider = penWidthSlider;
    }

    public JLabel getPenWidthSliderLabel() {
        return penWidthSliderLabel;
    }

    public void setPenWidthSliderLabel() {
        penWidthSliderLabel.setText("SIZE: " + penWidthSlider.getValue());
    }


    public int getPenWidthValue() {
        return penWidthSlider.getValue();
    }


    public JComponent getGUI() {
        return sliderPanel;

    }

    public void hidePanel(){
        sliderPanel.setVisible(false);
    }

    public void showPanel(){
        sliderPanel.setVisible(true);
    }
}
