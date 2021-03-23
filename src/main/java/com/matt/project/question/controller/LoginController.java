package com.matt.project.question.controller;

import com.matt.project.question.async.EventModel;
import com.matt.project.question.async.EventProducer;
import com.matt.project.question.async.EventType;
import com.matt.project.question.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 16:38
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;
    @Autowired
    private EventProducer eventProducer;

    @RequestMapping(value = "/toLogin",method = RequestMethod.GET)
    public String toLogin(@RequestParam(name = "next", required = false)String next,
                          Model model){
        model.addAttribute("next",next);
        return "login";
    }

    @RequestMapping(value = "/reg", method = RequestMethod.POST)
    public String reg(@RequestParam(name = "name")String name,
                      @RequestParam(name = "password")String password,
                      @RequestParam(name = "next")String next,
                      Model model) {
        Map<String, Object> result = userService.register(name, password);

        if (result.containsKey("msg")) {
            model.addAttribute("msg",result.get("msg"));
            return "login";
        }
        model.addAttribute("msg","注册成功,请登录");

        eventProducer.fireEvent(new EventModel(EventType.REG)
                .setExt("username",name)
                .setExt("email", name )
        );
        return "login";
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam(name = "name")String name,
                        @RequestParam(name = "password")String password,
                        @RequestParam(name = "rememberme",defaultValue = "false")Boolean rememberme,
                        @RequestParam(name = "next")String next,
                        HttpServletResponse response,
                        Model model) {

        Map<String, Object> login = userService.login(name, password);
        if (login.containsKey("ticket")) {
            model.addAttribute("msg",login.get("msg"));
            Cookie cookie = new Cookie("ticket", (String)login.get("ticket"));
            cookie.setPath("/");
            if (rememberme) {
                cookie.setMaxAge(3600*24*5);
            }
            response.addCookie(cookie);

            if (StringUtils.isNotBlank(next)) {
                return "redirect:" + next;
            }
            return "redirect:/";
        } else {
            model.addAttribute("msg", login.get("msg"));
            return "login";
        }


    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(@CookieValue(name = "ticket")String ticket ) {

        userService.logout(ticket);
        return "redirect:/";
    }




}
