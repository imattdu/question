package com.matt.project.question.dao;

import com.matt.project.question.model.Message;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 11:26
 */
public interface MessageDAO {

    Integer saveMessage(Message message);

    Integer updateMessageHasReadByConversationId(@Param(value = "hasRead") Integer hasRead,
                                                 @Param(value = "conversationId") String conversationId);

    List<Message> listConversation(Integer userId);

    Integer countUnreadMessage(String conversationId);

    List<Message> listMessageByConversationId(String conversationId);


}
