package com.teambeta.sketcherapp.drawingTools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Text settings including font, and other encryption options.
 */
public class TextToolSettings extends JPanel {
    private JPanel encryptionPanel;
    private JLabel encryptionsLabel;
    private JLabel morseCodeLabel;
    private JLabel caesarLabel;
    private JLabel numberInputLabel;
    private JCheckBox morseCodeSelector;
    private JCheckBox caesarSelector;
    private JTextField numberInputField;
    private JComboBox<String> fontSelector;

    private static final int COMPONENT_SPACING = 20;
    private static final int ENCRYPTION_PANEL_HEIGHT = 40;
    private static final int HEADER_FONT_SIZE = 16;
    private static final int FONT_SIZE = 12;
    private static final int ELEMENT_SPACING = 10;
    private static final int NUMBER_FIELD_WIDTH = 40;
    private static final int NUMBER_FIELD_HEIGHT = 25;
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

        encryptionPanel = new JPanel();
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
        numberInputField.setPreferredSize(new Dimension(NUMBER_FIELD_WIDTH, NUMBER_FIELD_HEIGHT));
        numberInputField.setMaximumSize(new Dimension(NUMBER_FIELD_WIDTH, NUMBER_FIELD_HEIGHT));
        morseCodeSelector.setBackground(Color.DARK_GRAY);
        caesarSelector.setBackground(Color.DARK_GRAY);

        fontSelector = new JComboBox<>(fonts);
        fontSelector.setSelectedItem(DEFAULT_FONT);

        setLabelFonts();
        setVisibility(false);

        add(encryptionsLabel);
        add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));

        buildEncryptionPanel();

        add(encryptionPanel);
        add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
        add(fontSelector);
    }

    /**
     * Helper function to build encryption panel and its components.
     */
    private void buildEncryptionPanel() {
        encryptionPanel.setLayout(new BoxLayout(encryptionPanel, BoxLayout.X_AXIS));
        encryptionPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, ENCRYPTION_PANEL_HEIGHT));
        encryptionPanel.setBackground(Color.DARK_GRAY);
        encryptionPanel.add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
        encryptionPanel.add(morseCodeLabel);
        encryptionPanel.add(morseCodeSelector);
        encryptionPanel.add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
        encryptionPanel.add(caesarLabel);
        encryptionPanel.add(caesarSelector);
        encryptionPanel.add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
        encryptionPanel.add(numberInputLabel);
        encryptionPanel.add(Box.createRigidArea(new Dimension(ELEMENT_SPACING, 0)));
        encryptionPanel.add(numberInputField);
        encryptionPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        encryptionPanel.add(Box.createRigidArea(new Dimension(COMPONENT_SPACING, 0)));
    }

    /**
     * Helper function to set fonts to labels.
     */
    private void setLabelFonts() {
        encryptionsLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, HEADER_FONT_SIZE));
        morseCodeLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, FONT_SIZE));
        caesarLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, FONT_SIZE));
        numberInputLabel.setFont(new Font(DEFAULT_FONT, Font.PLAIN, FONT_SIZE));
    }

    /**
     * Sets visibility to all components.
     *
     * @param isVisible specifies visibility level for all comonents.
     */
    public void setVisibility(Boolean isVisible) {
        encryptionPanel.setVisible(isVisible);
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
     *
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
     *
     * @return morseCodeSelector component.
     */
    public JCheckBox getMorseCodeSelector() {
        return morseCodeSelector;
    }

    /**
     * Rerturns caesar checkbox component.
     *
     * @return caesarSelector component.
     */
    public JCheckBox getCaesarSelector() {
        return caesarSelector;
    }

    /**
     * Returns number input field.
     *
     * @return numberInputField component.
     */
    public JTextField getNumberInputField() {
        return numberInputField;
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
}
