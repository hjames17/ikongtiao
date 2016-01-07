package com.wetrack.wechat.controller;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


/**
 * Created by zhanghong on 16/1/4.
 */

@Controller
public class RepairOrderController {

    static final String BASE_PATH = "/repairOrder";

    @Autowired
    MissionService missionService;

    @Autowired
    RepairOrderService repairOrderService;


    @RequestMapping(value = BASE_PATH + "/comment" , method = {RequestMethod.POST})
    public void create(@RequestBody Comment comment) throws Exception{
        return repairOrderService.comment(comment.getRepairOrderId(), comment.getRate(), comment.getComment());
    }

}
