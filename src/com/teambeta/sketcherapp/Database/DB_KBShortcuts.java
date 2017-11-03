package com.teambeta.sketcherapp.Database;

import com.teambeta.sketcherapp.model.Shortcuts;

import java.awt.*;
import java.sql.*;
//todo bug with the
public class DB_KBShortcuts {

    public static final String KB_SHORTCUTS_TABLE = "KB_SHORTCUTS_TABLE";
    public static final String SHORTCUT_NAME = "SHORTCUT_NAME";
    public static final String KEY_STROKE = "KEY_STROKE";
    public static final String IS_CTRL = "IS_CTRL";
    public static final String IS_ALT = "IS_ALT";
    public static final String IS_SHIFT = "IS_SHIFT";

    Shortcuts shortcuts;

    public DB_KBShortcuts(Shortcuts shortcuts) {
        this.shortcuts = shortcuts;
    }

    public void createTable() {
        Connection connection = null;

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
//     + IS_CTRL + "    CHAR(1)       NOT NULL, "
            Statement stmt = connection.createStatement();
            stmt.execute(sqlCreate);
            System.out.println(KB_SHORTCUTS_TABLE + " Table created or exists");
            stmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void insert(String shortcutName, String keyStroke, String isCTRL, String isALT, String isSHIFT) {

        if(isDataExists(shortcutName)){
            return;
        }

        Connection connection = null;
        PreparedStatement preparedStatement = null; // protects from SQL injection attacks
        try {

            connection = ConnectionConfiguration.getConnection();

            preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + KB_SHORTCUTS_TABLE +
                            "(SHORTCUT_NAME,KEY_STROKE,IS_CTRL,IS_SHIFT,IS_ALT)" +
                            "VALUES(?,?,?,?,?) ");

            preparedStatement.setString(1, shortcutName); // 1 = first column
            preparedStatement.setString(2, keyStroke);
            preparedStatement.setString(3, isCTRL);
            preparedStatement.setString(4, isSHIFT);
            preparedStatement.setString(5, isALT);
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

          /*  preparedStatement = connection.prepareStatement(
                    "UPDATE " + KB_SHORTCUTS_TABLE + " WHERE " + SHORTCUT_NAME
                            + " = " + shortcutName +
                            " (SHORTCUT_NAME,KEY_STROKE,IS_CTRL,IS_ALT,IS_SHIFT) " +
                            " VALUES(?,?,?,?,?) ");

            preparedStatement.setString(1, shortcutName); // 1 = first column
            preparedStatement.setString(2, keyStroke);
            preparedStatement.setString(3, isCTRL);
            preparedStatement.setString(4, isALT);
            preparedStatement.setString(5, isSHIFT);
            preparedStatement.executeUpdate();
            preparedStatement.close();*/
            String query = "UPDATE  " + KB_SHORTCUTS_TABLE
                    + " SET "
                    +  SHORTCUT_NAME  + " =  ?, "
                    +  KEY_STROKE  + " =  ?, "
                    +  IS_CTRL  + " =  ?, "
                    +  IS_SHIFT  + " =  ?, "
                    +  IS_ALT  + " =  ? "
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
         //   printTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void printTable() {
        Connection connection = null;
        //  PreparedStatement preparedStatement = null; // protects from SQL injection attacks
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

                    System.out.print(rs.getString(i) + " "); //Print one element of a row

                }

                System.out.println();//Move to the next line to print the next row.

            }

            System.out.println();

            //   preparedStatement.close();

            rs.close();
            st.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void dropTable() {
        Connection connection = null;
        Statement stmt = null;

        connection = ConnectionConfiguration.getConnection();

        String sqlCreate = " DROP TABLE " + KB_SHORTCUTS_TABLE;


        try {
            stmt = connection.createStatement();
            stmt.execute(sqlCreate);
            connection.close();
            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void generateDBKeyBindings () {

        printTable();

        Connection connection = null;
        ResultSet rset = null; //table of record from DB
        try{
            connection =    ConnectionConfiguration.getConnection();
            Statement s2 = connection.createStatement();
            rset = s2.executeQuery("select * from " + KB_SHORTCUTS_TABLE);

            while(rset.next()){
                System.out.println("from db:  " + rset.getString(SHORTCUT_NAME));
                System.out.println("from db:  " + rset.getString(KEY_STROKE));
                System.out.println("from db:  " + rset.getBoolean(IS_CTRL));

                boolean isCtrl = false;
                boolean isShift = false;
                boolean isAlt = false;

                if(rset.getString(IS_CTRL).equals("true")){
                    isCtrl = true;
                }

                if(rset.getString(IS_SHIFT).equals("true")){
                    isShift = true;
                }

                if(rset.getString(IS_ALT).equals("true")){
                    isAlt = true;
                }



                shortcuts.removeBinding2(rset.getString(KEY_STROKE),  isCtrl , isShift, isAlt);
                shortcuts.changeKeyBinding2(rset.getString(KEY_STROKE),  isCtrl , isShift, isAlt,rset.getString(SHORTCUT_NAME));
             //   shortcuts.setKBShortcut(rset.getString(SHORTCUT_NAME), rset.getInt(KEY_STROKE), rset.getBoolean(IS_CTRL) , rset.getBoolean(IS_SHIFT), rset.getBoolean(IS_ALT));
            }
            rset.close();
            connection.close();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public boolean isDataExists(String shortcutName) {

        Connection connection = null;
        //  PreparedStatement preparedStatement = null; // protects from SQL injection attacks

        Statement st = null;
        ResultSetMetaData rsmd = null;


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
                     //   System.out.println(shortcutName + " exists ");
                        return true;
                    } else {
                     //   System.out.println(shortcutName + " does not exists ");
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

    public boolean isTableExists(){
        Connection connection = null;
        connection = ConnectionConfiguration.getConnection();
        try{
          /*  DatabaseMetaData md =  connection.getMetaData();
            ResultSet rs = md.getTables(null, null,KB_SHORTCUTS_TABLE, null);
            rs.last();
            rs.close();
            connection.close();
            return rs.getRow() > 0;*/

            DatabaseMetaData dbm = connection.getMetaData();
            // check if "employee" table is there
            ResultSet tables = dbm.getTables(null, null, KB_SHORTCUTS_TABLE, null);
            if (tables.next()) {
                tables.close();
                connection.close();
                return true;
            }
            else {
                tables.close();
                connection.close();
                return  false;
            }





        }catch(SQLException e){
            e.printStackTrace();

        }
        return false;
    }
    /*
    public String getKeyStroke(String shortcutName){

        Connection connection = null;
        //  PreparedStatement preparedStatement = null; // protects from SQL injection attacks
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

                    if(rs.getString(i).equals(shortcutName)){
                        return rs.
                    }
                    System.out.print(rs.getString(i) + " "); //Print one element of a row

                }

                System.out.println();//Move to the next line to print the next row.

            }

            System.out.println();

            //   preparedStatement.close();

            rs.close();
            st.close();
            connection.close();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/


}
