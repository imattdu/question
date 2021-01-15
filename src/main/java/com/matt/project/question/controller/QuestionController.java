package com.matt.project.question.controller;

import com.matt.project.question.model.*;
import com.matt.project.question.service.CommentService;
import com.matt.project.question.service.LikeService;
import com.matt.project.question.service.QuestionService;
import com.matt.project.question.service.UserService;
import com.matt.project.question.service.impl.FollowServiceImpl;
import com.matt.project.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author matt
 * @create 2021-01-08 17:20
 */
@Controller
public class QuestionController {

    @Autowired
    private QuestionService questionService;
    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;
    @Autowired
    private CommentService commentService;
    @Autowired
    private LikeService likeService;
    @Autowired
    private FollowServiceImpl followService;

    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam(name = "title")String title,
                              @RequestParam(name = "content")String content) {

        Question question = new Question();
        question.setTitle(title);
        question.setContent(content);
        question.setCreateDate(new Date());
        question.setCommentCount(0);

        User user = hostHolder.getUser();
        if (user == null) {
            return WendaUtil.getJSONString("999");
        }
        try {
            question.setUserId(user.getId());
            questionService.addQuestion(question);
            return WendaUtil.getJSONString("0");
        } catch (Exception e) {
            e.printStackTrace();
        }


        return WendaUtil.getJSONString("1", "错误");
    }

    @RequestMapping(value = "/question/{id}",method = RequestMethod.GET)
    public String getQuestionByID(@PathVariable(value = "id")Integer id,
                                  Model model){

        Question question = questionService.getQuestionById(id);
        User user = hostHolder.getUser();
        model.addAttribute("question",question);
        model.addAttribute("user",user);
        List<Comment> commentList = commentService.listCommentByEntity(EntityType.ENTITY_QUESTION, id);
        List<ViewObject> comments = new ArrayList<>();
        for (Comment comment : commentList) {
            ViewObject vo = new ViewObject();
            Long likeCount = likeService.countLike(EntityType.ENTITY_QUESTION, comment.getId());
            vo.set("likeCount", likeCount);
            vo.set("comment", comment);
            if (user == null) {
                vo.set("liked", "0");
            } else {
                Integer likeStatus = likeService.getLikeStatus(user.getId(),
                        EntityType.ENTITY_QUESTION, comment.getId());
                vo.set("liked", likeStatus);
            }





            vo.set("user",userService.getUserById(comment.getUserId()));
            comments.add(vo);
        }

        List<ViewObject> followUsers = new ArrayList<ViewObject>();
        // 获取关注的用户信息
        List<Integer> users = followService.listFollower(EntityType.ENTITY_QUESTION, id, 20);
        for (Integer userId : users) {
            ViewObject vo = new ViewObject();
            User u = userService.getUserById(userId);
            if (u == null) {
                continue;
            }
            vo.set("name", u.getName());
            vo.set("headUrl", u.getHeadUrl());
            vo.set("id", u.getId());
            followUsers.add(vo);
        }
        model.addAttribute("followUsers", followUsers);
        if (hostHolder.getUser() != null) {
            model.addAttribute("followed", followService.isFollower(hostHolder.getUser().getId(),
                    EntityType.ENTITY_QUESTION, id));
        } else {
            model.addAttribute("followed", false);
        }

        model.addAttribute("comments",comments);
        return "detail";
    }


}
