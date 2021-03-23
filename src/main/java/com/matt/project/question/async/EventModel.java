package com.matt.project.question.async;


import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author matt
 * @create 2021-01-13 10:53
 */
public class EventModel implements Serializable {

    private EventType eventType;
    private int actorId;
    private int entityType;
    private int entityId;

    // 通知的某个人
    private int entityOwnerId;
    // 扩展字段
    private Map<String, String> exts = new HashMap<>();

    public EventType getEventType() {
        return eventType;
    }

    public EventModel setEventType(EventType eventType) {
        this.eventType = eventType;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public int getEntityOwnerId() {
        return entityOwnerId;
    }

    public EventModel setEntityOwnerId(int entityOwnerId) {
        this.entityOwnerId = entityOwnerId;
        return this;
    }

    public Map<String, String> getExts() {
        return exts;
    }

    public EventModel setExts(Map<String, String> exts) {
        this.exts = exts;
        return this;
    }

    public EventModel setExt(String key, String value) {
        this.exts.put(key, value);
        return this;
    }

    public String getExt(String key) {
        return this.exts.get(key);
    }

    public EventModel(){
        super();
    }

    public EventModel(EventType eventType) {
        this.eventType = eventType;

    }
}
