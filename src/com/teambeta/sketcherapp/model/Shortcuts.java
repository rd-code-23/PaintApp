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
     * gets any tools key code
     */
    public char getKBShortcut(String tool) {
        switch (tool) {
            case CLEAR_TOOL_SHORTCUT:
                return (char) clearToolKeyCode;
            case EXPORT_SHORTCUT:
                return (char) exportKeyCode;
            case IMPORT_SHORTCUT:
                return (char) importKeyCode;
            case BRUSH_TOOL_SHORTCUT:
                return (char) brushToolKeyCode;
            case LINE_TOOL_SHORTCUT:
                return (char) lineToolKeyCode;
            default:
                System.out.println(NO_SUCH_TOOL);
                return 0;
        }
    }

    /**
     * when the user changes a binding, this function will check to see if there is another tool using the same binding
     */
    public boolean isValidKeyBinding(int keyCode, boolean ctrl, boolean shift, boolean alt) {
        if (keyCode == brushToolKeyCode && ctrl == isCtrl_brushTool && shift == isShift_brushTool() && alt == isAlt_brushTool()) {
            return false;
        }
        if (keyCode == lineToolKeyCode && ctrl == isCtrl_lineTool && shift == isShift_lineTool() && alt == isAlt_lineTool()) {
            return false;
        }
        if (keyCode == clearToolKeyCode && ctrl == isCtrl_clearTool && shift == isShift_clearTool() && alt == isAlt_clearTool()) {
            return false;
        }

        if (keyCode == importKeyCode && ctrl == isCtrl_import && shift == isShift_import() && alt == isAlt_import()) {
            return false;
        }

        if (keyCode == exportKeyCode && ctrl == isCtrl_export && shift == isShift_export() && alt == isAlt_export()) {
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
}
