package com.teambeta.sketcherapp.model;

import com.teambeta.sketcherapp.ui.DrawArea;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.net.URL;

public class MouseCursor {
    private static final String DRAG_CURSOR = "dragCursor.png";
    private static final String TARGET_CURSOR = "target.png";
    private static DrawArea drawArea;

    public MouseCursor(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    public void dragCursor() {
        setCursor(DRAG_CURSOR);
    }

    public void targetCursor() {
        setCursor(TARGET_CURSOR);
    }

    private void setCursor(String customCursor) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        JLabel l = new JLabel(new ImageIcon(getClass().getClassLoader().getResource(customCursor)));
        Image image = new ImageIcon(getClass().getClassLoader().getResource(customCursor)).getImage();
        Point point = new Point(0, 0);
        Cursor cursor = toolkit.createCustomCursor(image, point, "Cursor");
        drawArea.setCursor(cursor);
    }

    public static void setDefaultCursor() {
        Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        drawArea.setCursor(normalCursor);
    }
}