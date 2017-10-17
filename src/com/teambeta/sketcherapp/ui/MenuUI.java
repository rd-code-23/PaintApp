package com.teambeta.sketcherapp.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * The MenuUI class adds a menu bar with submenus to the top of the application.
 */


public class MenuUI extends JMenuBar {


    private static final String FILE_MENU_BUTTON_TEXT = "File";
    private static final String EDIT_MENU_BUTTON_TEXT = "Edit";
    private static final String IMAGE_MENU_BUTTON_TEXT = "Image";
    private static final String WINDOW_MENU_BUTTON_TEXT = "Window";
    private static final String HELP_MENU_BUTTON_TEXT = "Help";


    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu imageMenu;
    private JMenu windowMenu;
    private JMenu helpMenu;


    private static final String FOPEN_MENU_BUTTON_TEXT = "Open";
    private static final String FNEW_MENU_BUTTON_TEXT = "New";
    private static final String FSAVE_MENU_BUTTON_TEXT = "Save";
    private static final String FSHARE_MENU_BUTTON_TEXT = "Share";
    private static final String FPRINT_MENU_BUTTON_TEXT = "Print";
    private static final String FCLOSE_MENU_BUTTON_TEXT = "Close";

    private JMenuItem fOpen;
    private JMenuItem fNew;
    private JMenuItem fSave;
    private JMenuItem fShare;
    private JMenuItem fPrint;
    private JMenuItem fClose;


    private JMenuItem eUndo;
    private JMenuItem eRedo;

    private static final String EUNDO_MENU_BUTTON_TEXT = "Undo";
    private static final String EREDO_MENU_BUTTON_TEXT = "Redo";


    private JMenuItem iCanvasSize;
    private JMenuItem iRotateCanvas;
    private JMenuItem iColourMode;
    private JMenuItem iImport;
    private JMenuItem iExport;

    private static final String ICANVASSIZE_MENU_BUTTON_TEXT = "Canvas Size";
    private static final String IROTATECANVAS_MENU_BUTTON_TEXT = "Rotate Canvas";
    private static final String ICOLOURMODE_MENU_BUTTON_TEXT = "Colour Mode";
    private static final String IIMPORT_MENU_BUTTON_TEXT = "Import";
    private static final String IEXPORT_MENU_BUTTON_TEXT = "Export";


    private JMenuItem wZoom;
    private JMenuItem wMinimize;
    private JMenuItem wNewWindow;

    private static final String WZOOM_MENU_BUTTON_TEXT = "Zoom";
    private static final String WMINIMIZE_MENU_BUTTON_TEXT = "Minimize";
    private static final String WNEWWINDOW_MENU_BUTTON_TEXT = "New Window";


    private JMenuItem hManual;
    private JMenuItem hDeveloperInfo;
    private JMenuItem hAbout;


    private static final String HMANUAL_MENU_BUTTON_TEXT = "Manual";
    private static final String HDEVELOPERINFO_MENU_BUTTON_TEXT = "Developer Info";
    private static final String HABOUT_MENU_BUTTON_TEXT = "About";

    private DrawArea drawArea;
    private  MainUI mainUI;

    private static final String EXPORT_CANVAS_DIALOG_TITLE = "Export Canvas";
    public static final String IMPORT_CANVAS_DIALOG_TITLE = "Import Canvas";

    /**
     * constructor
     */

    public MenuUI(DrawArea drawArea, MainUI mainUI) {

        this.drawArea = drawArea;
        this.mainUI = mainUI;
        prepareMenuBar();
    }

    /**
     * The prepareMenuBar function builds the menu and adds submenus.
     */
    public void prepareMenuBar() {

        fileMenu = new JMenu(FILE_MENU_BUTTON_TEXT);
        editMenu = new JMenu(EDIT_MENU_BUTTON_TEXT);
        imageMenu = new JMenu(IMAGE_MENU_BUTTON_TEXT);
        windowMenu = new JMenu(WINDOW_MENU_BUTTON_TEXT);
        helpMenu = new JMenu(HELP_MENU_BUTTON_TEXT);

        add(fileMenu);
        add(editMenu);
        add(imageMenu);
        add(windowMenu);
        add(helpMenu);

        fOpen = new JMenuItem(FOPEN_MENU_BUTTON_TEXT);
        fNew = new JMenuItem(FNEW_MENU_BUTTON_TEXT);
        fSave = new JMenuItem(FSAVE_MENU_BUTTON_TEXT);
        fShare = new JMenuItem(FSHARE_MENU_BUTTON_TEXT);
        fPrint = new JMenuItem(FPRINT_MENU_BUTTON_TEXT);
        fClose = new JMenuItem(FCLOSE_MENU_BUTTON_TEXT);

        fileMenu.add(fOpen);
        fileMenu.add(fNew);
        fileMenu.add(fSave);
        fileMenu.add(fShare);
        fileMenu.add(fPrint);
        fileMenu.add(fClose);

        eUndo = new JMenuItem(EUNDO_MENU_BUTTON_TEXT);
        eRedo = new JMenuItem(EREDO_MENU_BUTTON_TEXT);

        editMenu.add(eUndo);
        editMenu.add(eRedo);

        iCanvasSize = new JMenuItem(ICANVASSIZE_MENU_BUTTON_TEXT);
        iRotateCanvas = new JMenuItem(IROTATECANVAS_MENU_BUTTON_TEXT);
        iColourMode = new JMenuItem(ICOLOURMODE_MENU_BUTTON_TEXT);
        iImport = new JMenuItem(IIMPORT_MENU_BUTTON_TEXT);
        iExport = new JMenuItem(IEXPORT_MENU_BUTTON_TEXT);

        imageMenu.add(iCanvasSize);
        imageMenu.add(iRotateCanvas);
        imageMenu.add(iColourMode);
        imageMenu.add(iImport);
        imageMenu.add(iExport);

        wZoom = new JMenuItem(WZOOM_MENU_BUTTON_TEXT);
        wNewWindow = new JMenuItem(WNEWWINDOW_MENU_BUTTON_TEXT);
        wMinimize = new JMenuItem(WMINIMIZE_MENU_BUTTON_TEXT);

        windowMenu.add(wZoom);
        windowMenu.add(wNewWindow);
        windowMenu.add(wMinimize);

        hManual = new JMenuItem(HMANUAL_MENU_BUTTON_TEXT);
        hDeveloperInfo = new JMenuItem(HDEVELOPERINFO_MENU_BUTTON_TEXT);
        hAbout = new JMenuItem(HABOUT_MENU_BUTTON_TEXT);

        helpMenu.add(hManual);
        helpMenu.add(hDeveloperInfo);
        helpMenu.add(hAbout);

        // MENU ACTION LISTENERS
        iExport.addActionListener(menuActionListener);
        iImport.addActionListener(menuActionListener);

    }

    private ActionListener menuActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Export canvas menu button
            if (e.getSource() == iExport) {
                exportImage();
            }
            if (e.getSource() == iImport) {
                 importImage();
            }
        }

    };


    public void exportImage(){

        String userDir = System.getProperty("user.home");
        JFileChooser fileChooser = new JFileChooser(userDir +"/Desktop");
        fileChooser.setDialogTitle(EXPORT_CANVAS_DIALOG_TITLE);
        int retrieval = fileChooser.showSaveDialog(null);

        if (retrieval == JFileChooser.APPROVE_OPTION) {
            File file = null;
            //write image to a file
            try {
                file = new File(fileChooser.getSelectedFile() + ".png");
                ImageIO.write(drawArea.getCanvas(), "png", file);
            } catch (IOException exc) {
                exc.printStackTrace();
            }
        }

    }

    public void importImage(){
            String userDir = System.getProperty("user.home");
            JFileChooser fileChooser = new JFileChooser(userDir +"/Desktop");
            //fileChooser.setCurrentDirectory(workingDirectory);
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

                if( drawArea.isCanvasAltered() == true)   {

                    //Custom button text
                    Object[] options = {"Save",
                            "Dont Save",
                            "Cancel"};
                    int userOption = JOptionPane.showOptionDialog(mainUI.getMainFrame(),
                            "Save your work?",
                            "",
                            JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,
                            null,
                            options,
                            options[2]);

                    if(userOption == 0){
                        exportImage();
                    }


                }


                drawArea.setImportedImage(image);

            }
        }



}

