package com.wetrack.ikongtiao.web;

import com.wetrack.message.MessageService;
import com.wetrack.message.MessageServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhanghong on 16/4/11.
 */
@Configuration
public class WebConfig {
    @Bean
    MessageService configMessageService(){

        return new MessageServiceProxy();
    }
}
