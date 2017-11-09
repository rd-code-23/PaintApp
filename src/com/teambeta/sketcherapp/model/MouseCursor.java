package com.teambeta.sketcherapp.model;

import com.teambeta.sketcherapp.ui.DrawArea;

import java.awt.*;

public class MouseCursor {

   private static DrawArea drawArea;

    public MouseCursor(DrawArea drawArea) {
        this.drawArea = drawArea;
    }

    public static void dragCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("C:\\Users\\rdhol\\IdeaProjects\\BetaSketcherApp\\src\\res\\dragCursor.png");
        Point point = new Point(0,0);
        Cursor cursor = toolkit.createCustomCursor(image,point,"Cursor");
        // Cursor c = toolkit.createCustomCursor(image , new Point(mainFrame.getX(),
        //   mainFrame.getY()), "img");


        drawArea.setCursor(cursor);
    }

    public static void targetCursor(){
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("C:\\Users\\rdhol\\IdeaProjects\\BetaSketcherApp\\src\\res\\target.png");
        Point point = new Point(0,0);
        Cursor cursor = toolkit.createCustomCursor(image,point,"Cursor");
        // Cursor c = toolkit.createCustomCursor(image , new Point(mainFrame.getX(),
        //   mainFrame.getY()), "img");


        drawArea.setCursor(cursor);
    }

    public  static void setDefaultCursor(){
        Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
        drawArea.setCursor(normalCursor);
    }







}
