package com.matt.project.question.service.impl;

import com.matt.project.question.service.LikeService;
import com.matt.project.question.util.JedisAdapter;
import com.matt.project.question.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.DigestException;

/**
 * @author matt
 * @create 2021-01-12 17:49
 */
@Service
public class LikeServiceImpl implements LikeService {

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public Long like(Integer userId, Integer entityType, Integer entityId) {

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.sadd(likeKey, String.valueOf(userId));

        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.srem(disLikeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public Long disLike(Integer userId, Integer entityType, Integer entityId) {


        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        jedisAdapter.sadd(disLikeKey, String.valueOf(userId));

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        jedisAdapter.srem(likeKey, String.valueOf(userId));
        return jedisAdapter.scard(likeKey);
    }

    @Override
    public Integer getLikeStatus(Integer userId, Integer entityType, Integer entityId) {

        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        Boolean likeResult = jedisAdapter.sismember(likeKey, String.valueOf(userId));
        if (likeResult){
            return 1;
        }
        String disLikeKey = RedisKeyUtil.getDisLikeKey(entityType, entityId);
        Boolean disLikeResult = jedisAdapter.sismember(disLikeKey, String.valueOf(userId));

        return disLikeResult == true ? -1 : 0;
    }

    @Override
    public Long countLike(Integer entityType, Integer entityId) {
        String likeKey = RedisKeyUtil.getLikeKey(entityType, entityId);
        Long likeCount = jedisAdapter.scard(likeKey);

        return likeCount;
    }
}
