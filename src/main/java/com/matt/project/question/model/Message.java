package com.matt.project.question.model;

import java.util.Date;

/**
 * @author matt
 * @create 2021-01-11 11:11
 */
public class Message {

    private Integer id;
    private Integer fromId;
    private Integer toId;
    private String content;
    private Date createDate;
    private Integer hasRead;
    private String conversationId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFromId() {
        return fromId;
    }

    public void setFromId(Integer fromId) {
        this.fromId = fromId;
    }

    public Integer getToId() {
        return toId;
    }

    public void setToId(Integer toId) {
        this.toId = toId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Integer getHasRead() {
        return hasRead;
    }

    public void setHasRead(Integer hasRead) {
        this.hasRead = hasRead;
    }

    public String getConversationId() {

       /* if (fromId < toId) {
            return String.format("%d_%d",fromId,toId);
        }*/
        return String.format("%d_%d", fromId, toId);

    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", fromId=" + fromId +
                ", toId=" + toId +
                ", content='" + content + '\'' +
                ", createDate=" + createDate +
                ", hasRead=" + hasRead +
                ", conversationId=" + conversationId +
                '}';
    }
}
