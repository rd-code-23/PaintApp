package com.teambeta.sketcherapp.drawingTools;


import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * Text tool class that places text inputted by user onto canvas.
 */
public class TextTool extends DrawingTool {
    private int currentY;
    private int currentX;
    private Color color;

    public TextTool() {
        color = Color.black;
        registerObservers();
        currentX = 0;
        currentY = 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        placeText(canvas, layers, e);
    }


    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    /**
     * Places text inputted by user on canvas.
     * @param canvas to draw text onto
     * @param layers first layer by default is layers[0]
     * @param e MouseEvent
     */
    private void placeText(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);

        //draw a point where the mouse was clicked
        currentX = e.getX();
        currentY = e.getY();

        // TODO: currently placing a text does not update on canvas unless another action is performed
        Point location = MouseInfo.getPointerInfo().getLocation();
        TextFieldInput textFieldInput = new TextFieldInput(color, (int) location.getX(), (int) location.getY());
        String userInput = textFieldInput.getUserInput();

        if (!userInput.equals("")) {
            layer1Graphics.drawString(userInput, currentX, currentY);
            DrawArea.drawLayersOntoCanvas(layers, canvas);
        }
    }

    /**
     * Add a new observer to ColorChooser. Selecting a color in ColorChooser will update the color in this class
     */
    private void registerObservers() {
        ColorChooser.addObserver(new GeneralObserver() {
            @Override
            public void update() {
                color = ColorChooser.getColor();
            }
        });
    }
}
