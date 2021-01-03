package com.matt.project.question.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 功能：
 *
 * @author matt
 * @create 2021-01-03 14:25
 */
@Controller
public class UserController {

    @RequestMapping(value = "/user/1", method = RequestMethod.GET)
    public String index() {
        return "index";
    }


}
