package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhanghong on 16/1/15.
 */
@Controller(value = "userRepairOrderController")
public class RepairOrderController {

    static final String BASE_PATH = "/u/repairOrder";

    @Autowired
    MissionService missionService;

    @Autowired
    RepairOrderService repairOrderService;

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/comment" , method = {RequestMethod.POST})
    public void create(@RequestBody Comment comment) throws Exception{
        repairOrderService.comment(comment.getRepairOrderId(), comment.getRate(), comment.getComment());
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/confirm" , method = {RequestMethod.POST})
    public String confirm(@RequestBody ConfirmationForm form) throws Exception{
        repairOrderService.confirm(form.getRepairOrderId(), form.isDeny());
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH , method = {RequestMethod.GET})
    public RepairOrder repairOrder(@RequestParam(value = "uid") String uid,
                              @RequestParam(value = "repairOrderId") Long repairOrderId) throws Exception{
        return repairOrderService.getById(repairOrderId);
    }

    static class ConfirmationForm{
        Long repairOrderId;
        boolean deny;

        public Long getRepairOrderId() {
            return repairOrderId;
        }

        public void setRepairOrderId(Long repairOrderId) {
            this.repairOrderId = repairOrderId;
        }

        public boolean isDeny() {
            return deny;
        }

        public void setDeny(boolean deny) {
            this.deny = deny;
        }
    }
}
