package com.wetrack.ikongtiao.notification.services;

import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.ikongtiao.service.api.monitor.TaskMonitorService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 16/4/11.
 */
//@Service
public class NotificationServiceImpl implements NotificationService {

    private static Logger log = LoggerFactory.getLogger(NotificationServiceImpl.class);

    public static final int ONE_MINUTE = 60*1000;

    @Autowired
    TaskMonitorService taskMonitorService;
    /**
     * 每隔五分钟测试队列，发送放入时间已经超过30分钟的消息
     */
    @Override
    @Scheduled(cron = "0 0/5 6-23  * * ? ")
    public void NotificationOntime() {
        Collection<String> tasks = taskMonitorService.getTaskIds();
        Long currentTime = System.currentTimeMillis();
        for(String taskId : tasks){
            try {
                Long pastTime = currentTime - taskMonitorService.getTaskLiveTime(taskId);
                if(pastTime <= 30*ONE_MINUTE){
                    continue;
                }
                /**
                 * TODO :改单条处理为批量处理，减少数据库查询次数
                 */
                if (taskId.startsWith(Constants.TASK_MISSION)) {
                    Integer missionId = Integer.valueOf(taskId.substring(Constants.TASK_MISSION.length()));
                    notifyMission(missionId);
                } else if (taskId.startsWith(Constants.TASK_REPAIR_ORDER)) {
                    Long repairOrderId = Long.valueOf(taskId.substring(Constants.TASK_REPAIR_ORDER.length()));
                    notifyRepairOrder(repairOrderId);
                }
            }catch (Exception e){
                log.error(String.format("notification ontime error, taskId is %s, message: %s", taskId, e.getMessage()));
                try {
                    taskMonitorService.deleteTask(taskId);
                }catch (Exception e1){
                    log.error(String.format("notification remove on error failed, taskId is %s, message: %s", taskId, e1.getMessage()));

                }
            }
        }
    }

    @Autowired
    @Qualifier("defaultMessageService")
    MessageMultiChannelService defaultMessageService;


    @Autowired
    RepairOrderService repairOrderService;
    private void notifyRepairOrder(Long repairOrderId) throws Exception{
        RepairOrder repairOrder = repairOrderService.getById(repairOrderId, true);
        Map<String, Object> params = new HashMap<String, Object>();
        switch (RepairOrderState.fromCode(repairOrder.getRepairOrderState())){
            case NEW:
                params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
                params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
                params.put(MessageParamKey.FIXER_ID, repairOrder.getFixerId());
                params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrder.getId());
                params.put(MessageParamKey.ADMIN_ID, repairOrder.getAdminUserId());
                defaultMessageService.send(MessageId.NEW_FIX_ORDER, params);
                break;
            case COST_READY:
                params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
                params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
                params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrder.getId());
                params.put(MessageParamKey.ADMIN_ID, repairOrder.getAdminUserId());
                defaultMessageService.send(MessageId.WAITING_AUDIT_REPAIR_ORDER, params);
                break;
            default:
                //无需通知，移除该消息
                taskMonitorService.deleteTask(Constants.TASK_REPAIR_ORDER + repairOrderId);
                break;

        }
    }

    @Autowired
    MissionService missionService;

    private void notifyMission(Integer missionId) throws Exception{
        Mission mission = missionService.getMission(missionId);
        Map<String, Object> params = new HashMap<String, Object>();
        switch (MissionState.fromCode(mission.getMissionState())){
            case NEW:
                params.put(MessageParamKey.MISSION_ID, mission.getId());
                params.put(MessageParamKey.USER_ID, mission.getUserId());
                defaultMessageService.send(MessageId.NEW_COMMISSION, params);
                break;
            default:
                //无需通知，移除该消息
                taskMonitorService.deleteTask(Constants.TASK_MISSION + missionId);
                break;
        }
    }



}
