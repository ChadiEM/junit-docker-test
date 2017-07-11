package com.chadimasri.tests.junit4docker;

import com.github.geowarin.junit.DockerRule;
import com.google.common.collect.Lists;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import java.sql.*;
import java.util.List;

import static com.chadimasri.tests.helpers.SQLConnectionHelper.getH2Connection;
import static com.chadimasri.tests.helpers.SQLConnectionHelper.insertNames;
import static com.chadimasri.tests.helpers.SQLConnectionHelper.readNames;
import static org.junit.Assert.assertEquals;

public class H2Test {
    @Rule
    public DockerRule h2Rule = DockerRule.builder()
                    .image("oscarfonts/h2")
                    .ports("1521")
                    .waitForLog("TCP server running at")
                    .waitForPort("1521/tcp")
                    .build();

    @Test
    public void insert_team1() throws ClassNotFoundException, SQLException {
        try (Connection conn = getH2Connection("localhost", 1521)) {
            insertNames(conn, Lists.newArrayList("Chadi"));
        }

        List<String> names;
        try (Connection conn = getH2Connection("localhost", 1521)) {
            names = readNames(conn);
        }

        assertEquals(Lists.newArrayList("Chadi"), names);
    }

    @Test
    public void insert_team2() throws ClassNotFoundException, SQLException {
        try (Connection conn = getH2Connection("localhost", 1521)) {
            insertNames(conn, Lists.newArrayList("Joe", "Paul", "Mario"));
        }

        List<String> names;
        try (Connection conn = getH2Connection("localhost", 1521)) {
            names = readNames(conn);
        }

        assertEquals(Lists.newArrayList("Joe", "Paul", "Mario"), names);
    }
}
