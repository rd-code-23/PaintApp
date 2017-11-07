package com.teambeta.sketcherapp.drawingTools;

import javax.swing.*;
import javax.swing.text.NumberFormatter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

/**
 * Text settings including font, and other encryption options.
 */
public class TextToolSettings extends JPanel {
    private JPanel transformPanel;
    private JLabel encryptionLabel;
    private JLabel morseCodeLabel;
    private JLabel caesarLabel;
    private JLabel caesarShiftLabel;
    private JCheckBox morseCodeSelector;
    private JCheckBox caesarSelector;
    private JFormattedTextField caesarShiftField;
    private NumberFormat integerFormat;
    private NumberFormatter integerFormatter;
    private JComboBox<String> fontSelector;

    private static final int COMPONENT_SPACING = 20;
    private static final int TRANSFORM_PANEL_HEIGHT = 40;
    private static final int HEADER_FONT_SIZE = 16;
    private static final int FONT_SIZE = 12;
    private static final int ELEMENT_SPACING = 10;
    private static final int NUMBER_FIELD_WIDTH = 40;
    private static final int NUMBER_FIELD_HEIGHT = 25;
    private static final int DEFAULT_SHIFT_VALUE = 0;
    private static final int MAX_SHIFT_VALUE = 100;
    private static final int MIN_SHIFT_VALUE = -100;
    private static final String DEFAULT_FONT = "Arial";
    private static final String MORSE_CODE_LABEL = "Morse Code";
    private static final String CAESAR_LABEL = "Caesar";
    private static final String NUMBER_LABEL = "Caesar Shift";
    private static final String TRANSFORM_LABEL = "Transform Text";

    /**
     * Constructor.
     */
    public TextToolSettings() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        setBackground(Color.DARK_GRAY);

        transformPanel = new JPanel();
        encryptionLabel = new JLabel(TRANSFORM_LABEL);
        morseCodeLabel = new JLabel(MORSE_CODE_LABEL);
        caesarLabel = new JLabel(CAESAR_LABEL);
        caesarShiftLabel = new JLabel(NUMBER_LABEL);
        encryptionLabel.setForeground(Color.WHITE);
        morseCodeLabel.setForeground(Color.WHITE);
        caesarLabel.setForeground(Color.WHITE);
        caesarShiftLabel.setForeground(Color.WHITE);

        morseCodeSelector = new JCheckBox();
        caesarSelector = new JCheckBox();

        integerFormat = NumberFormat.getIntegerInstance();
        integerFormatter = new NumberFormatter(integerFormat);
        integerFormatter.setAllowsInvalid(false);
        integerFormatter.setValueClass(Integer.class);
        integerFormatter.setCommitsOnValidEdit(true);
        integerFormatter.setMaximum(MAX_SHIFT_VALUE);
        integerFormatter.setMinimum(MIN_SHIFT_VALUE);

        caesarShiftField = new JFormattedTextField(integerFormatter);
        caesarShiftField.setPreferredSize(new Dimension(NUMBER_FIELD_WIDTH, NUMBER_FIELD_HEIGHT));
        caesarShiftField.setMaximumSize(new Dimension(NUMBER_FIELD_WIDTH, NUMBER_FIELD_HEIGHT));
        caesarShiftField.setValue(new Integer(DEFAULT_SHIFT_VALUE));
        morseCodeSelector.setBackground(Color.DARK_GRAY);
        caesarSelector.setBackground(Color.DARK_GRAY);

        fontSelector = new JComboBox<>(fonts);
        fontSelector.setSelectedItem(DEFAULT_FONT);

        enableCaesarShiftField(false);

        setLabelFonts();
        setVisibility(false);

        add(encryptionLabel);
        add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));

        buildTransformPanel();

        add(transformPanel);
        add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
        add(fontSelector);

    }

    /**
     * Helper function to build transform panel and its components.
     */
    private void buildTransformPanel() {
        transformPanel.setLayout(new BoxLayout(transformPanel, BoxLayout.X_AXIS));
        transformPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, TRANSFORM_PANEL_HEIGHT));
        transformPanel.setBackground(Color.DARK_GRAY);
        transformPanel.add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
        transformPanel.add(morseCodeLabel);
        transformPanel.add(morseCodeSelector);
        transformPanel.add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
        transformPanel.add(caesarLabel);
        transformPanel.add(caesarSelector);
        transformPanel.add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
        transformPanel.add(caesarShiftLabel);
        transformPanel.add(Box.createRigidArea(new Dimension(ELEMENT_SPACING, 0)));
        transformPanel.add(caesarShiftField);
        transformPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        transformPanel.add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
    }

    /**
     * Helper function to set fonts to labels.
     */
    private void setLabelFonts() {
        encryptionLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, HEADER_FONT_SIZE));
        morseCodeLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, FONT_SIZE));
        caesarLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, FONT_SIZE));
        caesarShiftLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, FONT_SIZE));
    }

    /**
     * Sets visibility to all components.
     *
     * @param isVisible specifies visibility level for all components.
     */
    public void setVisibility(Boolean isVisible) {
        transformPanel.setVisible(isVisible);
        encryptionLabel.setVisible(isVisible);
        morseCodeLabel.setVisible(isVisible);
        caesarLabel.setVisible(isVisible);
        caesarShiftLabel.setVisible(isVisible);
        morseCodeSelector.setVisible(isVisible);
        caesarSelector.setVisible(isVisible);
        caesarShiftField.setVisible(isVisible);
        fontSelector.setVisible(isVisible);
    }

    /**
     * Add action listener to components.
     *
     * @param actionListener specified by MainUI.
     */
    public void addActionListener(ActionListener actionListener) {
        morseCodeSelector.addActionListener(actionListener);
        caesarSelector.addActionListener(actionListener);
        caesarShiftField.addActionListener(actionListener);
        fontSelector.addActionListener(actionListener);
    }

    /**
     * Enable or disable caesar shift field. When morse code is selected, the field should be disabled.
     *
     * @param isEnabled specifies if the field is enabled.
     */
    public void enableCaesarShiftField(Boolean isEnabled) {
        caesarShiftField.setEnabled(isEnabled);
        if (isEnabled) {
            caesarShiftField.setBackground(Color.WHITE);
            caesarShiftLabel.setForeground(Color.WHITE);
        } else {
            caesarShiftField.setBackground(Color.GRAY);
            caesarShiftLabel.setForeground(Color.GRAY);
        }
    }

    public void setMorseCodeSelection(Boolean isSelected) {
        morseCodeSelector.setSelected(isSelected);
    }

    public void setCaesarSelection(Boolean isSelected) {
        caesarSelector.setSelected(isSelected);
    }

    /**
     * Returns morse code checkbox component.
     *
     * @return morseCodeSelector component.
     */
    public JCheckBox getMorseCodeSelector() {
        return morseCodeSelector;
    }

    /**
     * Returns caesar checkbox component.
     *
     * @return caesarSelector component.
     */
    public JCheckBox getCaesarSelector() {
        return caesarSelector;
    }

    /**
     * Returns number input field.
     *
     * @return caesarShiftField component.
     */
    public JTextField getCaesarShiftField() {
        return caesarShiftField;
    }

    /**
     * Returns font selector.
     *
     * @return fontSelector component.
     */
    public JComboBox<String> getFontSelector() {
        return fontSelector;
    }

    /**
     * Return the selected item in the font selector.
     *
     * @return string of font selected.
     */
    public String getFontFromSelector() {
        return (String) fontSelector.getSelectedItem();
    }

    /**
     * Return the state of the Caesar Encrypt checkbox.
     *
     * @return state of Caesar Encrypt checkbox.
     */
    public boolean isCaesarSelected() {
        return caesarSelector.isSelected();
    }

    /**
     * Return the state of the Morse Code checkbox.
     *
     * @return state of Morse Code checkbox.
     */
    public boolean isMorseSelected() {
        return morseCodeSelector.isSelected();
    }

    /**
     * Return the value of the Caesar Encrypt shift field.
     *
     * @return value of the Caesar Encrypt shift field.
     */
    public int getCaesarShiftValue() {
        return (int) caesarShiftField.getValue();
    }
}
