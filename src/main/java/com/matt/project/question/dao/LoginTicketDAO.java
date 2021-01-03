package com.matt.project.question.dao;

import com.matt.project.question.model.LoginTicket;
import org.apache.ibatis.annotations.Param;

/**
 * 功能：
 *
 * @author matt
 * @create 2020-12-29 17:00
 */
public interface LoginTicketDAO {

    Integer saveLoginTicket(LoginTicket loginTicket);

    LoginTicket getLoginTicketByTicket(String ticket);

    Integer updateLoginTicketByTicket(@Param(value = "ticket") String ticket,@Param(value = "status") Integer status);

}
