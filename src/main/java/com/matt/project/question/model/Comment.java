package com.matt.project.question.model;

import java.util.Date;

/**
 * @author matt
 * @create 2021-01-11 9:42
 */
public class Comment {

    private Integer id;
    private Integer userId;
    private Date createDate;
    private Integer entityId;
    private Integer entityType;
    private String content;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getEntityId() {
        return entityId;
    }

    public void setEntityId(Integer entityId) {
        this.entityId = entityId;
    }

    public Integer getEntityType() {
        return entityType;
    }

    public void setEntityType(Integer entityType) {
        this.entityType = entityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", entityId=" + entityId +
                ", entityType=" + entityType +
                ", content='" + content + '\'' +
                ", status=" + status +
                '}';
    }
}
