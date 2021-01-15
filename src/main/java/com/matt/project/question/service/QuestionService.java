package com.matt.project.question.service;

import com.matt.project.question.model.Question;

import java.util.List;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 15:51
 */
public interface QuestionService {


    List<Question> listQuestion();

    Boolean addQuestion(Question question);

    Question getQuestionById(Integer id);

    List<Question> listQuestionByCreatedate();
}
