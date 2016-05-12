package com.wetrack.ikongtiao.notification.services.messages;

import com.wetrack.ikongtiao.notification.services.Message;

/**
 * Created by zhanghong on 16/3/1.
 */
public class SmsMessage implements Message {
    int id;
    String text;
    String receiver;
    @Override
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }
}
