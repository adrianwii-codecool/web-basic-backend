package com.codecool.dao;

import java.sql.*;

public class Connector {
    java.sql.Connection connection;

    private String user = ""; //TODO please provide your user password and connection string
    private String password = "";
    private static final String CONNECTION_STRING = "";

    public Connection Connect() throws ClassNotFoundException {
        connection = null;

        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(CONNECTION_STRING, user, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }
}
