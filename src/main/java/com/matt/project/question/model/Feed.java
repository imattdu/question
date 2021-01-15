package com.matt.project.question.model;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.print.attribute.standard.PrinterURI;
import java.util.Date;

/**
 * @author matt
 * @create 2021-01-15 9:58
 */
public class Feed {

    private Integer id;
    private Integer type;
    private Integer userId;
    private Date createDate;
    // JSON格式
    private String data;

    private JSONObject dataJSON = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        // json字符串解析为json对象
        this.dataJSON = JSONObject.parseObject(this.data);
    }

    public String get(String key) {
        return dataJSON == null ? null : dataJSON.getString(key);
    }

    @Override
    public String toString() {
        return "Feed{" +
                "id=" + id +
                ", type=" + type +
                ", userId=" + userId +
                ", createDate=" + createDate +
                ", data='" + data + '\'' +
                '}';
    }
}
