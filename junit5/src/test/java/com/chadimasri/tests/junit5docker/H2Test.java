package com.chadimasri.tests.junit5docker;

import com.github.junit5docker.Docker;
import com.github.junit5docker.Port;
import com.github.junit5docker.WaitFor;
import com.google.common.collect.Lists;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.chadimasri.tests.helpers.SQLConnectionHelper.getH2Connection;
import static com.chadimasri.tests.helpers.SQLConnectionHelper.insertNames;
import static com.chadimasri.tests.helpers.SQLConnectionHelper.readNames;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

import java.sql.*;
import java.util.List;

// https://hub.docker.com/r/oscarfonts/h2/~/dockerfile/
@Docker(
        image = "oscarfonts/h2",
        ports = @Port(exposed = 1521, inner = 1521),
        waitFor = @WaitFor("TCP server running at")
)
public class H2Test {

    @Test
    @DisplayName("Insert and Query Team 1")
    public void insert_team1() throws ClassNotFoundException, SQLException {
        try (Connection conn = getH2Connection("localhost", 1521)) {
            insertNames(conn, Lists.newArrayList("Chadi"));
        }

        List<String> names;
        try (Connection conn = getH2Connection("localhost", 1521)) {
            names = readNames(conn);
        }

        assertIterableEquals(Lists.newArrayList("Chadi"), names);
    }

    @Test
    @DisplayName("Insert and Query Team 2")
    public void insert_team2() throws ClassNotFoundException, SQLException {
        try (Connection conn = getH2Connection("localhost", 1521)) {
            insertNames(conn, Lists.newArrayList("Joe", "Paul", "Mario"));
        }

        List<String> names;
        try (Connection conn = getH2Connection("localhost", 1521)) {
            names = readNames(conn);
        }

        assertIterableEquals(Lists.newArrayList("Joe", "Paul", "Mario"), names);
    }
}
