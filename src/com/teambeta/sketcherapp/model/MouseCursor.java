package com.teambeta.sketcherapp.model;

import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;
import java.io.File;

public class MouseCursor {
    private static final String DRAG_CURSOR = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "res" + File.separator + "dragCursor.png";

    private static final String TARGET_CURSOR = System.getProperty("user.dir") + File.separator + "src" +
            File.separator + "res" + File.separator + "target.png";
    private static DrawArea drawArea;

    public MouseCursor(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    public static void dragCursor() {

        setCursor(DRAG_CURSOR);
    }

    public static void targetCursor() {

        setCursor(TARGET_CURSOR);


    }

    private static void setCursor(String customCursor){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage(customCursor);
        Point point = new Point(0, 0);
        Cursor cursor = toolkit.createCustomCursor(image, point, "Cursor");
        // Cursor c = toolkit.createCustomCursor(image , new Point(mainFrame.getX(),
        //   mainFrame.getY()), "img");
        drawArea.setCursor(cursor);
    }


    public static void setDefaultCursor() {
        Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        drawArea.setCursor(normalCursor);
    }


}
