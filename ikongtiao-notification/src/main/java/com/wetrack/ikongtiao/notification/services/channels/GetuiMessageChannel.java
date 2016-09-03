package com.wetrack.ikongtiao.notification.services.channels;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wetrack.auth.service.TokenService;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.notification.services.AbstractMessageChannel;
import com.wetrack.ikongtiao.notification.services.Message;
import com.wetrack.ikongtiao.notification.services.MessageAdapter;
import com.wetrack.ikongtiao.notification.services.messages.GetuiMessage;
import com.wetrack.ikongtiao.notification.services.messages.JPushMessage;
import com.wetrack.ikongtiao.notification.util.JPusher;
import com.wetrack.ikongtiao.repo.api.fixer.FixerDeviceRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.message.GetuiPush;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhanghong on 16/3/1.
 */
@Service
public class GetuiMessageChannel extends AbstractMessageChannel {
    private Logger logger = LoggerFactory.getLogger(GetuiMessageChannel.class);


    @Autowired
    FixerRepo fixerRepo;

    @Autowired
    FixerDeviceRepo fixerDeviceRepo;

    @Autowired
    MissionRepo missionRepo;

    GetuiMessageChannel(){
        registerAdapter(MessageId.ASSIGNED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                message.setId(MessageId.ASSIGNED_MISSION);
                int fixerId = Integer.valueOf(params.get(MessageParamKey.FIXER_ID).toString());
//                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId(fixerId);
//                message.setReceiver(fixerDevice.getClientId());
                message.setReceiver(Constants.TOKEN_ID_PREFIX_FIXER + fixerId);
                message.setTitle("有新的任务分配给你");
                message.setContent(String.format("任务号是%s,点击查看详情", params.get(MessageParamKey.MISSION_SID)));
                // 任务id，
                Map<String, String> map = new HashMap<>();
                map.put(MessageParamKey.MISSION_ID, params.get(MessageParamKey.MISSION_SID).toString());
                map.put("type", "mission");
                message.setData(map);
                return message;
            }
        });
        registerAdapter(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                message.setId(MessageId.ASSIGNED_FIXER);
                int fixerId = Integer.valueOf(params.get(MessageParamKey.FIXER_ID).toString());
//                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId(fixerId);
                message.setReceiver(Constants.TOKEN_ID_PREFIX_FIXER + fixerId);
                message.setTitle("有新的维修单分配给你");
                message.setContent(String.format("维修单号是%s,点击查看详情", params.get(MessageParamKey.REPAIR_ORDER_SID)));
                // 任务id，
                Map<String, String> map = new HashMap<>();
                map.put(MessageParamKey.MISSION_ID, params.get(MessageParamKey.MISSION_SID).toString());
                map.put(MessageParamKey.REPAIR_ORDER_ID, params.get(MessageParamKey.REPAIR_ORDER_SID).toString());
                map.put("type", "mission");
                message.setData(map);
                return message;
            }
        });

        MessageAdapter auditResultMessageAdapter = new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                message.setId(messageId);
                int fixerId = Integer.valueOf(params.get(MessageParamKey.FIXER_ID).toString());
//                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId(Integer.valueOf(params.get(MessageParamKey.FIXER_ID).toString()));
                message.setReceiver(Constants.TOKEN_ID_PREFIX_FIXER + fixerId);
                String auditText , resultText, content = "点击查看详情";
                Object auditInfo = params.get(MessageParamKey.FIXER_AUDIT_INFO);
                switch (messageId){
                    case MessageId.FIXER_CERT_AUDIT_PASS:
                        auditText = "实名";
                        resultText = "已通过";
                        break;
                    case MessageId.FIXER_CERT_AUDIT_DENY:
                        auditText = "实名";
                        resultText = "被驳回";
                        FixerCertInfo certInfo = (FixerCertInfo)auditInfo;
                        content = certInfo.getDenyReason();
                        break;
                    case MessageId.FIXER_INSURANCE_AUDIT_PASS:
                        auditText = "保险";
                        resultText = "已通过";
                        break;
                    case MessageId.FIXER_INSURANCE_AUDIT_DENY:
                        auditText = "保险";
                        resultText = "被驳回";
                        FixerInsuranceInfo insuranceInfo = (FixerInsuranceInfo)auditInfo;
                        content = insuranceInfo.getDenyReason();
                        break;
                    case MessageId.FIXER_PROFESS_AUDIT_PASS:
                        auditText = "技能";
                        resultText = "已通过";
                        break;
                    case MessageId.FIXER_PROFESS_AUDIT_DENY:
                        auditText = "技能";
                        resultText = "被驳回";
                        FixerProfessionInfo professionInfo = (FixerProfessionInfo)auditInfo;
                        content = professionInfo.getDenyReason();
                        break;
                    default:
                        auditText = "";
                        resultText = "";
                        break;

                }
                message.setTitle(String.format("您的%s认证%s", auditText, resultText));
                message.setContent(content);
                // 任务id，
                Map<String, String> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.FIXER_ID).toString());
                map.put("type", "certificate");
                map.put("auditType", String.format("%d", messageId%10));
//                map.put("auditInfo", auditInfo);
                message.setData(map);
                return message;
            }
        };

        registerAdapter(MessageId.FIXER_CERT_AUDIT_PASS, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_CERT_AUDIT_DENY, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_INSURANCE_AUDIT_PASS, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_INSURANCE_AUDIT_DENY, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_PROFESS_AUDIT_PASS, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_PROFESS_AUDIT_DENY, auditResultMessageAdapter);

        registerAdapter(MessageId.IM_NOTIFY_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                message.setId(MessageId.IM_NOTIFY_FIXER);
                int fixerId = Integer.valueOf(params.get(MessageParamKey.FIXER_ID).toString());
//                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId(Integer.valueOf(params.get(MessageParamKey.FIXER_ID).toString()));
                message.setReceiver(Constants.TOKEN_ID_PREFIX_FIXER + fixerId);
                message.setTitle("你有新的消息");
                message.setContent("你有新的消息，请点击查看");
                // 任务id，
                Map<String, String> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.IM_PUSH_NOTIFY_SESSION_ID).toString());
                map.put("type", "message");
                message.setData(map);
                return message;
            }
        });
        registerAdapter(MessageId.COMMENT_REPAIR_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                message.setId(MessageId.COMMENT_REPAIR_ORDER);
                int fixerId = Integer.valueOf(params.get(MessageParamKey.FIXER_ID).toString());
//                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId(Integer.valueOf(params.get(MessageParamKey.FIXER_ID).toString()));
                message.setReceiver(Constants.TOKEN_ID_PREFIX_FIXER + fixerId);
                message.setTitle("您的维修服务已经被评价");
                message.setContent(String.format("你的维修单%s获得了%d星评价，请点击查看",
                        params.get(MessageParamKey.REPAIR_ORDER_SID), params.get(MessageParamKey.REPAIR_ORDER_COMMENT_RATE)));
                // 任务id，
                Map<String, String> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.FIXER_ID).toString());
                map.put("type", "comment");
                map.put(MessageParamKey.REPAIR_ORDER_COMMENT_ID, params.get(MessageParamKey.REPAIR_ORDER_COMMENT_ID).toString());
                message.setData(map);
                return message;
            }
        });
    }


    @Resource
    private GetuiPush getuiPush;


    @Override
    protected void callSend(){
        while(true) {
            try {
                MessageRaw messageRaw = bufList.take();
                if (adapterMap.get(messageRaw.id) != null) {
                    if(!clientOffLine(Integer.valueOf(messageRaw.params.get(MessageParamKey.FIXER_ID).toString()))) {

                        Message message = null;
                        try {
                            message = adapterMap.get(messageRaw.id).build(messageRaw.id, messageRaw.params);
                        }catch (Exception e){
                            logger.error("{} build message failed, id {} , reason : {}", this.getName(), messageRaw.id, e.getMessage());
                        }
                        if(message != null) {
                            try {
                                doSend(message);
                                logger.info("{} sent message {} to fixer {} succeed.", this.getName(), messageRaw.id, messageRaw.params.get(MessageParamKey.FIXER_ID));
                            }catch (Exception e){
                                logger.error("发送消息{}失败，messageChannel {}, 原因:",message.getId(),  this.getName(), e.getMessage());
                            }
                        }
                    }else{
                        logger.info("推送对象{}离线，消息无法送达", messageRaw.params.get(MessageParamKey.FIXER_ID));

                    }
                }
            } catch (InterruptedException e) {
                logger.error("message channel take message failed ! " + e.getMessage());
//            e.printStackTrace();
            } catch (Exception e){
                logger.error("getui send message error ! {}",  e.getMessage());
                //抛弃任何异常
            }
        }
    }


    @Resource
    JPusher jPusher;

    @Override
    protected void doSend(Message message) {
        GetuiMessage getui = (GetuiMessage)message;
        logger.info("jpush发送消息，消息内容为:{}", Jackson.base().writeValueAsString(getui));
//        boolean success = getuiPush
//                .pushNotificationToUserId(getui.getReceiver(), getui.getTitle(), getui.getContent(),
//                        Jackson.mobile().writeValueAsString(getui.getData()));

        JPushMessage jpMessage = new JPushMessage();
        jpMessage.setId(message.getId());
        jpMessage.setTitle(getui.getTitle());
        jpMessage.setContent(getui.getContent());
        jpMessage.setAliasList(Arrays.asList(getui.getReceiver()));
        ObjectMapper m = new ObjectMapper();
        jpMessage.setExtras(m.convertValue(getui.getData(), Map.class));
        boolean success = jPusher.pushEvent(jpMessage);
    }

    @Autowired
    TokenService tokenService;
    private boolean clientOffLine(Integer fixerId){
//        Collection<Token> tokens = tokenService.findAllByUserId(Constants.TOKEN_ID_PREFIX_FIXER + fixerId);
//        if(tokens != null && tokens.size() > 0){
//            for(Token token : tokens){
//                if(!token.isExpired() && !token.isLoggedout()){
//                    return false;
//                }
//            }
//        }
//        return true;
        return false;
    }

    @Override
    public String getName() {
        return "getui";
    }
}
