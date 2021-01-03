package com.matt.project.question.interceptor;


import com.matt.project.question.dao.LoginTicketDAO;
import com.matt.project.question.dao.UserDAO;
import com.matt.project.question.model.HostHolder;
import com.matt.project.question.model.LoginTicket;
import com.matt.project.question.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 17:40
 */
@Component
public class PassportInterceptor implements HandlerInterceptor {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Autowired
    private HostHolder hostHolder;



    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {

        String ticket = null;
        Cookie[] cookies = httpServletRequest.getCookies();
        for (Cookie cookie : cookies) {
            if ("ticket".equals(cookie.getName())) {
                ticket = cookie.getValue();
                break;
            }
        }

        if (ticket != null) {
            LoginTicket loginTicket = loginTicketDAO.getLoginTicketByTicket(ticket);
            if (loginTicket == null || loginTicket.getStatus() == 1
                || loginTicket.getExpired().before(new Date())) {
                return true;
            }
            User user = userDAO.getUserById(loginTicket.getUserId());
            hostHolder.setUser(user);
        }


        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        if (modelAndView != null) {
            modelAndView.addObject("user",hostHolder.getUser());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
