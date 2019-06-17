package com.proj;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    public static final String DB_URL = "jdbc:mysql://localhost:3306/posibnyk?useSSL=false";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "234abcd";

    public static Connection getConnection() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER,
                    DB_PASSWORD);
            return connection;
        } catch (SQLException ex) {
            throw new RuntimeException("===Error connecting to the database===", ex);
        } catch (ClassNotFoundException ex) {
            throw new RuntimeException("===Driver error===", ex);
        }
    }
}
