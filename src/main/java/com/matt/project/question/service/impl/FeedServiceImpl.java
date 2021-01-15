package com.matt.project.question.service.impl;

import com.matt.project.question.dao.FeedDAO;
import com.matt.project.question.model.Feed;
import com.matt.project.question.service.FeedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-15 10:04
 */
@Service
public class FeedServiceImpl implements FeedService {

    @Autowired
    private FeedDAO feedDAO;

    @Override
    public List<Feed> getUserFeeds(int maxId, List<Integer> userIds, int count) {
        return feedDAO.listFeedByUser(maxId, userIds, count);
    }

    @Override
    public boolean saveFeed(Feed feed) {
        feedDAO.saveFeed(feed);
        return feed.getId() > 0;
    }

    @Override
    public Feed getById(int id) {
        return feedDAO.getFeedById(id);
    }




}
