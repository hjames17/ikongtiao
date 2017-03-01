package com.wetrack.ikongtiao.web.controller;

import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.RoImage;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import studio.wetrack.accountService.auth.domain.User;

import javax.servlet.http.HttpServletRequest;
import java.util.List;


/**
 * Created by zhanghong on 16/1/4.
 */

@ResponseBody
@Controller
public class RepairOrderController {

    static final String BASE_PATH = "/repairOrder";
    static final String COMMENT_PATH = BASE_PATH + "/comment";


    @Autowired
    MissionService missionService;

    @Autowired
    RepairOrderService repairOrderService;

    @Autowired
    FixerService fixerService;

    @SignTokenAuth
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST})
    public String create(@RequestBody CreateForm form, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        RepairOrder repairOrder;
        if(form.getQuick() != null && form.getQuick() == true) {
            repairOrder = repairOrderService.create(fixerService.getFixerIdFromTokenUser(user), form.getMissionId(),
                    form.getNamePlateImg(), form.getMakeOrderNum(), form.getRepairOrderDesc(), form.getAccessoryContent(),
                    form.getImages(), true);
        }else{
            repairOrder = repairOrderService.create(fixerService.getFixerIdFromTokenUser(user), form.getMissionId(),
                    form.getNamePlateImg(), form.getMakeOrderNum(), form.getRepairOrderDesc(), form.getAccessoryContent(),
                    form.getImages(), false);
        }
        return repairOrder.getSerialNumber();
    }

    @SignTokenAuth

    @RequestMapping(value = BASE_PATH + "/update" , method = {RequestMethod.POST})
    public void update(@RequestBody RepairOrder repairOrder, HttpServletRequest request) throws Exception{
//        User user = (User)request.getAttribute("user");
        String id = repairOrder.getSerialNumber();
        if(StringUtils.isEmpty(id)){
            id = repairOrder.getId().toString();
        }
        RepairOrder current = repairOrderService.getById(id, true);
        if(current == null){
            throw new BusinessException("维修单不存在");
        }
        if(current.getRepairOrderState().compareTo(RepairOrderState.AUDIT_READY.getCode()) >= 0){
            throw new BusinessException("维修单已经完成报价，不能修改，请联系客服!");
        }
        repairOrder.setRepairOrderState(null);//不能改，设null
        repairOrderService.update(repairOrder, current);
    }

    @SignTokenAuth
    @RequestMapping(value = BASE_PATH + "/listOfMission" , method = {RequestMethod.GET})
    public List<RepairOrder> listForMission(@RequestParam(value = "missionId") String missionId) throws Exception{
        return repairOrderService.listForMission(missionId, false);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{id}", method = {RequestMethod.GET})
    public RepairOrder repairOrder(@PathVariable(value = "id") String id) throws Exception{
        return repairOrderService.getById(id, false);
    }

    @SignTokenAuth
    @RequestMapping(value = BASE_PATH + "/finish", method = {RequestMethod.POST})
    public String setFinished(@RequestBody String id) throws Exception{
        repairOrderService.setFinished(id);
        return "ok";
    }




    public static class CreateForm {
        String missionId;
        String namePlateImg;
        String makeOrderNum;
        String repairOrderDesc;
        String accessoryContent;
        Boolean quick;
        List<RoImage> images;

        public String getMissionId() {
            return missionId;
        }

        public void setMissionId(String missionId) {
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

        public List<RoImage> getImages() {
            return images;
        }

        public void setImages(List<RoImage> images) {
            this.images = images;
        }

        public Boolean getQuick() {
            return quick;
        }

        public void setQuick(Boolean quick) {
            this.quick = quick;
        }
    }
}
