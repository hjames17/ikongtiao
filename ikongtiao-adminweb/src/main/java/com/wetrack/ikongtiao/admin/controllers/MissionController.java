package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.filter.AjaxResponseWrapper;
import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.error.MissionErrorMessage;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhanghong on 15/12/28.
 */
@Controller
public class MissionController {

    static final String BASE_PATH = "mission";

    @Autowired
    MissionService missionService;


    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/list" , method = {RequestMethod.POST})
    public PageList<MissionDto> listMission(@RequestBody AppMissionQueryParam param) {
        if (param == null) {
            throw new AjaxException(MissionErrorMessage.MISSION_LIST_PARAM_ERROR);
        }
        return missionService.listMissionByAppQueryParam(param);
    }

    @AjaxResponseWrapper
    @RequestMapping(value = BASE_PATH + "/deny" , method = {RequestMethod.POST})
    public String denyMission(@RequestBody DenyForm denyForm) throws Exception{

        checkValid(denyForm.getMissionId(), denyForm.getAdminUserId());
        if(StringUtils.isEmpty(denyForm.getReason())){
            throw new Exception("拒绝原因不能为空");
        }

        missionService.denyMission(denyForm.getMissionId(), denyForm.getAdminUserId(), denyForm.getReason());
        return "任务成功关闭!";
    }

    @AjaxResponseWrapper
    @RequestMapping(value = BASE_PATH + "/accept" , method = {RequestMethod.GET})
    public String acceptMission(@RequestParam(value = "adminUserId") Integer adminUserId,
                                @RequestParam(value = "missionId") Integer missionId) throws Exception{

        checkValid(missionId, adminUserId);

        missionService.acceptMission(missionId, adminUserId);

        return "任务受理成功!";
    }

    @AjaxResponseWrapper
    @RequestMapping(value = BASE_PATH + "/dispatch" , method = {RequestMethod.GET})
    public String dispatchMission(@RequestParam(value = "adminUserId") Integer adminUserId,
                                  @RequestParam(value = "missionId") Integer missionId,
                                  @RequestParam(value = "fixerId") Integer fixerId) throws Exception{

        checkValid(missionId, adminUserId);
        if(fixerId == null){
            throw new Exception("没有指定诊断员id");
        }

        missionService.dispatchMission(missionId, fixerId, adminUserId);
        return "任务指派成功!";
    }
    @AjaxResponseWrapper
    @RequestMapping(value = BASE_PATH + "/describe" , method = {RequestMethod.POST})
    public String describeMission(@RequestBody DescribeForm form) throws Exception {
        checkValid(form.getMissionId(), form.getAdminUserId());
        if(StringUtils.isEmpty(form.getDecription())){
            throw new Exception("描述为空");
        }

        missionService.submitMissionDescription(form.getMissionId(), form.getDecription());
        return "任务描述已提交!";
    }

    void checkValid(Integer missionId, Integer adminUserId) throws Exception{
        if(adminUserId == null){
            throw new Exception("没有处理人的id");
        }
        if(missionId == null){
            throw new Exception("没有目标任务id");
        }
    }


    public static class DenyForm {
        Integer adminUserId;
        Integer missionId;
        String reason;

        public Integer getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(Integer adminUserId) {
            this.adminUserId = adminUserId;
        }

        public Integer getMissionId() {
            return missionId;
        }

        public void setMissionId(Integer missionId) {
            this.missionId = missionId;
        }

        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }


    public static class DescribeForm {
        Integer adminUserId;
        Integer missionId;
        String decription;

        public Integer getAdminUserId() {
            return adminUserId;
        }

        public void setAdminUserId(Integer adminUserId) {
            this.adminUserId = adminUserId;
        }

        public Integer getMissionId() {
            return missionId;
        }

        public void setMissionId(Integer missionId) {
            this.missionId = missionId;
        }

        public String getDecription() {
            return decription;
        }

        public void setDecription(String decription) {
            this.decription = decription;
        }
    }
}
