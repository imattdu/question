package com.matt.project.question.controller;

import com.matt.project.question.async.EventModel;
import com.matt.project.question.async.EventProducer;
import com.matt.project.question.async.EventType;
import com.matt.project.question.dao.CommentDAO;
import com.matt.project.question.model.*;
import com.matt.project.question.service.QuestionService;
import org.apache.catalina.Host;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import sun.util.resources.et.CalendarData_et;

import java.util.Date;

/**
 * @author matt
 * @create 2021-01-11 10:28
 */
@Controller
public class CommentController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "/comment/save", method = RequestMethod.POST)
    public String saveComment(@RequestParam("questionId") int questionId,
                              @RequestParam("content") String content){

        try {
            Comment comment = new Comment();
            User user = hostHolder.getUser();
            if (user != null) {
                comment.setUserId(user.getId());
            } else {
                return "redirect:/toLogin";
            }
            comment.setContent(content);
            comment.setCreateDate(new Date());
            comment.setEntityType(EntityType.ENTITY_QUESTION);
            comment.setEntityId(questionId);
            comment.setStatus(0);
            commentDAO.saveComment(comment);

            questionService.incrQuestionComment(questionId);

            eventProducer.fireEvent(new EventModel(EventType.COMMENT)
                    .setActorId(user.getId())
                    .setEntityType(EntityType.ENTITY_QUESTION)
                    .setEntityId(questionId)
            );

        } catch (Exception e) {
            e.printStackTrace();
        }

        return "redirect:/question/"+questionId;
    }

}
