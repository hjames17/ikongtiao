package com.wetrack.message.channels;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
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
public class WechatMessageChannel extends AbstractMessageChannel {
    private Logger LOGGER = LoggerFactory.getLogger(WechatMessageChannel.class);

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    FixerRepo fixerRepo;
    @Autowired
    RepairOrderRepo repairOrderRepo;

    @Value("${weixin.page.host}")
    String weixinPageHost;
    @Value("${weixin.page.mission}")
    String weixinMissionPage;
    @Value("${weixin.page.im}")
    String weixinImPage;
    @Value("${host.static}")
    String staticHost;
    static final String ACTION_DETAIL = "detail";
    static final String ACTION_REPAIRORDER = "repairOderId";
    static final String ACTION_COMMENT = "comment";

    WechatMessageChannel(){
        registerAdapter(MessageId.ACCEPT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("任务已被受理");
                message.setContent("您的任务已经被受理了!点击查看详情");
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
                String url = String.format("%s%s?action=%s&uid=%s&id=%s",
                        weixinPageHost, weixinMissionPage, ACTION_DETAIL, userInfo.getId(), params.get("missionId"));
                message.setUrl(url);
                return message;
            }
        });
        registerAdapter(MessageId.REJECT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("任务已被拒绝");
                message.setContent("您的任务已经被拒绝了，请点击查看原因");
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
                String url = String.format("%s%s?action=%s&uid=%s&id=%s",
                        weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.MISSION_ID));
                message.setUrl(url);
                return message;
            }
        });
        registerAdapter(MessageId.ASSIGNED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("任务被分配了");
                message.setContent(String.format("维修员%s将前往诊断您的故障!点击查看详情", fixer.getName()));
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
                String url = String.format("%s%s?action=%s&uid=%s&id=%s",
                        weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.MISSION_ID));
                message.setUrl(url);
                return message;
            }
        });
        registerAdapter(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                RepairOrder repairOrder = repairOrderRepo.getById((Long) params.get(MessageParamKey.REPAIR_ORDER_ID));
                UserInfo userInfo = userInfoRepo.getById(repairOrder.getUserId());
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("维修单已分配");
                message.setContent(String.format("维修员%s将为您维修故障!点击查看详情", fixer.getName()));
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
                String url = String
                        .format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_REPAIRORDER,
                                repairOrder.getUserId(), params.get(MessageParamKey.REPAIR_ORDER_ID));
                message.setUrl(url);
                return message;
            }
        });
        registerAdapter(MessageId.COMPLETED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("任务已完成");
                message.setContent(String.format("您的任务%d已经完成维修!点击查看详情", params.get(MessageParamKey.MISSION_ID)));
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
                String url = String.format("%s%s?action=%s&uid=%s&id=%s",
                        weixinPageHost, weixinMissionPage, ACTION_DETAIL, params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.MISSION_ID));
                message.setUrl(url);
                return message;
            }
        });

        registerAdapter(MessageId.WAITING_CONFIRM_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("您有待确认的维修单");
                message.setContent(String.format("维修单%d已经生成，请点击进行确认", params.get(MessageParamKey.REPAIR_ORDER_ID)));
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
                String url = String
                        .format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_REPAIRORDER,
                                params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.REPAIR_ORDER_ID));

                message.setUrl(url);
                return message;
            }
        });
        registerAdapter(MessageId.COMPLETED_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("维修单已完成");
                message.setContent(String.format("你的维修单%d已经完成了，请点击评价本次服务", params.get(MessageParamKey.REPAIR_ORDER_ID)));
                //TODO 图片地址可配置
                message.setPicUrl(staticHost + "/images/ikongtiao/mission.png");
                String url = String
                        .format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_COMMENT,
                                params.get(MessageParamKey.USER_ID), params.get(MessageParamKey.REPAIR_ORDER_ID));
                message.setUrl(url);
                return message;
            }
        });
        registerAdapter(MessageId.FIXER_NOTIFY_WECHAT, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("您有新的消息");
                message.setContent("维修员给您发来消息，请点击查看");
                message.setPicUrl(staticHost + "/images/ikongtiao/2.png");
                String url = String.format("%s%s?uid=%s&type=%d",
                        weixinPageHost, weixinImPage, params.get(MessageParamKey.USER_ID), 1);
                message.setUrl(url);
                return message;
            }
        });
        registerAdapter(MessageId.KEFU_NOTIFY_WECHAT, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WechatMessage message = new WechatMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getWechatOpenId());
                message.setTitle("您有新的消息");
                message.setContent("维大师客服给您消息，请点击查看");
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
