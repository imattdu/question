package com.matt.project.question.controller;

import com.matt.project.question.async.EventModel;
import com.matt.project.question.async.EventProducer;
import com.matt.project.question.async.EventType;
import com.matt.project.question.dao.CommentDAO;
import com.matt.project.question.model.Comment;
import com.matt.project.question.model.EntityType;
import com.matt.project.question.model.HostHolder;
import com.matt.project.question.model.User;
import com.matt.project.question.service.CommentService;
import com.matt.project.question.service.LikeService;
import com.matt.project.question.service.UserService;
import com.matt.project.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author matt
 * @create 2021-01-12 14:42
 */
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;
    @Autowired
    private HostHolder hostHolder;

    @Autowired
    private EventProducer eventProducer;
    @Autowired
    private CommentDAO commentDAO;
    @Autowired
    private UserService userService;

    @RequestMapping(value = "/like", method = RequestMethod.POST)
    @ResponseBody
    public String like(@RequestParam(name = "commentId")Integer commentId){
        System.out.println(commentId);
        User user = hostHolder.getUser();
        if (user == null) {
            return WendaUtil.getJSONString("999");
        }


        Long count = likeService.like(user.getId(), EntityType.ENTITY_QUESTION, commentId);

        Comment comment = commentDAO.getCommentById(commentId);
        eventProducer.fireEvent(new EventModel(EventType.LIKE)
                .setActorId(hostHolder.getUser().getId())
                .setEntityId(commentId)
                .setEnvityType(EntityType.ENTITY_COMMENT)
                .setEntityOwnerId(userService.getUserById(comment.getUserId()).getId())
                .setExt("questionId", String.valueOf(comment.getEntityId()))
        );

        return WendaUtil.getJSONString("0",String.valueOf(count));
    }

    @RequestMapping(value = "/dislike", method = RequestMethod.POST)
    @ResponseBody
    public String dislike(@RequestParam(name = "commentId")Integer commentId){

        User user = hostHolder.getUser();
        if (user == null) {
            return WendaUtil.getJSONString("999");
        }
        Long count = likeService.disLike(user.getId(), EntityType.ENTITY_QUESTION, commentId);
        return WendaUtil.getJSONString("0",String.valueOf(count));
    }


}
