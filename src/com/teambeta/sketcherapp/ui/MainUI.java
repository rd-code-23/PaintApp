package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.Database.DB_KBShortcuts;
import com.teambeta.sketcherapp.drawingTools.*;
import com.teambeta.sketcherapp.model.ImportExport;
import com.teambeta.sketcherapp.model.Shortcuts;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Main UI class to wrap all GUI elements together.
 */
public class MainUI {
    private static final int PANEL_SECTION_SPACING = 20;
    private static final int WEST_PANEL_WIDTH = 120;
    private static final int COLOR_PANEL_HEIGHT = 200;
    private static LineTool lineTool;
    private static BrushTool brushTool;
    private static RectangleTool rectangleTool;
    private static EraserTool eraserTool;
    private static EllipseTool ellipseTool;
    private static TextTool textTool;
    private static PaintBucketTool paintBucketTool;
    private static EyeDropperTool eyeDropperTool;
    private static FanTool fanTool;
    private static CelticKnotTool celticKnotTool;
    private static DNATool dnaTool;
    private static AirBrushTool airBrushTool;
    private static TriangleTool triangleTool;
    private static DrawingTool selectedDrawingTool;
    private static EyeDropperStats eyeDropperStats;

    private static final String CLEAR_BUTTON_TEXT = "Clear";
    private static final String BRUSH_BUTTON_TEXT = "Brush";
    private static final String RECTANGLE_TOOL_BUTTON_TEXT = "Rectangle";
    private static final String LINE_TOOL_BUTTON_TEXT = "Line";
    private static final String ERASER_TOOL_BUTTON_TEXT = "Eraser";
    private static final String ELLIPSE_TOOL_BUTTON_TEXT = "Ellipse";
    private static final String TEXT_TOOL_BUTTON_TEXT = "Text";
    private static final String PAINT_BUCKET_BUTTON_TEXT = "Paint Bucket";
    private static final String EYEDROPPER_TOOL_BUTTON_TEXT = "Eye Dropper";
    private static final String FAN_TOOL_BUTTON_TEXT = "Fan-out";
    private static final String CELTIC_KNOT_TOOL_BUTTON_TEXT = "Celtic Knot";
    private static final String DNA_TOOL_BUTTON_TEXT = "DNA";
    private static final String AIR_BRUSH_TOOL_BUTTON_TEXT = "Airbrush";
    private static final String TRIANGLE_TOOL_BUTTON_TEXT = "Triangle";
    private JFrame mainFrame;

    private static final String APPLICATION_NAME = "Beta Paint";
    private static final int APPLICATION_WIDTH = 1920;
    private static final int APPLICATION_HEIGHT = 1080;
    private static final int EDITOR_PANEL_WIDTH = 300;
    private static final int EDITOR_PANEL_HEIGHT = 300;
    public static final int CANVAS_WIDTH = 1600;
    public static final int CANVAS_HEIGHT = 900;
    private static final String START_SOUND_PATH = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "res" + File.separator + "start-sound.wav";

    private JButton clearButton;
    private JButton brushToolButton;
    private JButton lineToolButton;
    private JButton rectangleToolButton;
    private JButton eraserToolButton;
    private JButton ellipseToolButton;
    private JButton eyeDropperToolButton;
    private JButton textToolButton;
    private JButton fanToolButton;
    private JButton celticKnotToolButton;
    private JButton paintBucketToolButton;
    private JButton dnaToolButton;
    private JButton airBrushToolButton;
    private JButton triangleToolButton;
    private static DrawArea drawArea;
    private static ColorChooser colorChooser;
    private WidthChanger widthChanger;
    private TextToolSettings textToolSettings;
    private ShortcutDialog keboardShortCutPanel;
    private Shortcuts shortcuts;
    private ImportExport importExport;
    private JPanel canvasTools;

    private DB_KBShortcuts db_kbShortcuts;

    private static final String APPLICATION_LOGO_IMAGE_DIRECTORY = "res/BPIcon.png";

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                if (drawArea.getCurrentlySelectedLayer().isVisible()) {
                    drawArea.clear();
                }
            } else if (e.getSource() == brushToolButton) {
                widthChanger.showPanel();
                selectedDrawingTool = brushTool;
                updateSizeSlider();
                drawArea.setColor(brushTool.getColor());
            } else if (e.getSource() == fanToolButton) {
                selectedDrawingTool = fanTool;
                updateSizeSlider();
            } else if (e.getSource() == lineToolButton) {
                selectedDrawingTool = lineTool;
                updateSizeSlider();
                drawArea.setColor(lineTool.getColor());
            } else if (e.getSource() == rectangleToolButton) {
                selectedDrawingTool = rectangleTool;
                updateSizeSlider();
                updateFillState(); // Tool supports filling
                drawArea.setColor(rectangleTool.getColor());
            } else if (e.getSource() == widthChanger.getJTextFieldComponent()) {
                widthChanger.setSliderComponent(widthChanger.getJTextFieldValue());
                widthChanger.setCurrentWidthValue(widthChanger.getJTextFieldValue());
                drawArea.setColor(brushTool.getColor());
            } else if (e.getSource() == ellipseToolButton) {
                selectedDrawingTool = ellipseTool;
                updateSizeSlider();
                updateFillState(); // Tool supports filling
                drawArea.setColor(ellipseTool.getColor());
            } else if (e.getSource() == eraserToolButton) {
                selectedDrawingTool = eraserTool;
                updateSizeSlider();
            } else if (e.getSource() == paintBucketToolButton) {
                selectedDrawingTool = paintBucketTool;
                updateSizeSlider();
            } else if (e.getSource() == eyeDropperToolButton) {
                selectedDrawingTool = eyeDropperTool;
            } else if (e.getSource() == celticKnotToolButton) {
                selectedDrawingTool = celticKnotTool;
                updateSizeSlider();
            } else if (e.getSource() == dnaToolButton) {
                selectedDrawingTool = dnaTool;
                updateSizeSlider();
            } else if (e.getSource() == airBrushToolButton) {
                selectedDrawingTool = airBrushTool;
                updateSizeSlider();
            } else if (e.getSource() == widthChanger.getCheckBoxGlobalSizeComponent()) {
                if (widthChanger.isGlobalSize()) {
                    widthChanger.setGlobalSize(false);
                } else {
                    widthChanger.setGlobalSize(true);
                }
            } else if (e.getSource() == textToolSettings.getFontSelector()) {
                textTool.setFont(textToolSettings.getFontFromSelector());
            } else if (e.getSource() == textToolSettings.getCaesarSelector()) {
                textTool.setCaesarConvert(textToolSettings.isCaesarSelected());
                if (textToolSettings.isCaesarSelected()) {
                    textToolSettings.enableCaesarShiftField(true);
                } else {
                    textToolSettings.enableCaesarShiftField(false);
                }
            } else if (e.getSource() == textToolSettings.getMorseCodeSelector()) {
                textTool.setMorseConvert(textToolSettings.isMorseSelected());
                if (!textToolSettings.isCaesarSelected()) {
                    textToolSettings.enableCaesarShiftField(false);
                } else {
                    textToolSettings.enableCaesarShiftField(true);
                }
            } else if (e.getSource() == widthChanger.getFillBox()) {
                widthChanger.setFill(!widthChanger.isFill());
                selectedDrawingTool.setFillState(widthChanger.isFill());
            } else if (e.getSource() == triangleToolButton) {
                selectedDrawingTool = triangleTool;
                updateSizeSlider();
                updateFillState();
            }

            /* We can also make it so that instead of hiding tool components when another is selected,
               have it so that components replace each other (depending on its position in the panel).

               Review 1: Default to hiding if the tool isn't the text tool. Use direct checks to see if the current tool
               is allowed to use the font dropdown menu. JButton objects currently are the only ones allowed to hide.
               Subclass JButton if we are going to use them for other purposes. Might as well add helper methods to this
               future subclass.
             */
            if (e.getSource() == textToolButton) {
                selectedDrawingTool = textTool;
                textToolSettings.setVisibility(true);
            } else if ((e.getSource() != textToolButton && e.getSource() != clearButton)
                    && (e.getSource() instanceof JButton)) {
                textToolSettings.setVisibility(false);
            }
        }
    };
    private LayersPanel layersPanel;

    /**
     * Constructor.
     */
    public MainUI() {
        initDrawingTools();
        prepareGUI();
    }


    /**
     * Create the drawing tool objects and set the pen tool as the default selection.
     */
    private void initDrawingTools() {
        textToolSettings = new TextToolSettings();
        lineTool = new LineTool();
        brushTool = new BrushTool();
        rectangleTool = new RectangleTool();
        eraserTool = new EraserTool(); // Requires drawArea due to requiring the canvas colour.
        ellipseTool = new EllipseTool();
        eyeDropperTool = new EyeDropperTool(); // Requires widthChanger UI element for direct text update.
        textTool = new TextTool();
        fanTool = new FanTool();
        celticKnotTool = new CelticKnotTool();
        paintBucketTool = new PaintBucketTool();
        dnaTool = new DNATool();
        airBrushTool = new AirBrushTool();
        triangleTool = new TriangleTool();
        selectedDrawingTool = brushTool;
    }

    public LayersPanel getLayersPanel() {
        return layersPanel;
    }

    /**
     * Class to listen for changes in the widthChanger slider.
     */
    public class listenForSlider implements ChangeListener {
        @Override
        public void stateChanged(ChangeEvent e) {
            if (e.getSource() == widthChanger.getSliderComponent()) {
                selectedDrawingTool.setToolWidth(widthChanger.getCurrentWidthValue());
                widthChanger.setCurrentWidthValue(selectedDrawingTool.getToolWidth());
                widthChanger.setJLabel();
            }
        }
    }

    public JFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(JFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * When a new brushTool is selected this method
     * will update the size panel to the current brush tool
     * values
     * if global is ticked then all the tool sizes change to current size
     * if global not ticked the component size can be different for each tool
     */
    public void updateSizeSlider() {
        if (!widthChanger.isGlobalSize()) {
            widthChanger.setCurrentWidthValue(selectedDrawingTool.getToolWidth());
            widthChanger.setSliderComponent(selectedDrawingTool.getToolWidth());
            widthChanger.setJLabel();
        } else {
            selectedDrawingTool.setToolWidth(widthChanger.getCurrentWidthValue());
            widthChanger.setCurrentWidthValue(selectedDrawingTool.getToolWidth());
            widthChanger.setJLabel();
        }
    }

    /**
     * When a new tool is selected, tools that support filling will set their
     * fill state to the fill checkbox.
     */
    public void updateFillState() {
        selectedDrawingTool.setFillState(widthChanger.isFill());
    }


    /**
     * Build main GUI.
     */
    private void prepareGUI() {
        mainFrame = new JFrame(APPLICATION_NAME);
        mainFrame.setIconImage(new ImageIcon(APPLICATION_LOGO_IMAGE_DIRECTORY).getImage());
        mainFrame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        mainFrame.getContentPane().setBackground(Color.DARK_GRAY);

        mainFrame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        mainFrame.getContentPane().setBackground(Color.DARK_GRAY);

        mainFrame.setLocationByPlatform(true);
        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        Container mainContent = mainFrame.getContentPane();
        mainContent.setLayout(new BorderLayout());
        drawArea = new DrawArea(this);

        importExport = new ImportExport(drawArea, this);
        BrightnessMenu brightnessMenu = new BrightnessMenu(drawArea);
        GreyscaleMenu greyscaleMenu = new GreyscaleMenu(drawArea);
        NoiseGeneratorMenu noiseGeneratorMenu = new NoiseGeneratorMenu(drawArea);
        CheckerboardMenu checkerboardMenu = new CheckerboardMenu(drawArea);

        JPanel drawAreaPanel = new JPanel();
        drawAreaPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = CANVAS_HEIGHT;
        c.ipadx = CANVAS_WIDTH;
        drawAreaPanel.setBackground(Color.decode("#222222"));
        drawAreaPanel.add(drawArea, c);
        mainContent.add(drawAreaPanel, BorderLayout.CENTER);

        /* START MAINUI BUTTONS */
        clearButton = new JButton(CLEAR_BUTTON_TEXT);
        brushToolButton = new JButton(BRUSH_BUTTON_TEXT);
        lineToolButton = new JButton(LINE_TOOL_BUTTON_TEXT);
        rectangleToolButton = new JButton(RECTANGLE_TOOL_BUTTON_TEXT);
        eraserToolButton = new JButton(ERASER_TOOL_BUTTON_TEXT);
        ellipseToolButton = new JButton(ELLIPSE_TOOL_BUTTON_TEXT);
        textToolButton = new JButton(TEXT_TOOL_BUTTON_TEXT);
        eyeDropperToolButton = new JButton(EYEDROPPER_TOOL_BUTTON_TEXT);
        fanToolButton = new JButton(FAN_TOOL_BUTTON_TEXT);
        celticKnotToolButton = new JButton(CELTIC_KNOT_TOOL_BUTTON_TEXT);
        paintBucketToolButton = new JButton(PAINT_BUCKET_BUTTON_TEXT);
        dnaToolButton = new JButton(DNA_TOOL_BUTTON_TEXT);
        airBrushToolButton = new JButton(AIR_BRUSH_TOOL_BUTTON_TEXT);
        triangleToolButton = new JButton(TRIANGLE_TOOL_BUTTON_TEXT);

        // Add a button to this array to register to actionListener and canvasTools
        // The order of this list determines the order of the buttons in the generated UI. Index -> 0 = Position -> First
        JButton[] buttonContainer = {
                clearButton, brushToolButton, airBrushToolButton, eraserToolButton, lineToolButton,
                fanToolButton, rectangleToolButton, ellipseToolButton, triangleToolButton, paintBucketToolButton,
                celticKnotToolButton, dnaToolButton, textToolButton, eyeDropperToolButton,
        };

        canvasTools = new JPanel();
        canvasTools.setLayout(new BoxLayout(canvasTools, BoxLayout.Y_AXIS));
        canvasTools.setBackground(Color.DARK_GRAY);
        canvasTools.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));

        // Register buttons to actionListener and canvasTools
        for (JButton button : buttonContainer) {
            button.addActionListener(actionListener);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            canvasTools.add(button);
            canvasTools.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        }
        canvasTools.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        /* END MAINUI BUTTONS */
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        //setting up the shortcuts and database
        shortcuts = new Shortcuts(canvasTools, this);
        db_kbShortcuts = new DB_KBShortcuts(shortcuts);
        keboardShortCutPanel = new ShortcutDialog(this, shortcuts);

        MenuUI menuUI = new MenuUI(drawArea, importExport, greyscaleMenu, brightnessMenu, noiseGeneratorMenu, checkerboardMenu, keboardShortCutPanel);

        northPanel.add(menuUI, BorderLayout.NORTH);

        JPanel toolSettings = new JPanel();
        toolSettings.setLayout(new BoxLayout(toolSettings, BoxLayout.X_AXIS));
        toolSettings.setBackground(Color.DARK_GRAY);
        toolSettings.add(Box.createRigidArea(new Dimension(WEST_PANEL_WIDTH, 0)));
        widthChanger = new WidthChanger();
        toolSettings.add(widthChanger.getGUI());
        northPanel.add(toolSettings, BorderLayout.CENTER);

        if (textToolSettings != null) {
            northPanel.add(textToolSettings, BorderLayout.EAST);
            textTool.setFont(textToolSettings.getFontFromSelector());
        }

        MainUI.listenForSlider listenForSlider = new MainUI.listenForSlider();
        widthChanger.getSliderComponent().addChangeListener(listenForSlider);
        widthChanger.getJTextFieldComponent().addActionListener(actionListener);
        textToolSettings.addActionListener(actionListener);
        widthChanger.getCheckBoxGlobalSizeComponent().addActionListener(actionListener);
        widthChanger.getFillBox().addActionListener(actionListener);

        JPanel colorPanel = new JPanel();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.setBackground(Color.DARK_GRAY);
        final Dimension CANVAS_TOOLS_MAX_SIZE = canvasTools.getMaximumSize();
        colorPanel.setMaximumSize(new Dimension((int) CANVAS_TOOLS_MAX_SIZE.getWidth(), COLOR_PANEL_HEIGHT));
        colorPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        colorChooser = new ColorChooser();

        JPanel colorChooserPanel = new JPanel();
        colorChooserPanel.setLayout(new GridBagLayout());
        GridBagConstraints colorChooserConstraints = new GridBagConstraints();
        colorChooserPanel.setBackground(Color.DARK_GRAY);
        colorChooserPanel.add(colorChooser, colorChooserConstraints);
        eyeDropperStats = new EyeDropperStats();
        colorChooserConstraints.gridx = 0;
        colorChooserConstraints.gridy = 1;
        colorChooserConstraints.insets = new Insets(PANEL_SECTION_SPACING, 0, 0, 0);
        colorChooserPanel.add(eyeDropperStats, colorChooserConstraints);
        colorPanel.add(colorChooserPanel);

        colorPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setPreferredSize(new Dimension(WEST_PANEL_WIDTH, 0));
        westPanel.setBackground(Color.DARK_GRAY);

        westPanel.add(canvasTools);
        westPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        westPanel.add(colorPanel);

        mainContent.add(westPanel, BorderLayout.WEST);

        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.setPreferredSize(new Dimension(EDITOR_PANEL_WIDTH, EDITOR_PANEL_HEIGHT));
        layersPanel = new LayersPanel(drawArea);
        editorPanel.add(layersPanel, BorderLayout.EAST);
        mainContent.add(editorPanel, BorderLayout.EAST);
        mainContent.add(northPanel, BorderLayout.NORTH);

        // Standard ActionListeners do not properly send updates to the text tool. PropertyChangeListeners work better.
        textToolSettings.getCaesarShiftField().addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                textTool.setCaesarShiftValue(textToolSettings.getCaesarShiftValue());
            }
        });

        if (db_kbShortcuts.isTableExists()) {
            generateDBDefaultKeyBindings();
            db_kbShortcuts.generateDBKeyBindings();
        } else {
            db_kbShortcuts.createTable();
            generateDefaultKeyBindings();
        }

    }

    /**
     * Return the currently selected drawing tool.
     *
     * @return selected drawing tool.
     */
    public static DrawingTool getSelectedDrawingTool() {
        return selectedDrawingTool;
    }

    /**
     * Display GUI.
     */
    public void displayUI() {
        mainFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        mainFrame.setLocationRelativeTo(null);  // positions GUI in center when opened
        mainFrame.setVisible(true);
        playStartUpSound();
    }

    /**
     * Returns eyedropper stats object.
     *
     * @return eyedropper stats object
     */
    public static ColorChooser getColorChooser() {
        return colorChooser;
    }

    /**
     * Temprorary fix to allow the eraser tool to work no matter the order of creation.
     *
     * @return The UI drawArea
     */
    public static DrawArea getDrawArea() {
        return drawArea;
    }

    /**
     * Play the start up sound when the application is launched or when user switches out of minimode
     */
    private void playStartUpSound() {
        File startUpSound = new File(START_SOUND_PATH);
        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    Clip clip = AudioSystem.getClip();
                    clip.open(AudioSystem.getAudioInputStream(startUpSound));
                    clip.start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    /**
     * generates the default key binding for shortcut keys
     */
    public void generateDefaultKeyBindings() {

        shortcuts.addKeyBinding(KeyEvent.VK_C, true, false, false, Shortcuts.CLEAR_TOOL_SHORTCUT, (evt) -> {
            drawArea.clear();
        });

        shortcuts.addKeyBinding(KeyEvent.VK_O, true, false, false, Shortcuts.EXPORT_SHORTCUT, (evt) -> {
            importExport.exportImage();
        });

        shortcuts.addKeyBinding(KeyEvent.VK_I, true, false, false, Shortcuts.IMPORT_SHORTCUT, (evt) -> {
            importExport.importImage();
        });

        shortcuts.addKeyBinding(KeyEvent.VK_B, true, false, false, Shortcuts.BRUSH_TOOL_SHORTCUT, (evt) -> {
            selectedDrawingTool = brushTool;
            updateSizeSlider();
        });

        shortcuts.addKeyBinding(KeyEvent.VK_L, true, false, false, Shortcuts.LINE_TOOL_SHORTCUT, (evt) -> {
            selectedDrawingTool = lineTool;
            updateSizeSlider();
        });
/*
        addKeyBinding(editorPanel, KeyEvent.VK_R, true, false, false, "RECT TOOL", (evt) -> {
            selectedDrawingTool = rectangleTool;
            updateFillState(); // Tool supports filling
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_E, true, false, false, "ELIPSE TOOL", (evt) -> {
            selectedDrawingTool = ellipseTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_Q, true, false, false, "ELIPSE TOOL", (evt) -> {
            selectedDrawingTool = ellipseTool;
            updateSizeSlider();
            updateFillState(); // Tool supports filling
        });
        addKeyBinding(editorPanel, KeyEvent.VK_E, true, false, false, "ERASER TOOL", (evt) -> {
            selectedDrawingTool = eraserTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_T, true, false, false, "TEXT TOOL", (evt) -> {
            selectedDrawingTool = textTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_P, true, false, false, "PAINTBUCKET TOOL", (evt) -> {
            selectedDrawingTool = paintBucketTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_F, true, false, false, "FAN TOOL", (evt) -> {
            selectedDrawingTool = fanTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_C, false, false, false, "CELTIC TOOL", (evt) -> {
            selectedDrawingTool = celticKnotTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_D, true, false, false, "DNA TOOL", (evt) -> {
            selectedDrawingTool = dnaTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_N, true, false, false, "EYE TOOL", (evt) -> {
            selectedDrawingTool = eyeDropperTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_A, true, false, false, "AIRBRUSH TOOL", (evt) -> {
            selectedDrawingTool = airBrushTool;
            updateSizeSlider();
        });*/
    }

    /**
     * gets the key binding for the shortcuts from the database
     */
    public void generateDBDefaultKeyBindings() {

        shortcuts.addKeyBinding(Shortcuts.getClearToolKeyCode(), Shortcuts.isAlt_clearTool(), Shortcuts.isShift_clearTool(), Shortcuts.isAlt_clearTool(), Shortcuts.CLEAR_TOOL_SHORTCUT, (evt) -> {
            drawArea.clear();
        });

        shortcuts.addKeyBinding(shortcuts.getExportKeyCode(), shortcuts.isCtrl_export(), shortcuts.isShift_export(), shortcuts.isAlt_export(), Shortcuts.EXPORT_SHORTCUT, (evt) -> {
            importExport.exportImage();
        });

        shortcuts.addKeyBinding(shortcuts.getImportKeyCode(), shortcuts.isCtrl_import(), shortcuts.isShift_import(), shortcuts.isAlt_import(), Shortcuts.IMPORT_SHORTCUT, (evt) -> {
            importExport.importImage();
        });

        shortcuts.addKeyBinding(shortcuts.getBrushToolKeyCode(), shortcuts.isCtrl_brushTool(), shortcuts.isShift_brushTool(), shortcuts.isAlt_brushTool(), shortcuts.BRUSH_TOOL_SHORTCUT, (evt) -> {
            selectedDrawingTool = brushTool;
            updateSizeSlider();
        });

        shortcuts.addKeyBinding(shortcuts.getLineToolKeyCode(), shortcuts.isCtrl_lineTool(), shortcuts.isShift_lineTool(), shortcuts.isAlt_lineTool(), Shortcuts.LINE_TOOL_SHORTCUT, (evt) -> {
            selectedDrawingTool = lineTool;
            updateSizeSlider();
        });
/*
        addKeyBinding(editorPanel, KeyEvent.VK_R, true, false, false, "RECT TOOL", (evt) -> {
            selectedDrawingTool = rectangleTool;
            updateFillState(); // Tool supports filling
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_E, true, false, false, "ELIPSE TOOL", (evt) -> {
            selectedDrawingTool = ellipseTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_Q, true, false, false, "ELIPSE TOOL", (evt) -> {
            selectedDrawingTool = ellipseTool;
            updateSizeSlider();
            updateFillState(); // Tool supports filling
        });
        addKeyBinding(editorPanel, KeyEvent.VK_E, true, false, false, "ERASER TOOL", (evt) -> {
            selectedDrawingTool = eraserTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_T, true, false, false, "TEXT TOOL", (evt) -> {
            selectedDrawingTool = textTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_P, true, false, false, "PAINTBUCKET TOOL", (evt) -> {
            selectedDrawingTool = paintBucketTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_F, true, false, false, "FAN TOOL", (evt) -> {
            selectedDrawingTool = fanTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_C, false, false, false, "CELTIC TOOL", (evt) -> {
            selectedDrawingTool = celticKnotTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_D, true, false, false, "DNA TOOL", (evt) -> {
            selectedDrawingTool = dnaTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_N, true, false, false, "EYE TOOL", (evt) -> {
            selectedDrawingTool = eyeDropperTool;
            updateSizeSlider();
        });
        addKeyBinding(editorPanel, KeyEvent.VK_A, true, false, false, "AIRBRUSH TOOL", (evt) -> {
            selectedDrawingTool = airBrushTool;
            updateSizeSlider();
        });*/
    }

    /**
     * Used in Shortcuts class to focus the canvas tools
     */
    public void focusCanvasTools() {
        canvasTools.setFocusable(true);
    }

    /**
     * Used in Shortcuts class to focus the canvas tools
     * this also makes the text field to be read only
     */
    public void focusWidthPanelToFalse() {
        widthChanger.getJTextFieldComponent().setFocusable(false);

    }

    /**
     * returns the database for the key board shortcuts
     */
    public DB_KBShortcuts getDb_kbShortcuts() {
        return db_kbShortcuts;
    }


}