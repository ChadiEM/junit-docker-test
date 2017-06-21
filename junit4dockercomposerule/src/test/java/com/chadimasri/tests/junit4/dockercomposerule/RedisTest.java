package com.chadimasri.tests.junit4.dockercomposerule;

import com.palantir.docker.compose.DockerComposeRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import redis.clients.jedis.Jedis;

public class RedisTest {

    @Rule
    public DockerComposeRule h2Rule =
            DockerComposeRule.builder()
                    .file("src/test/resources/docker-compose-redis.yml")
                    .build();

    private Jedis jedis;

    @Before
    public void initJedis() {
        jedis = new Jedis("localhost", 6380);
    }

    @Test
    public void test() {
        System.out.println("Before set, " + jedis.get("foo"));
        jedis.set("foo", "bar");
        System.out.println("After set, " + jedis.get("foo"));
    }

    @Test
    public void test2() {
        System.out.println(jedis.get("foo"));
    }
}
