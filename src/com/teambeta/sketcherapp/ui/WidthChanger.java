package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Class that changes width of tools.
 */
public class WidthChanger {
    private static final int INITIAL_WIDTH_VALUE = 10;
    private static final int MINIMUM_SLIDER_VALUE = 0;
    private static final int MAXIMUM_SLIDER_VALUE = 1000;
    private static final int SIZE_FONT_JLABEL = 16;
    private static final String PANEL_DESCRIPTION = "Size";
    private static final int MAX_COL_JTEXTFIELD = 3;
    private static final int HEIGHT_COMPONENT = 40;
    private static final int WIDTH_PANEL_LABEL = 50;
    private static final int WIDTH_TEXTFIELD = 30;
    private static final int WIDTH_SLIDER = 450;
    private static final int HEIGHT_PANEL = 300;
    private static final int WIDTH_PANEL = 50;
    private static final String FILL_CHECKBOX_TEXT = "Fill Shape";
    private static final String GLOBAL_WIDTH_CHECKBOX_TEXT = "Global";
    private static final String FONT_TYPE = "Arial";
    private static final String CUSTOM_DARK_GREY = "#343434";
    private static final int MINOR_TICK_SPACING = 100;
    private static final int TEXT_PANEL_SPACING = 225;

    private JPanel textPanel = new JPanel();
    private JPanel widthPanel = new JPanel();
    private JSlider widthSlider;          // lets user change currentWidthValue
    private JLabel panelLabel;      //used to describe the panel for the user
    private JTextField widthTextField;    //lets user change currentWidthValue
    private int currentWidthValue = 0;
    private JCheckBox globalSize;          //if global is ticked then all the tool sizes change to current size
    //if global not ticked the component size can be different for each tool
    private boolean isGlobalSize = true;

    private JCheckBox fillBox;
    private boolean isFill = false;

    /**
     * Constructor.
     */
    public WidthChanger() {
        renderPanel();
    }

    /**
     * Sets up the panel to change width.
     */
    private void renderPanel() {
        currentWidthValue = INITIAL_WIDTH_VALUE;
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.X_AXIS));
        textPanel.setBackground(Color.DARK_GRAY);

        buildTextField();
        buildCheckBoxes();

        textPanel.add(panelLabel);
        textPanel.add(widthTextField);
        textPanel.add(Box.createRigidArea(new Dimension(TEXT_PANEL_SPACING, 0)));
        textPanel.add(globalSize);
        textPanel.add(fillBox);

        buildWidthSlider();

        widthPanel.setLayout(new BoxLayout(widthPanel, BoxLayout.Y_AXIS));
        widthPanel.setBackground(Color.DARK_GRAY);

        textPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        widthSlider.setAlignmentX(Component.LEFT_ALIGNMENT);
        widthPanel.add(textPanel);
        widthPanel.add(widthSlider);
    }

    /**
     * Build width slider.
     */
    private void buildWidthSlider() {
        widthSlider = new JSlider(JSlider.HORIZONTAL, MINIMUM_SLIDER_VALUE, MAXIMUM_SLIDER_VALUE, INITIAL_WIDTH_VALUE);
        widthSlider.setBackground(Color.DARK_GRAY);
        widthSlider.setMinorTickSpacing(MINOR_TICK_SPACING);
        widthSlider.setPaintTicks(true);
        widthSlider.setMaximumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setPreferredSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setMinimumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
    }

    /**
     * Build check boxes.
     */
    private void buildCheckBoxes() {
        Font checkBoxFont = new Font(FONT_TYPE, Font.PLAIN,SIZE_FONT_JLABEL);
        globalSize = new JCheckBox(GLOBAL_WIDTH_CHECKBOX_TEXT);
        globalSize.setBackground(Color.DARK_GRAY);
        globalSize.setForeground(Color.WHITE);
        globalSize.setSelected(true);
        globalSize.setFont(checkBoxFont);
        globalSize.setFocusPainted(false);

        fillBox = new JCheckBox(FILL_CHECKBOX_TEXT);
        fillBox.setBackground(Color.DARK_GRAY);
        fillBox.setForeground(Color.WHITE);
        fillBox.setSelected(false);
        fillBox.setFont(checkBoxFont);
        fillBox.setFocusPainted(false);
        isFill = fillBox.isSelected();
    }

    /**
     * Build text field.
     */
    private void buildTextField() {
        panelLabel = new JLabel(PANEL_DESCRIPTION);
        panelLabel.setForeground(Color.WHITE);
        panelLabel.setFont(new Font(FONT_TYPE, Font.PLAIN, SIZE_FONT_JLABEL));
        panelLabel.setMaximumSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        panelLabel.setPreferredSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        panelLabel.setMinimumSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        widthTextField = new JTextField("" + INITIAL_WIDTH_VALUE, MAX_COL_JTEXTFIELD);
        widthTextField.setBackground(Color.decode(CUSTOM_DARK_GREY));
        widthTextField.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        widthTextField.setForeground(Color.WHITE);
        widthTextField.setMaximumSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
        widthTextField.setPreferredSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
        widthTextField.setMinimumSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
    }

    /**
     * return JTextField holding the current width value
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
        return widthPanel;
    }

    public void hidePanel() {
        widthPanel.setVisible(false);
    }

    public void showPanel() {
        widthPanel.setVisible(true);
    }

    /**
     * Get the fill state checkbox for manipulations.
     *
     * @return The fill state JCheckBox
     */
    public JCheckBox getFillBox() {
        return fillBox;
    }

    /**
     * Determine if the fill state is enabled
     *
     * @return The fill state
     */
    public boolean isFill() {
        isFill = fillBox.isSelected();
        return isFill;
    }

    /**
     * Update the fill state for supporting tools.
     *
     * @param state The state for filling
     */
    public void setFill(boolean state) {
        isFill = state;
    }
}