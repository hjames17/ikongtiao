package com.wetrack.ikongtiao.notification.services;

import com.wetrack.message.MessageService;

/**
 * Created by zhanghong on 16/4/12.
 */
public interface MessageMultiChannelService extends MessageService {
    void registerChannel(MessageChannel messageChannel);
}
