package com.matt.project.question.service.impl;

import com.matt.project.question.dao.MessageDAO;
import com.matt.project.question.model.Message;
import com.matt.project.question.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 15:24
 */
@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private MessageDAO messageDAO;

    @Override
    public Integer saveMessage(Message message) {

        if (message == null) {
            return -1;
        }
        messageDAO.saveMessage(message);
        return message.getId();
    }

    @Override
    public Integer readMessageByConversationId(String conversationId) {

        Integer readCount = messageDAO.updateMessageHasReadByConversationId(1, conversationId);

        return readCount;
    }

    @Override
    public List<Message> listConversation(Integer userId) {

        List<Message> messageList = messageDAO.listConversation(userId);
        return messageList;
    }

    @Override
    public Integer countUnreadMessage(String  conversationId) {

        Integer count = messageDAO.countUnreadMessage(conversationId);
        return count;
    }

    @Override
    public List<Message> listMessageByConversationId(String conversationId) {

        List<Message> messageList = messageDAO.listMessageByConversationId(conversationId);
        return messageList;
    }
}
