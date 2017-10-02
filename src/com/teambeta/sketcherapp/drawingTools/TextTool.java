package com.teambeta.sketcherapp.drawingTools;


import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

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

    private void placeText(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);


        //draw a point where the mouse was clicked
        currentX = e.getX();
        currentY = e.getY();

        layer1Graphics.drawString("YAAAA", currentX, currentY);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
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
