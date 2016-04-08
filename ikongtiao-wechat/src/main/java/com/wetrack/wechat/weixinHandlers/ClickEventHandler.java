package com.wetrack.wechat.weixinHandlers;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhanghong on 15/12/17.
 */
@Component
public class ClickEventHandler implements WxMpMessageHandler {


    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        String eventKey = wxMessage.getEventKey();
        //获取用户基本信息
        //WxMpUser wxUser = wxMpService.userInfo(wxMessage.getFromUserName(), "zh_CN");

        String text = "";
        if(eventKey.equals("KF_HOT_LINE")){
            text = "4008-260-780";
        }

        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT().content(text).fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName()).build();
        return m;
    }
}
