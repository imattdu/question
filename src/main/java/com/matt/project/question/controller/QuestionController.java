package com.matt.project.question.controller;

import com.matt.project.question.model.*;
import com.matt.project.question.service.CommentService;
import com.matt.project.question.service.QuestionService;
import com.matt.project.question.service.UserService;
import com.matt.project.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
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
            vo.set("comment", comment);
            vo.set("user",userService.getUserById(comment.getUserId()));
            comments.add(vo);
        }
        model.addAttribute("comments",comments);
        return "detail";
    }


}
