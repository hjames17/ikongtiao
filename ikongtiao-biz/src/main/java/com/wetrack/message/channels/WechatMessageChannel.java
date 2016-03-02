package com.wetrack.message.channels;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.message.*;
import com.wetrack.message.messages.WechatMessage;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpCustomMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
@Service
public class WechatMessageChannel extends MessageChannel {
    private Logger LOGGER = LoggerFactory.getLogger(WechatMessageChannel.class);

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    FixerRepo fixerRepo;

    @Value("${weixin.page.host}")
    String weixinPageHost;
    @Value("${weixin.page.mission}")
    String weixinMissionPage;
    @Value("${weixin.page.im}")
    String weixinImPage;
    @Value("${host.static}")
    String staticHost;
    static final String ACTION_DETAIL = "detail";
    static final String ACTION_CONFIRMATION = "repairOderId";
    static final String ACTION_COMMENT = "comment";

    WechatMessageChannel(){
        registerAssembler(MessageId.ACCEPT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("任务已被受理");
                message.setContent("你的任务已经被受理了，请点击查看详情");
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String.format("%s%s?action=%s&uid=%s&id=%s",
                        weixinPageHost, weixinMissionPage, ACTION_DETAIL, userInfo.getId(), params.get("missionId"));
                message.setUrl(url);
                return message;
            }
        });
        registerAssembler(MessageId.REJECT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("任务已被拒绝");
                message.setContent("你的任务已经被拒绝了，请点击查看详情");
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String.format("%s%s?action=%s&uid=%s&id=%s",
                        weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.MISSION_ID));
                message.setUrl(url);
                return message;
            }
        });
        registerAssembler(MessageId.ASSIGNED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("任务被分配了");
                message.setContent(String.format("你的任务被分配给%s维修员请等待维修员与你联系，请点击查看详情", fixer.getName()));
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String.format("%s%s?action=%s&uid=%s&id=%s",
                        weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.MISSION_ID));
                message.setUrl(url);
                return message;
            }
        });
        registerAssembler(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer)params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("维修单被分配了");
                message.setContent(String.format("你的维修单被分配给%s维修员请等待维修员与你联系，请点击查看详情", fixer.getName()));
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                message.setUrl((String)params.get("url"));
                return message;
            }
        });
        registerAssembler(MessageId.COMPLETED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));

                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("任务已完成");
                message.setContent(String.format("你的任务已经由维修员%s完成了,请点击查看详情］", fixer.getName()));
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String.format("%s%s?action=%s&uid=%s&id=%s",
                        weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.MISSION_ID));
                message.setUrl(url);
                return message;
            }
        });

        registerAssembler(MessageId.WAITING_CONFIRM_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("你有待确认的维修单");
                message.setContent("你有待确认的维修单，请点击查看详情");
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String
                        .format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_CONFIRMATION,
                                params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.REPAIR_ORDER_ID));

                message.setUrl(url);
                return message;
            }
        });
        registerAssembler(MessageId.COMPLETED_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("维修单已完成");
                message.setContent("你的维修单已经完成了，请点击查看详情");
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String
                        .format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_COMMENT,
                                params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.REPAIR_ORDER_ID));
                message.setUrl(url);
                return message;
            }
        });
        registerAssembler(MessageId.FIXER_NOTIFY_WECHAT, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("你有新的消息");
                message.setContent("你有新的维修员消息，请点击查看");
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String.format("%s%s?uid=%s&type=%d",
                        weixinPageHost, weixinImPage, params.get(MessageParamKey.USER_ID), 1);
                message.setUrl(url);
                return message;
            }
        });
        registerAssembler(MessageId.KEFU_NOTIFY_WECHAT, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("你有新的消息");
                message.setContent("你有新的客服消息，请点击查看");
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String.format("%s%s?uid=%s&type=%d",
                        weixinPageHost, weixinImPage, params.get(MessageParamKey.USER_ID), 0);
                message.setUrl(url);
                return message;
            }
        });
    }


    @Autowired
    WxMpService wxMpService;

    @Override
    protected void doSend(Message message) {
        WechatMessage wechatMessage = (WechatMessage)message;
            WxMpCustomMessage.WxArticle article1 = new WxMpCustomMessage.WxArticle();
            article1.setUrl(wechatMessage.getUrl());
            article1.setPicUrl(wechatMessage.getPicUrl());
            article1.setDescription(wechatMessage.getContent());
            article1.setTitle(wechatMessage.getTitle());
            WxMpCustomMessage wxMpCustomMessage = WxMpCustomMessage.NEWS()
                    .toUser(wechatMessage.getReceiver())
                    .addArticle(article1)
                    .build();
            LOGGER.info("发送微信通知,openId:{},内容:{}", wechatMessage.getReceiver(),
                    Jackson.mobile().writeValueAsString(wxMpCustomMessage));
            try {
                wxMpService.customMessageSend(wxMpCustomMessage);
            } catch (WxErrorException e) {
                LOGGER.error("发送微信通知失败,openId:{},内容:{}", wechatMessage.getReceiver(),
                        Jackson.mobile().writeValueAsString(wxMpCustomMessage));
                e.printStackTrace();
            }
    }
}
