package com.teambeta.sketcherapp.model;

import com.teambeta.sketcherapp.ui.DrawArea;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.LinkedList;

public class PrintCanvas implements Printable {
    private static final Color transparentColor = new Color(0x00FFFFFF, true);
    DrawArea drawArea;

    int scaledWidth;
    int scaledHeight;

    public PrintCanvas(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    public void getPrintDimensionsDialog() {
        boolean isValidDimension = false;
        boolean isPrint = false;
        while (!isValidDimension) {

            JTextField newWidth = new JTextField(5);
            JTextField newHeight = new JTextField(5);
            JPanel myPanel = new JPanel();

            myPanel.add(new JLabel("Width:"));
            myPanel.add(newWidth);
            myPanel.add(Box.createHorizontalStrut(10)); // a spacer
            myPanel.add(new JLabel("Height:"));
            myPanel.add(newHeight);

            int result = JOptionPane.showConfirmDialog(null, myPanel,
                    "Print Dimensions", JOptionPane.OK_CANCEL_OPTION);
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
        } // end of while

        if (isPrint) {
            print();
        }
    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {

        //print only 1 page
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }

        Graphics2D g = (Graphics2D) graphics;
        BufferedImage canvas = new BufferedImage(scaledWidth, scaledHeight, BufferedImage.TYPE_INT_RGB);
        drawLayersOntoCanvas(canvas,drawArea);
        g.drawImage(canvas, 0, 0, null);

        return PAGE_EXISTS;

    }

    private  void drawLayersOntoCanvas(BufferedImage[] layers, BufferedImage canvas) {
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

    public  void drawLayersOntoCanvas(BufferedImage canvas,DrawArea drawArea) {
        LinkedList<ImageLayer> layers = drawArea.getDrawingLayers();

        BufferedImage[] bufferedImages = new BufferedImage[layers.size()];
        for (int i = 0; i < bufferedImages.length; i++) {
            if (layers.get(i).isVisible()) {
                bufferedImages[i] = getScaledImage(layers.get(i).getBufferedImage(), scaledWidth, scaledHeight);
            }
        }
        drawLayersOntoCanvas(bufferedImages, canvas);
    }

    //TAKEN FROM
    //https://stackoverflow.com/questions/11367324/how-do-i-scale-a-bufferedimage?noredirect=1&lq=1
    private BufferedImage getScaledImage(Image srcImg, int w, int h) {
        BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TRANSLUCENT);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(srcImg, 0, 0, w, h, null);
        g2.dispose();
        return resizedImg;
    }

    public static void clearBufferImageToTransparent(BufferedImage bufferedImage) {
        Graphics2D graphics = (Graphics2D) bufferedImage.getGraphics();
        graphics.setComposite(AlphaComposite.Src);
        graphics.setColor(transparentColor);
        graphics.setBackground(transparentColor);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
    }



    public boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public void print() {
        PrinterJob job = PrinterJob.getPrinterJob(); //Get the printer's job list
        job.setPrintable(this); //We print with this class (btnPrintAction, which implements Printable)
        if (job.printDialog() == true) { //If we clicked OK in the print dialog
            try {
                job.print();
            } catch (PrinterException ex) {


            }
        }
    }

}

