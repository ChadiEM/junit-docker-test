package com.chadimasri.tests.junit4.dockercomposerule;

import com.palantir.docker.compose.DockerComposeRule;
import com.palantir.docker.compose.connection.DockerPort;
import org.junit.Rule;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

public class RedisTest {
    @Rule
    public final DockerComposeRule jedisRule =
            DockerComposeRule.builder()
                    .file("src/test/resources/docker-compose-redis.yml")
                    .build();

    @Test
    public void insert_team1() {
        Jedis jedis = getJedis();

        jedis.rpush("names1", "Chadi");

        assertEquals(newArrayList("Chadi"), jedis.lrange("names1", 0, -1));
        assertEquals(newHashSet("names1"), jedis.keys("*"));
    }

    @Test
    public void insert_team2() {
        Jedis jedis = getJedis();

        jedis.rpush("names2", "Joe", "Paul", "Mario");

        assertEquals(newArrayList("Joe", "Paul", "Mario"), jedis.lrange("names2", 0, -1));
        assertEquals(newHashSet("names2"), jedis.keys("*"));
    }

    private Jedis getJedis() {
        DockerPort jedis = jedisRule.containers().container("redis").port(6379);
        return new Jedis(jedis.getIp(), jedis.getExternalPort());
    }
}
