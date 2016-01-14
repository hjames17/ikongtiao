package com.wetrack.ikongtiao.service.api;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface RepairOrderService {
    List<RepairOrder> listForMission(Integer missionId) throws Exception;

    RepairOrder create(Integer creatorId, Integer missionId, String namePlateImg, String makeOrderNum, String repairOrderDesc, String accessoryContent) throws Exception;

    void addCost(Long repairOrderId, List<Accessory> accessoryList, Float laborCost) throws Exception;

    void dispatchRepairOrder(Integer adminUserId, Long repairOrderId, Integer fixerId) throws Exception;

    void setCostFinished(Integer adminUserId, Long repairOrderId) throws Exception;
    void setPrepared(Integer adminUserId, Long repairOrderId) throws Exception;

    void setFinished(Long repairOrderId) throws Exception;
    RepairOrder getById(Long id) throws Exception;

    void comment(Long repairOrderId, Integer rate, String comment) throws Exception;

    Accessory createAccessory(Long repairOrderId, String name, Integer count, Float price) throws Exception;

    boolean updateAccessory(Accessory accessory) throws Exception;

    boolean deleteAccessory(Long accessoryId) throws Exception;
}
