package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.statistics.StatsCount;
import com.wetrack.ikongtiao.param.StatsQueryParam;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by zhanghong on 15/12/28.
 */
@Controller
@RequestMapping(value = "/stats")
public class StatisticsController {

    @Autowired
    MissionService missionService;

    @Autowired
    RepairOrderService repairOrderService;

    @ResponseBody
    @RequestMapping(value = "/mission" , method = {RequestMethod.POST})
    public List<StatsCount> statsMission(@RequestBody StatsQueryParam queryParam) {
        if(queryParam.getFinished() != null){
            if(queryParam.getFinished() == false){
                queryParam.setStates(new Integer[]{MissionState.NEW.getCode(), MissionState.ACCEPT.getCode(),
                        MissionState.DISPATCHED.getCode(), MissionState.FIXING.getCode()});
            }else{
                queryParam.setStates(new Integer[]{MissionState.COMPLETED.getCode()});
            }
        }

        return missionService.statsMission(queryParam);
    }
    @ResponseBody
    @RequestMapping(value = "/repairOrder" , method = {RequestMethod.POST})
    public List<StatsCount> statsRepairOrder(@RequestBody StatsQueryParam queryParam) {
        if(queryParam.getFinished() != null){
            if(queryParam.getFinished() == false){
                queryParam.setStates(new Integer[]{RepairOrderState.NEW.getCode().intValue(), RepairOrderState.AUDIT_READY.getCode().intValue(),
                        RepairOrderState.CONFIRMED.getCode().intValue(), RepairOrderState.COST_READY.getCode().intValue(),
                        RepairOrderState.PREPARED.getCode().intValue(), RepairOrderState.FIXING.getCode().intValue()});
            }else{
                queryParam.setStates(new Integer[]{RepairOrderState.COMPLETED.getCode().intValue()});
            }
        }

        return repairOrderService.statsRepairOrder(queryParam);
    }


}
