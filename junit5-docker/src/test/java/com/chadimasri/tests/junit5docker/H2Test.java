package com.chadimasri.tests.junit5docker;

import com.github.junit5docker.Docker;
import com.github.junit5docker.Port;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.chadimasri.tests.helpers.H2Helper.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

@Docker(
        image = "oscarfonts/h2",
        ports = @Port(exposed = 1521, inner = 1521)
)
public class H2Test {
    @Test
    @DisplayName("Insert and Query Team 1")
    void insert_team1() throws SQLException {
        try (Connection conn = getConnection()) {
            insertNames(conn, newArrayList("Chadi"));
        }

        List<String> names;
        try (Connection conn = getConnection()) {
            names = readNames(conn);
        }

        assertIterableEquals(newArrayList("Chadi"), names);
    }

    @Test
    @DisplayName("Insert and Query Team 2")
    void insert_team2() throws SQLException {
        try (Connection conn = getConnection()) {
            insertNames(conn, newArrayList("Joe", "Paul", "Mario"));
        }

        List<String> names;
        try (Connection conn = getConnection()) {
            names = readNames(conn);
        }

        assertIterableEquals(newArrayList("Joe", "Paul", "Mario"), names);
    }

    private Connection getConnection() throws SQLException {
        return getH2Connection("localhost", 1521);
    }
}
