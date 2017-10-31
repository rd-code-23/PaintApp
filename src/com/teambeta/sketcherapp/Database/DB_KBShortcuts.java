package com.teambeta.sketcherapp.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class DB_KBShortcuts {

    public static final String KB_SHORTCUTS_TABLE = "KB_SHORTCUTS_TABLE";
    public static final String SHORTCUT_NAME = "SHORTCUT_NAME";
    public static final String KEY_STROKE = "KEY_STROKE";
    public static final String IS_CTRL = "IS_CTRL";
    public static final String IS_ALT = "IS_ALT";
    public static final String IS_SHIFT = "IS_SHIFT";

    public void createTable() {
        Connection connection = null;

        try {
            connection = ConnectionConfiguration.getConnection();
            String sqlCreate = "CREATE TABLE IF NOT EXISTS " + KB_SHORTCUTS_TABLE
                    + "( "
                    + SHORTCUT_NAME + "  VARCHAR(50) PRIMARY KEY, "
                    + KEY_STROKE + "  VARCHAR(5), "
                    + IS_CTRL + "     ENUM('T', 'F'), "
                    + IS_ALT + "     ENUM('T', 'F'), "
                    + IS_SHIFT + "     ENUM('T', 'F'), "
                    + ")";

            Statement stmt = connection.createStatement();
            stmt.execute(sqlCreate);
            System.out.println("Table created or exists");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insert(String shortcutName, String keyStroke, String isCTRL, String isALT, String isSHIFT) {
        Connection connection = null;
        PreparedStatement preparedStatement = null; // protects from SQL injection attacks
        try {

            connection = ConnectionConfiguration.getConnection();

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + KB_SHORTCUTS_TABLE +
                            "(SHORTCUT_NAME,KEY_STROKE,IS_CTRL,IS_ALT,IS_SHIFT)" +
                            "VALUES(?,?,?,?,?) ");
            preparedStatement.setString(1, shortcutName); // 1 = first column
            preparedStatement.setString(2, keyStroke);
            preparedStatement.setString(3, isCTRL);
            preparedStatement.setString(4, isALT);
            preparedStatement.setString(5, isSHIFT);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update(String shortcutName, String keyStroke, String isCTRL, String isALT, String isSHIFT) {
        Connection connection = null;
        PreparedStatement preparedStatement = null; // protects from SQL injection attacks
        try {

            connection = ConnectionConfiguration.getConnection();

            preparedStatement = connection.prepareStatement(
                    "UPDATE " + KB_SHORTCUTS_TABLE + " WHERE " + SHORTCUT_NAME
                            + " = " + shortcutName +
                            "(SHORTCUT_NAME,KEY_STROKE,IS_CTRL,IS_ALT,IS_SHIFT)" +
                            "VALUES(?,?,?,?,?) ");
            preparedStatement.setString(1, shortcutName); // 1 = first column
            preparedStatement.setString(2, keyStroke);
            preparedStatement.setString(3, isCTRL);
            preparedStatement.setString(4, isALT);
            preparedStatement.setString(5, isSHIFT);
            preparedStatement.executeUpdate();
            preparedStatement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
