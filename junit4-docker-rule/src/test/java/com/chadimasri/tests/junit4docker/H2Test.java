package com.chadimasri.tests.junit4docker;

import com.github.geowarin.junit.DockerRule;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.chadimasri.tests.helpers.H2Helper.*;
import static com.google.common.collect.Lists.newArrayList;
import static org.junit.Assert.assertEquals;

public class H2Test {
    @Rule
    public DockerRule h2Rule = DockerRule.builder()
            .image("oscarfonts/h2")
            .ports("1521/tcp")
            .waitForPort("1521/tcp")
            .build();

    @Test
    public void insert_team1() throws SQLException {
        try (Connection conn = getConnection()) {
            insertNames(conn, newArrayList("Chadi"));
        }

        List<String> names;
        try (Connection conn = getConnection()) {
            names = readNames(conn);
        }

        assertEquals(newArrayList("Chadi"), names);
    }

    @Test
    public void insert_team2() throws SQLException {
        try (Connection conn = getConnection()) {
            insertNames(conn, newArrayList("Joe", "Paul", "Mario"));
        }

        List<String> names;
        try (Connection conn = getConnection()) {
            names = readNames(conn);
        }

        assertEquals(newArrayList("Joe", "Paul", "Mario"), names);
    }

    private Connection getConnection() throws SQLException {
        return getH2Connection(h2Rule.getDockerHost(), h2Rule.getHostPort("1521/tcp"));
    }
}
