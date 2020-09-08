package com.codecool.dao;

import java.sql.*;

public class Connector {
    java.sql.Connection connection;

    private String user = ""; //TODO please provide your user password and connection string
    private String password = "";
    // to connect with heroku, in connection string after database name add '?sslmode=require'
    // check also how to connect to database via inteliJ https://www.jetbrains.com/help/datagrip/how-to-connect-to-heroku-postgres.html
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
