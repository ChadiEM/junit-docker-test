package com.chadimasri.tests.helpers;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLConnectionHelper {
    public static Connection getH2Connection() throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://172.17.0.2:1521//opt/h2-data/test");
    }

    public static Connection getH2Connection(String host, int port) throws SQLException {
        return DriverManager.getConnection("jdbc:h2:tcp://" + host + ":" + port + "//opt/h2-data/test");
//        return DriverManager.getConnection("jdbc:h2:tcp://172.17.0.1:1521/opt/h2-data/test");
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
