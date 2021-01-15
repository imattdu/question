package com.matt.project.question.service.impl;

import com.matt.project.question.service.FollowService;
import com.matt.project.question.util.JedisAdapter;
import com.matt.project.question.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * @author matt
 * @create 2021-01-14 10:36
 */
@Service
public class FollowServiceImpl implements FollowService {

    @Autowired
    private JedisAdapter jedisAdapter;

    // 登录用户
    @Override
    public Boolean follow(Integer userId, Integer entityType, Integer entityId) {

        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        Date date = new Date();

        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zadd(followerKey,date.getTime(),String.valueOf(userId));
        tx.zadd(followeeKey,date.getTime(),String.valueOf(entityId));
        List<Object> resList = jedisAdapter.exec(tx, jedis);
        return resList.size() == 2 && (Long)resList.get(0) > 0 && (Long)resList.get(1) > 0;
    }

    public Boolean unfollow(Integer userId, Integer entityType, Integer entityId) {

        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, userId);
        Date date = new Date();

        Jedis jedis = jedisAdapter.getJedis();
        Transaction tx = jedisAdapter.multi(jedis);
        tx.zrem(followerKey,String.valueOf(userId));
        tx.zrem(followeeKey,String.valueOf(entityId));
        List<Object> resList = jedisAdapter.exec(tx, jedis);
        return resList.size() == 2 && (Long)resList.get(0) > 0 && (Long)resList.get(1) > 0;
    }



    public List<Integer> getIdsFromSet(Set<String> set) {
        List<Integer> res = new ArrayList<>();
        for (String s : set) {
            res.add(Integer.parseInt(s));
        }
        return res;
     }

    public List<Integer> listFollower(Integer entityType, Integer entityId, Integer count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        Set<String> zrange = jedisAdapter.zrevrange(followerKey, 0, count);
        return this.getIdsFromSet(zrange);
    }

    public List<Integer> listFollower(Integer entityType, Integer entityId, int offset,
                                      Integer count) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        Set<String> zrange = jedisAdapter.zrevrange(followerKey, offset, count);
        return this.getIdsFromSet(zrange);
    }

    public List<Integer> listFollowee(Integer entityType, Integer entityId,
                                      Integer count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        Set<String> zrange = jedisAdapter.zrevrange(followeeKey, 0, count);
        return this.getIdsFromSet(zrange);
    }

    public List<Integer> listFollowee(Integer entityType, Integer entityId, int offset,
                                      Integer count) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        Set<String> zrange = jedisAdapter.zrevrange(followeeKey, offset, count);
        return this.getIdsFromSet(zrange);
    }

    public Long countFollower(Integer entityType, Integer entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);
        Long count = jedisAdapter.zcard(followerKey);
        return count;
    }

    public Long countFollowee(Integer entityType, Integer entityId) {
        String followeeKey = RedisKeyUtil.getFolloweeKey(entityType, entityId);
        Long count = jedisAdapter.zcard(followeeKey);
        return count;
    }

    public Boolean isFollower(Integer userId,Integer entityType, Integer entityId) {
        String followerKey = RedisKeyUtil.getFollowerKey(entityType, entityId);

        Double zscore = jedisAdapter.zscore(followerKey, String.valueOf(userId));
        return zscore != null;
    }



}
