package com.matt.project.question.service.impl;

import com.matt.project.question.dao.QuestionDAO;
import com.matt.project.question.model.Question;
import com.matt.project.question.service.QuestionService;
import com.matt.project.question.service.SensitiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 15:51
 */
@Service
public class QuestionServiceImpl implements QuestionService {

    @Autowired
    private QuestionDAO questionDAO;
    @Autowired
    private SensitiveServiceImpl sensitiveService;

    @Override
    public List<Question> listQuestion() {
        return null;
    }

    @Override
    public Boolean addQuestion(Question question) {
        if (question == null) {
            return false;
        }
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));

        question.setTitle(sensitiveService.filter(question.getTitle()));
        question.setContent(sensitiveService.filter(question.getContent()));

        questionDAO.saveQuestion(question);
        return question.getId() > 0;
    }

    @Override
    public Question getQuestionById(Integer id) {

        Question question = questionDAO.getQuestionById(id);
        return question;
    }
}
