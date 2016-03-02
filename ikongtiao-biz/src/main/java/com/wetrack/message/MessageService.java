package com.wetrack.message;

import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
public interface MessageService {
    void registerChannel(MessageChannel messageChannel);
    void send(int messageId, Map<String, Object> params);
}
