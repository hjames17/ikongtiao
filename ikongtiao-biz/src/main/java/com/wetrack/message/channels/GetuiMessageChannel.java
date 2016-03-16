package com.wetrack.message.channels;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerDeviceRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.message.*;
import com.wetrack.message.messages.GetuiMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by zhanghong on 16/3/1.
 */
@Service
public class GetuiMessageChannel extends AbstractMessageChannel {
    private Logger LOGGER = LoggerFactory.getLogger(GetuiMessageChannel.class);


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
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
                message.setTitle("有新的任务分配给你");
                message.setContent(String.format("任务号是%d,点击查看详情", params.get(MessageParamKey.MISSION_ID)));
                // 任务id，
                Map<String, Object> map = new HashMap<>();
                map.put(MessageParamKey.MISSION_ID, params.get(MessageParamKey.MISSION_ID));
                map.put("type", "mission");
                message.setData(map);
                return message;
            }
        });
        registerAdapter(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
                message.setTitle("有新的维修单分配给你");
                message.setContent(String.format("维修单号是%d,点击查看详情", params.get(MessageParamKey.REPAIR_ORDER_ID)));
                // 任务id，
                Map<String, Object> map = new HashMap<>();
                map.put(MessageParamKey.MISSION_ID, params.get(MessageParamKey.MISSION_ID));
                map.put(MessageParamKey.REPAIR_ORDER_ID, params.get(MessageParamKey.REPAIR_ORDER_ID));
                map.put("type", "mission");
                message.setData(map);
                return message;
            }
        });

        MessageAdapter auditResultMessageAdapter = new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
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
                Map<String, Object> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.FIXER_ID));
                map.put("type", "certificate");
                map.put("auditType", messageId%10);
                map.put("auditInfo", auditInfo);
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


        registerAdapter(MessageId.KEFU_NOTIFY_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
                message.setTitle("你有新的客服消息");
                message.setContent("你有新的客服消息，请点击查看");
                // 任务id，
                Map<String, Object> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.FIXER_ID));
                map.put("type", "message");
                message.setData(map);
                return message;
            }
        });
        registerAdapter(MessageId.COMMENT_REPAIR_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
                message.setTitle("您的维修服务已经被评价");
                message.setContent(String.format("你的维修单%d获得了%d星评价，请点击查看",
                        params.get(MessageParamKey.REPAIR_ORDER_ID), params.get(MessageParamKey.REPAIR_ORDER_COMMENT_RATE)));
                // 任务id，
                Map<String, Object> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.FIXER_ID));
                map.put("type", "comment");
                map.put(MessageParamKey.REPAIR_ORDER_COMMENT_ID, params.get(MessageParamKey.REPAIR_ORDER_COMMENT_ID));
                message.setData(map);
                return message;
            }
        });
    }


    @Resource
    private GetuiPush getuiPush;

    @Override
    protected void doSend(Message message) {
        GetuiMessage getui = (GetuiMessage)message;
        LOGGER.info("个推发送消息，消息内容为:{}", Jackson.base().writeValueAsString(getui));
        boolean success = getuiPush
                .pushNotification(getui.getReceiver(), getui.getTitle(), getui.getContent(),
                        Jackson.mobile().writeValueAsString(getui.getData()));
    }
}
