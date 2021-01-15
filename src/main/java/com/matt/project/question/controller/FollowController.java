package com.matt.project.question.controller;

import com.matt.project.question.model.*;
import com.matt.project.question.service.CommentService;
import com.matt.project.question.service.UserService;
import com.matt.project.question.service.impl.FollowServiceImpl;
import com.matt.project.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author matt
 * @create 2021-01-14 11:32
 */
@Controller
public class FollowController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private FollowServiceImpl followService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;


    @RequestMapping(value = "/followUser", method = RequestMethod.POST)
    @ResponseBody
    public String follow(@RequestParam(name = "userId") Integer userId) {

        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString("999");
        }
        Boolean follow = followService.follow(hostHolder.getUser().getId(),
                EntityType.ENTITY_USER, userId);
        return WendaUtil.getJSONString(follow ? "0" : "1", String.valueOf(
                followService.countFollower(EntityType.ENTITY_USER,
                        hostHolder.getUser().getId())));
    }

    @RequestMapping(value = "/unfollowUser", method = RequestMethod.POST)
    @ResponseBody
    public String unfollow(@RequestParam(name = "userId") Integer userId) {

        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString("999");
        }
        Boolean unfollow = followService.unfollow(hostHolder.getUser().getId(),
                EntityType.ENTITY_USER, userId);
        return WendaUtil.getJSONString(unfollow ? "0" : "1", String.valueOf(
                followService.countFollower(EntityType.ENTITY_USER,
                        hostHolder.getUser().getId())));
    }

    @RequestMapping(value = "/followQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String followQuestion(@RequestParam(name = "questionId") Integer questionId,
                                 Model model) {

        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString("999");
        }

        /*Question q = questionService.getById(questionId);
        if (q == null) {
            return WendaUtil.getJSONString(1, "问题不存在");
        }*/

        boolean ret = followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);

       /* eventProducer.fireEvent(new EventModel(EventType.FOLLOW)
                .setActorId(hostHolder.getUser().getId()).setEntityId(questionId)
                .setEntityType(EntityType.ENTITY_QUESTION).setEntityOwnerId(q.getUserId()));*/

        Map<String, Object> info = new HashMap<>();
        info.put("headUrl", hostHolder.getUser().getHeadUrl());
        info.put("name", hostHolder.getUser().getName());
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.countFollower(EntityType.ENTITY_QUESTION, questionId));
        return WendaUtil.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(value = "/unfollowQuestion", method = RequestMethod.POST)
    @ResponseBody
    public String unfollowQuestion(@RequestParam(name = "questionId") Integer questionId) {

        if (hostHolder.getUser() == null) {
            return WendaUtil.getJSONString("999");
        }

        // 问题不存在

        boolean ret = followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION, questionId);



        Map<String, Object> info = new HashMap<>();
        info.put("id", hostHolder.getUser().getId());
        info.put("count", followService.countFollower(EntityType.ENTITY_QUESTION, questionId));
        return WendaUtil.getJSONString(ret ? 0 : 1, info);
    }

    @RequestMapping(value = "/user/{uid}/followees")
    public String followees(@PathVariable(value = "uid")Integer uid, Model model){

        List<Integer> followeeIds = followService.listFollowee( EntityType.ENTITY_USER,uid, 0, 10);


        if (hostHolder.getUser() != null) {
            model.addAttribute("followees", getUsersInfo(hostHolder.getUser().getId(), followeeIds));
        } else {
            model.addAttribute("followees", getUsersInfo(0, followeeIds));
        }
        model.addAttribute("followeeCount", followService.countFollowee(EntityType.ENTITY_USER, uid));
        model.addAttribute("curUser", userService.getUserById(uid));
        return "followees";
    }

    @RequestMapping(value = "/user/{uid}/followers")
    public String followers(@PathVariable(value = "uid")Integer userId,Model model){

        List<Integer> followerIds = followService.listFollower(EntityType.ENTITY_USER, userId, 0, 10);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followers", getUsersInfo(hostHolder.getUser().getId(), followerIds));
        } else {
            model.addAttribute("followers", getUsersInfo(0, followerIds));
        }
        model.addAttribute("followerCount", followService.countFollower(EntityType.ENTITY_USER, userId));
        model.addAttribute("curUser", userService.getUserById(userId));

        return "followers";
    }

    private List<ViewObject> getUsersInfo(int localUserId, List<Integer> userIds){

        List<ViewObject> voList = new ArrayList<>();
        for (Integer userId : userIds) {
            User user = userService.getUserById(userId);
            if (user == null) {
                continue;
            }
            ViewObject vo = new ViewObject();
            vo.set("user", user);

            vo.set("followerCount", followService.countFollower(EntityType.ENTITY_USER, userId));
            vo.set("followeeCount", followService.countFollowee(EntityType.ENTITY_USER, userId));
            if (localUserId != 0) {
                vo.set("followed", followService.isFollower(localUserId, EntityType.ENTITY_USER, userId));
            } else {
                vo.set("followed", false);
            }
            //commentCount
            Integer commentCount = commentService.countCommentByUserId(user.getId());
            vo.set("commentCount",commentCount);
            voList.add(vo);
        }

        return voList;
    }
}
