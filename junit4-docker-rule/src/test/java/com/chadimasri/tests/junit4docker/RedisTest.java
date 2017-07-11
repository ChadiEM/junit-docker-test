package com.chadimasri.tests.junit4docker;

import com.github.geowarin.junit.DockerRule;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import redis.clients.jedis.Jedis;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;
import static org.junit.Assert.assertEquals;

@Ignore("Does not work")
public class RedisTest {
    @Rule
    public DockerRule jedisRule = DockerRule.builder()
            .image("redis")
            .ports("6379/tcp")
            .waitForPort("6379/tcp")
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
        return new Jedis(jedisRule.getDockerHost(), jedisRule.getHostPort("6379/tcp"));
    }
}
