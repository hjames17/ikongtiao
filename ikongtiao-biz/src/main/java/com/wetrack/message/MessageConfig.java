package com.wetrack.message;

import com.wetrack.message.channels.GetuiMessageChannel;
import com.wetrack.message.channels.SmsMessageChannel;
import com.wetrack.message.channels.WebNotificationMessageChannel;
import com.wetrack.message.channels.WechatMessageChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhanghong on 16/3/7.
 */
@Configuration
public class MessageConfig {

    @Autowired
    WechatMessageChannel wechatMessageChannel;
    @Autowired
    WebNotificationMessageChannel webNotificationMessageChannel;
    @Autowired
    SmsMessageChannel smsMessageChannel;
    @Autowired
    GetuiMessageChannel getuiMessageChannel;

    @Bean
    MessageService configMessageService(){
        MessageService messageService = new MessageServiceImpl();
        messageService.registerChannel(wechatMessageChannel);
        messageService.registerChannel(webNotificationMessageChannel);
        messageService.registerChannel(smsMessageChannel);
        messageService.registerChannel(getuiMessageChannel);

        return messageService;
    }
}
