package com.matt.project.question.service;

/**
 * @author matt
 * @create 2021-01-12 17:05
 */
public interface LikeService {

    Long like(Integer userId, Integer entityType, Integer entityId);

    Long disLike(Integer userId, Integer entityType, Integer entityId);

    Integer getLikeStatus(Integer userId, Integer entityType, Integer entityId);

    Long countLike(Integer entityType, Integer entityId);

}
