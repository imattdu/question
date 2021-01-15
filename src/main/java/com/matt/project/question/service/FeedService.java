package com.matt.project.question.service;

import com.matt.project.question.model.Feed;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-15 10:03
 */
public interface FeedService {

    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count);

    public boolean saveFeed(Feed feed) ;

    public Feed getById(int id);

}
