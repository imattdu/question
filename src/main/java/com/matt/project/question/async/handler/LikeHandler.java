package com.matt.project.question.async.handler;

import com.matt.project.question.async.EventHandler;
import com.matt.project.question.async.EventModel;
import com.matt.project.question.async.EventType;
import com.matt.project.question.model.Message;
import com.matt.project.question.model.User;
import com.matt.project.question.service.MessageService;
import com.matt.project.question.service.UserService;
import com.matt.project.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author matt
 * @create 2021-01-13 13:54
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    MessageService messageService;
    @Autowired
    UserService userService;

    @Override
    public void doHandler(EventModel eventModel) {
        Message message = new Message();
        message.setFromId(WendaUtil.SYSTEM_USER_ID);
        message.setToId(eventModel.getActorId());
        message.setCreateDate(new Date());
        message.setHasRead(0);
        User user = userService.getUserById(eventModel.getActorId());
        message.setContent("用户 " + user.getName() + "赞了你的评论");
        System.out.println(message.toString());
        messageService.saveMessage(message);
    }

    @Override
    public List<EventType> listSupportEventType() {
        return Arrays.asList(EventType.LIKE);
    }
}
