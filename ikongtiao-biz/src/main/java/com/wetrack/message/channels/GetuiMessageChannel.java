package com.wetrack.message.channels;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.repo.api.fixer.FixerDeviceRepo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
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
public class GetuiMessageChannel extends MessageChannel {
    private Logger LOGGER = LoggerFactory.getLogger(GetuiMessageChannel.class);


    @Autowired
    FixerRepo fixerRepo;

    @Autowired
    FixerDeviceRepo fixerDeviceRepo;

    GetuiMessageChannel(){
        registerAssembler(MessageId.ASSIGNED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                Fixer fixer = fixerRepo.getFixerById((Integer)params.get(MessageParamKey.FIXER_ID));
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer)params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
                message.setTitle("有新的任务分配给你");
                message.setContent(String.format("任务地址是%s,点击查看详情", fixer.getAddress()));
                // 任务id，
                Map<String, Object> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.MISSION_ID));
                map.put("type", "mission");
                message.setData(map);
                return message;
            }
        });
        registerAssembler(MessageId.ASSIGNED_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                Fixer fixer = fixerRepo.getFixerById((Integer)params.get(MessageParamKey.FIXER_ID));
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer)params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
                message.setTitle("有新的维修单分配给你");
                message.setContent(String.format("地址是%s,点击查看详情", fixer.getAddress()));
                // 任务id，
                Map<String, Object> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.MISSION_ID));
                map.put("type", "mission");
                message.setData(map);
                return message;
            }
        });
        registerAssembler(MessageId.FIXER_SUCCESS_AUDIT, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer)params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
                message.setTitle("已完成审核");
                message.setContent("你的资料已经完成审核了");
                // 任务id，
                Map<String, Object> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.FIXER_ID));
                map.put("type", "certificate");
                message.setData(map);
                return message;
            }
        });
        registerAssembler(MessageId.FIXER_FAILED_AUDIT, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer)params.get(MessageParamKey.FIXER_ID));
                message.setReceiver(fixerDevice.getClientId());
                message.setTitle("认证失败");
                message.setContent("你的实名认证被驳回");
                // 任务id，
                Map<String, Object> map = new HashMap<>();
                map.put("id", params.get(MessageParamKey.FIXER_ID));
                map.put("type", "certificate");
                message.setData(map);
                return message;
            }
        });
        registerAssembler(MessageId.KEFU_NOTIFY_FIXER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                GetuiMessage message = new GetuiMessage();
                FixerDevice fixerDevice = fixerDeviceRepo.getFixerDeviceByFixerId((Integer)params.get(MessageParamKey.FIXER_ID));
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
