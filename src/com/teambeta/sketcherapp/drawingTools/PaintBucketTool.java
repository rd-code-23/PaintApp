package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The PaintBucket class implements the drawing behavior for when the PaintBucket tool has been selected
 */
public class PaintBucketTool extends DrawingTool {
    private Color color;


    /**
     * The constructor sets the properties of the tool to their default values
     */
    public PaintBucketTool() {
        color = Color.black;
        registerObservers();
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
    public Color getColor() {
        return ColorChooser.getColor();
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setColor(this.getColor());
        //get the coordinates of where the user clicked the mouse
        int currentX = e.getX();
        int currentY = e.getY();
        //get the color of the pixel clicked
        Color colorToReplace = new Color(layers[0].getRGB(currentX, currentY));

        //fill the connected area of the clicked pixels color with the paint buckets color.
        


        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }
}
