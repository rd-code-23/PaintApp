package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The TriangleTool class implements the drawing behavior for when the Line tool has been selected
 */
public class TriangleTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private int initX;
    private int initY;
    private int mouseOriginX;
    private int mouseOriginY;
    private Color color;
    private BufferedImage previewLayer = null;
    private int triangleWidth;
    private final int DEFAULT_WIDTH_VALUE = 10;
    private boolean fillShape;



    /**
     * The constructor sets the properties of the tool to their default values
     */
    public TriangleTool() {
        registerObservers();
        color = Color.black;
        initX = 0;
        initY = 0;
        currentX = 0;
        currentY = 0;
        mouseOriginX = 0;
        mouseOriginY = 0;
        triangleWidth = DEFAULT_WIDTH_VALUE;
        fillShape = false;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        if (previewLayer == null) {
            previewLayer = DrawArea.getPreviewLayer();
        }
        //clear preview layer
        DrawArea.clearBufferImageToTransparent(previewLayer);

        //init graphics objects
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasGraphics.setColor(color);
        Graphics2D previewLayerGraphics = (Graphics2D) previewLayer.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        previewLayerGraphics.setColor(color);
        previewLayerGraphics.setStroke(new BasicStroke(getToolWidth()));

        calcTriangleCoordinateData(e, layers, canvas);


        int[] x = {initX, currentX, currentX};
        int[] y = {currentY, initY, currentY};
//        int [] x = {initX,Math.abs(currentX-initX),currentX};
//        int [] y ={currentY,Math.abs(currentY-initY),currentY};

        if (fillShape) {
            previewLayerGraphics.fillPolygon(x,y,3);
        }
        previewLayerGraphics.drawPolygon(x,y,3);
        DrawArea.drawLayersOntoCanvas(layers, canvas);


        //info: https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html
        //draw the preview layer on top of the drawing layer(s)
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
        canvasGraphics.drawImage(previewLayer, 0, 0, null);
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        int [] x ={initX,currentX,currentX};
        int [] y ={currentY,initY,currentY};
//        int [] x = {initX,Math.abs(currentX-initX),currentX};
//        int [] y ={currentY,Math.abs(currentY-initY),currentY};


        calcTriangleCoordinateData(e, layers, canvas);

        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);
        layer1Graphics.setStroke(new BasicStroke(getToolWidth()));


        if (fillShape) {
            layer1Graphics.fillPolygon(x,y,3);
        }
        layer1Graphics.drawPolygon(x,y,3);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    private void calcTriangleCoordinateData(MouseEvent e, BufferedImage[] layers, BufferedImage canvas) {
        // Get the coordinates of where the user released the mouse.
        currentX = e.getX();
        currentY = e.getY();

        // Handle cases where the triangle lies in a quadrant (with origin 0,0 at click) other than IV.
        if (currentY < mouseOriginY) {
            initY = mouseOriginY;
        }
        if (currentX < mouseOriginX) {
            initX = mouseOriginX ;
        }


    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        //set the coordinates to the current pixel clicked
        currentX = e.getX();
        currentY = e.getY();
        initX = currentX;
        initY = currentY;
        mouseOriginX = currentX;
        mouseOriginY = currentY;
    }

    @Override
    public int getToolWidth() {
        return triangleWidth;
    }

    @Override
    public void setToolWidth(int width) {
        triangleWidth = width;
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

    @Override
    public void setFillState(boolean fillState) {
        fillShape = fillState;
    }

}
