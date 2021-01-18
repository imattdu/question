package com.matt.project.question.util;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Transaction;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        jedisPool = new JedisPool("redis://123.56.135.43:6379/9");
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

    public Jedis getJedis() {
        return jedisPool.getResource();
    }

    public Transaction multi(Jedis jedis) {
        Transaction transaction = jedis.multi();
        return transaction;
    }

    public List<Object> exec(Transaction transaction, Jedis jedis) {
        List<Object> resList = new ArrayList<>();
        try {
            resList = transaction.exec();
            return resList;
        } catch (Exception e) {
            e.printStackTrace();
            return resList;
        } finally {
            if (transaction != null) {
                try {
                    transaction.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long zadd(Jedis jedis, String key, Double score, String member) {
        Long zadd = jedis.zadd(key, score, member);
        return zadd;
    }

    public Long zrem(Jedis jedis, String key, String member) {
        Long zrem = jedis.zrem(key, member);
        return zrem;
    }

    public Set<String> zrange(String key, Integer start, Integer end) {
        Set<String> res = new HashSet<>();
        Jedis jedis = jedisPool.getResource();
        try {

            res = jedis.zrange(key, start, end);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Set<String> zrevrange(String key, Integer start, Integer end) {
        Set<String> res = new HashSet<>();
        Jedis jedis = jedisPool.getResource();
        try {

            res = jedis.zrevrange(key, start, end);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return res;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Long zcard(String key) {
        Jedis jedis = jedisPool.getResource();
        try {

            Long count = jedis.zcard(key);
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0l;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public Double zscore(String key, String member) {
        Jedis jedis = jedisPool.getResource();
        try {

            Double zscore = jedis.zscore(key, member);
            return zscore;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }


}
