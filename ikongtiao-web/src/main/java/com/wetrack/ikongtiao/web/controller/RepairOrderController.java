package com.wetrack.ikongtiao.web.controller;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.RoImage;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        RepairOrder repairOrder = repairOrderService.create(fixerService.getFixerIdFromTokenUser(user), form.getMissionId(),
                form.getNamePlateImg(), form.getMakeOrderNum(), form.getRepairOrderDesc(), form.getAccessoryContent(),
                form.getImages());
        return repairOrder.getId().toString();
    }

    @SignTokenAuth

    @RequestMapping(value = BASE_PATH + "/update" , method = {RequestMethod.POST})
    public void update(@RequestBody RepairOrder repairOrder, HttpServletRequest request) throws Exception{
//        User user = (User)request.getAttribute("user");
        RepairOrder current = repairOrderService.getById(repairOrder.getId(), true);
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
    public List<RepairOrder> listForMission(@RequestParam(value = "missionId") Integer missionId) throws Exception{
        return repairOrderService.listForMission(missionId, false);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{id}", method = {RequestMethod.GET})
    public RepairOrder repairOrder(@PathVariable(value = "id") long id) throws Exception{
        return repairOrderService.getById(id, false);
    }

    @SignTokenAuth
    @RequestMapping(value = BASE_PATH + "/finish", method = {RequestMethod.POST})
    public String setFinished(@RequestBody Long id) throws Exception{
        repairOrderService.setFinished(id);
        return "ok";
    }




    public static class CreateForm {
        Integer missionId;
        String namePlateImg;
        String makeOrderNum;
        String repairOrderDesc;
        String accessoryContent;
        List<RoImage> images;

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

        public List<RoImage> getImages() {
            return images;
        }

        public void setImages(List<RoImage> images) {
            this.images = images;
        }
    }
}
