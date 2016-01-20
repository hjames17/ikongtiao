package com.wetrack.wechat.config;

import com.wetrack.wechat.weixinHandlers.SubscriptionHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhanghong on 15/12/17.
 */
@Configuration
public class WebConfig {
    @Autowired
    WxMpService weixinService;

    @Autowired
    SubscriptionHandler subscriptionHandler;

    @Autowired
    @Bean
    WxMpMessageRouter getWeixinMessageRouter(WxMpService weixinService){
        WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(weixinService);
        wxMpMessageRouter
                .rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE).handler(subscriptionHandler).end();

        return wxMpMessageRouter;
    }
}
