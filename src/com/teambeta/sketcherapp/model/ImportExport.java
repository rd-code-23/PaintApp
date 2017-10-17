package com.teambeta.sketcherapp.model;

import com.teambeta.sketcherapp.Main;
import com.teambeta.sketcherapp.ui.DrawArea;
import com.teambeta.sketcherapp.ui.MainUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class ImportExport {

    private static final String EXPORT_CANVAS_DIALOG_TITLE = "Export Canvas";
    private static final String IMPORT_CANVAS_DIALOG_TITLE = "Import Canvas";
    private static final String USER_HOME = "user.home";
    private static final String USER_DESKTOP = "/Desktop";
    private static final String PNG_EXTENSION = ".png";
    private static final String PNG = "png";
    private static final String JOPTION_SAVE = "Save";
    private static final String JOPTION_DONT_SAVE = "Dont Save";
    private static final String JOPTION_CANCEL = "Cancel";
    private static final String JOPTION_SAVE_YOUR_WORK = "Save your work?";

    private DrawArea drawArea;
    private MainUI mainUI;

    /**
     * Constructor so we can can access methods from other classes
     * @param drawArea
     * @param mainUI
     */
    public ImportExport(DrawArea drawArea, MainUI mainUI) {
        this.drawArea = drawArea;
        this.mainUI = mainUI;
    }

    /**
     * lets user export an image into PNG to their computer
     */
    public void exportImage() {
        String userDir = System.getProperty(USER_HOME);
        JFileChooser fileChooser = new JFileChooser(userDir + USER_DESKTOP);
        fileChooser.setDialogTitle(EXPORT_CANVAS_DIALOG_TITLE);
        int retrieval = fileChooser.showSaveDialog(null);

        if (retrieval == JFileChooser.APPROVE_OPTION) {
            File file = null;
            //write image to a file
            try {
                file = new File(fileChooser.getSelectedFile() + PNG_EXTENSION);
                ImageIO.write(drawArea.getCanvas(), PNG, file);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }

        drawArea.setCanvasAltered(false);
    }

    /**
     * lets user import an image from their computer and into the canvas
     * if user has altered the canvas without saving then
     * the program prompts a message to let them save
     */
    public void importImage() {
        String userDir = System.getProperty(USER_HOME);
        JFileChooser fileChooser = new JFileChooser(userDir + USER_DESKTOP);
        fileChooser.setDialogTitle(IMPORT_CANVAS_DIALOG_TITLE);
        int retrieval = fileChooser.showOpenDialog(null);

        if (retrieval == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            BufferedImage image = null;
            File imageFile = new File(selectedFile.getAbsolutePath());
            try {
                image = ImageIO.read(imageFile);
            } catch (IOException exc) {
                exc.printStackTrace();
            }

            if (drawArea.isCanvasAltered() == true) {

                Object[] options = {JOPTION_SAVE,
                        JOPTION_DONT_SAVE,
                        JOPTION_CANCEL};
                int userOption = JOptionPane.showOptionDialog(mainUI.getMainFrame(),
                        JOPTION_SAVE_YOUR_WORK,
                        "",
                        JOptionPane.YES_NO_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        options,
                        options[2]);

                if (userOption == 0) {
                    exportImage();
                }

            }

            drawArea.setImportedImage(image);

        }
    }

}
