package com.teambeta.sketcherapp.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import com.teambeta.sketcherapp.ui.MainUI;

public class Shortcuts {

    public static final String NO_SUCH_TOOL = "NO SUCH TOOL";

    public static final String CLEAR_TOOL_SHORTCUT = "CLEAR";
    static int clearToolKeyCode;
    static boolean isCtrl_clearTool;
    static boolean isShift_clearTool;
    static boolean isAlt_clearTool;

    public static final String EXPORT_SHORTCUT = "EXPORT";
    static int exportKeyCode;
    static boolean isCtrl_export;
    static boolean isShift_export;
    static boolean isAlt_export;

    public static final String IMPORT_SHORTCUT = "IMPORT";
    static int importKeyCode;
    static boolean isCtrl_import;
    static boolean isShift_import;
    static boolean isAlt_import;

    public static final String BRUSH_TOOL_SHORTCUT = "BRUSH TOOL";
    static int brushToolKeyCode;
    static boolean isCtrl_brushTool;
    static boolean isShift_brushTool;
    static boolean isAlt_brushTool;

    public static final String LINE_TOOL_SHORTCUT = "LINE TOOL";
    static int lineToolKeyCode;
    static boolean isCtrl_lineTool;
    static boolean isShift_lineTool;
    static boolean isAlt_lineTool;

    public static final String RECTANGLE_TOOL_SHORTCUT = "RECT TOOL";
    static int rectToolKeyCode;
    static boolean isCtrl_rectTool;
    static boolean isShift_rectTool;
    static boolean isAlt_rectTool;

    public static final String AIRBRUSH_TOOL_SHORTCUT = "AIRBRUSH TOOL";
    static int airBrushToolKeyCode;
    static boolean isCtrl_airBrushTool;
    static boolean isShift_airBrushTool;
    static boolean isAlt_airBrushTool;

    public static final String CELTICKNOT_TOOL_SHORTCUT = "CELTIC TOOL";
    static int celticKnotToolKeyCode;
    static boolean isCtrl_celticKnotTool;
    static boolean isShift_celticKnotTool;
    static boolean isAlt_celticKnotTool;

    public static final String COLOR_CHOOSER_TOOL_SHORTCUT = "COLOR CHOOSER TOOL";
    static int colorChooserToolKeyCode;
    static boolean isCtrl_colorChooserTool;
    static boolean isShift_colorChooserTool;
    static boolean isAlt_colorChooserTool;

    public static final String DNA_TOOL_SHORTCUT = "DNA TOOL";
    static int dnaToolKeyCode;
    static boolean isCtrl_dnaTool;
    static boolean isShift_dnaTool;
    static boolean isAlt_dnaTool;

    public static final String ELLIPSE_TOOL_SHORTCUT = "ELLIPSE TOOL";
    static int ellipseToolKeyCode;
    static boolean isCtrl_ellipseTool;
    static boolean isShift_ellipseTool;
    static boolean isAlt_ellipseTool;

    public static final String ERASER_TOOL_SHORTCUT = "ERASER TOOL";
    static int eraserToolKeyCode;
    static boolean isCtrl_eraserTool;
    static boolean isShift_eraserTool;
    static boolean isAlt_eraserTool;

    public static final String EYE_DROP_TOOL_SHORTCUT = "EYE DROP TOOL";
    static int eyeDropToolKeyCode;
    static boolean isCtrl_eyeDropTool;
    static boolean isShift_eyeDropTool;
    static boolean isAlt_eyeDropTool;

    public static final String FAN_TOOL_SHORTCUT = "FAN TOOL";
    static int fanToolKeyCode;
    static boolean isCtrl_fanTool;
    static boolean isShift_fanTool;
    static boolean isAlt_fanTool;

    public static final String PAINTBUCKET_TOOL_SHORTCUT = "PAINT BUCKET TOOL";
    static int paintBucketToolKeyCode;
    static boolean isCtrl_paintBucketTool;
    static boolean isShift_paintBucketTool;
    static boolean isAlt_paintBucketTool;

    public static final String SELECTION_TOOL_SHORTCUT = "SELECTION TOOL";
    static int selectionToolKeyCode;
    static boolean isCtrl_selectionTool;
    static boolean isShift_selectionTool;
    static boolean isAlt_selectionTool;

    public static final String TEXT_TOOL_SHORTCUT = "TEXT TOOL";
    static int textToolKeyCode;
    static boolean isCtrl_textTool;
    static boolean isShift_textTool;
    static boolean isAlt_textTool;

    public static final String TRIANGLE_TOOL_SHORTCUT = "TRIANGLE TOOL";
    static int triangleToolKeyCode;
    static boolean isCtrl_triangleTool;
    static boolean isShift_triangleTool;
    static boolean isAlt_triangleTool;

    public static final String PRINT_SHORTCUT = "PRINT";
    static int printToolKeyCode;
    static boolean isCtrl_printTool;
    static boolean isShift_printTool;
    static boolean isAlt_printTool;

    public static final String SHORTCUT_SHORTCUT = "SHORTCUT";
    static int shortcutToolKeyCode;
    static boolean isCtrl_shortcutTool;
    static boolean isShift_shortcutTool;
    static boolean isAlt_shortcutTool;

    public static final String CLOSE_SHORTCUT = "CLOSE";
    static int closeToolKeyCode;
    static boolean isCtrl_closeTool;
    static boolean isShift_closeTool;
    static boolean isAlt_closeTool;

    public static final String NEW_SHORTCUT = "NEW";
    static int newToolKeyCode;
    static boolean isCtrl_newTool;
    static boolean isShift_newTool;
    static boolean isAlt_newTool;

    public static final String GREYSCALE_SHORTCUT = "GREYSCALE";
    static int greyscaleToolKeyCode;
    static boolean isCtrl_greyscaleTool;
    static boolean isShift_greyscaleTool;
    static boolean isAlt_greyscaleTool;

    public static final String BRIGHTNESS_SHORTCUT = "BRIGHTNESS";
    static int brightToolKeyCode;
    static boolean isCtrl_brightTool;
    static boolean isShift_brightTool;
    static boolean isAlt_brightTool;

    public static final String SATURATION_SHORTCUT = "SATURATION";
    static int saturationToolKeyCode;
    static boolean isCtrl_saturationTool;
    static boolean isShift_saturationTool;
    static boolean isAlt_saturationTool;

    public static final String SPIRAL_TOOL_SHORTCUT = "SPIRAL TOOL";
    static int spiralToolKeyCode;
    static boolean isCtrl_spiralTool;
    static boolean isShift_spiralTool;
    static boolean isAlt_spiralTool;

    public static final String ZOOM_TOOL_SHORTCUT = "ZOOM TOOL";
    static int zoomToolKeyCode;
    static boolean isCtrl_zoomTool;
    static boolean isShift_zoomTool;
    static boolean isAlt_zoomTool;

    private JComponent component;
    private MainUI mainUI;
    private ActionMap actionMap;
    private InputMap im;

    public Shortcuts(JComponent component, MainUI mainUI) {
        this.component = component;
        im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap = component.getActionMap();
        this.mainUI = mainUI;
    }

    /**
     * adds a new key binding that does not exists in the database
     */
    public void addKeyBinding(int keyCode, boolean useControl, boolean useShift, boolean useAlt, String id, ActionListener actionListener) {
        char c = (char) keyCode;
        mainUI.getDb_kbShortcuts().insert(id, "" + c, "" + useControl, "" + useAlt, "" + useShift);
        addBinding(keyCode, useControl, useShift, useAlt, id);
        actionMap.put(id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });
    }

    /**
     * changes a  key binding that exists and updates the database
     */
    public void changeKeyBinding(int keyCode, boolean useControl, boolean useShift, boolean useAlt, String id) {
        char c = (char) keyCode;
        mainUI.getDb_kbShortcuts().update(id, "" + c, "" + useControl, "" + useAlt, "" + useShift);
        addBinding(keyCode, useControl, useShift, useAlt, id);
    }

    /**
     * used by the program to get bindings from the database
     */
    public void getDBShortcuts(int keyCode, boolean useControl, boolean useShift, boolean useAlt, String id) {
        addBinding(keyCode, useControl, useShift, useAlt, id);
        mainUI.focusCanvasTools();
        mainUI.focusWidthPanelToFalse();
    }

    /**
     * adds the key binding to the program
     */
    private void addBinding(int keyCode, boolean useControl, boolean useShift, boolean useAlt, String id) {
        if (useControl && useAlt && useShift) {
            im.put(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), id);
        } else if (useControl && useAlt) {
            im.put(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK), id);
        } else if (useControl && useShift) {
            im.put(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), id);
        } else if (useAlt && useShift) {
            im.put(KeyStroke.getKeyStroke(keyCode, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK), id);
        } else if (useControl) {
            im.put(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK), id);
        } else if (useShift) {
            im.put(KeyStroke.getKeyStroke(keyCode, InputEvent.SHIFT_DOWN_MASK), id);
        } else if (useAlt) {
            im.put(KeyStroke.getKeyStroke(keyCode, InputEvent.ALT_DOWN_MASK), id);
        } else {
            im.put(KeyStroke.getKeyStroke(keyCode, 0, false), id);
        }

        setKBShortcut(id, keyCode, useControl, useShift, useAlt);

        mainUI.focusCanvasTools();
        mainUI.focusWidthPanelToFalse();
    }

    /**
     * removes a binding
     */
    public void removeBinding(int keyCode, boolean useControl, boolean useShift, boolean useAlt) {
        if (useControl && useAlt && useShift) {
            im.remove(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        } else if (useControl && useAlt) {
            im.remove(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK | InputEvent.ALT_DOWN_MASK));
        } else if (useControl && useShift) {
            im.remove(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        } else if (useAlt && useShift) {
            im.remove(KeyStroke.getKeyStroke(keyCode, InputEvent.ALT_DOWN_MASK | InputEvent.SHIFT_DOWN_MASK));
        } else if (useControl) {
            im.remove(KeyStroke.getKeyStroke(keyCode, InputEvent.CTRL_DOWN_MASK));
        } else if (useShift) {
            im.remove(KeyStroke.getKeyStroke(keyCode, InputEvent.SHIFT_DOWN_MASK));
        } else if (useAlt) {
            im.remove(KeyStroke.getKeyStroke(keyCode, InputEvent.ALT_DOWN_MASK));
        } else {
            im.remove(KeyStroke.getKeyStroke(keyCode, 0, false));
        }

        mainUI.focusCanvasTools();
        mainUI.focusWidthPanelToFalse();
    }

    /**
     * sets all the variables so the program can get key binding information
     */
    public static void setKBShortcut(String tool, int keyCode, boolean useControl, boolean useShift, boolean useAlt) {
        switch (tool) {
            case CLEAR_TOOL_SHORTCUT:
                clearToolKeyCode = keyCode;
                isCtrl_clearTool = useControl;
                isShift_clearTool = useShift;
                isAlt_clearTool = useAlt;
                break;
            case EXPORT_SHORTCUT:
                exportKeyCode = keyCode;
                isCtrl_export = useControl;
                isShift_export = useShift;
                isAlt_export = useAlt;
                break;
            case IMPORT_SHORTCUT:
                importKeyCode = keyCode;
                isCtrl_import = useControl;
                isShift_import = useShift;
                isAlt_import = useAlt;
                break;
            case BRUSH_TOOL_SHORTCUT:
                brushToolKeyCode = keyCode;
                isCtrl_brushTool = useControl;
                isShift_brushTool = useShift;
                isAlt_brushTool = useAlt;
                break;
            case LINE_TOOL_SHORTCUT:
                lineToolKeyCode = keyCode;
                isCtrl_lineTool = useControl;
                isShift_lineTool = useShift;
                isAlt_lineTool = useAlt;
                break;
            case AIRBRUSH_TOOL_SHORTCUT:
                airBrushToolKeyCode = keyCode;
                isCtrl_airBrushTool = useControl;
                isShift_airBrushTool = useShift;
                isAlt_airBrushTool = useAlt;
                break;
            case CELTICKNOT_TOOL_SHORTCUT:
                celticKnotToolKeyCode = keyCode;
                isCtrl_celticKnotTool = useControl;
                isShift_celticKnotTool = useShift;
                isAlt_celticKnotTool = useAlt;
                break;
            case COLOR_CHOOSER_TOOL_SHORTCUT:
                colorChooserToolKeyCode = keyCode;
                isCtrl_colorChooserTool = useControl;
                isShift_colorChooserTool = useShift;
                isAlt_colorChooserTool = useAlt;
                break;
            case DNA_TOOL_SHORTCUT:
                dnaToolKeyCode = keyCode;
                isCtrl_dnaTool = useControl;
                isShift_dnaTool = useShift;
                isAlt_dnaTool = useAlt;
                break;
            case ELLIPSE_TOOL_SHORTCUT:
                ellipseToolKeyCode = keyCode;
                isCtrl_ellipseTool = useControl;
                isShift_ellipseTool = useShift;
                isAlt_ellipseTool = useAlt;
                break;
            case ERASER_TOOL_SHORTCUT:
                eraserToolKeyCode = keyCode;
                isCtrl_eraserTool = useControl;
                isShift_eraserTool = useShift;
                isAlt_eraserTool = useAlt;
                break;
            case EYE_DROP_TOOL_SHORTCUT:
                eyeDropToolKeyCode = keyCode;
                isCtrl_eyeDropTool = useControl;
                isShift_eyeDropTool = useShift;
                isAlt_eyeDropTool = useAlt;
                break;
            case FAN_TOOL_SHORTCUT:
                fanToolKeyCode = keyCode;
                isCtrl_fanTool = useControl;
                isShift_fanTool = useShift;
                isAlt_fanTool = useAlt;
                break;
            case PAINTBUCKET_TOOL_SHORTCUT:
                paintBucketToolKeyCode = keyCode;
                isCtrl_paintBucketTool = useControl;
                isShift_paintBucketTool = useShift;
                isAlt_paintBucketTool = useAlt;
                break;
            case SELECTION_TOOL_SHORTCUT:
                selectionToolKeyCode = keyCode;
                isCtrl_selectionTool = useControl;
                isShift_selectionTool = useShift;
                isAlt_selectionTool = useAlt;
                break;
            case RECTANGLE_TOOL_SHORTCUT:
                rectToolKeyCode = keyCode;
                isCtrl_rectTool = useControl;
                isShift_rectTool = useShift;
                isAlt_rectTool = useAlt;
                break;
            case TEXT_TOOL_SHORTCUT:
                textToolKeyCode = keyCode;
                isCtrl_textTool = useControl;
                isShift_textTool = useShift;
                isAlt_textTool = useAlt;
                break;
            case TRIANGLE_TOOL_SHORTCUT:
                triangleToolKeyCode = keyCode;
                isCtrl_triangleTool = useControl;
                isShift_triangleTool = useShift;
                isAlt_triangleTool = useAlt;
                break;
            case PRINT_SHORTCUT:
                printToolKeyCode = keyCode;
                isCtrl_printTool = useControl;
                isShift_printTool = useShift;
                isAlt_printTool = useAlt;
                break;
            case SHORTCUT_SHORTCUT:
                shortcutToolKeyCode = keyCode;
                isCtrl_shortcutTool = useControl;
                isShift_shortcutTool = useShift;
                isAlt_shortcutTool = useAlt;
                break;
            case CLOSE_SHORTCUT:
                closeToolKeyCode = keyCode;
                isCtrl_closeTool = useControl;
                isShift_closeTool = useShift;
                isAlt_closeTool = useAlt;
                break;
            case NEW_SHORTCUT:
                newToolKeyCode = keyCode;
                isCtrl_newTool = useControl;
                isShift_newTool = useShift;
                isAlt_newTool = useAlt;
                break;
            case GREYSCALE_SHORTCUT:
                greyscaleToolKeyCode = keyCode;
                isCtrl_greyscaleTool = useControl;
                isShift_greyscaleTool = useShift;
                isAlt_greyscaleTool = useAlt;
                break;
            case BRIGHTNESS_SHORTCUT:
                brightToolKeyCode = keyCode;
                isCtrl_brightTool = useControl;
                isShift_brightTool = useShift;
                isAlt_brightTool = useAlt;
                break;
            case SATURATION_SHORTCUT:
                saturationToolKeyCode = keyCode;
                isCtrl_saturationTool = useControl;
                isShift_saturationTool = useShift;
                isAlt_saturationTool = useAlt;
                break;
            case SPIRAL_TOOL_SHORTCUT:
                spiralToolKeyCode = keyCode;
                isCtrl_spiralTool = useControl;
                isShift_spiralTool = useShift;
                isAlt_spiralTool = useAlt;
                break;
            case ZOOM_TOOL_SHORTCUT:
                zoomToolKeyCode = keyCode;
                isCtrl_zoomTool = useControl;
                isShift_zoomTool = useShift;
                isAlt_zoomTool = useAlt;
                break;
            default:
                System.out.println(NO_SUCH_TOOL);
        }
    }

    /**
     * sets the key binding to default settings
     */
    public void resetDefaultBindings() {
        im.clear();
        mainUI.focusCanvasTools();
        mainUI.getDb_kbShortcuts().dropTable();
        mainUI.getDb_kbShortcuts().createTable();
        mainUI.generateDefaultKeyBindings();
    }

    /**
     * when the user changes a binding, this function will check to see if there is another tool using the same binding
     */
    public boolean isValidKeyBinding(int keyCode, boolean ctrl, boolean shift, boolean alt) {
        if (keyCode == brushToolKeyCode && ctrl == isCtrl_brushTool && shift == isShift_brushTool && alt == isAlt_brushTool) {
            return false;
        }
        if (keyCode == lineToolKeyCode && ctrl == isCtrl_lineTool && shift == isShift_lineTool && alt == isAlt_lineTool) {
            return false;
        }
        if (keyCode == clearToolKeyCode && ctrl == isCtrl_clearTool && shift == isShift_clearTool && alt == isAlt_clearTool) {
            return false;
        }

        if (keyCode == importKeyCode && ctrl == isCtrl_import && shift == isShift_import && alt == isAlt_import) {
            return false;
        }

        if (keyCode == exportKeyCode && ctrl == isCtrl_export && shift == isShift_export && alt == isAlt_export) {
            return false;
        }

        if (keyCode == airBrushToolKeyCode && ctrl == isCtrl_airBrushTool && shift == isShift_airBrushTool && alt == isAlt_airBrushTool) {
            return false;
        }

        if (keyCode == celticKnotToolKeyCode && ctrl == isCtrl_celticKnotTool && shift == isShift_celticKnotTool && alt == isAlt_celticKnotTool) {
            return false;
        }

        if (keyCode == colorChooserToolKeyCode && ctrl == isCtrl_colorChooserTool && shift == isShift_colorChooserTool && alt == isAlt_colorChooserTool) {
            return false;
        }

        if (keyCode == dnaToolKeyCode && ctrl == isCtrl_dnaTool && shift == isShift_dnaTool && alt == isAlt_dnaTool) {
            return false;
        }

        if (keyCode == ellipseToolKeyCode && ctrl == isCtrl_ellipseTool && shift == isShift_ellipseTool && alt == isAlt_ellipseTool) {
            return false;
        }

        if (keyCode == eraserToolKeyCode && ctrl == isCtrl_eraserTool && shift == isShift_eraserTool && alt == isAlt_eraserTool) {
            return false;
        }

        if (keyCode == eyeDropToolKeyCode && ctrl == isCtrl_eyeDropTool && shift == isShift_eyeDropTool && alt == isAlt_eyeDropTool) {
            return false;
        }

        if (keyCode == fanToolKeyCode && ctrl == isCtrl_fanTool && shift == isShift_fanTool && alt == isAlt_fanTool) {
            return false;
        }

        if (keyCode == paintBucketToolKeyCode && ctrl == isCtrl_paintBucketTool && shift == isShift_paintBucketTool && alt == isAlt_paintBucketTool) {
            return false;
        }

        if (keyCode == selectionToolKeyCode && ctrl == isCtrl_selectionTool && shift == isShift_selectionTool && alt == isAlt_selectionTool) {
            return false;
        }

        if (keyCode == rectToolKeyCode && ctrl == isCtrl_rectTool && shift == isShift_rectTool && alt == isAlt_rectTool) {
            return false;
        }

        if (keyCode == textToolKeyCode && ctrl == isCtrl_textTool && shift == isShift_textTool && alt == isAlt_textTool) {
            return false;
        }

        if (keyCode == triangleToolKeyCode && ctrl == isCtrl_triangleTool && shift == isShift_triangleTool && alt == isAlt_triangleTool) {
            return false;
        }

        if (keyCode == printToolKeyCode && ctrl == isCtrl_printTool && shift == isShift_printTool && alt == isAlt_printTool) {
            return false;
        }

        if (keyCode == shortcutToolKeyCode && ctrl == isCtrl_shortcutTool && shift == isShift_shortcutTool && alt == isAlt_shortcutTool) {
            return false;
        }

        if (keyCode == closeToolKeyCode && ctrl == isCtrl_closeTool && shift == isShift_closeTool && alt == isAlt_closeTool) {
            return false;
        }

        if (keyCode == newToolKeyCode && ctrl == isCtrl_newTool && shift == isShift_newTool && alt == isAlt_newTool) {
            return false;
        }

        if (keyCode == greyscaleToolKeyCode && ctrl == isCtrl_greyscaleTool && shift == isShift_greyscaleTool && alt == isAlt_greyscaleTool) {
            return false;
        }

        if (keyCode == brightToolKeyCode && ctrl == isCtrl_brightTool && shift == isShift_brightTool && alt == isAlt_brightTool) {
            return false;
        }

        if (keyCode == saturationToolKeyCode && ctrl == isCtrl_saturationTool && shift == isShift_saturationTool && alt == isAlt_saturationTool) {
            return false;
        }

        if (keyCode == spiralToolKeyCode && ctrl == isCtrl_spiralTool && shift == isShift_spiralTool && alt == isAlt_spiralTool) {
            return false;
        }

        if (keyCode == zoomToolKeyCode && ctrl == isCtrl_zoomTool && shift == isShift_zoomTool && alt == isAlt_zoomTool) {
            return false;
        }
        return true;
    }

    public JComponent getComponent() {
        return component;
    }

    public void setComponent(JComponent component) {
        this.component = component;

    }

    public int getLineToolKeyCode() {
        return lineToolKeyCode;
    }

    public void setLineToolKeyCode(int lineToolKeyCode) {
        this.lineToolKeyCode = lineToolKeyCode;
    }

    public static int getExportKeyCode() {
        return exportKeyCode;
    }

    public static void setExportKeyCode(int exportKeyCode) {
        Shortcuts.exportKeyCode = exportKeyCode;
    }

    public static int getImportKeyCode() {
        return importKeyCode;
    }

    public static void setImportKeyCode(int importKeyCode) {
        Shortcuts.importKeyCode = importKeyCode;
    }

    public static int getBrushToolKeyCode() {
        return brushToolKeyCode;
    }

    public static void setBrushToolKeyCode(int brushToolKeyCode) {
        Shortcuts.brushToolKeyCode = brushToolKeyCode;
    }

    public static int getClearToolKeyCode() {
        return clearToolKeyCode;
    }

    public static void setClearToolKeyCode(int clearToolKeyCode) {
        Shortcuts.clearToolKeyCode = clearToolKeyCode;
    }

    public static boolean isCtrl_clearTool() {
        return isCtrl_clearTool;
    }

    public static void setIsCtrl_clearTool(boolean isCtrl_clearTool) {
        Shortcuts.isCtrl_clearTool = isCtrl_clearTool;
    }

    public static boolean isShift_clearTool() {
        return isShift_clearTool;
    }

    public static void setIsShift_clearTool(boolean isShift_clearTool) {
        Shortcuts.isShift_clearTool = isShift_clearTool;
    }

    public static boolean isAlt_clearTool() {
        return isAlt_clearTool;
    }

    public static void setIsAlt_clearTool(boolean isAlt_clearTool) {
        Shortcuts.isAlt_clearTool = isAlt_clearTool;
    }

    public static boolean isCtrl_export() {
        return isCtrl_export;
    }

    public static void setIsCtrl_export(boolean isCtrl_export) {
        Shortcuts.isCtrl_export = isCtrl_export;
    }

    public static boolean isShift_export() {
        return isShift_export;
    }

    public static void setIsShift_export(boolean isShift_export) {
        Shortcuts.isShift_export = isShift_export;
    }

    public static boolean isAlt_export() {
        return isAlt_export;
    }

    public static void setIsAlt_export(boolean isAlt_export) {
        Shortcuts.isAlt_export = isAlt_export;
    }

    public static boolean isCtrl_import() {
        return isCtrl_import;
    }

    public static void setIsCtrl_import(boolean isCtrl_import) {
        Shortcuts.isCtrl_import = isCtrl_import;
    }

    public static boolean isShift_import() {
        return isShift_import;
    }

    public static void setIsShift_import(boolean isShift_import) {
        Shortcuts.isShift_import = isShift_import;
    }

    public static boolean isAlt_import() {
        return isAlt_import;
    }

    public static void setIsAlt_import(boolean isAlt_import) {
        Shortcuts.isAlt_import = isAlt_import;
    }

    public static boolean isCtrl_brushTool() {
        return isCtrl_brushTool;
    }

    public static void setIsCtrl_brushTool(boolean isCtrl_brushTool) {
        Shortcuts.isCtrl_brushTool = isCtrl_brushTool;
    }

    public static boolean isShift_brushTool() {
        return isShift_brushTool;
    }

    public static void setIsShift_brushTool(boolean isShift_brushTool) {
        Shortcuts.isShift_brushTool = isShift_brushTool;
    }

    public static boolean isAlt_brushTool() {
        return isAlt_brushTool;
    }

    public static void setIsAlt_brushTool(boolean isAlt_brushTool) {
        Shortcuts.isAlt_brushTool = isAlt_brushTool;
    }

    public static boolean isCtrl_lineTool() {
        return isCtrl_lineTool;
    }

    public static void setIsCtrl_lineTool(boolean isCtrl_lineTool) {
        Shortcuts.isCtrl_lineTool = isCtrl_lineTool;
    }

    public static boolean isShift_lineTool() {
        return isShift_lineTool;
    }

    public static void setIsShift_lineTool(boolean isShift_lineTool) {
        Shortcuts.isShift_lineTool = isShift_lineTool;
    }

    public static boolean isAlt_lineTool() {
        return isAlt_lineTool;
    }

    public static void setIsAlt_lineTool(boolean isAlt_lineTool) {
        Shortcuts.isAlt_lineTool = isAlt_lineTool;
    }

    public static String getRectangleToolShortcut() {
        return RECTANGLE_TOOL_SHORTCUT;
    }

    public static int getRectToolKeyCode() {
        return rectToolKeyCode;
    }

    public static void setRectToolKeyCode(int rectToolKeyCode) {
        Shortcuts.rectToolKeyCode = rectToolKeyCode;
    }

    public static boolean isCtrl_rectTool() {
        return isCtrl_rectTool;
    }

    public static void setIsCtrl_rectTool(boolean isCtrl_rectTool) {
        Shortcuts.isCtrl_rectTool = isCtrl_rectTool;
    }

    public static boolean isShift_rectTool() {
        return isShift_rectTool;
    }

    public static void setIsShift_rectTool(boolean isShift_rectTool) {
        Shortcuts.isShift_rectTool = isShift_rectTool;
    }

    public static boolean isAlt_rectTool() {
        return isAlt_rectTool;
    }

    public static void setIsAlt_rectTool(boolean isAlt_rectTool) {
        Shortcuts.isAlt_rectTool = isAlt_rectTool;
    }

    public static String getAirbrushToolShortcut() {
        return AIRBRUSH_TOOL_SHORTCUT;
    }

    public static int getAirBrushToolKeyCode() {
        return airBrushToolKeyCode;
    }

    public static void setAirBrushToolKeyCode(int airBrushToolKeyCode) {
        Shortcuts.airBrushToolKeyCode = airBrushToolKeyCode;
    }

    public static boolean isCtrl_airBrushTool() {
        return isCtrl_airBrushTool;
    }

    public static void setIsCtrl_airBrushTool(boolean isCtrl_airBrushTool) {
        Shortcuts.isCtrl_airBrushTool = isCtrl_airBrushTool;
    }

    public static boolean isShift_airBrushTool() {
        return isShift_airBrushTool;
    }

    public static void setIsShift_airBrushTool(boolean isShift_airBrushTool) {
        Shortcuts.isShift_airBrushTool = isShift_airBrushTool;
    }

    public static boolean isAlt_airBrushTool() {
        return isAlt_airBrushTool;
    }

    public static void setIsAlt_airBrushTool(boolean isAlt_airBrushTool) {
        Shortcuts.isAlt_airBrushTool = isAlt_airBrushTool;
    }

    public static String getCelticknotToolShortcut() {
        return CELTICKNOT_TOOL_SHORTCUT;
    }

    public static int getCelticKnotToolKeyCode() {
        return celticKnotToolKeyCode;
    }

    public static void setCelticKnotToolKeyCode(int celticKnotToolKeyCode) {
        Shortcuts.celticKnotToolKeyCode = celticKnotToolKeyCode;
    }

    public static boolean isCtrl_celticKnotTool() {
        return isCtrl_celticKnotTool;
    }

    public static void setIsCtrl_celticKnotTool(boolean isCtrl_celticKnotTool) {
        Shortcuts.isCtrl_celticKnotTool = isCtrl_celticKnotTool;
    }

    public static boolean isShift_celticKnotTool() {
        return isShift_celticKnotTool;
    }

    public static void setIsShift_celticKnotTool(boolean isShift_celticKnotTool) {
        Shortcuts.isShift_celticKnotTool = isShift_celticKnotTool;
    }

    public static boolean isAlt_celticKnotTool() {
        return isAlt_celticKnotTool;
    }

    public static void setIsAlt_celticKnotTool(boolean isAlt_celticKnotTool) {
        Shortcuts.isAlt_celticKnotTool = isAlt_celticKnotTool;
    }

    public static String getColorChooserToolShortcut() {
        return COLOR_CHOOSER_TOOL_SHORTCUT;
    }

    public static int getColorChooserToolKeyCode() {
        return colorChooserToolKeyCode;
    }

    public static void setColorChooserToolKeyCode(int colorChooserToolKeyCode) {
        Shortcuts.colorChooserToolKeyCode = colorChooserToolKeyCode;
    }

    public static boolean isCtrl_colorChooserTool() {
        return isCtrl_colorChooserTool;
    }

    public static void setIsCtrl_colorChooserTool(boolean isCtrl_colorChooserTool) {
        Shortcuts.isCtrl_colorChooserTool = isCtrl_colorChooserTool;
    }

    public static boolean isShift_colorChooserTool() {
        return isShift_colorChooserTool;
    }

    public static void setIsShift_colorChooserTool(boolean isShift_colorChooserTool) {
        Shortcuts.isShift_colorChooserTool = isShift_colorChooserTool;
    }

    public static boolean isAlt_colorChooserTool() {
        return isAlt_colorChooserTool;
    }

    public static void setIsAlt_colorChooserTool(boolean isAlt_colorChooserTool) {
        Shortcuts.isAlt_colorChooserTool = isAlt_colorChooserTool;
    }

    public static String getDnaToolShortcut() {
        return DNA_TOOL_SHORTCUT;
    }

    public static int getDnaToolKeyCode() {
        return dnaToolKeyCode;
    }

    public static void setDnaToolKeyCode(int dnaToolKeyCode) {
        Shortcuts.dnaToolKeyCode = dnaToolKeyCode;
    }

    public static boolean isCtrl_dnaTool() {
        return isCtrl_dnaTool;
    }

    public static void setIsCtrl_dnaTool(boolean isCtrl_dnaTool) {
        Shortcuts.isCtrl_dnaTool = isCtrl_dnaTool;
    }

    public static boolean isShift_dnaTool() {
        return isShift_dnaTool;
    }

    public static void setIsShift_dnaTool(boolean isShift_dnaTool) {
        Shortcuts.isShift_dnaTool = isShift_dnaTool;
    }

    public static boolean isAlt_dnaTool() {
        return isAlt_dnaTool;
    }

    public static void setIsAlt_dnaTool(boolean isAlt_dnaTool) {
        Shortcuts.isAlt_dnaTool = isAlt_dnaTool;
    }

    public static String getEllipseToolShortcut() {
        return ELLIPSE_TOOL_SHORTCUT;
    }

    public static int getEllipseToolKeyCode() {
        return ellipseToolKeyCode;
    }

    public static void setEllipseToolKeyCode(int ellipseToolKeyCode) {
        Shortcuts.ellipseToolKeyCode = ellipseToolKeyCode;
    }

    public static boolean isCtrl_ellipseTool() {
        return isCtrl_ellipseTool;
    }

    public static void setIsCtrl_ellipseTool(boolean isCtrl_ellipseTool) {
        Shortcuts.isCtrl_ellipseTool = isCtrl_ellipseTool;
    }

    public static boolean isShift_ellipseTool() {
        return isShift_ellipseTool;
    }

    public static void setIsShift_ellipseTool(boolean isShift_ellipseTool) {
        Shortcuts.isShift_ellipseTool = isShift_ellipseTool;
    }

    public static boolean isAlt_ellipseTool() {
        return isAlt_ellipseTool;
    }

    public static void setIsAlt_ellipseTool(boolean isAlt_ellipseTool) {
        Shortcuts.isAlt_ellipseTool = isAlt_ellipseTool;
    }

    public static String getEraserToolShortcut() {
        return ERASER_TOOL_SHORTCUT;
    }

    public static int getEraserToolKeyCode() {
        return eraserToolKeyCode;
    }

    public static void setEraserToolKeyCode(int eraserToolKeyCode) {
        Shortcuts.eraserToolKeyCode = eraserToolKeyCode;
    }

    public static boolean isCtrl_eraserTool() {
        return isCtrl_eraserTool;
    }

    public static void setIsCtrl_eraserTool(boolean isCtrl_eraserTool) {
        Shortcuts.isCtrl_eraserTool = isCtrl_eraserTool;
    }

    public static boolean isShift_eraserTool() {
        return isShift_eraserTool;
    }

    public static void setIsShift_eraserTool(boolean isShift_eraserTool) {
        Shortcuts.isShift_eraserTool = isShift_eraserTool;
    }

    public static boolean isAlt_eraserTool() {
        return isAlt_eraserTool;
    }

    public static void setIsAlt_eraserTool(boolean isAlt_eraserTool) {
        Shortcuts.isAlt_eraserTool = isAlt_eraserTool;
    }

    public static String getEyeDropToolShortcut() {
        return EYE_DROP_TOOL_SHORTCUT;
    }

    public static int getEyeDropToolKeyCode() {
        return eyeDropToolKeyCode;
    }

    public static void setEyeDropToolKeyCode(int eyeDropToolKeyCode) {
        Shortcuts.eyeDropToolKeyCode = eyeDropToolKeyCode;
    }

    public static boolean isCtrl_eyeDropTool() {
        return isCtrl_eyeDropTool;
    }

    public static void setIsCtrl_eyeDropTool(boolean isCtrl_eyeDropTool) {
        Shortcuts.isCtrl_eyeDropTool = isCtrl_eyeDropTool;
    }

    public static boolean isShift_eyeDropTool() {
        return isShift_eyeDropTool;
    }

    public static void setIsShift_eyeDropTool(boolean isShift_eyeDropTool) {
        Shortcuts.isShift_eyeDropTool = isShift_eyeDropTool;
    }

    public static boolean isAlt_eyeDropTool() {
        return isAlt_eyeDropTool;
    }

    public static void setIsAlt_eyeDropTool(boolean isAlt_eyeDropTool) {
        Shortcuts.isAlt_eyeDropTool = isAlt_eyeDropTool;
    }

    public static String getFanToolShortcut() {
        return FAN_TOOL_SHORTCUT;
    }

    public static int getFanToolKeyCode() {
        return fanToolKeyCode;
    }

    public static void setFanToolKeyCode(int fanToolKeyCode) {
        Shortcuts.fanToolKeyCode = fanToolKeyCode;
    }

    public static boolean isCtrl_fanTool() {
        return isCtrl_fanTool;
    }

    public static void setIsCtrl_fanTool(boolean isCtrl_fanTool) {
        Shortcuts.isCtrl_fanTool = isCtrl_fanTool;
    }

    public static boolean isShift_fanTool() {
        return isShift_fanTool;
    }

    public static void setIsShift_fanTool(boolean isShift_fanTool) {
        Shortcuts.isShift_fanTool = isShift_fanTool;
    }

    public static boolean isAlt_fanTool() {
        return isAlt_fanTool;
    }

    public static void setIsAlt_fanTool(boolean isAlt_fanTool) {
        Shortcuts.isAlt_fanTool = isAlt_fanTool;
    }

    public static String getPaintbucketToolShortcut() {
        return PAINTBUCKET_TOOL_SHORTCUT;
    }

    public static int getPaintBucketToolKeyCode() {
        return paintBucketToolKeyCode;
    }

    public static void setPaintBucketToolKeyCode(int paintBucketToolKeyCode) {
        Shortcuts.paintBucketToolKeyCode = paintBucketToolKeyCode;
    }

    public static boolean isCtrl_paintBucketTool() {
        return isCtrl_paintBucketTool;
    }

    public static void setIsCtrl_paintBucketTool(boolean isCtrl_paintBucketTool) {
        Shortcuts.isCtrl_paintBucketTool = isCtrl_paintBucketTool;
    }

    public static boolean isShift_paintBucketTool() {
        return isShift_paintBucketTool;
    }

    public static void setIsShift_paintBucketTool(boolean isShift_paintBucketTool) {
        Shortcuts.isShift_paintBucketTool = isShift_paintBucketTool;
    }

    public static boolean isAlt_paintBucketTool() {
        return isAlt_paintBucketTool;
    }

    public static void setIsAlt_paintBucketTool(boolean isAlt_paintBucketTool) {
        Shortcuts.isAlt_paintBucketTool = isAlt_paintBucketTool;
    }

    public static String getSelectionToolShortcut() {
        return SELECTION_TOOL_SHORTCUT;
    }

    public static int getSelectionToolKeyCode() {
        return selectionToolKeyCode;
    }

    public static void setSelectionToolKeyCode(int selectionToolKeyCode) {
        Shortcuts.selectionToolKeyCode = selectionToolKeyCode;
    }

    public static boolean isCtrl_selectionTool() {
        return isCtrl_selectionTool;
    }

    public static void setIsCtrl_selectionTool(boolean isCtrl_selectionTool) {
        Shortcuts.isCtrl_selectionTool = isCtrl_selectionTool;
    }

    public static boolean isShift_selectionTool() {
        return isShift_selectionTool;
    }

    public static void setIsShift_selectionTool(boolean isShift_selectionTool) {
        Shortcuts.isShift_selectionTool = isShift_selectionTool;
    }

    public static boolean isAlt_selectionTool() {
        return isAlt_selectionTool;
    }

    public static void setIsAlt_selectionTool(boolean isAlt_selectionTool) {
        Shortcuts.isAlt_selectionTool = isAlt_selectionTool;
    }

    public static String getTextToolShortcut() {
        return TEXT_TOOL_SHORTCUT;
    }

    public static int getTextToolKeyCode() {
        return textToolKeyCode;
    }

    public static void setTextToolKeyCode(int textToolKeyCode) {
        Shortcuts.textToolKeyCode = textToolKeyCode;
    }

    public static boolean isCtrl_textTool() {
        return isCtrl_textTool;
    }

    public static void setIsCtrl_textTool(boolean isCtrl_textTool) {
        Shortcuts.isCtrl_textTool = isCtrl_textTool;
    }

    public static boolean isShift_textTool() {
        return isShift_textTool;
    }

    public static void setIsShift_textTool(boolean isShift_textTool) {
        Shortcuts.isShift_textTool = isShift_textTool;
    }

    public static boolean isAlt_textTool() {
        return isAlt_textTool;
    }

    public static void setIsAlt_textTool(boolean isAlt_textTool) {
        Shortcuts.isAlt_textTool = isAlt_textTool;
    }

    public static String getTriangleToolShortcut() {
        return TRIANGLE_TOOL_SHORTCUT;
    }

    public static int getTriangleToolKeyCode() {
        return triangleToolKeyCode;
    }

    public static void setTriangleToolKeyCode(int triangleToolKeyCode) {
        Shortcuts.triangleToolKeyCode = triangleToolKeyCode;
    }

    public static boolean isCtrl_triangleTool() {
        return isCtrl_triangleTool;
    }

    public static void setIsCtrl_triangleTool(boolean isCtrl_triangleTool) {
        Shortcuts.isCtrl_triangleTool = isCtrl_triangleTool;
    }

    public static boolean isShift_triangleTool() {
        return isShift_triangleTool;
    }

    public static void setIsShift_triangleTool(boolean isShift_triangleTool) {
        Shortcuts.isShift_triangleTool = isShift_triangleTool;
    }

    public static boolean isAlt_triangleTool() {
        return isAlt_triangleTool;
    }

    public static void setIsAlt_triangleTool(boolean isAlt_triangleTool) {
        Shortcuts.isAlt_triangleTool = isAlt_triangleTool;
    }

    public static String getPrintShortcut() {
        return PRINT_SHORTCUT;
    }

    public static int getPrintToolKeyCode() {
        return printToolKeyCode;
    }

    public static void setPrintToolKeyCode(int printToolKeyCode) {
        Shortcuts.printToolKeyCode = printToolKeyCode;
    }

    public static boolean isCtrl_printTool() {
        return isCtrl_printTool;
    }

    public static void setIsCtrl_printTool(boolean isCtrl_printTool) {
        Shortcuts.isCtrl_printTool = isCtrl_printTool;
    }

    public static boolean isShift_printTool() {
        return isShift_printTool;
    }

    public static void setIsShift_printTool(boolean isShift_printTool) {
        Shortcuts.isShift_printTool = isShift_printTool;
    }

    public static boolean isAlt_printTool() {
        return isAlt_printTool;
    }

    public static void setIsAlt_printTool(boolean isAlt_printTool) {
        Shortcuts.isAlt_printTool = isAlt_printTool;
    }

    public static String getShortcutShortcut() {
        return SHORTCUT_SHORTCUT;
    }

    public static int getShortcutToolKeyCode() {
        return shortcutToolKeyCode;
    }

    public static void setShortcutToolKeyCode(int shortcutToolKeyCode) {
        Shortcuts.shortcutToolKeyCode = shortcutToolKeyCode;
    }

    public static boolean isCtrl_shortcutTool() {
        return isCtrl_shortcutTool;
    }

    public static void setIsCtrl_shortcutTool(boolean isCtrl_shortcutTool) {
        Shortcuts.isCtrl_shortcutTool = isCtrl_shortcutTool;
    }

    public static boolean isShift_shortcutTool() {
        return isShift_shortcutTool;
    }

    public static void setIsShift_shortcutTool(boolean isShift_shortcutTool) {
        Shortcuts.isShift_shortcutTool = isShift_shortcutTool;
    }

    public static boolean isAlt_shortcutTool() {
        return isAlt_shortcutTool;
    }

    public static void setIsAlt_shortcutTool(boolean isAlt_shortcutTool) {
        Shortcuts.isAlt_shortcutTool = isAlt_shortcutTool;
    }

    public static String getCloseShortcut() {
        return CLOSE_SHORTCUT;
    }

    public static int getCloseToolKeyCode() {
        return closeToolKeyCode;
    }

    public static void setCloseToolKeyCode(int closeToolKeyCode) {
        Shortcuts.closeToolKeyCode = closeToolKeyCode;
    }

    public static boolean isCtrl_closeTool() {
        return isCtrl_closeTool;
    }

    public static void setIsCtrl_closeTool(boolean isCtrl_closeTool) {
        Shortcuts.isCtrl_closeTool = isCtrl_closeTool;
    }

    public static boolean isShift_closeTool() {
        return isShift_closeTool;
    }

    public static void setIsShift_closeTool(boolean isShift_closeTool) {
        Shortcuts.isShift_closeTool = isShift_closeTool;
    }

    public static boolean isAlt_closeTool() {
        return isAlt_closeTool;
    }

    public static void setIsAlt_closeTool(boolean isAlt_closeTool) {
        Shortcuts.isAlt_closeTool = isAlt_closeTool;
    }

    public static String getNewShortcut() {
        return NEW_SHORTCUT;
    }

    public static int getNewToolKeyCode() {
        return newToolKeyCode;
    }

    public static void setNewToolKeyCode(int newToolKeyCode) {
        Shortcuts.newToolKeyCode = newToolKeyCode;
    }

    public static boolean isCtrl_newTool() {
        return isCtrl_newTool;
    }

    public static void setIsCtrl_newTool(boolean isCtrl_newTool) {
        Shortcuts.isCtrl_newTool = isCtrl_newTool;
    }

    public static boolean isShift_newTool() {
        return isShift_newTool;
    }

    public static void setIsShift_newTool(boolean isShift_newTool) {
        Shortcuts.isShift_newTool = isShift_newTool;
    }

    public static boolean isAlt_newTool() {
        return isAlt_newTool;
    }

    public static void setIsAlt_newTool(boolean isAlt_newTool) {
        Shortcuts.isAlt_newTool = isAlt_newTool;
    }

    public static String getGreyscaleShortcut() {
        return GREYSCALE_SHORTCUT;
    }

    public static int getGreyscaleToolKeyCode() {
        return greyscaleToolKeyCode;
    }

    public static void setGreyscaleToolKeyCode(int greyscaleToolKeyCode) {
        Shortcuts.greyscaleToolKeyCode = greyscaleToolKeyCode;
    }

    public static boolean isCtrl_greyscaleTool() {
        return isCtrl_greyscaleTool;
    }

    public static void setIsCtrl_greyscaleTool(boolean isCtrl_greyscaleTool) {
        Shortcuts.isCtrl_greyscaleTool = isCtrl_greyscaleTool;
    }

    public static boolean isShift_greyscaleTool() {
        return isShift_greyscaleTool;
    }

    public static void setIsShift_greyscaleTool(boolean isShift_greyscaleTool) {
        Shortcuts.isShift_greyscaleTool = isShift_greyscaleTool;
    }

    public static boolean isAlt_greyscaleTool() {
        return isAlt_greyscaleTool;
    }

    public static void setIsAlt_greyscaleTool(boolean isAlt_greyscaleTool) {
        Shortcuts.isAlt_greyscaleTool = isAlt_greyscaleTool;
    }

    public static String getBrightnessShortcut() {
        return BRIGHTNESS_SHORTCUT;
    }

    public static int getBrightToolKeyCode() {
        return brightToolKeyCode;
    }

    public static void setBrightToolKeyCode(int brightToolKeyCode) {
        Shortcuts.brightToolKeyCode = brightToolKeyCode;
    }

    public static boolean isCtrl_brightTool() {
        return isCtrl_brightTool;
    }

    public static void setIsCtrl_brightTool(boolean isCtrl_brightTool) {
        Shortcuts.isCtrl_brightTool = isCtrl_brightTool;
    }

    public static boolean isShift_brightTool() {
        return isShift_brightTool;
    }

    public static void setIsShift_brightTool(boolean isShift_brightTool) {
        Shortcuts.isShift_brightTool = isShift_brightTool;
    }

    public static boolean isAlt_brightTool() {
        return isAlt_brightTool;
    }

    public static void setIsAlt_brightTool(boolean isAlt_brightTool) {
        Shortcuts.isAlt_brightTool = isAlt_brightTool;
    }

    public static String getSaturationShortcut() {
        return SATURATION_SHORTCUT;
    }

    public static int getSaturationToolKeyCode() {
        return saturationToolKeyCode;
    }

    public static void setSaturationToolKeyCode(int saturationToolKeyCode) {
        Shortcuts.saturationToolKeyCode = saturationToolKeyCode;
    }

    public static boolean isCtrl_saturationTool() {
        return isCtrl_saturationTool;
    }

    public static void setIsCtrl_saturationTool(boolean isCtrl_saturationTool) {
        Shortcuts.isCtrl_saturationTool = isCtrl_saturationTool;
    }

    public static boolean isShift_saturationTool() {
        return isShift_saturationTool;
    }

    public static void setIsShift_saturationTool(boolean isShift_saturationTool) {
        Shortcuts.isShift_saturationTool = isShift_saturationTool;
    }

    public static boolean isAlt_saturationTool() {
        return isAlt_saturationTool;
    }

    public static void setIsAlt_saturationTool(boolean isAlt_saturationTool) {
        Shortcuts.isAlt_saturationTool = isAlt_saturationTool;
    }

    public static String getSpiralToolShortcut() {
        return SPIRAL_TOOL_SHORTCUT;
    }

    public static int getSpiralToolKeyCode() {
        return spiralToolKeyCode;
    }

    public static void setSpiralToolKeyCode(int spiralToolKeyCode) {
        Shortcuts.spiralToolKeyCode = spiralToolKeyCode;
    }

    public static boolean isCtrl_spiralTool() {
        return isCtrl_spiralTool;
    }

    public static void setIsCtrl_spiralTool(boolean isCtrl_spiralTool) {
        Shortcuts.isCtrl_spiralTool = isCtrl_spiralTool;
    }

    public static boolean isShift_spiralTool() {
        return isShift_spiralTool;
    }

    public static void setIsShift_spiralTool(boolean isShift_spiralTool) {
        Shortcuts.isShift_spiralTool = isShift_spiralTool;
    }

    public static boolean isAlt_spiralTool() {
        return isAlt_spiralTool;
    }

    public static void setIsAlt_spiralTool(boolean isAlt_spiralTool) {
        Shortcuts.isAlt_spiralTool = isAlt_spiralTool;
    }

    public static String getZoomToolShortcut() {
        return ZOOM_TOOL_SHORTCUT;
    }

    public static int getZoomToolKeyCode() {
        return zoomToolKeyCode;
    }

    public static void setZoomToolKeyCode(int zoomToolKeyCode) {
        Shortcuts.zoomToolKeyCode = zoomToolKeyCode;
    }

    public static boolean isCtrl_zoomTool() {
        return isCtrl_zoomTool;
    }

    public static void setIsCtrl_zoomTool(boolean isCtrl_zoomTool) {
        Shortcuts.isCtrl_zoomTool = isCtrl_zoomTool;
    }

    public static boolean isShift_zoomTool() {
        return isShift_zoomTool;
    }

    public static void setIsShift_zoomTool(boolean isShift_zoomTool) {
        Shortcuts.isShift_zoomTool = isShift_zoomTool;
    }

    public static boolean isAlt_zoomTool() {
        return isAlt_zoomTool;
    }

    public static void setIsAlt_zoomTool(boolean isAlt_zoomTool) {
        Shortcuts.isAlt_zoomTool = isAlt_zoomTool;
    }
}
