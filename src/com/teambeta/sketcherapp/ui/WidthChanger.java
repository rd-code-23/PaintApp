package com.teambeta.sketcherapp.ui;

import javax.swing.*;
import java.awt.*;

public class WidthChanger {

    private static final int INITIAL_WIDTH_VALUE = 10;
    private static final int MINIMUM_SLIDER_VALUE = 0;
    private static final int MAXIMUM_SLIDER_VALUE = 100;
    private static final int SIZE_FONT_JLABEL = 24;
    private static final String PANEL_DESCRIPTION = "Size";
    private static final int MAX_COL_JTEXTFIELD = 3;
    private static final int HEIGHT_COMPONENT = 40;
    private static final int WIDTH_PANEL_LABEL = 50;
    private static final int WIDTH_TEXTFIELD = 30;
    private static final int WIDTH_TEXTFIELD_EYEDROPPER = 150;
    private static final int TEXTFIELD_EYEDROPPER_FONTSIZE = 15;
    private static final String TEXTFIELD_EYEDROPPER_DEFAULT_STRING = "R: N/A G: N/A B: N/A";
    private static final String PANEL_EYEDROPPER = "Eye Dropper";
    private static final int PANEL_EYEDROPPER_RIGHT_MARGIN = 25;
    private static final int WIDTH_SLIDER = 450;
    private static final int HEIGHT_PANEL = 50;
    private static final int WIDTH_PANEL = 10;
    private static final int WIDTH_EXTRA_SPACE = 80;
    private static final int MINOT_TICK_SPACE_SLIDER = 10;
    private static final int MAJOR_TICK_SPACE_SLIDER = 25;
    private static final String FILL_CHECKBOX_TEXT = "Fill Shape";
    private static final String GLOBAL_WIDTH_CHECKBOX_TEXT = "Global";
    private static final String EYEDROPPER_TEXTFIELD_RED_PREFIX = "R: ";
    private static final String EYEDROPPER_TEXTFIELD_GREEN_PREFIX = "G: ";
    private static final String EYEDROPPER_TEXTFIELD_BLUE_PREFIX = "B: ";
    private static final String EYEDROPPER_TEXTFIELD_SPACE = " ";

    JPanel widthPanel = new JPanel();
    private JSlider widthSlider;          // lets user change currentWidthValue
    private JLabel panelLabel;      //used to describe the panel for the user
    private JTextField widthTextField;    //lets user change currentWidthValue
    private int currentWidthValue = 0;
    private JCheckBox globalSize;          //if global is ticked then all the tool sizes change to current size
    //if global not ticked the component size can be different for each tool
    private boolean isGlobalSize = true;

    private JCheckBox fillBox;
    private boolean isFill = false;

    private JTextField eyeDropperTextField;
    private JLabel eyeDropperTextFieldLabel;


    /**
     * sets up the panel to change width
     */
    public void renderPanel() {
        currentWidthValue = INITIAL_WIDTH_VALUE;

        widthPanel.setLayout(new BoxLayout(widthPanel, BoxLayout.X_AXIS));
        widthPanel.setPreferredSize(new Dimension(WIDTH_PANEL, HEIGHT_PANEL));
        widthPanel.setBackground(Color.DARK_GRAY);

        panelLabel = new JLabel(PANEL_DESCRIPTION);
        panelLabel.setForeground(Color.red);
        panelLabel.setFont(new Font("Serif", Font.PLAIN, SIZE_FONT_JLABEL));

        widthTextField = new JTextField("" + INITIAL_WIDTH_VALUE, MAX_COL_JTEXTFIELD);

        widthSlider = new JSlider(JSlider.HORIZONTAL, MINIMUM_SLIDER_VALUE, MAXIMUM_SLIDER_VALUE, INITIAL_WIDTH_VALUE);
        widthSlider.setMinorTickSpacing(MINOT_TICK_SPACE_SLIDER);
        widthSlider.setPaintLabels(true);
        widthSlider.setMajorTickSpacing(MAJOR_TICK_SPACE_SLIDER);
        widthSlider.setPaintTicks(true);

        panelLabel.setMaximumSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        panelLabel.setPreferredSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        panelLabel.setMinimumSize(new Dimension(WIDTH_PANEL_LABEL, HEIGHT_COMPONENT));
        widthPanel.add(Box.createRigidArea(new Dimension(WIDTH_EXTRA_SPACE, HEIGHT_COMPONENT)));
        widthPanel.add(panelLabel);

        widthTextField.setMaximumSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
        widthTextField.setPreferredSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
        widthTextField.setMinimumSize(new Dimension(WIDTH_TEXTFIELD, HEIGHT_COMPONENT));
        widthPanel.add(widthTextField);

        widthSlider.setMaximumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setPreferredSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setMinimumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthPanel.add(widthSlider);

        widthPanel.add(Box.createRigidArea(new Dimension(WIDTH_EXTRA_SPACE, HEIGHT_COMPONENT)));
        widthSlider.setMaximumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setPreferredSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        widthSlider.setMinimumSize(new Dimension(WIDTH_SLIDER, HEIGHT_COMPONENT));
        globalSize = new JCheckBox(GLOBAL_WIDTH_CHECKBOX_TEXT);
        globalSize.setSelected(true);
        widthPanel.add(globalSize);

        fillBox = new JCheckBox(FILL_CHECKBOX_TEXT);
        fillBox.setSelected(false);
        isFill = fillBox.isSelected();
        widthPanel.add(fillBox);

        widthPanel.add(Box.createRigidArea(new Dimension(WIDTH_EXTRA_SPACE, HEIGHT_COMPONENT)));
        eyeDropperTextFieldLabel = new JLabel(PANEL_EYEDROPPER);
        eyeDropperTextFieldLabel.setForeground(Color.WHITE);
        eyeDropperTextFieldLabel.setFont(new Font("serif", Font.PLAIN, SIZE_FONT_JLABEL));
        widthPanel.add(eyeDropperTextFieldLabel);

        widthPanel.add(Box.createRigidArea(new Dimension(PANEL_EYEDROPPER_RIGHT_MARGIN, HEIGHT_COMPONENT)));
        eyeDropperTextField = new JTextField(TEXTFIELD_EYEDROPPER_DEFAULT_STRING, 3);
        eyeDropperTextField.setMaximumSize(new Dimension(WIDTH_TEXTFIELD_EYEDROPPER, HEIGHT_COMPONENT));
        eyeDropperTextField.setPreferredSize(new Dimension(WIDTH_TEXTFIELD_EYEDROPPER, HEIGHT_COMPONENT));
        eyeDropperTextField.setMinimumSize(new Dimension(WIDTH_TEXTFIELD_EYEDROPPER, HEIGHT_COMPONENT));
        eyeDropperTextField.setHorizontalAlignment(JTextField.CENTER);
        eyeDropperTextField.setFont(new Font("Serif", Font.BOLD, TEXTFIELD_EYEDROPPER_FONTSIZE));
        eyeDropperTextField.setEditable(false);
        widthPanel.add(eyeDropperTextField);

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
     * @return The fill state JCheckBox
     */
    public JCheckBox getFillBox() {
        return fillBox;
    }

    /**
     * Determine if the fill state is enabled
     * @return The fill state
     */
    public boolean isFill() {
        isFill = fillBox.isSelected();
        return isFill;
    }

    /**
     * Update the fill state for supporting tools.
     * @param state The state for filling
     */
    public void setFill(boolean state) {
        isFill = state;
    }

    /**
     *
     * @return The eye dropper text field to be used by the eye dropper tool
     */
    public JTextField getEyeDropperTextField() {
        return eyeDropperTextField;
    }

    /**
     * Update the eye dropper text field with a color object.
     * @param color The color to be parsed
     */
    public void updateEyeDropperTextField(Color color) {
        eyeDropperTextField.setText(EYEDROPPER_TEXTFIELD_RED_PREFIX + color.getRed()
                                    + EYEDROPPER_TEXTFIELD_SPACE
                                    + EYEDROPPER_TEXTFIELD_GREEN_PREFIX + color.getGreen()
                                    + EYEDROPPER_TEXTFIELD_SPACE
                                    + EYEDROPPER_TEXTFIELD_BLUE_PREFIX + color.getBlue());
    }


}
