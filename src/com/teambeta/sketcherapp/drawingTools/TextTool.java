package com.teambeta.sketcherapp.drawingTools;


import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * Text tool class that places text inputted by user onto canvas.
 */
public class TextTool extends DrawingTool {
    private int currentY;
    private int currentX;
    private Color color;
    private int textSize;
    private String font;
    private final int DEFAULT_WIDTH_VALUE = 20;

    public TextTool() {
        color = Color.black;
        registerObservers();
        currentX = 0;
        textSize = DEFAULT_WIDTH_VALUE;
        currentY = 0;
    }

    @Override
    public Color getColor() {
        return null;
    }

    @Override
    public void onDrag(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers,
                       BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e,
                          LinkedList<ImageLayer> drawingLayers) {
    }

    private ImageLayer getSelectedLayer(LinkedList<ImageLayer> drawingLayers) {
        //get the selected layer, this assumes there is only one selected layer.
        for (int i = 0; i < drawingLayers.size(); i++) {
            ImageLayer drawingLayer = drawingLayers.get(i);
            if (drawingLayer.isSelected()) {
                return drawingLayer;
            }
        }
        return null;
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e,
                        LinkedList<ImageLayer> drawingLayers) {
        placeText(canvas, drawingLayers, e);
    }


    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e,
                        LinkedList<ImageLayer> drawingLayers) {
    }

    @Override
    public int getToolWidth() {
        return textSize;
    }

    @Override
    public void setToolWidth(int width) {
        textSize = width;
    }

    /**
     * Sets font for text.
     *
     * @param fontType String of font type.
     */
    public void setFont(String fontType) {
        font = fontType;
    }

    /**
     * @param canvas        to draw text onto
     * @param drawingLayers The layers of the DrawArea
     * @param e             MouseEvent
     */
    private void placeText(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers, MouseEvent e) {
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        Graphics2D selectedLayerGraphics;
        if (selectedLayer != null) {
            selectedLayerGraphics = (Graphics2D) selectedLayer.getBufferedImage().getGraphics();

            Font customFont = new Font(font, Font.PLAIN, getToolWidth());
            selectedLayerGraphics.setFont(customFont);
            selectedLayerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            selectedLayerGraphics.setColor(color);

            //draw where the mouse was clicked
            currentX = e.getX();
            currentY = e.getY();
            Point location = MouseInfo.getPointerInfo().getLocation();

            TextFieldInput textFieldInput = new TextFieldInput(color,
                    (int) location.getX(), (int) location.getY(), customFont);
            textFieldInput.setFontType(customFont.getFontName());
            String userInput = textFieldInput.getUserInput();
            if (!userInput.equals("")) {
                selectedLayerGraphics.drawString(userInput, currentX, currentY);
                DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
            }
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

    @Override
    public void setFillState(boolean fillState) {
    }
}