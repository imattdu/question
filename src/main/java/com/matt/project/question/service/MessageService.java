package com.matt.project.question.service;

import com.matt.project.question.model.Message;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 15:24
 */
public interface MessageService {

    Integer saveMessage(Message message);

    List<Message> listConversation(Integer userId);

    Integer countUnreadMessage(Integer userId);

    List<Message> listMessageByConversationId(String conversationId);
}
