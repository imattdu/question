package com.matt.project.question.async;

import com.alibaba.fastjson.JSONObject;
import com.matt.project.question.util.JedisAdapter;
import com.matt.project.question.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author matt
 * @create 2021-01-13 11:05
 */
@Service
public class EventProducer {

    @Autowired
    private JedisAdapter jedisAdapter;

    /**
     * 功能：发送事件
     * @author matt
     * @date 2021/1/13
     * @param eventModel
     * @return java.lang.Boolean
    */
    public Boolean fireEvent(EventModel eventModel) {

        try {
            String eventModelJson = JSONObject.toJSONString(eventModel);
            String eventQueueKey = RedisKeyUtil.getEventQueueKey();
            Long lpush = jedisAdapter.lpush(eventQueueKey, eventModelJson);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
}
