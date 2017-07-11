package com.chadimasri.tests.junit4.dockercomposerule;

import com.google.common.collect.Lists;
import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.DockerPort;
import com.palantir.docker.compose.connection.waiting.HealthCheck;
import com.palantir.docker.compose.connection.waiting.SuccessOrFailure;
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
    public DockerComposeRule h2Rule =
            DockerComposeRule.builder()
                    .file("src/test/resources/docker-compose-h2.yml")
                    .build();

    @Test
    public void insert_team1() throws ClassNotFoundException, SQLException {
        try (Connection conn = getH2Connection(h2Rule.hostNetworkedPort(1521).getIp(), 1521)) {
            insertNames(conn, Lists.newArrayList("Chadi"));
        }

        List<String> names;
        try (Connection connection = getH2Connection(h2Rule.hostNetworkedPort(1521).getIp(), 1521)) {
            names = readNames(connection);
        }

        assertEquals(Lists.newArrayList("Chadi"), names);
    }

    @Test
    public void insert_team2() throws ClassNotFoundException, SQLException {
        try (Connection conn = getH2Connection(h2Rule.hostNetworkedPort(1521).getIp(), 1521)) {
            insertNames(conn, Lists.newArrayList("Joe", "Paul", "Mario"));
        }

        List<String> names;
        try (Connection connection = getH2Connection(h2Rule.hostNetworkedPort(1521).getIp(), 1521)) {
            names = readNames(connection);
        }

        assertEquals(Lists.newArrayList("Joe", "Paul", "Mario"), names);
    }

}
