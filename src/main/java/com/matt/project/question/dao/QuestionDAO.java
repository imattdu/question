package com.matt.project.question.dao;

import com.matt.project.question.model.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 14:58
 */
public interface QuestionDAO {

    Integer saveQuestion(Question question);

    Question getQuestionById(Integer id);

    List<Question> listQuestionByPage(@Param(value = "offset") Integer offset,
                                      @Param(value = "count")Integer count);

    List<Question> listQuestionByUserId(Integer userId);

    Integer incrQuestionComment(Integer questionId);

}
