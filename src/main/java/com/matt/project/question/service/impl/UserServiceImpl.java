package com.matt.project.question.service.impl;

import com.matt.project.question.dao.LoginTicketDAO;
import com.matt.project.question.model.LoginTicket;
import com.matt.project.question.model.User;
import com.matt.project.question.dao.UserDAO;
import com.matt.project.question.service.UserService;
import com.matt.project.question.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 16:20
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private LoginTicketDAO loginTicketDAO;

    @Override
    public Map<String, Object> register(String name, String password) {

        Map<String, Object> map = new HashMap<>();
        User user = null;
        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不可以为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不可以为空");
            return map;
        }

        user = userDAO.getUserByName(name);
        if (user != null) {
            map.put("msg", "该用户已经存在");
            return map;
        }

        user = new User();
        user.setName(name);
        user.setSalt(UUID.randomUUID().toString().substring(0, 5));
        user.setHeadUrl("https://images.nowcoder.com/images/20201125/909968901_1606312408558/FECD76F09C4EFFA7102ECDBC1795FB3B");
        user.setPassword(WendaUtil.MD5(password + user.getSalt()));
        userDAO.saveUser(user);

        return map;
    }

    @Override
    public Map<String, Object> login(String name, String password) {

        Map<String, Object> map = new HashMap<>();
        User user = null;

        if (StringUtils.isBlank(name)) {
            map.put("msg", "用户名不可以为空");
            return map;
        }

        if (StringUtils.isBlank(password)) {
            map.put("msg", "密码不可以为空");
            return map;
        }

        user = userDAO.getUserByName(name);
        if (user == null) {
            map.put("msg", "用户不存在");
            return map;
        }

        if (!WendaUtil.MD5(password + user.getSalt()).equals(user.getPassword())) {
            map.put("msg", "用户名和密码不匹配");
            return map;
        }
        String ticket = saveLoginTicket(user.getId());
        map.put("ticket", ticket);
        return map;
    }

    @Override
    public void logout(String ticket) {
        loginTicketDAO.updateLoginTicketByTicket(ticket,1);
    }

    public String saveLoginTicket(Integer userId) {

        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(userId);
        Date date = new Date();
        date.setTime(date.getTime() + 1000 * 3600 * 24);
        loginTicket.setExpired(date);
        loginTicket.setStatus(0);
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-", ""));
        loginTicketDAO.saveLoginTicket(loginTicket);
        return loginTicket.getTicket();
    }
}
