package com.teambeta.sketcherapp.model;

import javax.swing.*;
import java.awt.*;

/**
 * Creates a custom tool tip popup for all tool icons.
 */
public class ToolButton extends JButton {
    private static final String DARK_GREY_CANVAS = "#222222";
    private static final String FONT_TYPE = "Arial";
    private static final int FONT_SIZE = 16;

    /**
     * Constructor.
     *
     * @param imageIcon path to the button's image.
     */
    public ToolButton(ImageIcon imageIcon) {
        super(imageIcon);
    }

    @Override
    public JToolTip createToolTip() {
        JToolTip toolTip = super.createToolTip();
        toolTip.setBackground(Color.decode(DARK_GREY_CANVAS));
        toolTip.setForeground(Color.WHITE);
        toolTip.setBorder(null);
        toolTip.setFont(new Font(FONT_TYPE, Font.PLAIN, FONT_SIZE));
        return toolTip;
    }
}
