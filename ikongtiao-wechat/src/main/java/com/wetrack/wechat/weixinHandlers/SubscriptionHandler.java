package com.wetrack.wechat.weixinHandlers;

import com.wetrack.ikongtiao.domain.wechat.WechatWelcomeNews;
import com.wetrack.ikongtiao.repo.api.wechat.WechatWelcomeNewsRepo;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.outxmlbuilder.NewsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * Created by zhanghong on 15/12/17.
 */
@Component
public class SubscriptionHandler implements WxMpMessageHandler {

    @Autowired
    WechatWelcomeNewsRepo wechatWelcomeNewsRepo;


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        //获取用户基本信息
        //WxMpUser wxUser = wxMpService.userInfo(wxMessage.getFromUserName(), "zh_CN");

//        WxMpXmlOutTextMessage m
//                = WxMpXmlOutMessage.TEXT().content("欢迎!")
//                .fromUser(wxMessage.getToUserName())
//                .toUser(wxMessage.getFromUserName()).build();
        return build(wxMessage.getToUserName(), wxMessage.getFromUserName());
    }

    private WxMpXmlOutMessage build(String fromUser, String toUser){

        NewsBuilder builder = WxMpXmlOutMessage.NEWS()
                .fromUser(fromUser)
                .toUser(toUser);


        List<WechatWelcomeNews> newsList = wechatWelcomeNewsRepo.findAll();
        int count = 0;
        for(WechatWelcomeNews news : newsList) {
            WxMpXmlOutNewsMessage.Item item = new WxMpXmlOutNewsMessage.Item();
            item.setDescription(news.getDescription());
            item.setPicUrl(news.getPicUrl());
            item.setTitle(news.getTitle());
            item.setUrl(news.getUrl());
            builder.addArticle(item);
            count++;
            if(count == 10){
                break;
            }
        }

        WxMpXmlOutNewsMessage m = builder.build();
        return m;
    }
}
