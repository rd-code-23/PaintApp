package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;

import javax.swing.*;
import java.awt.*;

/**
 * Panel which displays RGB values based on ColorChooser.
 */
public class EyeDropperStats extends JPanel {
    private static final int TEXTFIELD_EYEDROPPER_FONTSIZE = 16;
    private static final String EYEDROPPER_TEXTFIELD_RED_PREFIX = "R:    ";
    private static final String EYEDROPPER_TEXTFIELD_GREEN_PREFIX = "G:    ";
    private static final String EYEDROPPER_TEXTFIELD_BLUE_PREFIX = "B:    ";
    private static final String EYEDROPPER_TEXTFIELD_SPACE = "\n";
    private static final String FONT_TYPE = "Arial";

    private Color color;
    private JTextArea eyeDropperTextField;

    /**
     * Constructor.
     */
    public EyeDropperStats() {
        color = Color.BLACK;
        registerObservers();
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        eyeDropperTextField = new JTextArea();
        eyeDropperTextField.setBackground(Color.DARK_GRAY);
        eyeDropperTextField.setForeground(Color.WHITE);
        eyeDropperTextField.setFont(new Font(FONT_TYPE, Font.BOLD, TEXTFIELD_EYEDROPPER_FONTSIZE));
        eyeDropperTextField.setEditable(false);

        updateTextField();
        add(eyeDropperTextField);
    }

    /**
     * Update text field from ColorChooser.
     */
    private void updateTextField() {
        eyeDropperTextField.setText(EYEDROPPER_TEXTFIELD_RED_PREFIX + color.getRed()
                + EYEDROPPER_TEXTFIELD_SPACE
                + EYEDROPPER_TEXTFIELD_GREEN_PREFIX + color.getGreen()
                + EYEDROPPER_TEXTFIELD_SPACE
                + EYEDROPPER_TEXTFIELD_BLUE_PREFIX + color.getBlue());
    }

    /**
     * Add a new observer to ColorChooser. Selecting a color in ColorChooser will update the color in this class.
     */
    private void registerObservers() {
        ColorChooser.addObserver(new GeneralObserver() {
            @Override
            public void update() {
                color = ColorChooser.getColor();
                updateTextField();
            }
        });
    }
}