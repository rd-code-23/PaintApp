package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.ImportExport;
import com.teambeta.sketcherapp.model.AboutMenu;
import com.teambeta.sketcherapp.model.NewWindow;

import com.teambeta.sketcherapp.model.PrintCanvas;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * The MenuUI class adds a menu bar with submenus to the top of the application.
 */
public class MenuUI extends JMenuBar {
    private static final String FILE_MENU_BUTTON_TEXT = "File";
    private static final String EDIT_MENU_BUTTON_TEXT = "Edit";
    private static final String IMAGE_MENU_BUTTON_TEXT = "Image";
    private static final String WINDOW_MENU_BUTTON_TEXT = "Window";
    private static final String HELP_MENU_BUTTON_TEXT = "Help";
    public static final String ESHORTCUTS_MENU_BUTTON_TEXT = "Shortcuts";

    JFrame mainFrame;
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
    private JMenuItem eKeyboardShortCuts;

    private static final String EUNDO_MENU_BUTTON_TEXT = "Undo";
    private static final String EREDO_MENU_BUTTON_TEXT = "Redo";

    private JMenuItem iCanvasSize;
    private JMenuItem iRotateCanvas;
    private JMenuItem iColourMode;
    private JMenuItem iHueSaturation;
    private JMenuItem iBrightnessContrast;
    private JMenuItem iGeneratorsSubMenu;
    private JMenuItem iCheckerBoard;
    private JMenuItem iGreyscale;
    private JMenuItem iNoise;
    private JMenuItem iImport;
    private JMenuItem iExport;

    private static final String ICANVASSIZE_MENU_BUTTON_TEXT = "Canvas Size";
    private static final String IROTATECANVAS_MENU_BUTTON_TEXT = "Rotate Canvas";
    private static final String ICOLOURMODE_MENU_BUTTON_TEXT = "Colour Mode";
    private static final String IHUESATURATION_MENU_BUTTON_TEXT = "Hue / Saturation";
    private static final String IBRIGHTNESSCONTRAST_MENU_BUTTON_TEXT = "Brightness / Contrast";
    private static final String IGENERATORSSUBMENU_MENU_BUTTON_TEXT = "Generators";
    private static final String ICHECKERBOARD_MENU_BUTTON_TEXT = "Checkerboard";
    private static final String IGREYSCALE_MENU_BUTTON_TEXT = "Greyscale";
    private static final String INOISE_MENU_BUTTON_TEXT = "Noise";
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
    private ImportExport importExport;
    private HueSaturationMenu hueSaturationMenu;
    private BrightnessContrastMenu brightnessContrastMenu;
    private GreyscaleMenu greyscaleMenu;
    private AboutMenu aboutMenu;
    private NewWindow newWindow;
    
    private NoiseGeneratorMenu noiseGeneratorMenu;
    private CheckerboardMenu checkerboardMenu;
    private ShortcutDialog keyboardShortCutPanel;
    private PrintCanvas printCanvas;


    /**
     * Constructor
     */
    public MenuUI(JFrame mainFrame, DrawArea drawArea, ImportExport importExport, GreyscaleMenu greyscaleMenu,
                  HueSaturationMenu hueSaturationMenu, BrightnessContrastMenu brightnessContrastMenu,
                  NoiseGeneratorMenu noiseGeneratorMenu, CheckerboardMenu checkerboardMenu,
                 ShortcutDialog keyboardShortCutPanel, PrintCanvas printCanvas) {
        this.mainFrame = mainFrame;
        this.drawArea = drawArea;
        this.importExport = importExport;
        this.greyscaleMenu = greyscaleMenu;
        this.hueSaturationMenu = hueSaturationMenu;
        this.brightnessContrastMenu = brightnessContrastMenu;
        this.noiseGeneratorMenu = noiseGeneratorMenu;
        this.checkerboardMenu = checkerboardMenu;
        this.keyboardShortCutPanel = keyboardShortCutPanel;
        this.printCanvas = printCanvas;
        prepareMenuBar();
    }

    /**
     * The prepareMenuBar function builds the menu and adds submenus.
     */
    private void prepareMenuBar() {
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
        eKeyboardShortCuts = new JMenuItem(ESHORTCUTS_MENU_BUTTON_TEXT);

        editMenu.add(eUndo);
        editMenu.add(eRedo);
        editMenu.add(eKeyboardShortCuts);

        iCanvasSize = new JMenuItem(ICANVASSIZE_MENU_BUTTON_TEXT);
        iRotateCanvas = new JMenuItem(IROTATECANVAS_MENU_BUTTON_TEXT);
        iColourMode = new JMenuItem(ICOLOURMODE_MENU_BUTTON_TEXT);
        iHueSaturation = new JMenuItem(IHUESATURATION_MENU_BUTTON_TEXT);
        iBrightnessContrast = new JMenuItem(IBRIGHTNESSCONTRAST_MENU_BUTTON_TEXT);
        iGeneratorsSubMenu = new JMenu(IGENERATORSSUBMENU_MENU_BUTTON_TEXT);
        iCheckerBoard = new JMenuItem(ICHECKERBOARD_MENU_BUTTON_TEXT);
        iGreyscale = new JMenuItem(IGREYSCALE_MENU_BUTTON_TEXT);
        iNoise = new JMenuItem(INOISE_MENU_BUTTON_TEXT);
        iImport = new JMenuItem(IIMPORT_MENU_BUTTON_TEXT);
        iExport = new JMenuItem(IEXPORT_MENU_BUTTON_TEXT);

        imageMenu.add(iCanvasSize);
        imageMenu.add(iRotateCanvas);
        imageMenu.add(iColourMode);
        imageMenu.add(iHueSaturation);
        imageMenu.add(iBrightnessContrast);
        imageMenu.add(iGreyscale);
        imageMenu.add(iGeneratorsSubMenu);
        iGeneratorsSubMenu.add(iCheckerBoard);
        iGeneratorsSubMenu.add(iNoise);
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
        iGreyscale.addActionListener(menuActionListener);
        hAbout.addActionListener(menuActionListener);
        fClose.addActionListener(menuActionListener);
        fNew.addActionListener(menuActionListener);
        iHueSaturation.addActionListener(menuActionListener);
        iBrightnessContrast.addActionListener(menuActionListener);
        iNoise.addActionListener(menuActionListener);
        iCheckerBoard.addActionListener(menuActionListener);
        eKeyboardShortCuts.addActionListener(menuActionListener);
        fPrint.addActionListener(menuActionListener);
    }

    private ActionListener menuActionListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            // Export canvas menu button
            if (e.getSource() == iExport) {
                importExport.exportImage();
            } else if (e.getSource() == iImport) {
                importExport.importImage();
            } else if (e.getSource() == iGreyscale) {
                greyscaleMenu.showWindow();
            } else if (e.getSource() == iHueSaturation) {
                hueSaturationMenu.showWindow();
            } else if (e.getSource() == iBrightnessContrast) {
                brightnessContrastMenu.showWindow();
            }  else if (e.getSource() == iGreyscale) {
                greyscaleMenu.showWindow();
            } else if (e.getSource() == hAbout) {
                AboutMenu.prepareAbout();
            } else if (e.getSource() == iNoise) {
                noiseGeneratorMenu.showWindow();
            } else if (e.getSource() == iCheckerBoard) {
                checkerboardMenu.showWindow();
            } else if (e.getSource() == eKeyboardShortCuts) {
                keyboardShortCutPanel.renderPanel();
            } else if (e.getSource() == fClose) {
                System.exit(0);
            } else if (e.getSource() == fNew) {
                Dimension dimension = NewWindow.displayPrompt();
                if (dimension != null) {
                    final int CANVAS_WIDTH = (int) dimension.getWidth();
                    final int CANVAS_HEIGHT = (int) dimension.getHeight();
                    MainUI mainUI = new MainUI(CANVAS_WIDTH, CANVAS_HEIGHT);
                    mainUI.displayUI();
                    mainFrame.dispose();
                }
            } else if (e.getSource() == fPrint) {
                printCanvas.getPrintDimensionsDialog();
            }
        }
    };
}
