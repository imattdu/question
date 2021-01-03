package com.matt.project.question.model;

import org.omg.PortableInterceptor.Interceptor;

import java.util.Date;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-29 16:57
 */
public class LoginTicket {

    private Integer id;

    private Integer userId;

    private Date expired;

    private Integer status;

    private String ticket;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getExpired() {
        return expired;
    }

    public void setExpired(Date expired) {
        this.expired = expired;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }
}
