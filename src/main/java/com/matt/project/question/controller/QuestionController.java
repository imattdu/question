package com.matt.project.question.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author matt
 * @create 2021-01-08 17:20
 */
@Controller
public class QuestionController {

    @RequestMapping(value = "/question/add", method = RequestMethod.POST)
    @ResponseBody
    public String addQuestion(@RequestParam(name = "title")String title,
                              @RequestParam(name = "content")String content) {

        return "";
    }
}
