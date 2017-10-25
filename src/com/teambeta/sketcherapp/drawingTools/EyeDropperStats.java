package com.teambeta.sketcherapp.drawingTools;

import javax.swing.*;
import java.awt.*;

/**
 * Panel which displays RGB values based on ColorChooser.
 */
public class EyeDropperStats extends JPanel {
    private static final String TEXTFIELD_EYEDROPPER_DEFAULT_STRING = "R: N/A G: N/A B: N/A";
    private static final int WIDTH_TEXTFIELD_EYEDROPPER = 150;
    private static final int TEXTFIELD_EYEDROPPER_FONTSIZE = 15;
    private static final int PANEL_EYEDROPPER_RIGHT_MARGIN = 25;
    private static final String EYEDROPPER_TEXTFIELD_RED_PREFIX = "R: ";
    private static final String EYEDROPPER_TEXTFIELD_GREEN_PREFIX = "G: ";
    private static final String EYEDROPPER_TEXTFIELD_BLUE_PREFIX = "B: ";
    private static final String EYEDROPPER_TEXTFIELD_SPACE = " ";
    private static final int HEIGHT_COMPONENT = 40;
    private static final String FONT_TYPE = "Arial";

    private JTextField eyeDropperTextField;
    private JLabel eyeDropperTextFieldLabel;

    /**
     * Constructor.
     */
    public EyeDropperStats() {
        setBackground(Color.DARK_GRAY);
        add(Box.createRigidArea(new Dimension(PANEL_EYEDROPPER_RIGHT_MARGIN, HEIGHT_COMPONENT)));
        eyeDropperTextField = new JTextField(TEXTFIELD_EYEDROPPER_DEFAULT_STRING, 3);
        eyeDropperTextField.setMaximumSize(new Dimension(WIDTH_TEXTFIELD_EYEDROPPER, HEIGHT_COMPONENT));
        eyeDropperTextField.setPreferredSize(new Dimension(WIDTH_TEXTFIELD_EYEDROPPER, HEIGHT_COMPONENT));
        eyeDropperTextField.setMinimumSize(new Dimension(WIDTH_TEXTFIELD_EYEDROPPER, HEIGHT_COMPONENT));
        eyeDropperTextField.setHorizontalAlignment(JTextField.CENTER);
        eyeDropperTextField.setFont(new Font(FONT_TYPE, Font.BOLD, TEXTFIELD_EYEDROPPER_FONTSIZE));
        eyeDropperTextField.setEditable(false);
        add(eyeDropperTextField);
    }

    /**
     * Update the eye dropper text field with a color object.
     *
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
