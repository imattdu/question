package com.matt.project.question.service;

import com.matt.project.question.util.RedisKeyUtil;
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
public interface FollowService {

    public Boolean follow(Integer userId, Integer entityType, Integer entityId);

    public Boolean unfollow(Integer userId, Integer entityType, Integer entityId);


    List<Integer> getIdsFromSet(Set<String> set);

    List<Integer> listFollower(Integer entityType, Integer entityId, Integer count);

    List<Integer> listFollower(Integer entityType, Integer entityId, int offset,
                                      Integer count);

    List<Integer> listFollowee(Integer entityType, Integer entityId,
                                      Integer count);

    public List<Integer> listFollowee(Integer entityType, Integer entityId, int offset,
                                      Integer count);

    public Long countFollower(Integer entityType, Integer entityId) ;

    public Long countFollowee(Integer entityType, Integer entityId);

    public Boolean isFollower(Integer userId,Integer entityType, Integer entityId);

}
