package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.Database.DB_KBShortcuts;
import com.teambeta.sketcherapp.drawingTools.*;
import com.teambeta.sketcherapp.model.ImportExport;
import com.teambeta.sketcherapp.model.Shortcuts;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Main UI class to wrap all GUI elements together.
 */
public class MainUI {
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
    private static SpiralTool spiralTool;
    private static DrawingTool selectedDrawingTool;
    private static EyeDropperStats eyeDropperStats;

    private static final String CLEAR_TOOL_BUTTON_TEXT = "Clear";
    private static final String SELECTION_TOOL_BUTTON_TEXT = "Selection";
    private static final String BRUSH_TOOL_BUTTON_TEXT = "Brush";
    private static final String RECTANGLE_TOOL_BUTTON_TEXT = "Rectangle";
    private static final String LINE_TOOL_BUTTON_TEXT = "Line";
    private static final String ERASER_TOOL_BUTTON_TEXT = "Eraser";
    private static final String ELLIPSE_TOOL_BUTTON_TEXT = "Ellipse";
    private static final String TEXT_TOOL_BUTTON_TEXT = "Text";
    private static final String BUCKET_BUTTON_TEXT = "Paint Bucket";
    private static final String EYEDROPPER_TOOL_BUTTON_TEXT = "Eye Dropper";
    private static final String FAN_TOOL_BUTTON_TEXT = "Fan-out";
    private static final String CELTIC_KNOT_TOOL_BUTTON_TEXT = "Celtic Knot";
    private static final String DNA_TOOL_BUTTON_TEXT = "DNA";
    private static final String AIR_BRUSH_TOOL_BUTTON_TEXT = "Airbrush";
    private static final String TRIANGLE_TOOL_BUTTON_TEXT = "Triangle";
    private static final String SPIRAL_TOOL_BUTTON_TEXT = "Spiral";
    private static final String BRIGHTNESS_CONTRAST_BUTTON_TEXT = "Brightness/Contrast";
    private static final String HUE_SATURATION_BUTTON_TEXT = "Hue/Saturation";

    private JFrame mainFrame;

    private static final String APPLICATION_NAME = "Beta Sketcher";
    private static final int APPLICATION_WIDTH = 1920;
    private static final int APPLICATION_HEIGHT = 1080;
    private static final int EDITOR_PANEL_WIDTH = 300;
    private static final int EDITOR_PANEL_HEIGHT = 300;
    private static final int PANEL_SECTION_SPACING = 20;
    private static final int WEST_PANEL_WIDTH = 120;
    private static final int COLOR_PANEL_HEIGHT = 250;
    private static final int IMAGE_EDITOR_PANEL_HEIGHT = 180;
    private static final String DARK_GREY_CANVAS = "#222222";
    private static final String FONT_TYPE = "Arial";
    private static final int FONT_SIZE = 16;
    private int canvasWidth;
    private int canvasHeight;

    private JButton clearButton;
    private JButton selectionButton;
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
    private JButton spiralToolButton;
    private JButton brightnessContrastButton;
    private JButton hueSaturationButton;

    private static DrawArea drawArea;
    private static ColorChooser colorChooser;
    private WidthChanger widthChanger;
    private TextToolSettings textToolSettings;
    private ShortcutDialog keboardShortCutPanel;
    private Shortcuts shortcuts;
    private ImportExport importExport;
    private JPanel canvasTools;
    private DB_KBShortcuts db_kbShortcuts;
    private JButton highlightedButton;
    private BrightnessMenu brightnessMenu;
    private SaturationMenu saturationMenu;

    private static final String RES_PATH = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "res";
    private static final String APPLICATION_LOGO_IMAGE_DIRECTORY = RES_PATH + File.separator + "BPIcon.png";
    private static final String AIR_BRUSH_ICON_DEFAULT = RES_PATH + File.separator + "airbrush.png";
    private static final String AIR_BRUSH_ICON_HIGHLIGHTED = RES_PATH + File.separator + "airbrush_highlighted.png";
    private static final String AIR_BRUSH_ICON_HOVER = RES_PATH + File.separator + "airbrush_hover.png";
    private static final String BRIGHTNESS_ICON_DEFAULT = RES_PATH + File.separator + "brightness.png";
    private static final String BRIGHTNESS_ICON_HIGHLIGHTED = RES_PATH + File.separator + "brightness_highlighted.png";
    private static final String BRIGHTNESS_ICON_HOVER = RES_PATH + File.separator + "brightness_hover.png";
    private static final String BRUSH_ICON_DEFAULT = RES_PATH + File.separator + "brush.png";
    private static final String BRUSH_ICON_HIGHLIGHTED = RES_PATH + File.separator + "brush_highlighted.png";
    private static final String BRUSH_ICON_HOVER = RES_PATH + File.separator + "brush_hover.png";
    private static final String BUCKET_ICON_DEFAULT = RES_PATH + File.separator + "bucket.png";
    private static final String BUCKET_ICON_HIGHLIGHTED = RES_PATH + File.separator + "bucket_highlighted.png";
    private static final String BUCKET_ICON_HOVER = RES_PATH + File.separator + "bucket_hover.png";
    private static final String CELTIC_ICON_DEFAULT = RES_PATH + File.separator + "celtic.png";
    private static final String CELTIC_ICON_HIGHLIGHTED = RES_PATH + File.separator + "celtic_highlighted.png";
    private static final String CELTIC_ICON_HOVER = RES_PATH + File.separator + "celtic_hover.png";
    private static final String CIRCLE_ICON_DEFAULT = RES_PATH + File.separator + "circle.png";
    private static final String CIRCLE_ICON_HIGHLIGHTED = RES_PATH + File.separator + "circle_highlighted.png";
    private static final String CIRCLE_ICON_HOVER = RES_PATH + File.separator + "circle_hover.png";
    private static final String CLEAR_ICON_DEFAULT = RES_PATH + File.separator + "clear_canvas.png";
    private static final String CLEAR_ICON_HIGHLIGHTED = RES_PATH + File.separator + "clear_canvas_highlighted.png";
    private static final String CLEAR_ICON_HOVER = RES_PATH + File.separator + "clear_canvas_hover.png";
    private static final String CROP_ICON_DEFAULT = RES_PATH + File.separator + "crop.png";
    private static final String CROP_ICON_HIGHLIGHTED = RES_PATH + File.separator + "crop_highlighted.png";
    private static final String CROP_ICON_HOVER = RES_PATH + File.separator + "crop_hover.png";
    private static final String DNA_ICON_DEFAULT = RES_PATH + File.separator + "dna.png";
    private static final String DNA_ICON_HIGHLIGHTED = RES_PATH + File.separator + "dna_highlighted.png";
    private static final String DNA_ICON_HOVER = RES_PATH + File.separator + "dna_hover.png";
    private static final String ERASER_ICON_DEFAULT = RES_PATH + File.separator + "eraser.png";
    private static final String ERASER_ICON_HIGHLIGHTED = RES_PATH + File.separator + "eraser_highlighted.png";
    private static final String ERASER_ICON_HOVER = RES_PATH + File.separator + "eraser_hover.png";
    private static final String EYEDROP_ICON_DEFAULT = RES_PATH + File.separator + "eyedrop.png";
    private static final String EYEDROP_ICON_HIGHLIGHTED = RES_PATH + File.separator + "eyedrop_highlighted.png";
    private static final String EYEDROP_ICON_HOVER = RES_PATH + File.separator + "eyedrop_hover.png";
    private static final String FAN_ICON_DEFAULT = RES_PATH + File.separator + "fan.png";
    private static final String FAN_ICON_HIGHLIGHTED = RES_PATH + File.separator + "fan_highlighted.png";
    private static final String FAN_ICON_HOVER = RES_PATH + File.separator + "fan_hover.png";
    private static final String HAND_ICON_DEFAULT = RES_PATH + File.separator + "hand.png";
    private static final String HAND_ICON_HIGHLIGHTED = RES_PATH + File.separator + "hand_highlighted.png";
    private static final String HAND_ICON_HOVER = RES_PATH + File.separator + "hand_hover.png";
    private static final String HUE_SATURATION_ICON_DEFAULT = RES_PATH + File.separator + "hue_saturation.png";
    private static final String HUE_SATURATION_ICON_HIGHLIGHTED = RES_PATH + File.separator +
            "hue_saturation_highlighted.png";
    private static final String HUE_SATURATION_ICON_HOVER = RES_PATH + File.separator +
            "hue_saturation_highlighted.png";
    private static final String LINE_ICON_DEFAULT = RES_PATH + File.separator + "line.png";
    private static final String LINE_ICON_HIGHLIGHTED = RES_PATH + File.separator + "line_highlighted.png";
    private static final String LINE_ICON_HOVER = RES_PATH + File.separator + "line_hover.png";
    private static final String SELECTION_ICON_DEFAULT = RES_PATH + File.separator + "selection.png";
    private static final String SELECTION_ICON_HIGHLIGHTED = RES_PATH + File.separator + "selection_highlighted.png";
    private static final String SELECTION_ICON_HOVER = RES_PATH + File.separator + "selection_hover.png";
    private static final String SHAPES_ICON_DEFAULT = RES_PATH + File.separator + "shapes.png";
    private static final String SHAPES_ICON_HIGHLIGHTED = RES_PATH + File.separator + "shapes_highlighted.png";
    private static final String SHAPES_ICON_HOVER = RES_PATH + File.separator + "shapes_hover.png";
    private static final String SPIRAL_ICON_DEFAULT = RES_PATH + File.separator + "spiral.png";
    private static final String SPIRAL_ICON_HIGHLIGHTED = RES_PATH + File.separator + "spiral_highlighted.png";
    private static final String SPIRAL_ICON_HOVER = RES_PATH + File.separator + "spiral_hover.png";
    private static final String SQUARE_ICON_DEFAULT = RES_PATH + File.separator + "square.png";
    private static final String SQUARE_ICON_HIGHLIGHTED = RES_PATH + File.separator + "square_highlighted.png";
    private static final String SQUARE_ICON_HOVER = RES_PATH + File.separator + "square_hover.png";
    private static final String TEXT_ICON_DEFAULT = RES_PATH + File.separator + "text.png";
    private static final String TEXT_ICON_HIGHLIGHTED = RES_PATH + File.separator + "text_highlighted.png";
    private static final String TEXT_ICON_HOVER = RES_PATH + File.separator + "text_hover.png";
    private static final String TRIANGLE_ICON_DEFAULT = RES_PATH + File.separator + "triangle.png";
    private static final String TRIANGLE_ICON_HIGHLIGHTED = RES_PATH + File.separator + "triangle_highlighted.png";
    private static final String TRIANGLE_ICON_HOVER = RES_PATH + File.separator + "triangle_hover.png";

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                if (drawArea.getCurrentlySelectedLayer().isVisible()) {
                    drawArea.clear();
                }
            } else if (e.getSource() == brushToolButton) {
                widthChanger.showPanel();
                selectedDrawingTool = brushTool;
                setHighlightedToDefault();
                highlightedButton = brushToolButton;
                brushToolButton.setIcon(new ImageIcon(BRUSH_ICON_HIGHLIGHTED));
                updateSizeSlider();
                drawArea.setColor(brushTool.getColor());
            } else if (e.getSource() == fanToolButton) {
                selectedDrawingTool = fanTool;
                setHighlightedToDefault();
                highlightedButton = fanToolButton;
                fanToolButton.setIcon(new ImageIcon(FAN_ICON_HIGHLIGHTED));
                updateSizeSlider();
            } else if (e.getSource() == lineToolButton) {
                selectedDrawingTool = lineTool;
                setHighlightedToDefault();
                highlightedButton = lineToolButton;
                lineToolButton.setIcon(new ImageIcon(LINE_ICON_HIGHLIGHTED));
                updateSizeSlider();
                drawArea.setColor(lineTool.getColor());
            } else if (e.getSource() == rectangleToolButton) {
                selectedDrawingTool = rectangleTool;
                setHighlightedToDefault();
                highlightedButton = rectangleToolButton;
                rectangleToolButton.setIcon(new ImageIcon(SQUARE_ICON_HIGHLIGHTED));
                updateSizeSlider();
                updateFillState(); // Tool supports filling
                drawArea.setColor(rectangleTool.getColor());
            } else if (e.getSource() == widthChanger.getJTextFieldComponent()) {
                widthChanger.setSliderComponent(widthChanger.getJTextFieldValue());
                widthChanger.setCurrentWidthValue(widthChanger.getJTextFieldValue());
                drawArea.setColor(brushTool.getColor());
            } else if (e.getSource() == ellipseToolButton) {
                selectedDrawingTool = ellipseTool;
                setHighlightedToDefault();
                highlightedButton = ellipseToolButton;
                ellipseToolButton.setIcon(new ImageIcon(CIRCLE_ICON_HIGHLIGHTED));
                updateSizeSlider();
                updateFillState(); // Tool supports filling
                drawArea.setColor(ellipseTool.getColor());
            } else if (e.getSource() == eraserToolButton) {
                selectedDrawingTool = eraserTool;
                setHighlightedToDefault();
                highlightedButton = eraserToolButton;
                eraserToolButton.setIcon(new ImageIcon(ERASER_ICON_HIGHLIGHTED));
                updateSizeSlider();
            } else if (e.getSource() == paintBucketToolButton) {
                selectedDrawingTool = paintBucketTool;
                setHighlightedToDefault();
                highlightedButton = paintBucketToolButton;
                paintBucketToolButton.setIcon(new ImageIcon(BUCKET_ICON_HIGHLIGHTED));
                updateSizeSlider();
            } else if (e.getSource() == eyeDropperToolButton) {
                selectedDrawingTool = eyeDropperTool;
                setHighlightedToDefault();
                highlightedButton = eyeDropperToolButton;
                eyeDropperToolButton.setIcon(new ImageIcon(EYEDROP_ICON_HIGHLIGHTED));
            } else if (e.getSource() == celticKnotToolButton) {
                selectedDrawingTool = celticKnotTool;
                setHighlightedToDefault();
                highlightedButton = celticKnotToolButton;
                celticKnotToolButton.setIcon(new ImageIcon(CELTIC_ICON_HIGHLIGHTED));
                updateSizeSlider();
            } else if (e.getSource() == dnaToolButton) {
                selectedDrawingTool = dnaTool;
                setHighlightedToDefault();
                highlightedButton = dnaToolButton;
                dnaToolButton.setIcon(new ImageIcon(DNA_ICON_HIGHLIGHTED));
                updateSizeSlider();
            } else if (e.getSource() == airBrushToolButton) {
                selectedDrawingTool = airBrushTool;
                setHighlightedToDefault();
                highlightedButton = airBrushToolButton;
                airBrushToolButton.setIcon(new ImageIcon(AIR_BRUSH_ICON_HIGHLIGHTED));
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
                setHighlightedToDefault();
                highlightedButton = triangleToolButton;
                triangleToolButton.setIcon(new ImageIcon(TRIANGLE_ICON_HIGHLIGHTED));
                updateSizeSlider();
                updateFillState();
            } else if (e.getSource() == spiralToolButton) {
                selectedDrawingTool = spiralTool;
                setHighlightedToDefault();
                highlightedButton = spiralToolButton;
                spiralToolButton.setIcon(new ImageIcon(SPIRAL_ICON_HIGHLIGHTED));
                updateSizeSlider();
            } else if (e.getSource() == brightnessContrastButton) {
                brightnessMenu.showWindow();
            } else if (e.getSource() == hueSaturationButton) {
                saturationMenu.showWindow();
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
                setHighlightedToDefault();
                highlightedButton = textToolButton;
                textToolButton.setIcon(new ImageIcon(TEXT_ICON_HIGHLIGHTED));
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
    public MainUI(int canvasWidth, int canvasHeight) {
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
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
        spiralTool = new SpiralTool();
        selectedDrawingTool = brushTool;
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
     * This is called when the 'x' is pressed.
     */
    private void exit() {
        Object[] exitOptions = {"Cancel",
                "Export canvas",
                "Exit without exporting"};


        int confirmed = JOptionPane.showOptionDialog(null,
                "Are you sure you want to quit?", "Confirm Quit",
                JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, exitOptions, null);

        ImportExport importExport = new ImportExport(drawArea, this);

        if (confirmed == JOptionPane.CANCEL_OPTION) {
            mainFrame.dispose();
            //System.exit(0);
        }
        if (confirmed == JOptionPane.NO_OPTION) {
            importExport.exportImage();
            //mainFrame.dispose();  Ideally would use this instead of the following line, but for some reason it
            //wont properly close the app. If someone knows why, feel free to fix it
            System.exit(0);

        }
    }

    /**
     * Build main GUI.
     */
    private void prepareGUI() {
        initializeMainFrame();

        Container mainContent = mainFrame.getContentPane();
        mainContent.setLayout(new BorderLayout());
        drawArea = new DrawArea(this);

        importExport = new ImportExport(drawArea, this);
        brightnessMenu = new BrightnessMenu(drawArea);
        saturationMenu = new SaturationMenu(drawArea);
        GreyscaleMenu greyscaleMenu = new GreyscaleMenu(drawArea);
        NoiseGeneratorMenu noiseGeneratorMenu = new NoiseGeneratorMenu(drawArea);
        CheckerboardMenu checkerboardMenu = new CheckerboardMenu(drawArea);

        initializeDrawArea(mainContent);
        initializeButtons();
        initializeCanvasTools();
        brushToolButton.setIcon(new ImageIcon(BRUSH_ICON_HIGHLIGHTED));

        /* END MAIN UI BUTTONS */
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());

        //setting up the shortcuts and database
        shortcuts = new Shortcuts(canvasTools, this);
        db_kbShortcuts = new DB_KBShortcuts(shortcuts);
        keboardShortCutPanel = new ShortcutDialog(this, shortcuts);

        MenuUI menuUI = new MenuUI(mainFrame, drawArea, importExport, greyscaleMenu, saturationMenu, brightnessMenu,
                noiseGeneratorMenu, checkerboardMenu, keboardShortCutPanel);


        northPanel.add(menuUI, BorderLayout.NORTH);
        initializeToolSettings(northPanel);
        initializeWidthChanger();

        JPanel colorPanel = initializeColorPanel();
        initializeColorChooserPanel(colorPanel);

        JPanel imageEditingPanel = initializeImageEditingPanel();

        JPanel westPanel = initializeWestPanel(colorPanel, imageEditingPanel);
        mainContent.add(westPanel, BorderLayout.WEST);

        JPanel editorPanel = initializeEditorPanel();
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
     * Set up main frame of the application.
     */
    private void initializeMainFrame() {
        mainFrame = new JFrame(APPLICATION_NAME);
        mainFrame.setIconImage(new ImageIcon(APPLICATION_LOGO_IMAGE_DIRECTORY).getImage());
        mainFrame.setSize(APPLICATION_WIDTH, APPLICATION_HEIGHT);
        mainFrame.getContentPane().setBackground(Color.DARK_GRAY);

        mainFrame.setLocationByPlatform(true);
        mainFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        mainFrame.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        if (ImportExport.isExported()) {
                            System.exit(0);
                        } else {
                            exit();
                        }
                    }
                });
    }

    /**
     * Set up draw area component.
     *
     * @param mainContent container.
     */
    private void initializeDrawArea(Container mainContent) {
        JPanel drawAreaPanel = new JPanel();
        drawAreaPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipady = canvasHeight;
        c.ipadx = canvasWidth;
        drawAreaPanel.setBackground(Color.decode(DARK_GREY_CANVAS));
        drawAreaPanel.add(drawArea, c);
        mainContent.add(drawAreaPanel, BorderLayout.CENTER);
    }

    /**
     * Initialize tool buttons.
     */
    private void initializeButtons() {
        clearButton = createButton(CLEAR_ICON_DEFAULT);
        selectionButton = createButton(SELECTION_ICON_DEFAULT);
        brushToolButton = createButton(BRIGHTNESS_ICON_DEFAULT);
        lineToolButton = createButton(LINE_ICON_DEFAULT);
        rectangleToolButton = createButton(SQUARE_ICON_DEFAULT);
        eraserToolButton = createButton(ERASER_ICON_DEFAULT);
        ellipseToolButton = createButton(CIRCLE_ICON_DEFAULT);
        textToolButton = createButton(TEXT_ICON_DEFAULT);
        eyeDropperToolButton = createButton(EYEDROP_ICON_DEFAULT);
        fanToolButton = createButton(FAN_ICON_DEFAULT);
        celticKnotToolButton = createButton(CELTIC_ICON_DEFAULT);
        paintBucketToolButton = createButton(BUCKET_ICON_DEFAULT);
        dnaToolButton = createButton(DNA_ICON_DEFAULT);
        airBrushToolButton = createButton(AIR_BRUSH_ICON_DEFAULT);
        triangleToolButton = createButton(TRIANGLE_ICON_DEFAULT);
        spiralToolButton = createButton(SPIRAL_ICON_DEFAULT);
        brightnessContrastButton = createButton(BRIGHTNESS_ICON_DEFAULT);
        hueSaturationButton = createButton(HUE_SATURATION_ICON_DEFAULT);
        highlightedButton = brushToolButton;
    }

    /**
     * Create a JButton with a specific tooltip format.
     *
     * @param iconPath path to the button's icon.
     * @return created JButton.
     */
    private JButton createButton(String iconPath) {
        return new JButton(new ImageIcon(iconPath)) {
            public JToolTip createToolTip() {
                JToolTip toolTip = super.createToolTip();
                toolTip.setBackground(Color.decode(DARK_GREY_CANVAS));
                toolTip.setForeground(Color.WHITE);
                toolTip.setBorder(null);
                toolTip.setFont(new Font(FONT_TYPE, Font.PLAIN, FONT_SIZE));
                return toolTip;
            }
        };
    }

    /**
     * Setup tool icons and action listeners for canvas tools.
     */
    private void initializeCanvasTools() {
        // Add a button to this array to register to actionListener and canvasTools
        // The order of this list determines the order of the buttons in the generated UI. Index -> 0 = Position ->
        // First
        JButton[] buttonContainer = {
                clearButton, selectionButton, brushToolButton, airBrushToolButton, eraserToolButton, lineToolButton,
                spiralToolButton, fanToolButton, rectangleToolButton, ellipseToolButton, triangleToolButton,
                paintBucketToolButton, celticKnotToolButton, dnaToolButton, textToolButton, eyeDropperToolButton,
                brightnessContrastButton, hueSaturationButton
        };
        String[] buttonTextContainer = {
                CLEAR_TOOL_BUTTON_TEXT, SELECTION_TOOL_BUTTON_TEXT, BRUSH_TOOL_BUTTON_TEXT, AIR_BRUSH_TOOL_BUTTON_TEXT,
                ERASER_TOOL_BUTTON_TEXT, LINE_TOOL_BUTTON_TEXT, SPIRAL_TOOL_BUTTON_TEXT, FAN_TOOL_BUTTON_TEXT,
                RECTANGLE_TOOL_BUTTON_TEXT, ELLIPSE_TOOL_BUTTON_TEXT, TRIANGLE_TOOL_BUTTON_TEXT, BUCKET_BUTTON_TEXT,
                CELTIC_KNOT_TOOL_BUTTON_TEXT, DNA_TOOL_BUTTON_TEXT, TEXT_TOOL_BUTTON_TEXT, EYEDROPPER_TOOL_BUTTON_TEXT,
                BRIGHTNESS_CONTRAST_BUTTON_TEXT, HUE_SATURATION_BUTTON_TEXT
        };
        String[] buttonHoverContainer = {
                CLEAR_ICON_HOVER, SELECTION_ICON_HOVER, BRUSH_ICON_HOVER, AIR_BRUSH_ICON_HOVER, ERASER_ICON_HOVER,
                LINE_ICON_HOVER, SPIRAL_ICON_HOVER, FAN_ICON_HOVER, SQUARE_ICON_HOVER, CIRCLE_ICON_HOVER,
                TRIANGLE_ICON_HOVER, BUCKET_ICON_HOVER, CELTIC_ICON_HOVER, DNA_ICON_HOVER, TEXT_ICON_HOVER,
                EYEDROP_ICON_HOVER, BRIGHTNESS_ICON_HOVER, HUE_SATURATION_ICON_HOVER
        };
        String[] buttonDefaultContainer = {
                CLEAR_ICON_DEFAULT, SELECTION_ICON_DEFAULT, BRUSH_ICON_DEFAULT, AIR_BRUSH_ICON_DEFAULT,
                ERASER_ICON_DEFAULT, LINE_ICON_DEFAULT, SPIRAL_ICON_DEFAULT, FAN_ICON_DEFAULT, SQUARE_ICON_DEFAULT,
                CIRCLE_ICON_DEFAULT, TRIANGLE_ICON_DEFAULT, BUCKET_ICON_DEFAULT, CELTIC_ICON_DEFAULT, DNA_ICON_DEFAULT,
                TEXT_ICON_DEFAULT, EYEDROP_ICON_DEFAULT, BRIGHTNESS_ICON_DEFAULT, HUE_SATURATION_ICON_DEFAULT
        };
        String[] buttonHighlightedContainer = {
                CLEAR_ICON_HIGHLIGHTED, SELECTION_ICON_HIGHLIGHTED, BRUSH_ICON_HIGHLIGHTED, AIR_BRUSH_ICON_HIGHLIGHTED,
                ERASER_ICON_HIGHLIGHTED, LINE_ICON_HIGHLIGHTED, SPIRAL_ICON_HIGHLIGHTED, FAN_ICON_HIGHLIGHTED,
                SQUARE_ICON_HIGHLIGHTED, CIRCLE_ICON_HIGHLIGHTED, TRIANGLE_ICON_HIGHLIGHTED, BUCKET_ICON_HIGHLIGHTED,
                CELTIC_ICON_HIGHLIGHTED, DNA_ICON_HIGHLIGHTED, TEXT_ICON_HIGHLIGHTED, EYEDROP_ICON_HIGHLIGHTED,
                BRIGHTNESS_ICON_HIGHLIGHTED, HUE_SATURATION_ICON_HIGHLIGHTED
        };

        canvasTools = new JPanel();
        canvasTools.setLayout(new BoxLayout(canvasTools, BoxLayout.Y_AXIS));
        canvasTools.setBackground(Color.DARK_GRAY);
        canvasTools.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));

        int buttonContainerIndex = 0;
        // Register buttons to actionListener and canvasTools
        for (JButton button : buttonContainer) {
            button.addActionListener(actionListener);
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            button.setContentAreaFilled(false);
            button.setToolTipText(buttonTextContainer[buttonContainerIndex]);
            int CURRENT_CONTAINER_INDEX = buttonContainerIndex;
            button.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseEntered(java.awt.event.MouseEvent evt) {
                    button.setIcon(new ImageIcon(buttonHoverContainer[CURRENT_CONTAINER_INDEX]));
                }

                public void mouseExited(java.awt.event.MouseEvent evt) {
                    if (button != highlightedButton) {
                        button.setIcon(new ImageIcon(buttonDefaultContainer[CURRENT_CONTAINER_INDEX]));
                    } else {
                        button.setIcon(new ImageIcon(buttonHighlightedContainer[CURRENT_CONTAINER_INDEX]));
                    }
                }
            });
            buttonContainerIndex++;
            if (button != eyeDropperToolButton && button != brightnessContrastButton && button != hueSaturationButton) {
                canvasTools.add(button);
                canvasTools.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
            }
        }
        canvasTools.setBorder(BorderFactory.createLineBorder(Color.GRAY));
    }

    /**
     * Set up color panel.
     *
     * @return color panel.
     */
    private JPanel initializeColorPanel() {
        JPanel colorPanel = new JPanel();
        colorChooser = new ColorChooser();
        colorPanel.setLayout(new BoxLayout(colorPanel, BoxLayout.Y_AXIS));
        colorPanel.setBackground(Color.DARK_GRAY);
        final Dimension CANVAS_TOOLS_MAX_SIZE = canvasTools.getMaximumSize();
        colorPanel.setMaximumSize(new Dimension((int) CANVAS_TOOLS_MAX_SIZE.getWidth(), COLOR_PANEL_HEIGHT));
        colorPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        colorPanel.add(eyeDropperToolButton);
        colorPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        return colorPanel;
    }

    /**
     * Set up tool settings panel.
     *
     * @param northPanel for the application.
     */
    private void initializeToolSettings(JPanel northPanel) {
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
    }

    /**
     * Set up width changer for tools.
     */
    private void initializeWidthChanger() {
        listenForSlider listenForSlider = new listenForSlider();
        widthChanger.getSliderComponent().addChangeListener(listenForSlider);
        widthChanger.getJTextFieldComponent().addActionListener(actionListener);
        textToolSettings.addActionListener(actionListener);
        widthChanger.getCheckBoxGlobalSizeComponent().addActionListener(actionListener);
        widthChanger.getFillBox().addActionListener(actionListener);
    }

    /**
     * Set up color chooser components.
     *
     * @param colorPanel for for the application.
     */
    private void initializeColorChooserPanel(JPanel colorPanel) {
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
    }

    /**
     * Set up editor panel.
     *
     * @return editor panel of the application.
     */
    private JPanel initializeEditorPanel() {
        JPanel editorPanel = new JPanel();
        editorPanel.setLayout(new BorderLayout());
        editorPanel.setPreferredSize(new Dimension(EDITOR_PANEL_WIDTH, EDITOR_PANEL_HEIGHT));
        layersPanel = new LayersPanel(drawArea);
        editorPanel.add(layersPanel, BorderLayout.EAST);
        return editorPanel;
    }

    /**
     * Set up image editing panel consisting of the brightness/contrast and hue/saturation buttons.
     *
     * @return image editing panel of the application.
     */
    private JPanel initializeImageEditingPanel() {
        JPanel imageEditingPanel = new JPanel();
        imageEditingPanel.setLayout(new BoxLayout(imageEditingPanel, BoxLayout.Y_AXIS));
        imageEditingPanel.setBackground(Color.DARK_GRAY);
        imageEditingPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        final Dimension CANVAS_TOOLS_MAX_SIZE = canvasTools.getMaximumSize();
        imageEditingPanel.setMaximumSize(new Dimension((int) CANVAS_TOOLS_MAX_SIZE.getWidth(),
                IMAGE_EDITOR_PANEL_HEIGHT));
        imageEditingPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        imageEditingPanel.add(brightnessContrastButton);
        imageEditingPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        imageEditingPanel.add(hueSaturationButton);
        return imageEditingPanel;
    }

    /**
     * Set up west panel.
     *
     * @param colorPanel of the application.
     * @return westPanel of the application.
     */
    private JPanel initializeWestPanel(JPanel colorPanel, JPanel imageEditingPanel) {
        JPanel westPanel = new JPanel();
        westPanel.setLayout(new BoxLayout(westPanel, BoxLayout.Y_AXIS));
        westPanel.setPreferredSize(new Dimension(WEST_PANEL_WIDTH, 0));
        westPanel.setBackground(Color.DARK_GRAY);

        westPanel.add(canvasTools);
        westPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        westPanel.add(colorPanel);
        westPanel.add(Box.createRigidArea(new Dimension(0, PANEL_SECTION_SPACING)));
        westPanel.add(imageEditingPanel);
        return westPanel;
    }

    /**
     * Sets highlighted tool back to its default icon.
     */
    private void setHighlightedToDefault() {
        if (highlightedButton != null) {
            if (highlightedButton == selectionButton) {
                selectionButton.setIcon(new ImageIcon(SELECTION_ICON_DEFAULT));
            } else if (highlightedButton == brushToolButton) {
                brushToolButton.setIcon(new ImageIcon(BRUSH_ICON_DEFAULT));
            } else if (highlightedButton == airBrushToolButton) {
                airBrushToolButton.setIcon(new ImageIcon(AIR_BRUSH_ICON_DEFAULT));
            } else if (highlightedButton == eraserToolButton) {
                eraserToolButton.setIcon(new ImageIcon(ERASER_ICON_DEFAULT));
            } else if (highlightedButton == lineToolButton) {
                lineToolButton.setIcon(new ImageIcon(LINE_ICON_DEFAULT));
            } else if (highlightedButton == fanToolButton) {
                fanToolButton.setIcon(new ImageIcon(FAN_ICON_DEFAULT));
            } else if (highlightedButton == rectangleToolButton) {
                rectangleToolButton.setIcon(new ImageIcon(SQUARE_ICON_DEFAULT));
            } else if (highlightedButton == ellipseToolButton) {
                ellipseToolButton.setIcon(new ImageIcon(CIRCLE_ICON_DEFAULT));
            } else if (highlightedButton == triangleToolButton) {
                triangleToolButton.setIcon(new ImageIcon(TRIANGLE_ICON_DEFAULT));
            } else if (highlightedButton == paintBucketToolButton) {
                paintBucketToolButton.setIcon(new ImageIcon(BUCKET_ICON_DEFAULT));
            } else if (highlightedButton == celticKnotToolButton) {
                celticKnotToolButton.setIcon(new ImageIcon(CELTIC_ICON_DEFAULT));
            } else if (highlightedButton == dnaToolButton) {
                dnaToolButton.setIcon(new ImageIcon(DNA_ICON_DEFAULT));
            } else if (highlightedButton == textToolButton) {
                textToolButton.setIcon(new ImageIcon(TEXT_ICON_DEFAULT));
            } else if (highlightedButton == eyeDropperToolButton) {
                eyeDropperToolButton.setIcon(new ImageIcon(EYEDROP_ICON_DEFAULT));
            } else if (highlightedButton == spiralToolButton) {
                spiralToolButton.setIcon(new ImageIcon(SPIRAL_ICON_DEFAULT));
            }
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
     * Get the layers panel.
     *
     * @return layers panel.
     */
    public LayersPanel getLayersPanel() {
        return layersPanel;
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
     * generates the default key binding for shortcut keys
     */
    public void generateDefaultKeyBindings() {

        shortcuts.addKeyBinding(KeyEvent.VK_C, true, false, false,
                Shortcuts.CLEAR_TOOL_SHORTCUT, (evt) -> {
                    drawArea.clear();
                });

        shortcuts.addKeyBinding(KeyEvent.VK_O, true, false, false,
                Shortcuts.EXPORT_SHORTCUT, (evt) -> {
                    importExport.exportImage();
                });

        shortcuts.addKeyBinding(KeyEvent.VK_I, true, false, false,
                Shortcuts.IMPORT_SHORTCUT, (evt) -> {
                    importExport.importImage();
                });

        shortcuts.addKeyBinding(KeyEvent.VK_B, true, false, false,
                Shortcuts.BRUSH_TOOL_SHORTCUT, (evt) -> {
                    selectedDrawingTool = brushTool;
                    setHighlightedToDefault();
                    highlightedButton = brushToolButton;
                    brushToolButton.setIcon(new ImageIcon(BRUSH_ICON_HIGHLIGHTED));
                    updateSizeSlider();
                });

        shortcuts.addKeyBinding(KeyEvent.VK_L, true, false, false,
                Shortcuts.LINE_TOOL_SHORTCUT, (evt) -> {
                    selectedDrawingTool = lineTool;
                    setHighlightedToDefault();
                    highlightedButton = lineToolButton;
                    lineToolButton.setIcon(new ImageIcon(LINE_ICON_HIGHLIGHTED));
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
     * Gets the key binding for the shortcuts from the database.
     */
    private void generateDBDefaultKeyBindings() {

        shortcuts.addKeyBinding(Shortcuts.getClearToolKeyCode(), Shortcuts.isAlt_clearTool(),
                Shortcuts.isShift_clearTool(), Shortcuts.isAlt_clearTool(), Shortcuts.CLEAR_TOOL_SHORTCUT, (evt) -> {
                    drawArea.clear();
                });

        shortcuts.addKeyBinding(shortcuts.getExportKeyCode(), shortcuts.isCtrl_export(), shortcuts.isShift_export(),
                shortcuts.isAlt_export(), Shortcuts.EXPORT_SHORTCUT, (evt) -> {
                    importExport.exportImage();
                });

        shortcuts.addKeyBinding(shortcuts.getImportKeyCode(), shortcuts.isCtrl_import(), shortcuts.isShift_import(),
                shortcuts.isAlt_import(), Shortcuts.IMPORT_SHORTCUT, (evt) -> {
                    importExport.importImage();
                });

        shortcuts.addKeyBinding(shortcuts.getBrushToolKeyCode(), shortcuts.isCtrl_brushTool(),
                shortcuts.isShift_brushTool(), shortcuts.isAlt_brushTool(), shortcuts.BRUSH_TOOL_SHORTCUT, (evt) -> {
                    selectedDrawingTool = brushTool;
                    setHighlightedToDefault();
                    highlightedButton = brushToolButton;
                    brushToolButton.setIcon(new ImageIcon(BRUSH_ICON_HIGHLIGHTED));
                    updateSizeSlider();
                });

        shortcuts.addKeyBinding(shortcuts.getLineToolKeyCode(), shortcuts.isCtrl_lineTool(),
                shortcuts.isShift_lineTool(), shortcuts.isAlt_lineTool(), Shortcuts.LINE_TOOL_SHORTCUT, (evt) -> {
                    selectedDrawingTool = lineTool;
                    setHighlightedToDefault();
                    highlightedButton = lineToolButton;
                    lineToolButton.setIcon(new ImageIcon(LINE_ICON_HIGHLIGHTED));
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
