package com.teambeta.sketcherapp.drawingTools;

import com.teambeta.sketcherapp.model.GeneralObserver;
import com.teambeta.sketcherapp.model.ImageLayer;
import com.teambeta.sketcherapp.model.MouseCursor;
import com.teambeta.sketcherapp.ui.DrawArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.LinkedList;

/**
 * The RectangleTool class implements the drawing behavior for when the Rectangle tool has been selected
 * <p>
 * Behaviour of the rectangle tool:
 * - The longest side will take the length of the shortest side.
 * - The end-point relative to the init-point can be in any 4 quadrants.
 * - Draw a square when the shift button is held on mouse release.
 */
public class RectangleSelectionTool extends DrawingTool {
    private int currentY;
    private int currentX;
    private int initX;
    private int initY;
    private int mouseOriginX;
    private int mouseOriginY;
    private int drawWidthX;
    private int drawHeightY;
    private int xAxisMagnitudeDelta;
    private int yAxisMagnitudeDelta;
    private Color color;
    private BufferedImage previewLayer = null;
    private int squareWidth;
    private final int DEFAULT_WIDTH_VALUE = 10;
    private boolean fillShape;
    private float dash[] = {10.0f};
    private BufferedImage originalSelectedCanvas;
    private static final Color transparentColor = new Color(0x00FFFFFF, true);
    private BufferedImage selectedCanvas; // the selection that the user has made
    private boolean isOverSelection; // is the mouse over the selectedCanvas
    private boolean isDrawn;// has the user selected somthing
    private int prevX, prevY;
    private boolean isDragSelection = false; // is the user choosing to drag the image
    LinkedList<ImageLayer> drawingLayersCopy;
    JRadioButton cutOption;
    JRadioButton copyOption;
    JLabel rightClickInfo;
    boolean doNothing = false;
    boolean isCut;

    JPanel selectionOptionPanel;

    //TODO BUG: north east panel not disappereing for textTool and selection, make empty jpanel to hide it
//TODO BUG: sometimes if you move the selection a bit above or below selected canvas it drops the image to low


    /**
     * The constructor sets the properties of the tool to their default values
     */
    public RectangleSelectionTool() {
        registerObservers();
        color = Color.black;
        initX = 0;
        initY = 0;
        currentX = 0;
        currentY = 0;
        mouseOriginX = 0;
        mouseOriginY = 0;
        drawWidthX = 0;
        drawHeightY = 0;
        xAxisMagnitudeDelta = 0;
        yAxisMagnitudeDelta = 0;
        squareWidth = DEFAULT_WIDTH_VALUE;
        fillShape = false;
        isCut = true;
    }

    /**
     * Checks to see where the user mouse is
     */
    @Override
    public void onMove(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        if (selectedCanvas != null) {
            int offset = 40;
            if (e.getY() >= initY - offset && e.getY() <= (selectedCanvas.getHeight() + initY - offset)
                    && e.getX() >= initX - offset && e.getX() <= (selectedCanvas.getWidth() + initX - offset)) {
                if (isDrawn) {
                    isOverSelection = true;
                    MouseCursor.dragCursor();
                }
            } else {
                doNothing = false;
                if (isDrawn) {
                    MouseCursor.targetCursor();
                } else {
                    MouseCursor.setDefaultCursor();
                }
                isOverSelection = false;
            }
        }
    }


    @Override
    public void onPress(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {


        if (SwingUtilities.isRightMouseButton(e)) {
            System.out.println("right click ");
            restartSelection();
            clearSelection2(canvas, drawingLayers);
            doNothing = true;
            //my code
        } else {

            if (!isDrawn) {
                MouseCursor.setDefaultCursor();
                canvas.getGraphics().setColor(color);
                currentX = e.getX();
                currentY = e.getY();
                initX = currentX;
                initY = currentY;
                mouseOriginX = currentX;
                mouseOriginY = currentY;
                originalSelectedCanvas = copyImage(canvas);
            } else {
                if (isCut)
                    clearSelection(canvas, drawingLayers);
                prevX = initX - e.getX();
                prevY = initY - e.getY();
                initializeVariables(canvas, e);
                isDragSelection = isOverSelection;
            }
        }
    }

    @Override
    public void onDrag(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {

        if (!doNothing) {
            if (!isDrawn) {
                if (previewLayer == null) {
                    previewLayer = DrawArea.getPreviewBufferedImage();
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
                previewLayerGraphics.setStroke(new BasicStroke(1f, BasicStroke.CAP_BUTT,
                        BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));

                calcSquareCoordinateData(e);

                previewLayerGraphics.drawRect(initX, initY, drawWidthX, drawHeightY);
                //info: https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html
                //draw the preview layer on top of the drawing layer(s)
                AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
                canvasGraphics.setComposite(alphaComposite);
                DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
                canvasGraphics.drawImage(previewLayer, 0, 0, null);
            } else {
                dragImage(canvas, e, drawingLayers);
            }
        }
    }

    @Override
    public void onRelease(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        if (!doNothing) {
            ImageLayer selectedLayer = getSelectedLayer(drawingLayers);

            if (!isDrawn) {
                isDrawn = true;
                int offset = 0;
                drawWidthX = drawWidthX + offset;
                drawHeightY = drawHeightY + offset;
                checkBounds(canvas);
                selectedCanvas = copyImage(selectedLayer.getBufferedImage().getSubimage(initX, initY,
                        drawWidthX, drawHeightY));
                DrawArea.clearBufferImageToTransparent(previewLayer);
            } else {
                MouseCursor.setDefaultCursor();
                isDrawn = false;
                if (isDragSelection) {
                    pasteDragSelection(canvas, drawingLayers, selectedLayer, e);
                } else {
                    pasteSelection(canvas, drawingLayers, selectedLayer, e);
                }
                selectedCanvas = null;
            }
        }


    }

    /**
     * if the user selects the canvas that goes out of bounds this function will correct the results
     */
//TODO needs fixing, doesnt get right and bottom end of the canvas properlly
    private void checkBounds(BufferedImage canvas) {
        if (initX + (drawWidthX) > canvas.getWidth()) {
            drawWidthX = (canvas.getWidth() - initX);

        }
        if (initX < 0) {
            drawWidthX = drawWidthX - Math.abs(initX);
            initX = 0;
        }
        if ((initY + (drawHeightY)) > canvas.getHeight()) {
            drawHeightY = Math.abs(canvas.getHeight() - initY);
        }
        if (initY < 0) {
            drawHeightY = drawHeightY - Math.abs(initY);
            initY = 0;
        }
        //canvas.getSubimage function will only take numbers >0 need minimum of 2 as we are going to subtract 1
        //in order to remove the preview border
        if (drawWidthX <= 1) {
            drawWidthX = 2;
        }
        if (drawHeightY <= 1) {
            drawHeightY = 2;
        }
    }

    public static BufferedImage copyImage(BufferedImage copyBuffer) {
        BufferedImage bufferedImage = new BufferedImage(copyBuffer.getWidth(), copyBuffer.getHeight(), copyBuffer.getType());
        Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
        g.drawImage(copyBuffer, 0, 0, null);
        g.dispose();
        return bufferedImage;
    }

    /**
     * clears the space where the user selected the canvas
     */
    private void clearSelection(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers) {
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        Graphics2D selectedLayerGraphics = (Graphics2D) selectedLayer.getBufferedImage().getGraphics();
        selectedLayerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        selectedLayerGraphics.setComposite(AlphaComposite.Clear);
        selectedLayerGraphics.setColor(transparentColor);
        selectedLayerGraphics.setStroke(new BasicStroke(0));
        int offset = 0;
        selectedLayerGraphics.fillRect(initX, initY, drawWidthX - offset, drawHeightY - offset);
        selectedLayerGraphics.drawRect(initX, initY, drawWidthX - offset, drawHeightY - offset);
        DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
    }

    private void clearSelection2(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers) {
        ImageLayer selectedLayer = getSelectedLayer(drawingLayers);
        Graphics2D selectedLayerGraphics = (Graphics2D) selectedLayer.getBufferedImage().getGraphics();
        selectedLayerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        selectedLayerGraphics.setComposite(AlphaComposite.Clear);
        selectedLayerGraphics.setColor(transparentColor);
        selectedLayerGraphics.setStroke(new BasicStroke(0));
        int offset = 0;
        selectedLayerGraphics.drawRect(initX, initY, drawWidthX - offset, drawHeightY - offset);
        //selectedLayerGraphics.fillRect(initX, initY, drawWidthX-offset, drawHeightY-offset);
        selectedLayerGraphics.drawRect(initX, initY, drawWidthX - offset, drawHeightY - offset);
        DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
    }


    /**
     * drags the selected canvas to its new location
     */
    private void dragImage(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        //init graphics objects
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        canvasGraphics.setColor(transparentColor);
        Graphics2D selectedCanvasGraphics = (Graphics2D) selectedCanvas.getGraphics();
        canvasGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        selectedCanvasGraphics.setColor(transparentColor);
        selectedCanvasGraphics.setComposite(alphaComposite);
        selectedCanvasGraphics.drawRect(prevX + e.getX(), prevY + e.getY(), drawWidthX, drawHeightY);
        //info: https://docs.oracle.com/javase/tutorial/2d/advanced/compositing.html
        //draw the preview layer on top of the drawing layer(s)
        canvasGraphics.setComposite(alphaComposite);
        DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        canvasGraphics.drawImage(selectedCanvas, prevX + e.getX(), prevY + e.getY(), null);
    }

    /**
     * places the selected canvas where the user has dragged it to its new location
     */
    private void pasteDragSelection(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers,
                                    ImageLayer selectedLayer, MouseEvent e) {
        //cut and paste the image into clicked location
        if (selectedLayer != null) {
            Graphics2D selectedLayerGraphics = (Graphics2D) selectedLayer.getBufferedImage().getGraphics();
            selectedLayerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            selectedLayerGraphics.setColor(color);
            selectedLayerGraphics.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));

            selectedLayerGraphics.drawImage(selectedCanvas, prevX + e.getX(), prevY + e.getY(), null);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
        isDrawn = false;
    }

    /**
     * cut and paste the image into clicked location
     */
    private void pasteSelection(BufferedImage canvas, LinkedList<ImageLayer> drawingLayers, ImageLayer selectedLayer, MouseEvent e) {
        if (selectedLayer != null) {
            Graphics2D selectedLayerGraphics = (Graphics2D) selectedLayer.getBufferedImage().getGraphics();
            selectedLayerGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            selectedLayerGraphics.setColor(color);
            selectedLayerGraphics.setStroke(new BasicStroke(3.0f, BasicStroke.CAP_BUTT,
                    BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
            selectedLayerGraphics.drawImage(selectedCanvas, e.getX(), e.getY(), null);
            DrawArea.drawLayersOntoCanvas(drawingLayers, canvas);
        }
        DrawArea.clearBufferImageToTransparent(selectedCanvas);
        isDrawn = false;
    }

    @Override
    public void onClick(BufferedImage canvas, MouseEvent e, LinkedList<ImageLayer> drawingLayers) {
        if (SwingUtilities.isRightMouseButton(e)) {
            System.out.println("right click ");
            restartSelection();
            clearSelection2(canvas, drawingLayers);
            doNothing = true;
            //my code
        } else {
            if (isDrawn) {
                isDrawn = false;
                doNothing = false;
                MouseCursor.setDefaultCursor();
                initializeVariables(canvas, e);
                pasteSelection(canvas, drawingLayers, null, e);
            }
        }
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
    public int getToolWidth() {
        return squareWidth;
    }

    @Override
    public void setToolWidth(int width) {
        squareWidth = width;
    }

    /**
     * getColor returns the current color the square tool is set to.
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

    public void setFillState(boolean fillState) {
        fillShape = fillState;
    }


    private void calcSquareCoordinateData(MouseEvent e) {
        // Get the coordinates of where the user released the mouse.
        currentX = e.getX();
        currentY = e.getY();

        // Draw the square with the longest side as long as the shortest side.
        xAxisMagnitudeDelta = Math.abs(currentX - mouseOriginX);
        yAxisMagnitudeDelta = Math.abs(currentY - mouseOriginY);

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

        // Handle cases where the square lies in a quadrant (with origin 0,0 at click) other than IV.
        if (currentY < mouseOriginY) {
            initY = mouseOriginY - drawHeightY;
        }
        if (currentX < mouseOriginX) {
            initX = mouseOriginX - drawWidthX;
        }
    }

    private void initializeVariables(BufferedImage canvas, MouseEvent e) {
        canvas.getGraphics().setColor(color);
        currentX = e.getX();
        currentY = e.getY();
        initX = currentX;
        initY = currentY;
        mouseOriginX = currentX;
        mouseOriginY = currentY;
        isCut = cutOption.isSelected();
    }


    public void restartSelection() {
        System.out.println("restart");
        MouseCursor.setDefaultCursor();
        //   DrawArea.drawLayersOntoCanvas(,originalSelectedCanvas);
        if (previewLayer != null)
            DrawArea.clearBufferImageToTransparent(previewLayer);
        if (selectedCanvas != null)
            DrawArea.clearBufferImageToTransparent(selectedCanvas);
        isOverSelection = false;
        isDrawn = false;

    }

    public void renderPanel() {

        selectionOptionPanel = new JPanel();
        selectionOptionPanel.setLayout(new GridBagLayout());

        rightClickInfo = new JLabel("Right click to cancel");
        rightClickInfo.setFont(new Font("David", Font.PLAIN, 24));
        rightClickInfo.setForeground(Color.RED);

        cutOption = new JRadioButton("Cut");
        cutOption.setSelected(true);
        cutOption.setFont(new Font("David", Font.PLAIN, 24));
        copyOption = new JRadioButton("Copy");
        copyOption.setFont(new Font("David", Font.PLAIN, 24));
        ButtonGroup group = new ButtonGroup();
        group.add(cutOption);
        group.add(copyOption);


        JPanel firstLine = new JPanel();
        firstLine.setLayout(new FlowLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        firstLine.add(cutOption, c);
        c.gridx = 1;
        c.gridy = 0;
        firstLine.add(copyOption, c);

        JPanel secondLine = new JPanel();
        secondLine.setLayout(new FlowLayout());
        c.gridx = 0;
        c.gridy = 1;
        secondLine.add(rightClickInfo, c);

        c.gridx = 0;
        c.gridy = 0;
        selectionOptionPanel.add(firstLine, c);

        c.gridx = 0;
        c.gridy = 1;
        selectionOptionPanel.add(secondLine, c);
        selectionOptionPanel.setBackground(Color.white);

        cutOption.addActionListener(actionListener);
        copyOption.addActionListener(actionListener);


    }

    public void hidePanel() {
        selectionOptionPanel.setVisible(false);
    }

    public void showPanel() {
        selectionOptionPanel.setVisible(true);
    }

    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == cutOption) {
                System.out.println("cut");
                isCut = true;
            }

            if (e.getSource() == copyOption) {
                System.out.println("copy");
                isCut = false;
            }
        }
    };

    public JPanel getSelectionOptionPanel() {
        return selectionOptionPanel;
    }
}