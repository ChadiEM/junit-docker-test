package com.chadimasri.tests.junit5docker;

import com.github.junit5docker.Docker;
import com.github.junit5docker.Port;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

import static com.chadimasri.tests.helpers.RedisHelper.getRedisClient;
import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Docker(
        image = "redis",
        ports = @Port(exposed = 6379, inner = 6379)
)
public class RedisTest {
    @Test
    @DisplayName("Insert and Query Team 1")
    void insert_team1() {
        try (Jedis jedis = getJedis()) {
            jedis.rpush("names1", "Chadi");
        }

        try (Jedis jedis = getJedis()) {
            assertEquals(newArrayList("Chadi"), jedis.lrange("names1", 0, -1));
            assertEquals(newHashSet("names1"), jedis.keys("*"));
        }
    }

    @Test
    @DisplayName("Insert and Query Team 2")
    void insert_team2() {
        try (Jedis jedis = getJedis()) {
            jedis.rpush("names2", "Joe", "Paul", "Mario");
        }

        try (Jedis jedis = getJedis()) {
            assertEquals(newArrayList("Joe", "Paul", "Mario"), jedis.lrange("names2", 0, -1));
            assertEquals(newHashSet("names2"), jedis.keys("*"));
        }
    }


    private Jedis getJedis() {
        return getRedisClient("localhost", 6379);
    }
}
