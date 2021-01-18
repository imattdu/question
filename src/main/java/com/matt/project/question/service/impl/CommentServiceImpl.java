package com.matt.project.question.service.impl;

import com.matt.project.question.dao.CommentDAO;
import com.matt.project.question.model.Comment;
import com.matt.project.question.model.EntityType;
import com.matt.project.question.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 10:25
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDAO commentDAO;

    @Override
    public Integer saveComment(Comment comment) {
        if (comment == null) {
            return -1;
        }
        Integer integer = commentDAO.saveComment(comment);
        return comment.getId();
    }

    @Override
    public List<Comment> listCommentByEntity(Integer commentType, Integer commentId) {
        List<Comment> commentList = commentDAO.listCommentByEntity(commentType, commentId);
        return commentList;
    }

    @Override
    public Integer countCommentByUserId(Integer userId) {

        Integer commentCount = commentDAO.countCommentByUserId(userId);
        return commentCount;
    }

    @Override
    public Integer countCommentByQuestionId(Integer questionId) {

        Integer commentCount = commentDAO.countCommentByQuestionId(questionId);

        return commentCount;
    }
}
