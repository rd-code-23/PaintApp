package com.teambeta.sketcherapp.drawingTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Text settings including font, and other encryption options
 */
public class TextToolSettings extends JPanel {
    private JLabel encryptionsLabel;
    private JLabel morseCodeLabel;
    private JLabel caesarLabel;
    private JLabel numberInputLabel;
    private JCheckBox morseCodeSelector;
    private JCheckBox caesarSelector;
    private JTextField numberInputField;
    private JComboBox<String> fontSelector;

    private static final String DEFAULT_FONT = "Arial";
    private static final String MORSE_CODE_LABEL = "Morse code";
    private static final String CEASAR_LABEL = "Caesar";
    private static final String NUMBER_LABEL = "Number";
    private static final String ENCRYPTIONS_LABEL = "Encryptions";

    /**
     * Constructor.
     */
    public TextToolSettings() {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        setBackground(Color.DARK_GRAY);

        encryptionsLabel = new JLabel(ENCRYPTIONS_LABEL);
        morseCodeLabel = new JLabel(MORSE_CODE_LABEL);
        caesarLabel = new JLabel(CEASAR_LABEL);
        numberInputLabel = new JLabel(NUMBER_LABEL);
        encryptionsLabel.setForeground(Color.WHITE);
        morseCodeLabel.setForeground(Color.WHITE);
        caesarLabel.setForeground(Color.WHITE);
        numberInputLabel.setForeground(Color.WHITE);

        morseCodeSelector = new JCheckBox();
        caesarSelector = new JCheckBox();
        numberInputField = new JTextField();
        morseCodeSelector.setBackground(Color.DARK_GRAY);
        caesarSelector.setBackground(Color.DARK_GRAY);

        fontSelector = new JComboBox<>(fonts);
        fontSelector.setSelectedItem(DEFAULT_FONT);

        setVisibility(false);

        add(encryptionsLabel);
        add(morseCodeLabel);
        add(morseCodeSelector);
        add(caesarLabel);
        add(caesarSelector);
        add(numberInputLabel);
        add(numberInputField);
        add(fontSelector);
    }

    /**
     * Sets visibility to all components.
     * @param isVisible specifies visibility level for all comonents.
     */
    public void setVisibility(Boolean isVisible) {
        encryptionsLabel.setVisible(isVisible);
        morseCodeLabel.setVisible(isVisible);
        caesarLabel.setVisible(isVisible);
        numberInputLabel.setVisible(isVisible);
        morseCodeSelector.setVisible(isVisible);
        caesarSelector.setVisible(isVisible);
        numberInputField.setVisible(isVisible);
        fontSelector.setVisible(isVisible);
    }

    /**
     * Add action listener to components.
     * @param actionListener specified by MainUI.
     */
    public void addActionListener(ActionListener actionListener) {
        morseCodeSelector.addActionListener(actionListener);
        caesarSelector.addActionListener(actionListener);
        numberInputField.addActionListener(actionListener);
        fontSelector.addActionListener(actionListener);
    }

    /**
     * Returns morse code checkbox component.
     * @return morseCodeSelector component.
     */
    public JCheckBox getMorseCodeSelector() {
        return morseCodeSelector;
    }

    /**
     * Rerturns caesar checkbox component.
     * @return caesarSlector component.
     */
    public JCheckBox getCaesarSelector() {
        return caesarSelector;
    }

    /**
     * Returns number input field.
     * @return numberInputField component.
     */
    public JTextField getNumberInputField() {
        return numberInputField;
    }

    /**
     * Returns font selector.
     * @return fontSelector component.
     */
    public JComboBox<String> getFontSelector() {
        return fontSelector;
    }

    /**
     * Return the selected item in the font selector.
     * @return string of font selected.
     */
    public String getFontFromSelector() {
        return (String) fontSelector.getSelectedItem();
    }
}
