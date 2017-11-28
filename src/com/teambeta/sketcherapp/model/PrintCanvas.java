package com.teambeta.sketcherapp.model;
import com.teambeta.sketcherapp.ui.DrawArea;
import com.teambeta.sketcherapp.ui.MainUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.print.*;
import java.util.LinkedList;
public class PrintCanvas implements Printable {
    private static final Color transparentColor = new Color(0x00FFFFFF, true);
    private static final String WIDTH_PRINT_DIALOG = "Width:";
    private static final String HEIGHT_PRINT_DIALOG = "Height:";
    private static final int COLUMN_WIDTH_PRINT_DIALOG = 5;
    private static final int SPACE_TEXTFIELD_PRINT_DIALOG = 10;
    private static final String PRINT_DIMENSIONS_PRINT_DIALOG = "Print Dimensions";
    private static final int SHORTCUT_DIALOG_WIDTH = 400;
    private static final int SHORTCUT_DIALOG_HEIGHT = 400;
    private static final String INSTRUCTION_PRINT_DIALOG = "Enter preferred print size or use auto-fit";
    private static final String AUTO_FIT_PRINT_DIALOG = "Auto-fit";
    private static final String OK_BUTTON_TEXT_PRINT_DIALOG = "Ok";
    private static final String CANCEL_BUTTON_TEXT_PRINT_DIALOG = "Cancel";
    private static final int WIDTH_DEFAULT_LETTER_SIZE = 575;
    private static final int HEIGHT_DEFAULT_LETTER_SIZE = 775;
    private static final String ERROR_MESSAGE_PRINT_DIALOG = "Inputs must be an integer";
    private DrawArea drawArea;
    private MainUI mainUI;
    private int scaledWidth;
    private int scaledHeight;
    private JDialog printDimensionDialog;
    private JLabel notValidPrintDimensionLabel = new JLabel();
    private boolean isPrint = false;
    private JButton autoFitButton;
    private JButton okButton;
    private JButton cancelButton;
    private JTextField newWidth;
    private JTextField newHeight;
    private JLabel instructions;
    public PrintCanvas(DrawArea drawArea, MainUI mainUI) {
        this.drawArea = drawArea;
        this.mainUI = mainUI;
    }
    /**
     * dialog to get the users preferred print dimensions
     */
    public void getPrintDimensionsDialog() {
        newWidth = new JTextField(COLUMN_WIDTH_PRINT_DIALOG);
        newHeight = new JTextField(COLUMN_WIDTH_PRINT_DIALOG);
        instructions = new JLabel(INSTRUCTION_PRINT_DIALOG);
        autoFitButton = new JButton(AUTO_FIT_PRINT_DIALOG);
        okButton = new JButton(OK_BUTTON_TEXT_PRINT_DIALOG);
        cancelButton = new JButton(CANCEL_BUTTON_TEXT_PRINT_DIALOG);
        printDimensionDialog = new JDialog(mainUI.getMainFrame(), PRINT_DIMENSIONS_PRINT_DIALOG, true);
        printDimensionDialog.setLocationRelativeTo(null);
        printDimensionDialog.setSize(SHORTCUT_DIALOG_WIDTH, SHORTCUT_DIALOG_HEIGHT);
        printDimensionDialog.setLayout(new GridBagLayout());
        isPrint = false;
        notValidPrintDimensionLabel.setText("");
        JPanel firstLine = new JPanel();
        firstLine.setLayout(new FlowLayout());
        GridBagConstraints c = new GridBagConstraints();
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
        secondLine.add(newHeight, c2);
        c2.gridx = 5;
        c2.gridy = 0;
        secondLine.add(Box.createHorizontalStrut(SPACE_TEXTFIELD_PRINT_DIALOG), c2);
        secondLine.add(autoFitButton, c2);
        JPanel thirdLine = new JPanel();
        firstLine.setLayout(new FlowLayout());
        GridBagConstraints c3 = new GridBagConstraints();
        c3.gridx = 0;
        c3.gridy = 0;
        thirdLine.add(notValidPrintDimensionLabel, c3);
        JPanel fourthLine = new JPanel();
        firstLine.setLayout(new FlowLayout());
        GridBagConstraints c4 = new GridBagConstraints();
        c4.gridx = 0;
        c4.gridy = 0;
        fourthLine.add(okButton, c4);
        c4.gridx = 1;
        c4.gridy = 0;
        fourthLine.add(cancelButton, c4);
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridBagLayout());
        GridBagConstraints c5 = new GridBagConstraints();
        c5.fill = GridBagConstraints.VERTICAL;
        c5.gridx = 0;
        c5.gridy = 0;
        mainPanel.add(firstLine, c5);
        c5.gridx = 0;
        c5.gridy = 1;
        mainPanel.add(secondLine, c5);
        c5.gridx = 0;
        c5.gridy = 2;
        mainPanel.add(thirdLine, c5);
        c5.gridx = 0;
        c5.gridy = 3;
        mainPanel.add(fourthLine, c5);
        autoFitButton.addActionListener(actionListener);
        okButton.addActionListener(actionListener);
        cancelButton.addActionListener(actionListener);
        printDimensionDialog.add(mainPanel, c5);
        printDimensionDialog.setVisible(true);
        if (isPrint) {
            print();
        }
    }
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == autoFitButton) {
                isPrint = true;
                scaledHeight = HEIGHT_DEFAULT_LETTER_SIZE;
                scaledWidth = WIDTH_DEFAULT_LETTER_SIZE;
                printDimensionDialog.dispose();
            }
            if (e.getSource() == okButton) {
                if (isInteger(newWidth.getText()) && isInteger(newHeight.getText())) {
                    isPrint = true;
                    scaledHeight = Integer.parseInt(newHeight.getText());
                    scaledWidth = Integer.parseInt(newWidth.getText());
                    printDimensionDialog.dispose();
                } else {
                    notValidPrintDimensionLabel.setForeground(Color.red);
                    notValidPrintDimensionLabel.setOpaque(true);
                    notValidPrintDimensionLabel.setText(ERROR_MESSAGE_PRINT_DIALOG);
                }
            }
            if (e.getSource() == cancelButton) {
                isPrint = false;
                printDimensionDialog.dispose();
            }
        }
    };
    /**
     * setups the print job
     */
    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) {
        //print only 1 page
        if (pageIndex > 0) {
            return NO_SUCH_PAGE;
        }
        BufferedImage canvas = new BufferedImage(drawArea.getWidth(), drawArea.getHeight(), BufferedImage.TYPE_INT_RGB);
        double imageableX = pageFormat.getImageableX();
        double imageableY = pageFormat.getImageableY();
        double imageableWidth = pageFormat.getImageableWidth();
        int pageWidth = 0;
        int pageHeight = 0;
        drawLayersOntoCanvas(canvas, drawArea);
        Graphics2D g = (Graphics2D) graphics;
        //calculation was taken from
        // https://stackoverflow.com/questions/18404553/proper-way-of-printing-a-bufferedimage-in-java
        if (pageFormat.getOrientation() == PageFormat.PORTRAIT) {
            pageWidth = (int) Math.min(imageableWidth, (double) canvas.getWidth());
            pageHeight = pageWidth * canvas.getHeight() / canvas.getWidth();
        } else {
            pageWidth = (int) Math.min(imageableWidth, (double) canvas.getWidth());
            pageHeight = pageWidth * canvas.getHeight() / canvas.getWidth();
        }
        g.drawImage(canvas, (int) imageableX, (int) imageableY, pageWidth, pageHeight, null);
        return PAGE_EXISTS;
    }

    public static BufferedImage rotateClockwise90(BufferedImage src) {
        int w = src.getWidth();
        int h = src.getHeight();
        BufferedImage dest = new BufferedImage(h, w, src.getType());
        for (int y = 0; y < h; y++)
            for (int x = 0; x < w; x++)
                dest.setRGB(y, x, src.getRGB(x, y));
        return dest;
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
    public void print() {
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
                bufferedImages[i] = layers.get(i).getBufferedImage();
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
