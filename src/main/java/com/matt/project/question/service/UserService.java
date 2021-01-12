package com.matt.project.question.service;

import com.matt.project.question.model.User;

import java.util.Map;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 16:19
 */
public interface UserService {

    Map<String,Object> register(String name, String password);


    Map<String,Object> login(String name, String password);

    void logout(String ticket);

    User getUserById(Integer id);

    User getUserByName(String name);


}
