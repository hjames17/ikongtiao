package com.wetrack.wechat;

import com.wetrack.ikongtiao.domain.BusinessSettings;
import com.wetrack.ikongtiao.domain.wechat.WechatPublicAccount;
import com.wetrack.ikongtiao.service.api.SettingsService;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by zhanghong on 15/12/17.
 */
@Configuration
public class WechatConfig {

//    @Value("${wechat.app.token}")
//    String token;
//    @Value("${wechat.app.appSecret}")
//    String appSecret;
//    @Value("${wechat.app.appId}")
//    String appId;
//    @Value("${wechat.app.aesKey}")
//    String aesKey;


    @Autowired
    SettingsService settingsService;

    @Autowired
    WechatPublicAccountService wechatPublicAccountService;


    @Bean
    WxMpConfigStorage getWeixinConfigStorage(){
        BusinessSettings businessSettings = settingsService.getBusinessSettings();
        String appId = businessSettings.getWechatAppId();
        WechatPublicAccount publicAccount = wechatPublicAccountService.findByAppId(appId);
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(appId);
        wxMpConfigStorage.setSecret(publicAccount.getAppSecret());
        wxMpConfigStorage.setToken(publicAccount.getToken());
        wxMpConfigStorage.setAesKey(publicAccount.getAesKey());
//        wxMpConfigStorage.setAppId(appId);
//        wxMpConfigStorage.setSecret(appSecret);
//        wxMpConfigStorage.setToken(token);
//        wxMpConfigStorage.setAesKey(aesKey);
        return wxMpConfigStorage;
    }

//    @Bean
//    WeixinServiceProvider createWeixinServiceProvider(){
//
//        Map<String, WxMpService> serviceMap = new HashMap<String, WxMpService>();
//
//        return new WeixinServiceProvider() {
//            @Override
//            public WxMpService getWeixinService(String appId) {
//                WxMpService weixinService = serviceMap.get(appId);
//
//                if(weixinService == null){
//                    weixinService = new WxMpServiceImpl();
//                    WechatPublicAccount wechatPublicAccount = wechatPublicAccountService.findByAppId(appId);
//                    WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
//                    wxMpConfigStorage.setAppId(appId);
//                    wxMpConfigStorage.setSecret(wechatPublicAccount.getAppSecret());
//                    wxMpConfigStorage.setToken(wechatPublicAccount.getToken());
//                    wxMpConfigStorage.setAesKey(wechatPublicAccount.getAesKey());
//
//                    weixinService.setWxMpConfigStorage(wxMpConfigStorage);
//                    serviceMap.put(appId, weixinService);
//                }
//
//                return weixinService;
//            }
//        };
//    }

    @Autowired
    @Bean
    WxMpService getWeixinService(WxMpConfigStorage weixinConfigStorage){

        WxMpService weixinService = new WxMpServiceImpl();
        weixinService.setWxMpConfigStorage(weixinConfigStorage);

        return weixinService;
    }

}
