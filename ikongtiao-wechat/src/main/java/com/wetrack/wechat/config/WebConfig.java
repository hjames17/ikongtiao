package com.wetrack.wechat.config;

import com.wetrack.wechat.weixinHandlers.SubscriptionHandler;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhanghong on 15/12/17.
 */
@Configuration
public class WebConfig {
//    @Autowired
//    WxMpService weixinService;

    @Autowired
    SubscriptionHandler subscriptionHandler;

//    @Autowired
//    WechatPublicAccountService wechatPublicAccountService;



//    @Bean
//    WechatMessageRouterProvider getWeixinMessageRouter(WxMpService weixinService){
//        return new WechatMessageRouterProvider() {
//
//            Map<WxMpService, WxMpMessageRouter> routerMap = new HashMap<WxMpService, WxMpMessageRouter>();
//
//            @Override
//            public WxMpMessageRouter getWeixinMessageRouter(WxMpService weixinService) {
//                WxMpMessageRouter router = routerMap.get(weixinService);
//                if(router == null){
//                    router = new WxMpMessageRouter(weixinService);
//                    router
//                            .rule().msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE).handler(subscriptionHandler).end();
//                    routerMap.put(weixinService, router);
//                }
//                return router;
//            }
//        };
//    }

    @Autowired
    @Bean
    WxMpMessageRouter getWeixinMessageRouter(WxMpService weixinService){
        WxMpMessageRouter wxMpMessageRouter = new WxMpMessageRouter(weixinService);
        wxMpMessageRouter
                .rule().async(false).msgType(WxConsts.XML_MSG_EVENT).event(WxConsts.EVT_SUBSCRIBE).handler(subscriptionHandler).end();

        return wxMpMessageRouter;
    }


}
