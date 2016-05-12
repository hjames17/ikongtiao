package com.wetrack.ikongtiao.repo.api.repairOrder;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.param.RepairOrderQueryParam;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface RepairOrderRepo {
    List<RepairOrder> listForMission(Integer missionId) throws Exception;

    List<RepairOrder> listByQueryParam(RepairOrderQueryParam param);
    int countByParam(RepairOrderQueryParam param);

    RepairOrder create(RepairOrder repairOrder) throws Exception;

    void update(RepairOrder repairOrder) throws Exception;

    RepairOrder getById(Long id);
}
