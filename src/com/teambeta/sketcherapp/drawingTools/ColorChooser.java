package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.annotation.Inherited;
import java.util.ArrayList;
import java.util.List;

/**
 * Color chooser for the application. Appears as a box, and when clicked, user is able to select a color for drawing
 * tools.
 */
public class ColorChooser extends JPanel {
    private static final String FONT_TYPE = "Arial";
    private static final int FONT_SIZE = 16;
    private static final String COLOR_SELECTOR_TEXT = "Color Selector";
    private static Color color;
    private static final int SQUARE_LENGTH = 80;
    private static final int PANEL_LENGTH = 80;
    private static final String DARK_GREY_CANVAS = "#222222";
    private static List<GeneralObserver> observers = new ArrayList<>();

    // Constructor that displays default color as a square panel (graphic selector).
    public ColorChooser() {
        color = Color.BLACK;
        setToolTipText(COLOR_SELECTOR_TEXT);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeColor();
            }
        });
    }

    @Override
    public JToolTip createToolTip() {
        JToolTip toolTip = new JToolTip();
        toolTip.setBackground(Color.decode(DARK_GREY_CANVAS));
        toolTip.setForeground(Color.WHITE);
        toolTip.setBorder(null);
        toolTip.setFont(new Font(FONT_TYPE, Font.PLAIN, FONT_SIZE));
        return toolTip;
    }

    @Override
    protected void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(Color.DARK_GRAY);
        setSize(SQUARE_LENGTH, SQUARE_LENGTH);

        graphics.setColor(getColor());
        graphics.fillRect(0, 0, SQUARE_LENGTH, SQUARE_LENGTH);
        graphics.dispose();
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(PANEL_LENGTH, PANEL_LENGTH);
    }

    /**
     * Changes color for the graphic selector.
     */
    private void changeColor() {
        Color previousColor = getColor();
        color = JColorChooser.showDialog(null, "Select a Color", color);
        // Canceling or exiting the colour panel will return null and break everything.
        // Store the previous colour and keep it if null is encountered.
        if (color == null) {
            color = previousColor;
        }
        paintComponent(getGraphics());
        notifyObservers();
    }

    /**
     * Used by the eye dropper tool to directly update the color of the color chooser.
     *
     * @param color The color to be set
     */
    public void setColorFromEyeDropper(Color color) {
        ColorChooser.color = color;
        paintComponent(getGraphics());
        notifyObservers();
    }

    /**
     * Gets the color of the graphic selector.
     *
     * @return color of the graphic selector.
     */
    public static Color getColor() {
        return color;
    }

    /**
     * Adds observers to a list.
     *
     * @param observer to notify when color is updated.
     */
    public static void addObserver(GeneralObserver observer) {
        observers.add(observer);
    }

    /**
     * Notifies all observers in list when color is updated.
     */
    private static void notifyObservers() {
        for (GeneralObserver observer : observers) {
            observer.update();
        }
    }
}