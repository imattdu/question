package com.matt.project.question.dao;

import com.matt.project.question.model.User;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 14:47
 */
public interface UserDAO {

    int saveUser(User user);

    User getUserByName(String name);

    User getUserById(Integer id);


}
