package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.model.*;

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
    private static final String HELP_MENU_BUTTON_TEXT = "Help";

    private JFrame mainFrame;
    private JMenu fileMenu;
    private JMenu editMenu;
    private JMenu imageMenu;
    private JMenu helpMenu;

    private static final String FNEW_MENU_BUTTON_TEXT = "New";
    private static final String FIMPORT_MENU_BUTTON_TEXT = "Import";
    private static final String FEXPORT_MENU_BUTTON_TEXT = "Export";
    private static final String FCLOSE_MENU_BUTTON_TEXT = "Close";
    private static final String FPRINT_MENU_BUTTON_TEXT = "Print";

    private JMenuItem fNew;
    private JMenuItem fImport;
    private JMenuItem fExport;
    private JMenuItem fClose;
    private JMenuItem fPrint;

    private static final String ESHORTCUTS_MENU_BUTTON_TEXT = "Shortcuts";

    private JMenuItem eKeyboardShortCuts;

    private JMenuItem iHueSaturation;
    private JMenuItem iBrightnessContrast;
    private JMenuItem iGeneratorsSubMenu;
    private JMenuItem iCheckerBoard;
    private JMenuItem iGreyscale;
    private JMenuItem iInvertColours;
    private JMenuItem iNoise;

    private static final String IHUESATURATION_MENU_BUTTON_TEXT = "Hue / Saturation";
    private static final String IBRIGHTNESSCONTRAST_MENU_BUTTON_TEXT = "Brightness / Contrast";
    private static final String IGENERATORSSUBMENU_MENU_BUTTON_TEXT = "Generators";
    private static final String ICHECKERBOARD_MENU_BUTTON_TEXT = "Checkerboard";
    private static final String IGREYSCALE_MENU_BUTTON_TEXT = "Greyscale";
    private static final String IINVERTCOLOURS_MENU_BUTTON_TEXT = "Invert Colours";
    private static final String INOISE_MENU_BUTTON_TEXT = "Noise";

    private JMenuItem hAbout;

    private static final String HABOUT_MENU_BUTTON_TEXT = "About";

    private DrawArea drawArea;
    private ImportExport importExport;
    private HueSaturationMenu hueSaturationMenu;
    private BrightnessContrastMenu brightnessContrastMenu;
    private GreyscaleMenu greyscaleMenu;
    private InvertMenu invertMenu;
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
                  ShortcutDialog keyboardShortCutPanel, PrintCanvas printCanvas, InvertMenu invertMenu) {
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
        this.invertMenu = invertMenu;

        prepareMenuBar();
    }

    /**
     * The prepareMenuBar function builds the menu and adds submenus.
     */
    private void prepareMenuBar() {
        fileMenu = new JMenu(FILE_MENU_BUTTON_TEXT);
        editMenu = new JMenu(EDIT_MENU_BUTTON_TEXT);
        imageMenu = new JMenu(IMAGE_MENU_BUTTON_TEXT);
        helpMenu = new JMenu(HELP_MENU_BUTTON_TEXT);

        add(fileMenu);
        add(editMenu);
        add(imageMenu);
        add(helpMenu);

        fNew = new JMenuItem(FNEW_MENU_BUTTON_TEXT);
        fImport = new JMenuItem(FIMPORT_MENU_BUTTON_TEXT);
        fExport = new JMenuItem(FEXPORT_MENU_BUTTON_TEXT);
        fClose = new JMenuItem(FCLOSE_MENU_BUTTON_TEXT);
        fPrint = new JMenuItem(FPRINT_MENU_BUTTON_TEXT);

        fileMenu.add(fNew);
        fileMenu.add(fImport);
        fileMenu.add(fExport);
        fileMenu.add(fPrint);
        fileMenu.add(fClose);

        eKeyboardShortCuts = new JMenuItem(ESHORTCUTS_MENU_BUTTON_TEXT);

        editMenu.add(eKeyboardShortCuts);

        iHueSaturation = new JMenuItem(IHUESATURATION_MENU_BUTTON_TEXT);
        iBrightnessContrast = new JMenuItem(IBRIGHTNESSCONTRAST_MENU_BUTTON_TEXT);
        iGeneratorsSubMenu = new JMenu(IGENERATORSSUBMENU_MENU_BUTTON_TEXT);
        iCheckerBoard = new JMenuItem(ICHECKERBOARD_MENU_BUTTON_TEXT);
        iGreyscale = new JMenuItem(IGREYSCALE_MENU_BUTTON_TEXT);
        iInvertColours = new JMenuItem(IINVERTCOLOURS_MENU_BUTTON_TEXT);
        iNoise = new JMenuItem(INOISE_MENU_BUTTON_TEXT);

        imageMenu.add(iHueSaturation);
        imageMenu.add(iBrightnessContrast);
        imageMenu.add(iGreyscale);
        imageMenu.add(iInvertColours);
        imageMenu.add(iGeneratorsSubMenu);
        iGeneratorsSubMenu.add(iNoise);
        iGeneratorsSubMenu.add(iCheckerBoard);


        hAbout = new JMenuItem(HABOUT_MENU_BUTTON_TEXT);

        helpMenu.add(hAbout);

        // MENU ACTION LISTENERS
        fExport.addActionListener(menuActionListener);
        fImport.addActionListener(menuActionListener);
        iGreyscale.addActionListener(menuActionListener);
        iInvertColours.addActionListener(menuActionListener);
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

            // Menu buttons
            if (e.getSource() == fExport) {
                importExport.exportImage();
            } else if (e.getSource() == fImport) {
                importExport.importImage();
            } else if (e.getSource() == iGreyscale) {
                greyscaleMenu.showWindow();
            } else if (e.getSource() == iInvertColours) {
                invertMenu.showWindow();
            } else if (e.getSource() == iHueSaturation) {
                hueSaturationMenu.showWindow();
            } else if (e.getSource() == iBrightnessContrast) {
                brightnessContrastMenu.showWindow();
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
                    ImageLayer.resetLayerNumber();
                    MainUI mainUI = new MainUI(CANVAS_WIDTH, CANVAS_HEIGHT);
                    mainUI.displayUI();
                    mainFrame.dispose();
                }
            } else {
                printCanvas.getPrintDimensionsDialog();
            }
        }
    };
}
