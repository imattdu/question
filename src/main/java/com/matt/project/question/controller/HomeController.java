package com.matt.project.question.controller;

import com.matt.project.question.model.*;
import com.matt.project.question.service.CommentService;
import com.matt.project.question.service.FollowService;
import com.matt.project.question.service.QuestionService;
import com.matt.project.question.service.UserService;
import com.matt.project.question.service.impl.CommentServiceImpl;
import com.matt.project.question.service.impl.FollowServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.util.ArrayList;
import java.util.List;

/**
 * @author matt
 * @create 2021-01-14 13:43
 */
@Controller
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);


    @Autowired
    UserService userService;
    @Autowired
    HostHolder hostHolder;
    @Autowired
    FollowServiceImpl followService;

    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;


    private List<ViewObject> getQuestions(int userId, int offset, int limit) {
        //List<Question> questionList = questionService.getLatestQuestions(userId, offset, limit);
        List<ViewObject> vos = new ArrayList<>();
       /* for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            vo.set("followCount", followService.getFollowerCount(EntityType.ENTITY_QUESTION, question.getId()));
            vo.set("user", userService.getUser(question.getUserId()));
            vos.add(vo);
        }*/
        return vos;
    }

    @RequestMapping(path = {"/"}, method = RequestMethod.GET)
    public String index(Model model) {

        List<Question> questionList = questionService.listQuestionByCreatedate();
        List<ViewObject> vos = new ArrayList<>();
        for (Question question : questionList) {
            ViewObject vo = new ViewObject();
            vo.set("question", question);
            Long questionFolloweeCouont = followService.countFollower(EntityType.ENTITY_QUESTION, question.getId());
            vo.set("followCount", questionFolloweeCouont);
            User user = userService.getUserById(question.getUserId());
            vo.set("user", user);



            vos.add(vo);
        }
        model.addAttribute("vos", vos);
        return "index";
    }

    @RequestMapping(path = {"/user/{userId}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String userIndex(Model model, @PathVariable("userId") int userId) {
        model.addAttribute("vos", getQuestions(userId, 0, 10));

        User user = userService.getUserById(userId);
        ViewObject vo = new ViewObject();
        vo.set("user_exists", true);
        if (user == null) {
            vo.set("user_exists", false);
        } else {
            vo.set("user", user);
            //vo.set("commentCount", commentService(userId));
            vo.set("followerCount", followService.countFollower(EntityType.ENTITY_USER, userId));
            vo.set("followeeCount", followService.countFollowee(EntityType.ENTITY_USER, userId));
            if (hostHolder.getUser() != null) {
                vo.set("followed", followService.isFollower(hostHolder.getUser().getId(), EntityType.ENTITY_USER, userId));
            } else {
                vo.set("followed", false);
            }
            Integer commentCount = commentService.countCommentByUserId(user.getId());
            vo.set("commentCount", commentCount);
        }

        model.addAttribute("profileUser", vo);
        return "profile";
    }
}
