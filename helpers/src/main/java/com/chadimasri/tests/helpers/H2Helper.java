package com.chadimasri.tests.helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2Helper {
    private static final int MAX_RECONNECTION_ATTEMPTS = 20;

    public static Connection getH2Connection(String host, int port) throws SQLException {
        int retries = 0;

        while (true) {
            try {
                return DriverManager.getConnection("jdbc:h2:tcp://" + host + ":" + port + "/mem:db;DB_CLOSE_DELAY=-1");
            } catch (SQLException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                retries++;
                if (retries > MAX_RECONNECTION_ATTEMPTS) {
                    throw e;
                }
            }
        }
    }

    public static void insertNames(Connection connection, List<String> names) throws SQLException {
        try (Statement stat = connection.createStatement()) {
            stat.execute("CREATE TABLE NAMES(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255))");
        }

        try (PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO NAMES(NAME) VALUES(?)")) {
            for (String name : names) {
                preparedStatement.setString(1, name);
                preparedStatement.execute();
            }
        }
    }

    public static List<String> readNames(Connection connection) throws SQLException {
        List<String> names = new ArrayList<>();
        try (Statement stat = connection.createStatement();
             ResultSet rs = stat.executeQuery("SELECT NAME FROM NAMES")) {
            while (rs.next()) {
                names.add(rs.getString("NAME"));
            }
        }
        return names;
    }

}
