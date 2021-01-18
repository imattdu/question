package com.matt.project.question.controller;

import com.matt.project.question.model.HostHolder;
import com.matt.project.question.model.Message;
import com.matt.project.question.model.User;
import com.matt.project.question.model.ViewObject;
import com.matt.project.question.service.MessageService;
import com.matt.project.question.service.UserService;
import com.matt.project.question.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author matt
 * @create 2021-01-11 15:06
 */
@Controller
public class MessageController {

    @Autowired
    private HostHolder hostHolder;
    @Autowired
    private UserService userService;

    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/msg/save", method = RequestMethod.POST)
    @ResponseBody
    public String sendMessage(@RequestParam(value = "toName")String toName,
                              @RequestParam(value = "content")String content){

        try {
            Message message = new Message();
            message.setContent(content);
            message.setCreateDate(new Date());
            User user = hostHolder.getUser();
            if (user != null) {
                message.setFromId(user.getId());
            } else {
                return WendaUtil.getJSONString("1","用户未登录");
            }
            user = userService.getUserByName(toName);
            if (user == null) {
                return WendaUtil.getJSONString("1","用户不存在，请输入正确用户名");
            }
            message.setToId(user.getId());
            message.setHasRead(0);

            Integer result = messageService.saveMessage(message);
            System.out.println(message.toString());
            return WendaUtil.getJSONString("0");
        } catch (Exception e) {
            e.printStackTrace();
            return WendaUtil.getJSONString("1", "发送私信失败");
        }
    }

    @RequestMapping(value = "/msg/list",method = RequestMethod.GET)
    public String toMessageList(Model model){

        User user = hostHolder.getUser();
        if (user == null){
            return "redirect:/toLogin";
        }
        Integer selfId = user.getId();
        List<Message> conversationList = messageService.listConversation(selfId);
        List<ViewObject> conversations = new ArrayList<>();
        for (Message message : conversationList) {
            ViewObject vo = new ViewObject();
            vo.set("message",message);
            int targetId = message.getFromId() == selfId ? message.getToId() :
                    message.getFromId();
            vo.set("user",userService.getUserById(targetId));
            // 读取未读
            vo.set("unread",messageService.countUnreadMessage(message.getConversationId()));


            conversations.add(vo);
        }
        model.addAttribute("conversations", conversations);
        return "letter";
    }


    @RequestMapping(value = "/msg/detail",method = RequestMethod.GET)
    public String toMessageDetail(@RequestParam(name = "conversationId")
                                              String conversationId,
                                  Model model){

        List<Message> messageList = messageService.listMessageByConversationId(conversationId);
        List<ViewObject> messages = new ArrayList<>();

        for (Message message : messageList) {
            ViewObject vo = new ViewObject();
            vo.set("message",message);
            User user = userService.getUserById(message.getFromId());
            if (user == null) {
                continue;
            }
            vo.set("headUrl", user.getHeadUrl());
            vo.set("userId", user.getId());
            vo.set("user", user);
            messages.add(vo);
        }

        model.addAttribute("messages",messages);

        messageService.readMessageByConversationId(conversationId);


        return "letterDetail";
    }

}
