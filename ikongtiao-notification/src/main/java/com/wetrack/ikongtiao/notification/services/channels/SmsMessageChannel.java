package com.wetrack.ikongtiao.notification.services.channels;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.OperatorType;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.notification.services.AbstractMessageChannel;
import com.wetrack.ikongtiao.notification.services.Message;
import com.wetrack.ikongtiao.notification.services.MessageAdapter;
import com.wetrack.ikongtiao.notification.services.messages.SmsMessage;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.jpa.UserInfoRepo;
import com.wetrack.ikongtiao.sms.util.SendWeSms;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
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
    private Logger log = LoggerFactory.getLogger(SmsMessageChannel.class);

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    FixerRepo fixerRepo;

    SmsMessageChannel(){


        //用户消息
        registerAdapter(MessageId.NEW_COMMISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                //重复提醒不发送给用户
                if(params.get(MessageParamKey.REPEAT) != null && (Boolean)params.get(MessageParamKey.REPEAT) == true){
                    return null;
                }
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务已经创建成功，任务号%s。查看详细信息，请进入微信公众号［维大师之爱空调］", params.get(MessageParamKey.MISSION_SID)));
                return message;
            }
        });
        registerAdapter(MessageId.ACCEPT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务已经被受理了，任务号%s。查看详细信息，请进入微信公众号［维大师之爱空调］", params.get(MessageParamKey.MISSION_SID)));
                return message;
            }
        });
        registerAdapter(MessageId.REJECT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务已经被拒绝了,任务号%s。查看详细信息,请进入微信公众号［维大师之爱空调］", params.get(MessageParamKey.MISSION_SID)));
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
                message.setText(String.format("你的任务%s被分配给维修员%s请等待维修员与你联系,查看详细信息,请进入微信公众号［维大师之爱空调］"
                        , params.get(MessageParamKey.MISSION_SID), fixer.getName()));
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
                message.setText(String.format("你的维修单%s被分配给维修员%s请等待维修员与你联系,查看详细信息,请进入微信公众号［维大师之爱空调］"
                        , params.get(MessageParamKey.REPAIR_ORDER_SID), fixer.getName()));
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
                message.setText(String.format("你的任务%s已经由维修员%s完成了,查看详细信息,请进入微信公众号［维大师之爱空调］"
                        , params.get(MessageParamKey.REPAIR_ORDER_SID), fixer.getName()));
                return message;
            }
        });

        registerAdapter(MessageId.WAITING_CONFIRM_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的维修单%s已经生成价格，请确认,查看详细信息,请进入微信公众号［维大师之爱空调］", params.get(MessageParamKey.REPAIR_ORDER_SID)));
                return message;
            }
        });
        registerAdapter(MessageId.COMPLETED_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的维修单%s已经完成了,查看详细信息,请进入微信公众号［维大师之爱空调］", params.get(MessageParamKey.REPAIR_ORDER_SID)));
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
                message.setText(String.format("有新的任务分配给你, 任务号%s,请打开维大师app查看详情", params.get(MessageParamKey.MISSION_SID)));
                return message;
            }
        });
        registerAdapter(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixer.getPhone());
                message.setText(String.format("有新的维修单分配给你,维修单号%s,请打开维大师app查看详情", params.get(MessageParamKey.REPAIR_ORDER_SID)));
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
                message.setText(String.format("你的维修单%s获得了%s星评价，请打开维大师app查看详情",
                        params.get(MessageParamKey.REPAIR_ORDER_SID), params.get(MessageParamKey.REPAIR_ORDER_COMMENT_RATE)));
                return message;
            }
        });
        registerAdapter(MessageId.FIXER_INITIAL_PASSWORD, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                message.setId(messageId);
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixer.getPhone());
                message.setText(String.format("您在［维大师］的账号已经创建，手机号码是您的账号，密码为%s, 登录后请修改",
                        params.get(MessageParamKey.PASSWORD)));
                return message;
            }
        });



        registerAdapter(MessageId.SERVICE_LOG_NOTIFY, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                OperatorType targetUserType = OperatorType.valueOf(params.get(MessageParamKey.OPERATOR_TYPE).toString());
                if(targetUserType != OperatorType.FIXER){
                    return null;
                }
                SmsMessage message = new SmsMessage();
                message.setId(MessageId.SERVICE_LOG_NOTIFY);
                Integer fixerId = Integer.valueOf(params.get(MessageParamKey.OPERATOR_ID).toString());
                Fixer fixer = fixerRepo.getFixerById(fixerId);
                message.setReceiver(fixer.getPhone());

                String missionId = params.get(MessageParamKey.MISSION_SID).toString();
                String repairOrderId = null;
                if(params.get(MessageParamKey.REPAIR_ORDER_SID) != null){
                    repairOrderId = params.get(MessageParamKey.REPAIR_ORDER_SID).toString();
                }
                String typeText = repairOrderId != null ? "维修单" : "任务";
                message.setText(typeText + (repairOrderId == null ? missionId : repairOrderId) + "今日服务进度需要填写");

                return message;

            }
        });
    }

    @Override
    protected void doSend(Message message) {
        SmsMessage sms = (SmsMessage)message;
        boolean success = SendWeSms.send(sms.getReceiver(), sms.getText());
    }

    @Override
    public String getName() {
        return "sms";
    }
}
