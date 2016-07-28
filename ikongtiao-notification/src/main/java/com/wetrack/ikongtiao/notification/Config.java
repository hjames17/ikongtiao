package com.wetrack.ikongtiao.notification;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.servlet.view.velocity.VelocityConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 16/4/11.
 */
@Configuration
@EnableScheduling
public class Config {


    String velocityResourcePath = "classpath:/template";

    @Bean
    VelocityConfigurer velocityConfigurer(){
        VelocityConfigurer velocityConfigurer = new VelocityConfigurer();
        velocityConfigurer.setResourceLoaderPath(velocityResourcePath);
        Map<String, Object> props = new HashMap<String, Object>();
        props.put("input.encoding", "UTF-8");
        props.put("output.encoding", "UTF-8");
        props.put("velocimacro.library.autoreload", true);
        velocityConfigurer.setVelocityPropertiesMap(props);

        return velocityConfigurer;
    }

    @Bean
    TaskScheduler taskScheduler(){
        return new ThreadPoolTaskScheduler();
    }
}
