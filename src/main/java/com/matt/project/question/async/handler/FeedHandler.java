package com.matt.project.question.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.matt.project.question.async.EventHandler;
import com.matt.project.question.async.EventModel;
import com.matt.project.question.async.EventType;
import com.matt.project.question.model.EntityType;
import com.matt.project.question.model.Feed;
import com.matt.project.question.model.Question;
import com.matt.project.question.model.User;
import com.matt.project.question.service.FeedService;
import com.matt.project.question.service.QuestionService;
import com.matt.project.question.service.UserService;
import com.matt.project.question.util.JedisAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * @author matt
 * @create 2021-01-15 10:11
 */
@Component
public class FeedHandler implements EventHandler {

    @Autowired
    private UserService userService;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private FeedService feedService;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void doHandler(EventModel eventModel) {
        Feed feed = new Feed();
        feed.setUserId(eventModel.getActorId());
        feed.setType(eventModel.getEventType().getValue());
        feed.setCreateDate(new Date());
        feed.setData(buildFeedData(eventModel));
        feedService.saveFeed(feed);
    }

    private String buildFeedData(EventModel model) {
        Map<String, String> map = new HashMap<String ,String>();
        // 触发用户是通用的
        User actor = userService.getUserById(model.getActorId());
        if (actor == null) {
            return null;
        }
        map.put("userId", String.valueOf(actor.getId()));
        map.put("userHead", actor.getHeadUrl());
        map.put("userName", actor.getName());

        if (model.getEventType() == EventType.COMMENT ||
                (model.getEventType() == EventType.FOLLOW  && model.getEntityType() == EntityType.ENTITY_QUESTION)) {
            Question question = questionService.getQuestionById(model.getEntityId());
            if (question == null) {
                return null;
            }
            map.put("questionId", String.valueOf(question.getId()));
            map.put("questionTitle", question.getTitle());
            return JSONObject.toJSONString(map);
        }
        return null;
    }

    @Override
    public List<EventType> listSupportEventType() {
        return Arrays.asList(EventType.COMMENT);
    }
}
