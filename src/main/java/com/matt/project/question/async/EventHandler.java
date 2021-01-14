package com.matt.project.question.async;

import java.util.List;

/**
 * @author matt
 * @create 2021-01-13 11:53
 */
public interface EventHandler {

    void doHandler(EventModel eventModel);

    /**
      * @author matt
     * @date 2021/1/13
     * @param
     * @return java.util.List<com.matt.project.question.async.EventType>
    */
    List<EventType> listSupportEventType();

}
