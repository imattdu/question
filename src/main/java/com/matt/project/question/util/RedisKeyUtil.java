package com.matt.project.question.util;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * @author matt
 * @create 2021-01-12 16:58
 */
public class RedisKeyUtil {

    private static String SPLIT = ":";

    private static String BIZ_LIKE = "LIKE";

    private static String BIZ_DISLIKE = "DISLIKE";

    private static String BIZ_EVENTQUEUE = "EVENT_QUEUE";

    public static String getLikeKey(Integer entityType, Integer entityId) {
        return BIZ_LIKE + SPLIT + String.valueOf(entityType) + SPLIT
                + String.valueOf(entityId);
    }

    public static String getDisLikeKey(Integer entityType, Integer entityId) {
        return BIZ_DISLIKE + SPLIT + String.valueOf(entityType) + SPLIT
                + String.valueOf(entityId);
    }

    public static String getEventQueueKey() {
        return BIZ_EVENTQUEUE;
    }

}
