package com.matt.project.question;

import redis.clients.jedis.Jedis;

/**
 * @author matt
 * @create 2021-01-12 13:24
 */
public class JedisTests {

    public static void main(String[] args) {

        Jedis jedis = new Jedis("192.168.96.128",6379);
        jedis.select(1);
        jedis.set("name", "matt");


    }
}
