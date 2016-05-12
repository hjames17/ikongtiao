package com.wetrack.ikongtiao.notification.services.messages;

import com.wetrack.ikongtiao.notification.services.Message;

/**
 * Created by zhanghong on 16/3/1.
 */
public class GetuiMessage implements Message {
    int id;
    String receiver;
    String title;
    String content;
    Object data;
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
