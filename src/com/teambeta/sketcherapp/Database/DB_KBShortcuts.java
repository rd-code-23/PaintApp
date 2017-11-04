package com.teambeta.sketcherapp.Database;

import com.teambeta.sketcherapp.model.Shortcuts;

import java.sql.*;

public class DB_KBShortcuts {

    private static final String KB_SHORTCUTS_TABLE = "KB_SHORTCUTS_TABLE";
    private static final String SHORTCUT_NAME = "SHORTCUT_NAME";
    private static final String KEY_STROKE = "KEY_STROKE";
    private static final String IS_CTRL = "IS_CTRL";
    private static final String IS_ALT = "IS_ALT";
    private static final String IS_SHIFT = "IS_SHIFT";

    private Connection connection = null;
    private Shortcuts shortcuts;


    /**
     * @param shortcuts used to generate the database
     */
    public DB_KBShortcuts(Shortcuts shortcuts) {
        this.shortcuts = shortcuts;
    }


    /**
     * creates a table
     */
    public void createTable() {
        try {
            connection = ConnectionConfiguration.getConnection();

            String sqlCreate = "CREATE TABLE IF NOT EXISTS " + KB_SHORTCUTS_TABLE
                    + "( "
                    + SHORTCUT_NAME + "  VARCHAR(50) PRIMARY KEY, "
                    + KEY_STROKE + "  VARCHAR(5), "
                    + IS_CTRL + "    VARCHAR(5)       NOT NULL, "
                    + IS_SHIFT + "    VARCHAR(5)       NOT NULL, "
                    + IS_ALT + "     VARCHAR(5)       NOT NULL "
                    + " );";

            Statement stmt = connection.createStatement();
            stmt.execute(sqlCreate);
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * inserts a record into the database
     */
    public void insert(String shortcutName, String keyStroke, String isCTRL, String isALT, String isSHIFT) {

        if (isDataExists(shortcutName)) {
            return;
        }

        PreparedStatement pst = null;

        try {
            connection = ConnectionConfiguration.getConnection();
            pst = connection.prepareStatement(
                    "INSERT INTO " + KB_SHORTCUTS_TABLE +
                            "(SHORTCUT_NAME,KEY_STROKE,IS_CTRL,IS_SHIFT,IS_ALT)" +
                            "VALUES(?,?,?,?,?) ");

            pst.setString(1, shortcutName); // 1 = first column
            pst.setString(2, keyStroke);
            pst.setString(3, isCTRL);
            pst.setString(4, isSHIFT);
            pst.setString(5, isALT);
            pst.executeUpdate();
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * updates a record in the database
     *
     * @param shortcutName
     * @param keyStroke
     * @param isCTRL
     * @param isALT
     * @param isSHIFT
     */
    public void update(String shortcutName, String keyStroke, String isCTRL, String isALT, String isSHIFT) {
        try {
            connection = ConnectionConfiguration.getConnection();

            String query = "UPDATE  " + KB_SHORTCUTS_TABLE
                    + " SET "
                    + SHORTCUT_NAME + " =  ?, "
                    + KEY_STROKE + " =  ?, "
                    + IS_CTRL + " =  ?, "
                    + IS_SHIFT + " =  ?, "
                    + IS_ALT + " =  ? "
                    + " WHERE "
                    + SHORTCUT_NAME + " LIKE ? ";

            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, shortcutName);
            pst.setString(2, keyStroke);
            pst.setString(3, isCTRL);
            pst.setString(4, isSHIFT);
            pst.setString(5, isALT);
            pst.setString(6, shortcutName);
            pst.executeUpdate();
            pst.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * drop table
     */
    public void dropTable() {
        try {
            connection = ConnectionConfiguration.getConnection();

            String sqlCreate = " DROP TABLE " + KB_SHORTCUTS_TABLE;

            Statement stmt = connection.createStatement();
            stmt.execute(sqlCreate);
            stmt.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * generates the key binding from the database
     */
    public void generateDBKeyBindings() {
        try {
            connection = ConnectionConfiguration.getConnection();
            Statement stmt = connection.createStatement();
            ResultSet rset = stmt.executeQuery("select * from " + KB_SHORTCUTS_TABLE);

            while (rset.next()) {

                boolean isCtrl = false;
                boolean isShift = false;
                boolean isAlt = false;

                if (rset.getString(IS_CTRL).equals("true")) {
                    isCtrl = true;
                }

                if (rset.getString(IS_SHIFT).equals("true")) {
                    isShift = true;
                }

                if (rset.getString(IS_ALT).equals("true")) {
                    isAlt = true;
                }

                int keyCode;
                keyCode = rset.getString(KEY_STROKE).charAt(0);

                shortcuts.removeBinding(keyCode, isCtrl, isShift, isAlt);


                shortcuts.getDBShortcuts(keyCode, isCtrl, isShift, isAlt, rset.getString(SHORTCUT_NAME));

            }

            stmt.close();
            rset.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * checks to see if a record exists
     *
     * @param shortcutName
     * @return
     */
    public boolean isDataExists(String shortcutName) {
        try {
            connection = ConnectionConfiguration.getConnection();
            String query = "SELECT (count(*) > 0) as found FROM " + KB_SHORTCUTS_TABLE + " WHERE " + SHORTCUT_NAME + " LIKE ? ";
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setString(1, shortcutName);


            try (ResultSet rs = pst.executeQuery()) {
                // Only expecting a single result
                if (rs.next()) {
                    boolean found = rs.getBoolean(1); // "found" column
                    if (found) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }

            pst.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * checks to see if a table exists
     *
     * @return
     */
    public boolean isTableExists() {
        try {
            connection = ConnectionConfiguration.getConnection();
            DatabaseMetaData dbm = connection.getMetaData();

            ResultSet rset = dbm.getTables(null, null, KB_SHORTCUTS_TABLE, null);

            if (rset.next()) {
                rset.close();
                connection.close();
                return true;
            } else {
                rset.close();
                connection.close();
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();

        }

        return false;
    }

    /**
     * prints a tables contents through the console
     */
    public void printTable() {
        ResultSet rs = null;
        Statement st = null;
        ResultSetMetaData rsmd = null;
        try {
            connection = ConnectionConfiguration.getConnection();
            st = connection.createStatement();
            rs = st.executeQuery("select * from " + KB_SHORTCUTS_TABLE);
            rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                //Print one row
                for (int i = 1; i <= columnsNumber; i++) {

                    System.out.print(rs.getString(i) + " ");

                }

                System.out.println();

            }

            System.out.println();


            rs.close();
            st.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


}
