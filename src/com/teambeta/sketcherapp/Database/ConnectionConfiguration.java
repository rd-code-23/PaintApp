package com.teambeta.sketcherapp.Database;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfiguration {

    private static final String JDBC_DRIVER = "org.sqlite.JDBC";
    private static final String DB_ADDRESS = "jdbc:sqlite:BetaAppDB.sqlite";

    /**
     * connects to the database
     * if we run this method, it will create a sqlite file in the project folder
     * must place the  sqlite jar file in libraries through the project structure
     */
    public static Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_ADDRESS);
            //  System.out.println("Connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }
}