package com.matt.project.question.dao;

import com.matt.project.question.model.Feed;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-15 10:04
 */
public interface FeedDAO {


    Integer saveFeed(Feed feed);

    Feed getFeedById(Integer id);


    List<Feed> listFeedByUser(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);




}
