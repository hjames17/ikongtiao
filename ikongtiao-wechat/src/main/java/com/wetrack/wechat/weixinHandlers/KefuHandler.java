package com.wetrack.wechat.weixinHandlers;

import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutTextMessage;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by zhanghong on 15/12/17.
 * 多客服消息处理入口
 */
@Component
public class KefuHandler implements WxMpMessageHandler {
    public static final String QR_SCENE = "qrscene_";


    /**
     * 处理多客服会话发起
     * @param wxMessage
     * @param context
     * @param wxMpService
     * @param sessionManager
     * @return
     * @throws WxErrorException
     */
    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) throws WxErrorException {

        //获取qrcode
        String eventKey = wxMessage.getEventKey();
        String qrCode = null;
        if(eventKey != null && eventKey.startsWith(QR_SCENE)){
            qrCode = eventKey.substring(QR_SCENE.length());
        }
        //获取用户基本信息
        WxMpUser wxUser = wxMpService.userInfo(wxMessage.getFromUserName(), "zh_CN");

        WxMpXmlOutTextMessage m
                = WxMpXmlOutMessage.TEXT().content("欢迎!").fromUser(wxMessage.getToUserName())
                .toUser(wxMessage.getFromUserName()).build();
        return m;
    }
}
