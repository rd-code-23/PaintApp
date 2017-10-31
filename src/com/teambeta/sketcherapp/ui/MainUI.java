package com.teambeta.sketcherapp.ui;

import com.teambeta.sketcherapp.drawingTools.*;
import com.teambeta.sketcherapp.model.ImportExport;
import com.teambeta.sketcherapp.model.AboutMenu;
import javafx.stage.FileChooser;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

/**
 * Main UI class to wrap all GUI elements together.
 */
public class MainUI {
    private static final String DEFAULT_FONT = "Arial";
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
    private JComboBox<String> fontSelector;

    private static DrawArea drawArea;
    private static ColorChooser colorChooser;
    private WidthChanger widthChanger;

    private static final String APPLICATION_LOGO_IMAGE_DIRECTORY = "res/BPIcon.png";

    private ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == clearButton) {
                drawArea.clear();
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
            } else if (e.getSource() == fontSelector) {
                textTool.setFont((String) fontSelector.getSelectedItem());
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
                fontSelector.setVisible(true);
            } else if ((e.getSource() != textToolButton) && (e.getSource() instanceof JButton)) {
                fontSelector.setVisible(false);
            }
        }
    };

    /**
     * Constructor.
     */
    public MainUI() {
        String fonts[] = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
        fontSelector = new JComboBox<>(fonts);
        fontSelector.setSelectedItem(DEFAULT_FONT);
        fontSelector.setVisible(false);
        initDrawingTools();
        prepareGUI();
    }

    /**
     * Create the drawing tool objects and set the pen tool as the default selection.
     */
    private void initDrawingTools() {
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
        drawArea = new DrawArea();

        ImportExport importExport = new ImportExport(drawArea, this);
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

        JPanel canvasTools = new JPanel();
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
        MenuUI menuUI = new MenuUI(drawArea, importExport, greyscaleMenu, noiseGeneratorMenu, checkerboardMenu);
        northPanel.add(menuUI, BorderLayout.NORTH);

        JPanel toolSettings = new JPanel();
        toolSettings.setLayout(new BoxLayout(toolSettings, BoxLayout.X_AXIS));
        toolSettings.setBackground(Color.DARK_GRAY);
        toolSettings.add(Box.createRigidArea(new Dimension(WEST_PANEL_WIDTH, 0)));
        widthChanger = new WidthChanger();
        toolSettings.add(widthChanger.getGUI());
        northPanel.add(toolSettings, BorderLayout.CENTER);

        if (fontSelector != null) {
            northPanel.add(fontSelector, BorderLayout.EAST);
            textTool.setFont((String) fontSelector.getSelectedItem());
        }

        MainUI.listenForSlider listenForSlider = new MainUI.listenForSlider();
        widthChanger.getSliderComponent().addChangeListener(listenForSlider);
        widthChanger.getJTextFieldComponent().addActionListener(actionListener);
        fontSelector.addActionListener(actionListener);
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
        mainContent.add(editorPanel, BorderLayout.EAST);
        mainContent.add(northPanel, BorderLayout.NORTH);
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
}