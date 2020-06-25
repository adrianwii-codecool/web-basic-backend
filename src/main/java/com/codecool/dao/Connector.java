package com.codecool.dao;

import java.sql.*;

public class Connector {
    java.sql.Connection connection;

    private String user = ""; //TODO please provide your user password and connection string
    private String password = "";
    private static final String CONNECTION_STRING = "jdbc:postgresql://ec2-46-137-79-235.eu-west-1.compute.amazonaws.com:5432/d8c6uv529kaagk";


    public Connector() throws ClassNotFoundException {
        this.connection = Connect();
    }

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
