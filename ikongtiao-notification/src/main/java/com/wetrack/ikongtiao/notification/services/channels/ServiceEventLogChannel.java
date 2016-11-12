package com.wetrack.ikongtiao.notification.services.channels;

import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.AccountType;
import com.wetrack.ikongtiao.events.*;
import com.wetrack.ikongtiao.notification.services.AbstractMessageChannel;
import com.wetrack.ikongtiao.notification.services.Message;
import com.wetrack.ikongtiao.notification.services.MessageAdapter;
import com.wetrack.ikongtiao.notification.services.messages.ServiceLogMessage;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

/**
 * Created by zhanghong on 16/9/17.
 */
@Service
public class ServiceEventLogChannel extends AbstractMessageChannel {

    @Autowired
    MissionEventHandler missionEventHandler;

    @Autowired
    RepairOrderEventHandler repairOrderEventHandler;

    ServiceEventLogChannel(){

        MessageAdapter missionEventAdpater = new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {

                MissionEvent event = new MissionEvent();
                event.setOperatorId(params.get(MessageParamKey.OPERATOR_ID).toString());
                event.setOperatorType(AccountType.valueOf(params.get(MessageParamKey.OPERATOR_TYPE).toString()));
                event.setMissionId(params.get(MessageParamKey.MISSION_SID).toString());
                event.setState(MissionState.fromCode(Integer.valueOf(params.get(MessageParamKey.MISSION_STATE).toString())));
                event.setTime(new Date(Long.valueOf(params.get(MessageParamKey.TIME).toString())));

                ServiceLogMessage message = new ServiceLogMessage();
                message.setId(messageId);
                message.setEvent(event);

                return message;
            }
        };

        registerAdapter(MessageId.NEW_COMMISSION, missionEventAdpater);
        registerAdapter(MessageId.REJECT_MISSION, missionEventAdpater);
        registerAdapter(MessageId.COMPLETED_MISSION, missionEventAdpater);
        registerAdapter(MessageId.ASSIGNED_MISSION, missionEventAdpater);
        registerAdapter(MessageId.ACCEPT_MISSION, missionEventAdpater);

        MessageAdapter repairOrderAdpater = new MessageAdapter() {
            @Override
            public Message build(int messageId, Map<String, Object> params) {


                RepairOrderEvent event = new RepairOrderEvent();
                event.setState(RepairOrderState.fromCode(Byte.valueOf(params.get(MessageParamKey.REPAIR_ORDER_STATE).toString())));
                event.setRepairOrderId(params.get(MessageParamKey.REPAIR_ORDER_SID).toString());
                event.setMissionId(params.get(MessageParamKey.MISSION_SID).toString());
                event.setOperatorId(params.get(MessageParamKey.OPERATOR_ID).toString());
                event.setOperatorType(AccountType.valueOf(params.get(MessageParamKey.OPERATOR_TYPE).toString()));
                event.setTime(new Date(Long.valueOf(params.get(MessageParamKey.TIME).toString())));

                ServiceLogMessage message = new ServiceLogMessage();
                message.setId(messageId);
                message.setEvent(event);

                return message;
            }
        };

        registerAdapter(MessageId.NEW_FIX_ORDER, repairOrderAdpater);
        registerAdapter(MessageId.WAITING_AUDIT_REPAIR_ORDER, repairOrderAdpater);
        registerAdapter(MessageId.WAITING_CONFIRM_FIX_ORDER, repairOrderAdpater);
        registerAdapter(MessageId.CONFIRM_FIX_ORDER, repairOrderAdpater);
        registerAdapter(MessageId.CANCEL_FIX_ORDER, repairOrderAdpater);
        registerAdapter(MessageId.ASSIGNED_FIXER, repairOrderAdpater);
        registerAdapter(MessageId.COMPLETED_FIX_ORDER, repairOrderAdpater);
        registerAdapter(MessageId.COMMENT_REPAIR_ORDER, repairOrderAdpater);
    }

    @Override
    protected void doSend(Message message) {
        Event event = ((ServiceLogMessage)message).getEvent();
        if(event instanceof MissionEvent) { //mission event
            missionEventHandler.handle(event);
        }else if(event instanceof RepairOrderEvent){//repair order event
            repairOrderEventHandler.handle(event);
        }
    }

    @Override
    public String getName() {
        return "service event log channel";
    }
}
