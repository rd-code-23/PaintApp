package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;

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
        floodFill(layers[0], currentX, currentY, colorToReplace);
        DrawArea.drawLayersOntoCanvas(layers, canvas);
    }

    //see https://en.wikipedia.org/wiki/Flood_fill
    private void floodFill(BufferedImage layer, int x, int y, Color colorToReplace) {
        //floodFillAlgorithm1(layer, x, y, colorToReplace);
        floodFillAlgorithm2(layer, x, y, colorToReplace);
    }

    private void floodFillAlgorithm2(BufferedImage layer, int x, int y, Color colorToReplace) {
        //TODO:fix problem with anti-aliasing causing caps in the fill
        Queue<Integer> xCoordsOfPixelsToColor = new LinkedList<>();
        Queue<Integer> yCoordsOfPixelsToColor = new LinkedList<>();
        int xOfCurrentPixel = x;
        int yOfCurrentPixel = y;
        xCoordsOfPixelsToColor.add(xOfCurrentPixel);
        yCoordsOfPixelsToColor.add(yOfCurrentPixel);
        int replacementColorRGB = this.color.getRGB();
        int colorToReplaceRGB = colorToReplace.getRGB();
        if (replacementColorRGB == colorToReplaceRGB) {
            //early exit
            return;
        }
        int eastMostXCoord;
        int westMostXCoord;
        Graphics2D layer1Graphics = (Graphics2D) layer.getGraphics();
        layer1Graphics.setColor(this.color);

        while (!xCoordsOfPixelsToColor.isEmpty()) {
            xOfCurrentPixel = xCoordsOfPixelsToColor.remove();
            yOfCurrentPixel = yCoordsOfPixelsToColor.remove();
            eastMostXCoord = xOfCurrentPixel;
            westMostXCoord = xOfCurrentPixel;

            int downPixelY = yOfCurrentPixel - 1;
            int upPixelY = yOfCurrentPixel + 1;
            while (eastMostXCoord < layer.getWidth() &&
                    layer.getRGB(eastMostXCoord, yOfCurrentPixel) == colorToReplaceRGB) {
                if (downPixelY < layer.getHeight() && (downPixelY >= 0) &&
                        (layer.getRGB(eastMostXCoord, downPixelY) - colorToReplaceRGB) == 0) {
                    xCoordsOfPixelsToColor.add(eastMostXCoord);
                    yCoordsOfPixelsToColor.add(downPixelY);
                }
                if (upPixelY < layer.getHeight() && (upPixelY >= 0) &&
                        (layer.getRGB(eastMostXCoord, upPixelY) - colorToReplaceRGB) == 0) {
                    xCoordsOfPixelsToColor.add(eastMostXCoord);
                    yCoordsOfPixelsToColor.add(upPixelY);
                }
                eastMostXCoord++;
            }
            while (westMostXCoord >= 0 &&
                    (layer.getRGB(westMostXCoord, yOfCurrentPixel) - colorToReplaceRGB) == 0) {
                if (downPixelY < layer.getHeight() && (downPixelY >= 0) &&
                        layer.getRGB(westMostXCoord, downPixelY) == colorToReplaceRGB) {
                    xCoordsOfPixelsToColor.add(westMostXCoord);
                    yCoordsOfPixelsToColor.add(downPixelY);
                }
                if (upPixelY < layer.getHeight() && (upPixelY >= 0) &&
                        (layer.getRGB(westMostXCoord, upPixelY) - colorToReplaceRGB) == 0) {
                    xCoordsOfPixelsToColor.add(westMostXCoord);
                    yCoordsOfPixelsToColor.add(upPixelY);
                }
                westMostXCoord--;
            }
            if (westMostXCoord != eastMostXCoord) {
                layer1Graphics.drawRect
                        (westMostXCoord, yOfCurrentPixel, eastMostXCoord - westMostXCoord, 1);
            }
        }
    }

    private void floodFillAlgorithm1(BufferedImage layer, int x, int y, Color colorToReplace) {
        Queue<Integer> xCoordsOfPixelsToColor = new LinkedList<>();
        Queue<Integer> yCoordsOfPixelsToColor = new LinkedList<>();
        int xOfCurrentPixel = x;
        int yOfCurrentPixel = y;
        xCoordsOfPixelsToColor.add(xOfCurrentPixel);
        yCoordsOfPixelsToColor.add(yOfCurrentPixel);
        int replacementColorRGB = this.color.getRGB();
        int colorToReplaceRGB = colorToReplace.getRGB();

        //repeat until the queue is empty.
        while (!xCoordsOfPixelsToColor.isEmpty()) {
            //color the current pixel
            xOfCurrentPixel = xCoordsOfPixelsToColor.remove();
            yOfCurrentPixel = yCoordsOfPixelsToColor.remove();
            layer.setRGB(xOfCurrentPixel, yOfCurrentPixel, replacementColorRGB);
            //put the adjacent pixels of the target color into the queue.
            boolean isLeftPixelInBounds = xOfCurrentPixel - 1 >= 0;
            boolean isRightPixelInBounds = xOfCurrentPixel + 1 <= layer.getWidth();
            boolean isUpPixelInBounds = yOfCurrentPixel + 1 <= layer.getHeight();
            boolean isDownPixelInBounds = yOfCurrentPixel - 1 >= 0;

            boolean isLeftPixelOfTargetColor = isLeftPixelInBounds &&
                    (layer.getRGB(xOfCurrentPixel - 1, yOfCurrentPixel) == colorToReplaceRGB);
            boolean isUpLeftPixelOfTargetColor = isLeftPixelInBounds && isUpPixelInBounds &&
                    (layer.getRGB(xOfCurrentPixel - 1, yOfCurrentPixel + 1) == colorToReplaceRGB);
            boolean isUpPixelOfTargetColor = isUpPixelInBounds &&
                    (layer.getRGB(xOfCurrentPixel, yOfCurrentPixel + 1) == colorToReplaceRGB);
            boolean isUpRightPixelOfTargetColor = isUpPixelInBounds && isRightPixelInBounds &&
                    (layer.getRGB(xOfCurrentPixel + 1, yOfCurrentPixel + 1) == colorToReplaceRGB);
            boolean isRightPixelOfTargetColor = isRightPixelInBounds &&
                    (layer.getRGB(xOfCurrentPixel + 1, yOfCurrentPixel) == colorToReplaceRGB);
            boolean isRightDownPixelOfTargetColor = isRightPixelInBounds && isDownPixelInBounds &&
                    (layer.getRGB(xOfCurrentPixel + 1, yOfCurrentPixel - 1) == colorToReplaceRGB);
            boolean isDownPixelOfTargetColor = isDownPixelInBounds &&
                    (layer.getRGB(xOfCurrentPixel, yOfCurrentPixel - 1) == colorToReplaceRGB);
            boolean isDownLeftPixelOfTargetColor = isDownPixelInBounds && isLeftPixelInBounds &&
                    (layer.getRGB(xOfCurrentPixel - 1, yOfCurrentPixel - 1) == colorToReplaceRGB);

            if (isLeftPixelOfTargetColor) {
                xCoordsOfPixelsToColor.add(xOfCurrentPixel - 1);
                yCoordsOfPixelsToColor.add(yOfCurrentPixel);
            }
            if (isUpLeftPixelOfTargetColor) {
                xCoordsOfPixelsToColor.add(xOfCurrentPixel - 1);
                yCoordsOfPixelsToColor.add(yOfCurrentPixel + 1);
            }
            if (isUpPixelOfTargetColor) {
                xCoordsOfPixelsToColor.add(xOfCurrentPixel);
                yCoordsOfPixelsToColor.add(yOfCurrentPixel + 1);
            }

            if (isUpRightPixelOfTargetColor) {
                xCoordsOfPixelsToColor.add(xOfCurrentPixel + 1);
                yCoordsOfPixelsToColor.add(yOfCurrentPixel + 1);
            }

            if (isRightPixelOfTargetColor) {
                xCoordsOfPixelsToColor.add(xOfCurrentPixel + 1);
                yCoordsOfPixelsToColor.add(yOfCurrentPixel);
            }

            if (isRightDownPixelOfTargetColor) {
                xCoordsOfPixelsToColor.add(xOfCurrentPixel + 1);
                yCoordsOfPixelsToColor.add(yOfCurrentPixel - 1);
            }

            if (isDownPixelOfTargetColor) {
                xCoordsOfPixelsToColor.add(xOfCurrentPixel);
                yCoordsOfPixelsToColor.add(yOfCurrentPixel - 1);
            }

            if (isDownLeftPixelOfTargetColor) {
                xCoordsOfPixelsToColor.add(xOfCurrentPixel - 1);
                yCoordsOfPixelsToColor.add(yOfCurrentPixel - 1);
            }
        }
    }

    @Override
    public void onPress(BufferedImage canvas, BufferedImage[] layers, MouseEvent e) {

    }
}
