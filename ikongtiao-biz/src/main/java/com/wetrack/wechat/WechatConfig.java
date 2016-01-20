package com.wetrack.wechat;

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
public class WechatConfig {

    @Value("${wechat.app.token}")
    String token;
    @Value("${wechat.app.appSecret}")
    String appSecret;
    @Value("${wechat.app.appId}")
    String appId;
    @Value("${wechat.app.aesKey}")
    String aesKey;


    @Bean
    WxMpConfigStorage getWeixinConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(appId);
        wxMpConfigStorage.setSecret(appSecret);
        wxMpConfigStorage.setToken(token);
        wxMpConfigStorage.setAesKey(aesKey);
        return wxMpConfigStorage;
    }

    @Autowired
    @Bean
    WxMpService getWeixinService(WxMpConfigStorage weixinConfigStorage){

        WxMpService weixinService = new WxMpServiceImpl();
        weixinService.setWxMpConfigStorage(weixinConfigStorage);

        return weixinService;
    }
}
