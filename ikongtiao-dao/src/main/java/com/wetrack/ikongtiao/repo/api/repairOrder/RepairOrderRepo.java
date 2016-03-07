package com.wetrack.ikongtiao.repo.api.repairOrder;

import com.wetrack.ikongtiao.domain.RepairOrder;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface RepairOrderRepo {
    List<RepairOrder> listForMission(Integer missionId) throws Exception;

    RepairOrder create(RepairOrder repairOrder) throws Exception;

    void update(RepairOrder repairOrder) throws Exception;

    RepairOrder getById(Long id);
}
