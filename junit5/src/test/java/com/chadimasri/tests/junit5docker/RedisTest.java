package com.chadimasri.tests.junit5docker;

import com.github.junit5docker.Docker;
import com.github.junit5docker.Port;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import redis.clients.jedis.Jedis;

@Docker(
        image = "redis",
        ports = @Port(exposed = 6380, inner = 6379)
)
public class RedisTest {

    private Jedis jedis;

    @BeforeEach
    public void initJedis() {
        jedis = new Jedis("localhost", 6380);
    }

    @Test
    @DisplayName("Reading a previously set variable")
    public void test() {
        System.out.println("Before set, " + jedis.get("foo"));
        jedis.set("foo", "bar");
        System.out.println("After set, " + jedis.get("foo"));
    }

    @Test
    @DisplayName("Reading a value from previous test")
    public void test2() {
        System.out.println(jedis.get("foo"));
    }
}
