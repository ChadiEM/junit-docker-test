package com.chadimasri.tests.junit4.dockercomposerule;

import com.palantir.docker.compose.DockerComposeRule;
import org.junit.Rule;
import org.junit.Test;

import java.sql.*;

public class H2Test {
    @Rule
    public DockerComposeRule h2Rule =
            DockerComposeRule.builder()
                    .file("src/test/resources/docker-compose-h2.yml")
                    .build();

    @Test
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
