package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

/**
 * The EllipseTool class implements the drawing behavior for when the Ellipse tool has been selected
 * <p>
 * Behaviour of the ellipse tool:
 * - The end-point relative to the init-point can be in any 4 quadrants.
 * - Draw a circle when the shift button is held on mouse release.
 */
public class EllipseTool extends DrawingTool {

    private int currentY;
    private int currentX;
    private int initX;
    private int initY;
    private int xAxisMagnitudeDelta;
    private int yAxisMagnitudeDelta;
    private int drawWidthX;
    private int drawHeightY;
    private Color color;
    private final int DEFAULT_STOKE_VALUE = 10;
    private int ellipseWidth;
    private boolean fillShape;
    private BufferedImage previewLayer = null;

    /**
     * The constructor sets the properties of the tool to their default values
     */
    public EllipseTool() {
        color = Color.black;
        registerObservers();
        initX = 0;
        initY = 0;
        currentX = 0;
        currentY = 0;
        drawWidthX = 0;
        drawHeightY = 0;
        ellipseWidth = DEFAULT_STOKE_VALUE;
        fillShape = false;
    }

    @Override
    public void onDrag(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        // canvas.getGraphics().setColor(color);
        calcEllipseCoordinateData(e, layers, canvas);

        if (previewLayer == null) {
            //previewLayer = new BufferedImage(canvas.getWidth(), canvas.getHeight(), BufferedImage.TYPE_INT_ARGB);
            previewLayer = DrawArea.getPreviewLayer();
        }
        //clear preview layer
        DrawArea.clearBufferImageToTransparent(previewLayer);

        //init graphics objects
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasGraphics.setColor(color);
        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);
        Graphics2D previewLayerGraphics = (Graphics2D) previewLayer.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        previewLayerGraphics.setColor(color);

        calcEllipseCoordinateData(e, layers, canvas);

        //draw the circle preview onto the preview layer
        previewLayerGraphics.drawOval(initX, initY, drawWidthX, drawHeightY);

        //info: https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html
        //draw the preview layer on top of the drawing layer(s)
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
        canvasGraphics.drawImage(previewLayer, 0, 0, null);
    }

    @Override
    public void onRelease(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        calcEllipseCoordinateData(e, layers, canvas);

        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);

        layer1Graphics.drawOval(initX, initY, drawWidthX, drawHeightY);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    private void calcEllipseCoordinateData(MouseEvent e, BufferedImage[] layers, BufferedImage canvas) {
        // Get the coordinates of where the user released the mouse.
        currentX = e.getX();
        currentY = e.getY();

        xAxisMagnitudeDelta = Math.abs(currentX - initX);
        yAxisMagnitudeDelta = Math.abs(currentY - initY);

        // Detect shift-down by the MouseEvent, e.
        if (e.isShiftDown()) {
            if (xAxisMagnitudeDelta > yAxisMagnitudeDelta) {
                drawWidthX = yAxisMagnitudeDelta;
                drawHeightY = yAxisMagnitudeDelta;
            } else {
                drawWidthX = xAxisMagnitudeDelta;
                drawHeightY = xAxisMagnitudeDelta;
            }
        } else {
            drawWidthX = xAxisMagnitudeDelta;
            drawHeightY = yAxisMagnitudeDelta;
        }

        // Handle cases where the ellipse lies in a quadrant (with origin 0,0 at click) other than IV.
        if (currentY < initY) {
            initY -= drawHeightY;
        }
        if (currentX < initX) {
            initX -= drawWidthX;
        }

        Graphics2D layer1Graphics = (Graphics2D) layers[0].getGraphics();
        layer1Graphics.setStroke(new BasicStroke(getToolWidth()));
        layer1Graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        layer1Graphics.setColor(color);

        // Draw a filled ellipse/circle if the alt key is down on release.
        if (fillShape) {
            layer1Graphics.fillOval(initX, initY, drawWidthX, drawHeightY);
        }
        layer1Graphics.drawOval(initX, initY, drawWidthX, drawHeightY);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    @Override
    public void onClick(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {
        canvas.getGraphics().setColor(color);
        currentX = e.getX();
        currentY = e.getY();
        initX = currentX;
        initY = currentY;
    }

    @Override
    public int getToolWidth() {
        return ellipseWidth;
    }

    @Override
    public void setToolWidth(int width) {
        ellipseWidth = width;
    }

    /**
     * getColor returns the current color the ellipse tool is set to.
     *
     * @return the current Color of the ellipse
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

    public void setFillState(boolean fillState) {
        fillShape = fillState;
    }
}
