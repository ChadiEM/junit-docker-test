package com.chadimasri.tests.helpers;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.exceptions.JedisConnectionException;

public class RedisHelper {
    private static final int MAX_RECONNECTION_ATTEMPTS = 40;

    public static Jedis getRedisClient(String host, int port) {
        Jedis jedis = new Jedis(host, port);

        int retries = 0;

        while (true) {
            try {
                jedis.ping();
                return jedis;
            } catch (JedisConnectionException e) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
                retries++;
                if (retries > MAX_RECONNECTION_ATTEMPTS) {
                    throw e;
                } else {
                    jedis.close();
                    jedis = new Jedis(host, port);
                    System.out.println("RETRYING...");
                }
            }
        }
    }
}
