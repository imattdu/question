package com.matt.project.question.controller;

import com.matt.project.question.model.ViewObject;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.*;


/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-27 10:11
 */

@Controller
public class IndexController {

    @RequestMapping(value = {"/"},method = RequestMethod.GET)
    public String index() {
        return "index";
    }


    public ViewObject listLatestQuestion(){


        return null;
    }


    @RequestMapping(value = {"/home"},method = RequestMethod.GET)
    public String toHome(Model model) {

        model.addAttribute("value1","hello");
        List<String> list = new ArrayList<>();
        list.add("aa");
        list.add("bb");
        list.add("cc");
        model.addAttribute("list",list);

        List<String> colors = Arrays.asList(new String[]{"RED", "GREEN", "BLUE"});
        model.addAttribute("colors",colors);

        Map<String, String> map = new HashMap<>();
        map.put("name", "matt");
        map.put("age", "19");
        model.addAttribute("map",map);

        return "home";
    }
}
