package com.teambeta.sketcherapp.model;

import com.teambeta.sketcherapp.ui.DrawArea;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.LinkedList;

public class PrintCanvas implements Printable {

    private static final Color transparentColor = new Color(0x00FFFFFF, true);
    private static final String WIDTH_PRINT_DIALOG = "Width:";
    private static final String HEIGHT_PRINT_DIALOG = "Height:";
    private static final int COLUMN_WIDTH_PRINT_DIALOG = 5;
    private static final int SPACE_TEXTFIELD_PRINT_DIALOG = 10;
    private static final String PRINT_DIMENSIONS_PRINT_DIALOG = "Print Dimensions";

    private DrawArea drawArea;
    private int scaledWidth;
    private int scaledHeight;

    public PrintCanvas(DrawArea drawArea) {
        this.drawArea = drawArea;
    }
    boolean isValidDimension = false;
    boolean isPrint = false;
    JButton autoFit = new JButton("Letter Size");
    /**
     * dialog to get the users preferred print dimensions
     */
    public void getPrintDimensionsDialog() {



//TODO USE DIALOG
        while (!isValidDimension) {
            JTextField newWidth = new JTextField(COLUMN_WIDTH_PRINT_DIALOG);
            JTextField newHeight = new JTextField(COLUMN_WIDTH_PRINT_DIALOG);
            JLabel instructions = new JLabel("Enter preferred print size or auto-fit to Letter Size");



            JPanel firstLine = new JPanel();
            firstLine.setLayout(new FlowLayout());
            GridBagConstraints c = new GridBagConstraints();
          //  c.anchor = GridBagConstraints.LINE_START;
            c.weightx = 0;
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = 0;
            firstLine.add(instructions, c);

            JPanel secondLine = new JPanel();
            secondLine.setLayout(new FlowLayout());
            GridBagConstraints c2 = new GridBagConstraints();
            c2.gridx = 0;
            c2.gridy = 0;
            secondLine.add(new JLabel(WIDTH_PRINT_DIALOG), c2);
            c2.gridx = 1;
            c2.gridy = 0;
            secondLine.add(newWidth, c2);
            c2.gridx = 2;
            c2.gridy = 0;
            secondLine.add(Box.createHorizontalStrut(SPACE_TEXTFIELD_PRINT_DIALOG), c2);
            c2.gridx = 3;
            c2.gridy = 0;
            secondLine.add(new JLabel(HEIGHT_PRINT_DIALOG), c2);
            c2.gridx = 4;
            c2.gridy = 0;
            secondLine.add(newHeight,c2);
            c2.gridx = 5;
            c2.gridy = 0;
            secondLine.add(Box.createHorizontalStrut(SPACE_TEXTFIELD_PRINT_DIALOG), c2);
            secondLine.add(autoFit,c2);

            JPanel mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            GridBagConstraints c3 = new GridBagConstraints();
            c3.fill = GridBagConstraints.VERTICAL;
            c3.gridx = 0;
            c3.gridy = 0;
            mainPanel.add(firstLine,c3);
            c3.gridx = 0;
            c3.gridy = 1;
            mainPanel.add(secondLine,c3);




            ActionListener actionListener = new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource() == autoFit ) {
                        System.out.println("autofit");
                        isValidDimension = true;
                        isPrint = true;
                        scaledHeight = 350;
                        scaledWidth = 350;
                    }
                }
            };
            autoFit.addActionListener(actionListener);
         /*     myPanel.add(Box.createHorizontalStrut(SPACE_TEXTFIELD_PRINT_DIALOG), c);
            c.gridx = 2;
            c.gridy = 1;
            myPanel.add(new JLabel(HEIGHT_PRINT_DIALOG), c);
             myPanel.add(newHeight,c);
            c.gridx = 0;
            c.gridy = 2;
            JButton autoFit = new JButton("Letter Size");
            myPanel.add(autoFit,c);(*/


            int result = JOptionPane.showConfirmDialog(null, mainPanel,
                    PRINT_DIMENSIONS_PRINT_DIALOG, JOptionPane.OK_CANCEL_OPTION);
            if (result == JOptionPane.OK_OPTION) {
                if (isInteger(newWidth.getText()) && isInteger(newHeight.getText())) {
                    isValidDimension = true;
                    isPrint = true;
                    scaledHeight = Integer.parseInt(newHeight.getText());
                    scaledWidth = Integer.parseInt(newWidth.getText());
                }
            }

            if (result == JOptionPane.CANCEL_OPTION || result == JOptionPane.CLOSED_OPTION) {
                isPrint = false;
                break;
            }

            if(autoFit.isSelected()){
                newWidth.setText("");

            }

        } // end of while

        if (isPrint) {
            print();
        }
    }


    /**
     * setups the print job
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        //print only 1 page
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g = (Graphics2D) graphics;
        BufferedImage canvas = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        drawLayersOntoCanvas(canvas, drawArea);
        g.drawImage(canvas, 0, 0, null);
        return PAGE_EXISTS;
    }

    /**
     * scales the canvas
     * TAKEN FROM
     * https://stackoverflow.com/questions/11367324/how-do-i-scale-a-bufferedimage?noredirect=1&lq=1
     */
    private BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    /**
     * checks if user input is an integer
     */
    private boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    /**
     * prints the canvas
     */
    private void print() {
        PrinterJob job = PrinterJob.getPrinterJob(); //Get the printer's job list

        job.setPrintable(this);
        if (job.printDialog() == true) {
            try {
                job.print();
            } catch (PrinterException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Draws the provided layers onto the provided canvasBufferedImage.
     *
     * @param layers the layers to draw
     * @param canvas the canvasBufferedImage to draw to
     */
    private void drawLayersOntoCanvas(BufferedImage[] layers, BufferedImage canvas) {
        int g;
        Graphics2D canvasGraphics = (Graphics2D) canvas.getGraphics();
        //clear the canvasBufferedImage.
        clearBufferImageToTransparent(canvas);
        //draw the layers in order
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
        canvasGraphics.setComposite(alphaComposite);
        for (BufferedImage layer : layers) {
            if (layer != null) {
                canvasGraphics.drawImage(layer, 0, 0, null);
            }
        }

    }

    /**
     * Draws the provided layers onto the provided canvasBufferedImage.
     */
    private void drawLayersOntoCanvas(BufferedImage canvas, DrawArea drawArea) {
        LinkedList<ImageLayer> layers = drawArea.getDrawingLayers();

        BufferedImage[] bufferedImages = new BufferedImage[layers.size()];
        for (int i = 0; i < bufferedImages.length; i++) {
            if (layers.get(i).isVisible()) {
                bufferedImages[i] = getScaledImage(layers.get(i).getBufferedImage(), scaledWidth, scaledHeight);
            }
        }
        drawLayersOntoCanvas(bufferedImages, canvas);
    }

    /**
     * Clears buffer image.
     *
     * @param bufferedImage canvasBufferedImage to clear.
     */
    private static void clearBufferImageToTransparent(BufferedImage bufferedImage) {
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.setColor(transparentColor);
        graphics.setBackground(transparentColor);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }

}

