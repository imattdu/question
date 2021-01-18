package com.matt.project.question.service;

import com.matt.project.question.model.Message;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 15:24
 */
public interface MessageService {

    Integer saveMessage(Message message);


    Integer readMessageByConversationId(String conversationId);

    List<Message> listConversation(Integer userId);

    Integer countUnreadMessage(String conversationId);

    List<Message> listMessageByConversationId(String conversationId);
}
