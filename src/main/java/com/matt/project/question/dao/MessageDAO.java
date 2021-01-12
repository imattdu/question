package com.matt.project.question.dao;

import com.matt.project.question.model.Message;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 11:26
 */
public interface MessageDAO {

    Integer saveMessage(Message message);

    List<Message> listConversation(Integer userId);

    Integer countUnreadMessage(Integer userId);

    List<Message> listMessageByConversationId(String conversationId);


}
