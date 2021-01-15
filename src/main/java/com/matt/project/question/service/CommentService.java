package com.matt.project.question.service;

import com.matt.project.question.model.Comment;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 10:21
 */
public interface CommentService {

    Integer saveComment(Comment comment);

    List<Comment> listCommentByEntity(Integer commentType,Integer commentId);

    Integer countCommentByUserId(Integer userId);
}
