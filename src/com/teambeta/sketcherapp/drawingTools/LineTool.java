package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import sun.plugin.dom.css.RGBColor;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The LineTool class implements the drawing behavior for when the Line tool has been selected
 */
public class LineTool extends DrawingTool {
    private int currentY;
    private int currentX;
    private int lastX;
    private int lastY;
    private int sizeInPixels;
    private Color color;
    private BufferedImage previewLayer = null;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public LineTool() {
        color = Color.black;
        registerObservers();
        sizeInPixels = 1;
        lastX = 0;
        lastY = 0;
        currentX = 0;
        currentY = 0;

    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

        if (previewLayer == null) {
            previewLayer = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
        }

        //clear preview layer
        // clearBufferImage(previewLayer);
        previewLayer = new BufferedImage(previewLayer.getWidth(), previewLayer.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasGraphics.setColor(color);

        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);

        Graphics2D previewLayerGraphics = (Graphics2D) previewLayer.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        previewLayerGraphics.setColor(color);

        previewLayerGraphics.setBackground(new Color(0, 0, 0, 0));
        previewLayerGraphics.clearRect(0, 0, previewLayer.getWidth(), previewLayer.getHeight());

        currentX = e.getX();
        currentY = e.getY();


        previewLayerGraphics.drawLine(lastX, lastY, currentX, currentY);


        // source: https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        //draw the preview layer on top of the drawing layer(s)
        //previewLayerGraphics.setComposite(alphaComposite);
        canvasGraphics.setComposite(alphaComposite);

        //TODO:have seperate drawing layer, only draw on canvas at the end
        for (int i = 0; i < layers.length; i++) {
            canvasGraphics.drawImage(layers[i], 0, 0, null);
        }
        canvasGraphics.drawImage(previewLayer, 0, 0, null);
    }

    private void clearBufferImage(BufferedImage bufferedImage) {
        for (int i = 0; i < bufferedImage.getHeight(); i++) {
            for (int j = 0; j < bufferedImage.getWidth(); j++) {
                bufferedImage.setRGB(j, i, Color.TRANSLUCENT);
            }
        }

    }


    @Override
    public  void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        Graphics canvasGraphics = canvas.getGraphics();
        canvasGraphics.setColor(this.getColor());
        //get the coordinates of where the user released the mouse
        currentX = e.getX();
        currentY = e.getY();
        //draw a line between the start and release points
        if (canvasGraphics != null) {
            // draw line if graphics context not null
            canvasGraphics.drawLine(lastX, lastY, currentX, currentY);
        }
        lastX = currentX;
        lastY = currentY;
    }

    @Override
    public void onClick(Graphics2D graphics, MouseEvent e) {
        //do nothing
    }

    @Override
    public void onPress(Graphics2D graphics, MouseEvent e) {
        //set the coordinates to the current pixel clicked
        currentX = e.getX();
        currentY = e.getY();
        lastX = currentX;
        lastY = currentY;
    }

    /**
     * getColor returns the current color the line tool is set to.
     *
     * @return the current Color of the LineTool
     */
    @Override
    public Color getColor() {
        return ColorChooser.getColor();
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
