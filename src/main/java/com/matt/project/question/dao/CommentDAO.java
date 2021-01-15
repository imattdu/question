package com.matt.project.question.dao;

import com.matt.project.question.model.Comment;
import com.matt.project.question.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 10:09
 */
public interface CommentDAO {

    Integer saveComment(Comment comment);

    List<Comment> listCommentByEntity(@Param(value = "entityType") Integer entityType,
                                      @Param(value = "entityId") Integer entityId);

    Comment getCommentById(Integer id);

    Integer countCommentByUserId(Integer userId);

}
