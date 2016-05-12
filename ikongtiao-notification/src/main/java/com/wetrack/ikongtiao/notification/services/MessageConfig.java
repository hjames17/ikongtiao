package com.wetrack.ikongtiao.notification.services;

import com.wetrack.ikongtiao.notification.services.channels.*;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Created by zhanghong on 16/3/7.
 */
@Configuration
@EnableScheduling
public class MessageConfig{

    @Autowired
    WechatMessageChannel wechatMessageChannel;
    @Autowired
    WebNotificationMessageChannel webNotificationMessageChannel;
    @Autowired
    SmsMessageChannel smsMessageChannel;
    @Autowired
    GetuiMessageChannel getuiMessageChannel;
    @Autowired
    EmailMessageChannel emailMessageChannel;

    @Bean(autowire = Autowire.BY_NAME, name = "defaultMessageService")
    MessageMultiChannelService configMessageService(){
        MessageMultiChannelService messageService = new MessageServiceImpl();
        messageService.registerChannel(wechatMessageChannel);
        messageService.registerChannel(webNotificationMessageChannel);
        messageService.registerChannel(smsMessageChannel);
        messageService.registerChannel(getuiMessageChannel);
        messageService.registerChannel(emailMessageChannel);

        return messageService;
    }


//    ApplicationContext context;
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.context = applicationContext;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        MessageMultiChannelService service = this.context.getBean("defaultMessageService", MessageMultiChannelService.class);
//        NotificationService notificationService = new NotificationServiceImpl();
//    }
}
