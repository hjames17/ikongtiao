package com.wetrack.message.channels;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.sms.util.SendWeSms;
import com.wetrack.message.*;
import com.wetrack.message.messages.SmsMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by zhanghong on 16/3/1.
 */
@Service
public class SmsMessageChannel extends AbstractMessageChannel {
    private Logger LOGGER = LoggerFactory.getLogger(SmsMessageChannel.class);

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    FixerRepo fixerRepo;

    SmsMessageChannel(){


        //用户消息
        registerAdapter(MessageId.ACCEPT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务已经被受理了，任务号%d。查看详细信息，请进入微信公众号［快修点点］", params.get(MessageParamKey.MISSION_ID)));
                return message;
            }
        });
        registerAdapter(MessageId.REJECT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务已经被拒绝了,任务号%d。查看详细信息,请进入微信公众号［快修点点］", params.get(MessageParamKey.MISSION_ID)));
                return message;
            }
        });
        registerAdapter(MessageId.ASSIGNED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务%d被分配给维修员%s请等待维修员与你联系,查看详细信息,请进入微信公众号［快修点点］"
                        , params.get(MessageParamKey.MISSION_ID), fixer.getName()));
                return message;
            }
        });
        registerAdapter(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的维修单%d被分配给维修员%s请等待维修员与你联系,查看详细信息,请进入微信公众号［快修点点］"
                        , params.get(MessageParamKey.REPAIR_ORDER_ID), fixer.getName()));
                return message;
            }
        });
        registerAdapter(MessageId.COMPLETED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务%d已经由维修员%s完成了,查看详细信息,请进入微信公众号［快修点点］"
                        , params.get(MessageParamKey.REPAIR_ORDER_ID), fixer.getName()));
                return message;
            }
        });

        registerAdapter(MessageId.WAITING_CONFIRM_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的维修单%d已经生成价格，请确认,查看详细信息,请进入微信公众号［快修点点］", params.get(MessageParamKey.REPAIR_ORDER_ID)));
                return message;
            }
        });
        registerAdapter(MessageId.COMPLETED_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的维修单%d已经完成了,查看详细信息,请进入微信公众号［快修点点］", params.get(MessageParamKey.REPAIR_ORDER_ID)));
                return message;
            }
        });


        //维修员消息
        registerAdapter(MessageId.ASSIGNED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixer.getPhone());
                message.setText(String.format("有新的任务分配给你, 任务号%s,请打开维大师app查看详情", params.get(MessageParamKey.MISSION_ID)));
                return message;
            }
        });
        registerAdapter(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixer.getPhone());
                message.setText(String.format("有新的维修单分配给你,维修单号%d,请打开维大师app查看详情", params.get(MessageParamKey.REPAIR_ORDER_ID)));
                return message;
            }
        });

        MessageAdapter auditResultMessageAdapter = new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixer.getPhone());
                String auditText , resultText, content = "请打开维大师app查看详情";
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
                        content = String.format("原因:" + certInfo.getDenyReason());
                        break;
                    case MessageId.FIXER_INSURANCE_AUDIT_PASS:
                        auditText = "保险";
                        resultText = "已通过";
                        break;
                    case MessageId.FIXER_INSURANCE_AUDIT_DENY:
                        auditText = "保险";
                        resultText = "被驳回";
                        FixerInsuranceInfo insuranceInfo = (FixerInsuranceInfo)auditInfo;
                        content = String.format("原因:" + insuranceInfo.getDenyReason());
                        break;
                    case MessageId.FIXER_PROFESS_AUDIT_PASS:
                        auditText = "技能";
                        resultText = "已通过";
                        break;
                    case MessageId.FIXER_PROFESS_AUDIT_DENY:
                        auditText = "技能";
                        resultText = "被驳回";
                        FixerProfessionInfo professionInfo = (FixerProfessionInfo)auditInfo;
                        content = String.format("原因:" + professionInfo.getDenyReason());
                        break;
                    default:
                        auditText = "";
                        resultText = "";
                        break;

                }
                message.setText(String.format("您的%s认证%s。%s", auditText, resultText, content));
                return message;
            }
        };

        registerAdapter(MessageId.FIXER_CERT_AUDIT_PASS, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_CERT_AUDIT_DENY, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_INSURANCE_AUDIT_PASS, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_INSURANCE_AUDIT_DENY, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_PROFESS_AUDIT_PASS, auditResultMessageAdapter);
        registerAdapter(MessageId.FIXER_PROFESS_AUDIT_DENY, auditResultMessageAdapter);


//        registerAdapter(MessageId.IM_NOTIFY_FIXER, new MessageAdapter() {
//            @Override
//            public Message build(int messageId, Map<String, Object> params) {
//                SmsMessage message = new SmsMessage();
//                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
//                message.setReceiver(fixer.getPhone());
//                message.setText("你有新的客服消息");
//                return message;
//            }
//        });
        registerAdapter(MessageId.COMMENT_REPAIR_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixer.getPhone());
                message.setText(String.format("你的维修单%d获得了%d星评价，请打开维大师app查看详情",
                        params.get(MessageParamKey.REPAIR_ORDER_ID), params.get(MessageParamKey.REPAIR_ORDER_COMMENT_RATE)));
                return message;
            }
        });
    }

    @Override
    protected void doSend(Message message) {
        SmsMessage sms = (SmsMessage)message;
        boolean success = SendWeSms.send(sms.getReceiver(), sms.getText());
    }
}
