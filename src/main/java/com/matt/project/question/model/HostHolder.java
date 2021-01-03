package com.matt.project.question.model;

import org.springframework.stereotype.Component;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-28 17:43
 */
@Component
public class HostHolder {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<User>();


    public ThreadLocal<User> getUserThreadLocal() {
        return userThreadLocal;
    }

    public User getUser() {
        return userThreadLocal.get();
    }

    public void setUser(User user) {
        userThreadLocal.set(user);
    }



    public void clear() {
        userThreadLocal.remove();
    }
}
