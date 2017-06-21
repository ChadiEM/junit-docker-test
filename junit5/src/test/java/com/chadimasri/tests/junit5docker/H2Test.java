package com.chadimasri.tests.junit5docker;

import com.github.junit5docker.Docker;
import com.github.junit5docker.Port;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.*;

// https://hub.docker.com/r/oscarfonts/h2/~/dockerfile/
@Docker(image = "oscarfonts/h2", ports = @Port(exposed = 1521, inner = 1521))
public class H2Test {

    @Test
    @DisplayName("Insert and Query Team 1")
    public void insert_team1() throws ClassNotFoundException, SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
             Statement stat = conn.createStatement()) {
            stat.execute("CREATE TABLE NAMES(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255))");
            stat.execute("INSERT INTO NAMES(NAME) VALUES('Chadi')");

            try (ResultSet rs = stat.executeQuery("SELECT * FROM NAMES")) {
                while (rs.next()) {
                    System.out.println(rs.getString("NAME"));
                }
            }
        }
    }

    @Test
    @DisplayName("Insert and Query Team 2")
    public void insert_team2() throws ClassNotFoundException, SQLException {
        try (Connection conn = DriverManager.getConnection("jdbc:h2:mem:test");
             Statement stat = conn.createStatement()) {
            stat.execute("CREATE TABLE NAMES(ID INT AUTO_INCREMENT PRIMARY KEY, NAME VARCHAR(255))");
            stat.execute("INSERT INTO NAMES(NAME) VALUES('Joe')");
            stat.execute("INSERT INTO NAMES(NAME) VALUES('Paul')");
            stat.execute("INSERT INTO NAMES(NAME) VALUES('Mario')");

            try (ResultSet rs = stat.executeQuery("SELECT * FROM NAMES")) {
                while (rs.next()) {
                    System.out.println(rs.getString("NAME"));
                }
            }
        }
    }

}
