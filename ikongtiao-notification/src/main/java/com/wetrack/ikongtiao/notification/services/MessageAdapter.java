package com.wetrack.ikongtiao.notification.services;

import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
public interface MessageAdapter {
    Message build(int messageId, Map<String, Object> params);
}
