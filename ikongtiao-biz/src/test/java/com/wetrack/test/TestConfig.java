package com.wetrack.test;

import com.wetrack.config.Config;
import com.wetrack.ikongtiao.DataBaseConfig;
import com.wetrack.message.MessageService;
import com.wetrack.message.MessageServiceProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by zhanghong on 17/4/12.
 */

@Configuration
@Import(value = {DataBaseConfig.class, Config.class})
//@ComponentScan(value = "studio.wetrack.pubu")
@PropertySource({"classpath:conf/app.conf", "classpath:conf/redis.conf"})
public class TestConfig {


    @Bean
    MessageService configMessageService(){

        return new MessageServiceProxy();
    }
}
