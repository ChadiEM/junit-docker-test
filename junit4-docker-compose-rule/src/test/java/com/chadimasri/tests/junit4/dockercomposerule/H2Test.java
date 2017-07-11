package com.chadimasri.tests.junit4.dockercomposerule;

import com.google.common.collect.Lists;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.DockerPort;
import org.junit.Rule;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static com.chadimasri.tests.helpers.H2Helper.*;
import static org.junit.Assert.assertEquals;

public class H2Test {
    @Rule
    public final DockerComposeRule h2Rule =
            DockerComposeRule.builder()
                    .file("src/test/resources/docker-compose-h2.yml")
                    .build();

    @Test
    public void insert_team1() throws ClassNotFoundException, SQLException {
        DockerPort h2 = h2Rule.containers().container("h2").port(1521);

        try (Connection conn = getH2Connection(h2.getIp(), h2.getExternalPort())) {
            insertNames(conn, Lists.newArrayList("Chadi"));
        }

        List<String> names;
        try (Connection conn = getH2Connection(h2.getIp(), h2.getExternalPort())) {
            names = readNames(conn);
        }

        assertEquals(Lists.newArrayList("Chadi"), names);
    }

    @Test
    public void insert_team2() throws ClassNotFoundException, SQLException {
        DockerPort h2 = h2Rule.containers().container("h2").port(1521);

        try (Connection conn = getH2Connection(h2.getIp(), h2.getExternalPort())) {
            insertNames(conn, Lists.newArrayList("Joe", "Paul", "Mario"));
        }

        List<String> names;
        try (Connection conn = getH2Connection(h2.getIp(), h2.getExternalPort())) {
            names = readNames(conn);
        }

        assertEquals(Lists.newArrayList("Joe", "Paul", "Mario"), names);
    }

}
