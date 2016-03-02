package com.wetrack.message.channels;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.UserInfo;
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
public class SmsMessageChannel extends MessageChannel {
    private Logger LOGGER = LoggerFactory.getLogger(SmsMessageChannel.class);

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    FixerRepo fixerRepo;

    SmsMessageChannel(){
        registerAssembler(MessageId.ACCEPT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务已经被受理了，任务号%d。查看详细信息，请进入微信公众号［快修点点］", params.get(MessageParamKey.MISSION_ID)));
                return message;
            }
        });
        registerAssembler(MessageId.REJECT_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务已经被拒绝了,任务号%d。查看详细信息,请进入微信公众号［快修点点］", params.get(MessageParamKey.MISSION_ID)));
                return message;
            }
        });
        registerAssembler(MessageId.ASSIGNED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务%d被分配给维修员%s请等待维修员与你联系,查看详细信息,请进入微信公众号［快修点点］"
                        ,params.get(MessageParamKey.MISSION_ID), fixer.getName()));
                return message;
            }
        });
        registerAssembler(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer)params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的维修单%d被分配给维修员%s请等待维修员与你联系,查看详细信息,请进入微信公众号［快修点点］"
                        , params.get(MessageParamKey.REPAIR_ORDER_ID) ,fixer.getName()));
                return message;
            }
        });
        registerAssembler(MessageId.COMPLETED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的任务%d已经由维修员%s完成了,查看详细信息,请进入微信公众号［快修点点］"
                        , params.get(MessageParamKey.REPAIR_ORDER_ID) ,fixer.getName()));
                return message;
            }
        });

        registerAssembler(MessageId.WAITING_CONFIRM_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的维修单%d已经生成价格，请确认,查看详细信息,请进入微信公众号［快修点点］", params.get(MessageParamKey.REPAIR_ORDER_ID)));
                return message;
            }
        });
        registerAssembler(MessageId.COMPLETED_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                SmsMessage message = new SmsMessage();
                UserInfo userInfo = userInfoRepo.getById((String)params.get(MessageParamKey.USER_ID));
                message.setReceiver(userInfo.getPhone());
                message.setText(String.format("你的维修单%d已经完成了,查看详细信息,请进入微信公众号［快修点点］", params.get(MessageParamKey.REPAIR_ORDER_ID)));
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
