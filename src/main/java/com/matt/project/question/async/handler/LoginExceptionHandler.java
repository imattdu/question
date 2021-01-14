package com.matt.project.question.async.handler;

import com.matt.project.question.async.EventHandler;
import com.matt.project.question.async.EventModel;
import com.matt.project.question.async.EventType;
import com.matt.project.question.util.MailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author matt
 * @create 2021-01-13 15:43
 */
@Component
public class LoginExceptionHandler implements EventHandler {

    @Autowired
    private MailSender mailSender;

    @Override
    public void doHandler(EventModel eventModel) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("username", eventModel.getExt("username"));
        mailSender.sendWithHTMLTemplate(eventModel.getExt("email"),
                "登陆IP异常", "mails/login_exception.html", map);
    }

    @Override
    public List<EventType> listSupportEventType() {

        return Arrays.asList(EventType.LOGIN);
    }
}
