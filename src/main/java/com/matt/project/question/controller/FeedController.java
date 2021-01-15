package com.matt.project.question.controller;

import com.matt.project.question.model.EntityType;
import com.matt.project.question.model.Feed;
import com.matt.project.question.model.HostHolder;
import com.matt.project.question.service.FeedService;
import com.matt.project.question.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * @author matt
 * @create 2021-01-15 12:44
 */
@Controller
public class FeedController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private FollowService followService;
    @Autowired
    private FeedService feedService;

    @RequestMapping(path = {"/pullfeeds"}, method = {RequestMethod.GET, RequestMethod.POST})
    private String getPullFeeds(Model model) {
        int localUserId = hostHolder.getUser() != null ? hostHolder.getUser().getId() : 0;
        List<Integer> followees = new ArrayList<>();
        if (localUserId != 0) {
            // 关注的人
            followees = followService.listFollowee(EntityType.ENTITY_USER,localUserId, Integer.MAX_VALUE);
        }
        List<Feed> feeds = feedService.getUserFeeds(Integer.MAX_VALUE, followees, 10);
        model.addAttribute("feeds", feeds);
        return "feeds";
    }
}
