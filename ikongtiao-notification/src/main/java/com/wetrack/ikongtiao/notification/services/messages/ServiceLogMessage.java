package com.wetrack.ikongtiao.notification.services.messages;

import com.wetrack.ikongtiao.events.Event;
import com.wetrack.ikongtiao.notification.services.Message;
import lombok.Data;

/**
 * Created by zhanghong on 16/9/17.
 */
@Data
public class ServiceLogMessage implements Message {

    int id;

    Event event;

    @Override
    public int getId() {
        return id;
    }
}
