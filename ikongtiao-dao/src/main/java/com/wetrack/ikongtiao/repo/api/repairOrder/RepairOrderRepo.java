package com.wetrack.ikongtiao.repo.api.repairOrder;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.dto.RepairOrderDto;
import com.wetrack.ikongtiao.param.RepairOrderQueryParam;
import com.wetrack.ikongtiao.param.StatsQueryParam;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface RepairOrderRepo {
    List<RepairOrder> listForMission(Integer missionId) throws Exception;

    List<RepairOrderDto> listByQueryParam(RepairOrderQueryParam param);
    int countByParam(RepairOrderQueryParam param);
    List<RepairOrder> listByStatsParam(StatsQueryParam param);
    int countLaborCostByStatsParam(StatsQueryParam param);
    int countByMissionId(int missionId);
    RepairOrder create(RepairOrder repairOrder) throws Exception;

    void update(RepairOrder repairOrder) throws Exception;

    RepairOrder getById(Long id);
    RepairOrder getBySid(String sid);
}
