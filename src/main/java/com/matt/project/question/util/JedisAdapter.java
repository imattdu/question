package com.matt.project.question.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * @author matt
 * @create 2021-01-12 14:27
 */
@Service
public class JedisAdapter implements InitializingBean {

    private Logger logger = LoggerFactory.getLogger(JedisAdapter.class);

    private JedisPool jedisPool = null;

    @Override
    public void afterPropertiesSet() throws Exception {
        jedisPool = new JedisPool("redis://192.168.96.128:6379/9");
        logger.info("创建 jedis 连接池");
    }

    public Long sadd(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sadd(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0l;
    }

    public Long srem(String key, String value) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.srem(key, value);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0l;
    }

    public Long lpush(String key, String json) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.lpush(key, json);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0l;
    }

    public List<String> brpop(Integer time, String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            List<String> values = jedis.brpop(time, key);
            return values;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return null;

    }

    public Long scard(String key) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.scard(key);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return 0l;
    }

    public Boolean sismember(String key, String member) {
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            return jedis.sismember(key, member);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return false;
    }


}
