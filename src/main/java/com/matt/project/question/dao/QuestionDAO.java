package com.matt.project.question.dao;

import com.matt.project.question.model.Question;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 14:58
 */
public interface QuestionDAO {

    Integer saveQuestion(Question question);

    Question getQuestionById(Integer id);

}
