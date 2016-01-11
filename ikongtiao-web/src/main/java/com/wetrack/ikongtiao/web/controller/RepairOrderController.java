package com.wetrack.ikongtiao.web.controller;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by zhanghong on 16/1/4.
 */

@ResponseBody
@Controller
public class RepairOrderController {

    static final String BASE_PATH = "/repairOrder";

    @Autowired
    MissionService missionService;

    @Autowired
    RepairOrderService repairOrderService;


    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST})
    public String create(@RequestBody CreateForm form) throws Exception{

        RepairOrder repairOrder = repairOrderService.create(form.getMissionId(), form.getNamePlateImg(), form.getMakeOrderNum(), form.getRepairOrderDesc(), form.getAccessoryContent());
        return repairOrder.getId().toString();
    }

    @RequestMapping(value = BASE_PATH + "/listOfMission" , method = {RequestMethod.GET})
    public List<RepairOrder> listForMission(@RequestParam(value = "missionId") Integer missionId) throws Exception{
        return repairOrderService.listForMission(missionId);
    }

    @RequestMapping(value = BASE_PATH , method = {RequestMethod.GET})
    public RepairOrder get(@RequestParam(value = "id") Long id) throws Exception{
        return repairOrderService.getById(id);
    }

    @RequestMapping(value = BASE_PATH + "/finish", method = {RequestMethod.POST})
    public void setFinished(@RequestParam(value = "id") Long id) throws Exception{
        repairOrderService.setFinished(id);
    }

    public static class OperationForm {
        Integer adminUserId;
        Long repairOrderId;
        Integer fixerId;

        public Integer getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(Integer adminUserId) {
            this.adminUserId = adminUserId;
        }

        public Long getRepairOrderId() {
            return repairOrderId;
        }

        public void setRepairOrderId(Long repairOrderId) {
            this.repairOrderId = repairOrderId;
        }

        public Integer getFixerId() {
            return fixerId;
        }

        public void setFixerId(Integer fixerId) {
            this.fixerId = fixerId;
        }
    }

    public static class CreateForm {
        Integer missionId;
        String namePlateImg;
        String makeOrderNum;
        String repairOrderDesc;
        String accessoryContent;

        public Integer getMissionId() {
            return missionId;
        }

        public void setMissionId(Integer missionId) {
            this.missionId = missionId;
        }

        public String getNamePlateImg() {
            return namePlateImg;
        }

        public void setNamePlateImg(String namePlateImg) {
            this.namePlateImg = namePlateImg;
        }

        public String getMakeOrderNum() {
            return makeOrderNum;
        }

        public void setMakeOrderNum(String makeOrderNum) {
            this.makeOrderNum = makeOrderNum;
        }

        public String getRepairOrderDesc() {
            return repairOrderDesc;
        }

        public void setRepairOrderDesc(String repairOrderDesc) {
            this.repairOrderDesc = repairOrderDesc;
        }

        public String getAccessoryContent() {
            return accessoryContent;
        }

        public void setAccessoryContent(String accessoryContent) {
            this.accessoryContent = accessoryContent;
        }
    }
}
