package com.wetrack.message.channels;

import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.domain.admin.Role;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.message.*;
import com.wetrack.message.messages.WebNotificationMessage;
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
public class WebNotificationMessageChannel extends AbstractMessageChannel {
    private Logger LOGGER = LoggerFactory.getLogger(WebNotificationMessageChannel.class);

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    FixerRepo fixerRepo;

    @Autowired
    MissionRepo missionRepo;

    @Autowired
    RepairOrderRepo repairOrderRepo;

    WebNotificationMessageChannel(){
        registerAdapter(MessageId.NEW_COMMISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WebNotificationMessage message = new WebNotificationMessage();
                Mission mission = missionRepo.getMissionById((Integer) params.get(MessageParamKey.MISSION_ID));
                message.setId(messageId);
                message.setType(WebNotificationMessage.RECEIVER_TYPE_ROLE);
                message.setReceiver(Role.EDIT_MISSION.toString());
                message.setTitle("有新任务啦");
                message.setContent("有新的任务，请赶快处理");
                message.setData(mission);
                return message;
            }
        });
        registerAdapter(MessageId.COMPLETED_MISSION, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WebNotificationMessage message = new WebNotificationMessage();
                Mission mission = missionRepo.getMissionById((Integer) params.get(MessageParamKey.MISSION_ID));
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                message.setId(messageId);
                message.setReceiver(mission.getAdminUserId().toString());
                message.setType(WebNotificationMessage.RECEIVER_TYPE_ID);
                message.setTitle("任务已完成");
                message.setContent(String.format("由维修员%s维修的任务已经完成了", fixer.getName()));
                message.setData(mission);
                return message;
            }
        });
        registerAdapter(MessageId.NEW_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WebNotificationMessage message = new WebNotificationMessage();
                Fixer fixer = fixerRepo.getFixerById((Integer) params.get(MessageParamKey.FIXER_ID));
                RepairOrder repairOrder = null;
                try {
                    repairOrder = repairOrderRepo.getById((Long) params.get(MessageParamKey.REPAIR_ORDER_ID));
                } catch (Exception e) {
                    LOGGER.error("repair order not exist, id %d", (Long) params.get(MessageParamKey.REPAIR_ORDER_ID));
                    return null;
                }
                message.setId(messageId);
                message.setReceiver(repairOrder.getAdminUserId().toString());
                message.setType(WebNotificationMessage.RECEIVER_TYPE_ID);
                message.setTitle("有新的维修单提交");
                message.setContent(String.format("维修员%s提交了新的维修单", fixer.getName()));
                message.setData(repairOrder);
                return message;
            }
        });
        registerAdapter(MessageId.CONFIRM_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WebNotificationMessage message = new WebNotificationMessage();
                RepairOrder repairOrder = null;
                try {
                    repairOrder = repairOrderRepo.getById((Long) params.get(MessageParamKey.REPAIR_ORDER_ID));
                } catch (Exception e) {
                    LOGGER.error("repair order not exist, id %d", (Long) params.get(MessageParamKey.REPAIR_ORDER_ID));
                    return null;
                }
                message.setId(messageId);
                message.setReceiver(repairOrder.getAdminUserId().toString());
                message.setType(WebNotificationMessage.RECEIVER_TYPE_ID);
                message.setTitle("有维修单被确认了");
                message.setContent(String.format("维修单%d已被客户确认", repairOrder.getId()));
                message.setData(repairOrder);
                return message;
            }
        });

        registerAdapter(MessageId.CANCEL_FIX_ORDER, new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WebNotificationMessage message = new WebNotificationMessage();
                UserInfo userInfo = userInfoRepo.getById((String) params.get(MessageParamKey.USER_ID));
                RepairOrder repairOrder = null;
                try {
                    repairOrder = repairOrderRepo.getById((Long) params.get(MessageParamKey.REPAIR_ORDER_ID));
                } catch (Exception e) {
                    LOGGER.error("repair order not exist, id %d", (Long) params.get(MessageParamKey.REPAIR_ORDER_ID));
                    return null;
                }
                message.setId(messageId);
                message.setReceiver(repairOrder.getAdminUserId().toString());
                message.setType(WebNotificationMessage.RECEIVER_TYPE_ID);
                message.setTitle("有维修单被取消了");
                message.setContent(String.format("维修单%d被用户%s取消了", repairOrder.getId(), userInfo.getPhone()));
                message.setData(repairOrder);
                return message;
            }
        });

        MessageAdapter fixerAuditMessageAdapter = new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {
                WebNotificationMessage message = new WebNotificationMessage();
                message.setId(messageId);
                message.setReceiver(Role.AUDIT_FIXER.toString());
                message.setType(WebNotificationMessage.RECEIVER_TYPE_ROLE);
                String auditText;
                switch (messageId){
                    case MessageId.FIXER_SUBMIT_CERT_AUDIT:
                        auditText = "实名";
                        break;
                    case MessageId.FIXER_SUBMIT_INSURANCE_AUDIT:
                        auditText = "保险";
                        break;
                    case MessageId.FIXER_SUBMIT_PROFESS_AUDIT:
                        auditText = "技能";
                        break;
                    default:
                        auditText = "";
                        break;
                }
                message.setTitle(String.format("维修员%s认证申请", auditText));
                message.setContent(String.format("维修员%d提交了实名认证申请", params.get(MessageParamKey.FIXER_ID)));
                message.setData(params.get(MessageParamKey.FIXER_AUDIT_INFO));
                return message;
            }
        };

        registerAdapter(MessageId.FIXER_SUBMIT_CERT_AUDIT, fixerAuditMessageAdapter);
        registerAdapter(MessageId.FIXER_SUBMIT_INSURANCE_AUDIT, fixerAuditMessageAdapter);
        registerAdapter(MessageId.FIXER_SUBMIT_PROFESS_AUDIT, fixerAuditMessageAdapter);
    }

    @Value("${host.admin}")
    private String hostAdmin;

    @Override
    protected void doSend(Message message) {
        WebNotificationMessage webMessage = (WebNotificationMessage)message;
        LOGGER.info("websocketPush,发送给:{};对应的消息内容为:{}", webMessage.getReceiver(), webMessage.getData());
        String result = Utils.get(HttpExecutor.class).post(hostAdmin + "/admin/socket/push")
                .addFormParam("messageTo", webMessage.getReceiver())
                .addFormParam("type", String.valueOf(webMessage.getType()))
                .addFormParam("message", Jackson.mobile().writeValueAsString(message)).executeAsString();
        LOGGER.info("websocketPush,发送给{},内容:{},结果:{}", webMessage.getReceiver(), message, result);
    }
}
