package com.wetrack.ikongtiao.notification.services;

import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.repo.jpa.MissionJpaRepo;
import com.wetrack.ikongtiao.repo.jpa.RepairOrderJpaRepo;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.ikongtiao.service.api.monitor.TaskMonitorService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhanghong on 16/4/11.
 */
@Service
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
                    notifyMission(taskId.substring(Constants.TASK_MISSION.length()));
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

    /**
     * 自动完成一定时间内无人处理没有未完成维修单的任务
     */
    @Autowired
    MissionJpaRepo missionJpaRepo;
    @Autowired
    RepairOrderJpaRepo repairOrderJpaRepo;
    @Override
    public void finishUnattendedMissions(){
            //找出所有可能的备选mission列表
            List<Mission> missionList = missionJpaRepo.findByMissionState(MissionState.FIXING.getCode());
            List<Integer> missionIds = new ArrayList<>();
            if(missionList == null || missionList.size() == 0){
                return;
            }

            for(Mission mission : missionList){
                missionIds.add(mission.getId());
            }
            List<Byte> closedStates = Arrays.asList(RepairOrderState.CLOSED.getCode(), RepairOrderState.COMPLETED.getCode());
            //select * from repair_order where mission_id in {#list} and repair_order_state!=6 and repair_order_state!=-1 group by mission_id
            //排除这些有维修单未完成而且未关闭的mission
            List<Integer> notFinishedMissionIds = repairOrderJpaRepo.findNonFinished(missionIds, closedStates);
            for(Integer missionId : notFinishedMissionIds){
                missionIds.remove(missionId);
            }
            //对剩余mission，获取其所有的维修单，并按照时间排序
            //select * from repair_order where mission_id in #{list2} and repair_order_state=6 order by update_time desc
            List<RepairOrder> repairOrders = repairOrderJpaRepo.findByMissionIdInAndRepairOrderState(
                    missionIds, RepairOrderState.COMPLETED.getCode(), new Sort(new Sort.Order(Sort.Direction.DESC, "updateTime")));
            //建立一个hashmap，为每一个missionId放入它的最后一个完成的维修单
            Map<Integer, RepairOrder> map = new HashMap<>();
            for (RepairOrder repairOrder : repairOrders) {
                if(map.get(repairOrder.getMissionId()) == null){
                    map.put(repairOrder.getMissionId(), repairOrder);
                }
            }

            List<Integer> finishedIds = new ArrayList<>();
            Date sevenDaysAgo = new DateTime().plusDays(7).withHourOfDay(23).withMinuteOfHour(59).toDate();
            //遍历hashmap，判断每个missionId对应的维修单是否在7天前完成，是则放入待更新队列
            for(RepairOrder repairOrder : map.values()){
                if(repairOrder.getUpdateTime().before(sevenDaysAgo)){
                    finishedIds.add(repairOrder.getMissionId());
                }
            }
            //保存更新队列，发送消息
            for(Integer missionId : finishedIds){
                try {
                    missionService.finishMission(missionId.toString());
                } catch (Exception e) {
                    log.error("can't update mission {} to finish state, {}", missionId, e.getMessage());
                    e.printStackTrace();
                }
            }
    }

    @Autowired
    @Qualifier("defaultMessageService")
    MessageMultiChannelService defaultMessageService;


    @Autowired
    RepairOrderService repairOrderService;
    private void notifyRepairOrder(Long repairOrderId) throws Exception{
        RepairOrder repairOrder = repairOrderService.getById(String.valueOf(repairOrderId), true);
        Map<String, Object> params = new HashMap<String, Object>();
        switch (RepairOrderState.fromCode(repairOrder.getRepairOrderState())){
            case NEW:
                params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
                params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
                params.put(MessageParamKey.FIXER_ID, repairOrder.getFixerId());
                params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrder.getId());
                params.put(MessageParamKey.REPAIR_ORDER_SID, repairOrder.getSerialNumber());
                params.put(MessageParamKey.ADMIN_ID, repairOrder.getAdminUserId());
                params.put(MessageParamKey.REPEAT, true);
                defaultMessageService.send(MessageId.NEW_FIX_ORDER, params);
                break;
            case COST_READY:
                params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
                params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
                params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrder.getId());
                params.put(MessageParamKey.REPAIR_ORDER_SID, repairOrder.getSerialNumber());
                params.put(MessageParamKey.ADMIN_ID, repairOrder.getAdminUserId());
                params.put(MessageParamKey.REPEAT, true);
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

    private void notifyMission(String missionId) throws Exception{
        Mission mission = missionService.getMission(missionId);
        Map<String, Object> params = new HashMap<String, Object>();
        switch (MissionState.fromCode(mission.getMissionState())){
            case NEW:
                params.put(MessageParamKey.MISSION_ID, mission.getId());
                params.put(MessageParamKey.MISSION_SID, mission.getSerialNumber());
                params.put(MessageParamKey.USER_ID, mission.getUserId());
                params.put(MessageParamKey.REPEAT, true);
                defaultMessageService.send(MessageId.NEW_COMMISSION, params);
                break;
            default:
                //无需通知，移除该消息
                taskMonitorService.deleteTask(Constants.TASK_MISSION + missionId);
                break;
        }
    }



}
