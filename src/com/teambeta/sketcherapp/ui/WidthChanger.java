package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;

public class WidthChanger {

    private static final int INITIAL_WIDTH_VALUE = 10;
    private static final int MINIMUM_SLIDER_VALUE = 0;
    private static final int MAXIMIM_SLIDER_VALUE = 100;
    private static final int SIZE_FONT_JLABEL = 24;
    private static final String PANEL_DESCRIPTION = "Size";
    private static final int MAX_COL_JTEXTFIELD = 3;
    private static final int HEIGHT_COMPONENT = 40;
    private static final int WIDTH_PANEL_LABEL = 50;
    private static final int WIDTH_TEXTFIELD = 30;
    private static final int WIDTH_SLIDER = 450;
    private static final int HEIGHT_PANEL = 50;
    private static final int WIDTH_PANEL = 10;
    private static final int WIDTH_EXTRA_SPACE = 80;
    private static final int MINOT_TICK_SPACE_SLIDER = 10;
    private static final int MAJOR_TICK_SPACE_SLIDER = 25;

    JPanel sliderPanel = new JPanel();
    private JSlider widthSlider;          // lets user change currentWidthValue
    private JLabel panelLabel;      //used to describe the panel for the user
    private JTextField widthTextField;    //lets user change currentWidthValue
    private int currentWidthValue = 0;
    private JCheckBox globalSize;          //if global is ticked then the component sizes wont change
    //if global not ticked the component size can be different for each tool
    private boolean isGlobalSize = true;


    /**
     * sets up the panel to change width
     */
    public void renderPanel() {
        currentWidthValue = INITIAL_WIDTH_VALUE;

        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.X_AXIS));
        sliderPanel.setPreferredSize(new Dimension(WIDTH_PANEL, HEIGHT_PANEL));
        sliderPanel.setBackground(Color.DARK_GRAY);

        panelLabel = new JLabel(PANEL_DESCRIPTION);
        panelLabel.setForeground(Color.red);
        panelLabel.setFont(new Font("Serif", Font.PLAIN, SIZE_FONT_JLABEL));

        widthTextField = new JTextField("" + INITIAL_WIDTH_VALUE, MAX_COL_JTEXTFIELD);

        widthSlider = new JSlider(JSlider.HORIZONTAL, MINIMUM_SLIDER_VALUE, MAXIMIM_SLIDER_VALUE, INITIAL_WIDTH_VALUE);
        widthSlider.setMinorTickSpacing(MINOT_TICK_SPACE_SLIDER);
        widthSlider.setPaintLabels(true);
        widthSlider.setMajorTickSpacing(MAJOR_TICK_SPACE_SLIDER);
        widthSlider.setPaintTicks(true);

        panelLabel.setMaximumSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        panelLabel.setPreferredSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        panelLabel.setMinimumSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        sliderPanel.add(Box.createRigidArea(new Dimension(WIDTH_EXTRA_SPACE, HEIGHT_COMPONENT)));
        sliderPanel.add(panelLabel);

        widthTextField.setMaximumSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
        widthTextField.setPreferredSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
        widthTextField.setMinimumSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
        sliderPanel.add(widthTextField);

        widthSlider.setMaximumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setPreferredSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setMinimumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        sliderPanel.add(widthSlider);

        sliderPanel.add(Box.createRigidArea(new Dimension(WIDTH_EXTRA_SPACE, HEIGHT_COMPONENT)));
        widthSlider.setMaximumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setPreferredSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setMinimumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        globalSize = new JCheckBox("Global");
        globalSize.setSelected(true);
        sliderPanel.add(globalSize);
    }

    /**
     * return JtextField holding the current width value
     */
    public JTextField getJTextFieldComponent() {
        return widthTextField;
    }

    /**
     * return the value of the JtextField holding the current width value
     */
    public int getJTextFieldValue() {
        return Integer.parseInt(widthTextField.getText());
    }

    /**
     * returns the JSlider holding current width value
     * the current slider
     */
    public JSlider getSliderComponent() {
        return widthSlider;
    }

    /**
     * Changes the value of the slider
     *
     * @param v the value of currentWidthValue
     */
    public void setSliderComponent(int v) {
        widthSlider.setValue(v);
    }

    /**
     * sets the JLabel  that is used to describe the panel for the user
     */
    public void setJLabel() {
        widthTextField.setText("" + currentWidthValue);
    }

    /**
     * checks to see if the global checkbox is ticked
     *
     * @return if size is global
     */
    public boolean isGlobalSize() {
        return isGlobalSize;
    }

    /**
     * changes the boolean value to see if user wants global sizes
     *
     * @param globalSize
     */
    public void setGlobalSize(boolean globalSize) {
        isGlobalSize = globalSize;
    }

    /**
     * return the jCheckBox that is used to let user choose global sizes or not
     *
     * @return
     */
    public JCheckBox getCheckBoxGlobalSizeComponent() {
        return globalSize;
    }

    /**
     * sets the current width or size of the tool
     *
     * @param currentWidthValue
     */

    public void setCurrentWidthValue(int currentWidthValue) {
        this.currentWidthValue = currentWidthValue;
    }

    /**
     * returns the current width or size of the tool
     *
     * @return
     */
    public int getCurrentWidthValue() {
        return widthSlider.getValue();
    }

    /**
     * returns the panel
     */
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
