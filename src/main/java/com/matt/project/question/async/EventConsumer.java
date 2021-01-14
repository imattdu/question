package com.matt.project.question.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.matt.project.question.util.JedisAdapter;
import com.matt.project.question.util.RedisKeyUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author matt
 * @create 2021-01-13 11:56
 */
@Service
public class EventConsumer implements InitializingBean, ApplicationContextAware {

    private Map<EventType, List<EventHandler>> config = new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    private JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {

        Map<String, EventHandler> beans = applicationContext.getBeansOfType(EventHandler.class);
        if (beans != null) {
            for (Map.Entry<String, EventHandler> entry : beans.entrySet()) {
                List<EventType> eventTypes = entry.getValue().listSupportEventType();
                for (EventType eventType : eventTypes) {

                    if (!config.containsKey(eventType)) {
                        config.put(eventType, new ArrayList<EventHandler>());
                    }
                    config.get(eventType).add(entry.getValue());

                }
            }
        }

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String eventQueueKey = RedisKeyUtil.getEventQueueKey();
                    List<String> valueList = jedisAdapter.brpop(0, eventQueueKey);

                    System.out.println(valueList);
                    for (String value : valueList) {
                        if (value.equals(eventQueueKey)) {
                            continue;
                        }
                        EventModel eventModel = JSON.parseObject(value, EventModel.class);
                        if (!config.containsKey(eventModel.getEventType())) {
                            System.out.println("非法事件");
                            continue;
                        }

                        for (EventHandler eventHandler : config.get(eventModel.getEventType())) {
                            eventHandler.doHandler(eventModel);

                        }
                    }
                }
            }
        });
        thread.start();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
