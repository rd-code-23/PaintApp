package com.teambeta.sketcherapp.model;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;

import com.teambeta.sketcherapp.Main;
import com.teambeta.sketcherapp.ui.DrawArea;
import com.teambeta.sketcherapp.ui.MainUI;

public class Shortcuts {

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


    int lineToolKeyCode;
    static boolean isCtrl_lineTool;
    static boolean isShift_lineTool;
    static boolean isAlt_lineTool;
    JComponent component;
    MainUI mainUI;

    public Shortcuts(JComponent component, MainUI mainUI) {
        this.component = component;
        im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        actionMap = component.getActionMap();
        this.mainUI = mainUI;
    }


    ActionMap actionMap;
    InputMap im;


    public void addKeyBinding(int keyCode, boolean useControl, boolean useShift, boolean useAlt, String id, ActionListener actionListener) {


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


        actionMap.put(id, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actionListener.actionPerformed(e);
            }
        });


        setKBShortcut(id, keyCode, useControl, useShift, useAlt);
        if (actionMap.get(id).equals(CLEAR_TOOL_SHORTCUT)) {
            System.out.println("its clear ");
        }

        //  System.out.println("action map size: " + actionMap.size());
        //System.out.println("actionMap: " + actionMap.get(id));
    }

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
    }


    public void changeKeyBinding(int keyCode, boolean useControl, boolean useShift, boolean useAlt, String id) {
        im = component.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
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
    }

    public void setKBShortcut(String tool, int keyCode, boolean useControl, boolean useShift, boolean useAlt) {
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
                System.out.println("NO SUCH TOOL for set ");
        }
    }

    public void removeAllBindings() {
        im.clear();
        mainUI.generateDefaultKeyBindings();
    }

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
                System.out.println("NO SUCH TOOL for get ");
                return 0;
        }
    }


    public boolean isValidKeyBinding(int keyCode, boolean ctrl, boolean shift, boolean alt) {
        if (keyCode == brushToolKeyCode && ctrl == isCtrl_brushTool && shift == isIsShift_brushTool() && alt == isIsAlt_brushTool()) {
            return false;
        }
        if (keyCode == lineToolKeyCode && ctrl == isCtrl_lineTool && shift == isIsShift_lineTool() && alt == isIsAlt_lineTool()) {
            return false;
        }
        if (keyCode == clearToolKeyCode && ctrl == isCtrl_clearTool && shift == isIsShift_clearTool() && alt == isIsAlt_clearTool()) {
            return false;
        }

        if (keyCode == importKeyCode && ctrl == isCtrl_import && shift == isIsShift_import() && alt == isIsAlt_import()) {
            return false;
        }

        if (keyCode == exportKeyCode && ctrl == isCtrl_export && shift == isIsShift_export() && alt == isIsAlt_export()) {
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

    public static boolean isIsCtrl_clearTool() {
        return isCtrl_clearTool;
    }

    public static void setIsCtrl_clearTool(boolean isCtrl_clearTool) {
        Shortcuts.isCtrl_clearTool = isCtrl_clearTool;
    }

    public static boolean isIsShift_clearTool() {
        return isShift_clearTool;
    }

    public static void setIsShift_clearTool(boolean isShift_clearTool) {
        Shortcuts.isShift_clearTool = isShift_clearTool;
    }

    public static boolean isIsAlt_clearTool() {
        return isAlt_clearTool;
    }

    public static void setIsAlt_clearTool(boolean isAlt_clearTool) {
        Shortcuts.isAlt_clearTool = isAlt_clearTool;
    }

    public static boolean isIsCtrl_export() {
        return isCtrl_export;
    }

    public static void setIsCtrl_export(boolean isCtrl_export) {
        Shortcuts.isCtrl_export = isCtrl_export;
    }

    public static boolean isIsShift_export() {
        return isShift_export;
    }

    public static void setIsShift_export(boolean isShift_export) {
        Shortcuts.isShift_export = isShift_export;
    }

    public static boolean isIsAlt_export() {
        return isAlt_export;
    }

    public static void setIsAlt_export(boolean isAlt_export) {
        Shortcuts.isAlt_export = isAlt_export;
    }

    public static boolean isIsCtrl_import() {
        return isCtrl_import;
    }

    public static void setIsCtrl_import(boolean isCtrl_import) {
        Shortcuts.isCtrl_import = isCtrl_import;
    }

    public static boolean isIsShift_import() {
        return isShift_import;
    }

    public static void setIsShift_import(boolean isShift_import) {
        Shortcuts.isShift_import = isShift_import;
    }

    public static boolean isIsAlt_import() {
        return isAlt_import;
    }

    public static void setIsAlt_import(boolean isAlt_import) {
        Shortcuts.isAlt_import = isAlt_import;
    }

    public static boolean isIsCtrl_brushTool() {
        return isCtrl_brushTool;
    }

    public static void setIsCtrl_brushTool(boolean isCtrl_brushTool) {
        Shortcuts.isCtrl_brushTool = isCtrl_brushTool;
    }

    public static boolean isIsShift_brushTool() {
        return isShift_brushTool;
    }

    public static void setIsShift_brushTool(boolean isShift_brushTool) {
        Shortcuts.isShift_brushTool = isShift_brushTool;
    }

    public static boolean isIsAlt_brushTool() {
        return isAlt_brushTool;
    }

    public static void setIsAlt_brushTool(boolean isAlt_brushTool) {
        Shortcuts.isAlt_brushTool = isAlt_brushTool;
    }

    public static boolean isIsCtrl_lineTool() {
        return isCtrl_lineTool;
    }

    public static void setIsCtrl_lineTool(boolean isCtrl_lineTool) {
        Shortcuts.isCtrl_lineTool = isCtrl_lineTool;
    }

    public static boolean isIsShift_lineTool() {
        return isShift_lineTool;
    }

    public static void setIsShift_lineTool(boolean isShift_lineTool) {
        Shortcuts.isShift_lineTool = isShift_lineTool;
    }

    public static boolean isIsAlt_lineTool() {
        return isAlt_lineTool;
    }

    public static void setIsAlt_lineTool(boolean isAlt_lineTool) {
        Shortcuts.isAlt_lineTool = isAlt_lineTool;
    }
}
