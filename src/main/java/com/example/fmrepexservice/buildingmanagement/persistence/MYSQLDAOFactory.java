package com.example.fmrepexservice.buildingmanagement.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MYSQLDAOFactory extends DAOFactory {

    //public static final String DRIVER = "org.h2.Driver";
    public static final String DRIVER = "com.mysql.jdbc.Driver";
    public static final String DBURL = "jdbc:mysql://localhost:3306/drone";
    public static final String USERNAME = "root";
    public static final String PASSWORD = "";
    //public static final String DBURL = "jdbc:sqlite:C:/db/card.s3db";
    //public static final String DBURL = "jdbc:sqlite:./card.s3db";

    // method to create H2 connections
    public static Connection createConnection() throws SQLException {
        // Use DRIVER and DBURL to create a connection
        // Recommend connection pool implementation/usage
        try {
            Class.forName(DRIVER);
            Connection connection= DriverManager.getConnection(
                    DBURL, USERNAME, PASSWORD);
            return connection;
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public DAO getBuildingDAO() {
        return new MYSQLBuildingDAO();
    }

}
