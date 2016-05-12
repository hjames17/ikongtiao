package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.domain.FaultType;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.repo.api.FaultTypeRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zhanghong on 15/12/28.
 */
@Controller
public class MissionController {

    static final String BASE_PATH = "/mission";

    @Autowired
    MissionService missionService;

    @Autowired
    UserInfoService userInfoService;

    @ResponseBody
    @SignTokenAuth(roleNameRequired = "VIEW_MISSION")
    @RequestMapping(value = BASE_PATH + "/list" , method = {RequestMethod.POST})
    public PageList<MissionDto> listMission(@RequestBody AppMissionQueryParam param) throws Exception{
        if (param == null) {
            throw new BusinessException("查询任务参数为空");
        }

        return missionService.listMissionByAppQueryParam(param);
    }

    @ResponseBody
    @SignTokenAuth(roleNameRequired = "VIEW_MISSION")
    @RequestMapping(value = BASE_PATH + "/{id}" , method = {RequestMethod.GET})
    public MissionDto getMission(@PathVariable(value = "id") int id) throws Exception{
        return missionService.getMissionDto(id);
    }

    @RequestMapping(value = BASE_PATH + "/save" , method = {RequestMethod.POST})
    @SignTokenAuth(roleNameRequired = "EDIT_MISSION")
    @ResponseBody
    public Integer addMission(@RequestBody Mission param, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        if (param == null || StringUtils.isEmpty(param.getUserId())
                ||StringUtils.isEmpty(param.getFaultType())
                ||StringUtils.isEmpty(param.getAddress()) || StringUtils.isEmpty(param.getMissionDesc())) {
            throw new BusinessException("任务参数缺失");
        }
        param.setAdminUserId(Integer.valueOf(user.getId()));
        param.setMissionState(MissionState.ACCEPT.getCode());
//
//        MissionAddress missionAddress = new MissionAddress();
//        missionAddress.setProvinceId(param.getProvinceId());
//        missionAddress.setCityId(param.getCityId());
//        missionAddress.setDistrictId(param.getDistrictId());
//        missionAddress.setAddress(param.getAddress());
//        missionAddress.setPhone(param.getContacterPhone());
//        missionAddress.setName(param.getContacterName());
        Mission created = missionService.saveMission(param);
        return created.getId();
    }

    @SignTokenAuth(roleNameRequired = "EDIT_MISSION")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/finish/{id}", method = {RequestMethod.POST})
    public void finishMission(@PathVariable(value = "id") int id ) throws Exception{
        //TODO 操作纪录
        missionService.finishMission(id);
    }

    @ResponseBody
    @SignTokenAuth(roleNameRequired = "EDIT_MISSION")
    @RequestMapping(value = BASE_PATH + "/deny" , method = {RequestMethod.POST})
    public void denyMission(@RequestBody DenyForm denyForm, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        denyForm.setAdminUserId(Integer.valueOf(user.getId()));
        checkValid(denyForm.getMissionId(), denyForm.getAdminUserId());
        if(StringUtils.isEmpty(denyForm.getReason())){
            throw new BusinessException("拒绝原因不能为空");
        }

        missionService.denyMission(denyForm.getMissionId(), denyForm.getAdminUserId(), denyForm.getReason());
    }

    @ResponseBody
    @SignTokenAuth(roleNameRequired = "EDIT_MISSION")
    @RequestMapping(value = BASE_PATH + "/accept" , method = {RequestMethod.GET})
    public void acceptMission(@RequestParam(value = "missionId") Integer missionId, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        checkValid(missionId, Integer.valueOf(user.getId()));

        missionService.acceptMission(missionId, Integer.valueOf(user.getId()));

    }

    @ResponseBody
    @SignTokenAuth(roleNameRequired = "EDIT_MISSION")
    @RequestMapping(value = BASE_PATH + "/dispatch" , method = {RequestMethod.GET})
    public void dispatchMission(@RequestParam(value = "missionId") Integer missionId,
                                  @RequestParam(value = "fixerId") Integer fixerId, HttpServletRequest request) throws Exception{
        User user = (User)request.getAttribute("user");
        checkValid(missionId, Integer.valueOf(user.getId()));
        if(fixerId == null){
            throw new BusinessException("没有指定诊断员id");
        }

        missionService.dispatchMission(missionId, fixerId, Integer.valueOf(user.getId()));
    }
    @ResponseBody
    @SignTokenAuth(roleNameRequired = "EDIT_MISSION")
    @RequestMapping(value = BASE_PATH + "/describe" , method = {RequestMethod.POST})
    public void describeMission(@RequestBody DescribeForm form, HttpServletRequest request) throws Exception {
        User user = (User)request.getAttribute("user");
        form.setAdminUserId(Integer.valueOf(user.getId()));
        checkValid(form.getMissionId(), form.getAdminUserId());
//        if(StringUtils.isEmpty(form.getDescription())){
//            throw new Exception("描述为空");
//        }

        missionService.submitMissionDescription(form.getMissionId(), form.getDescription(), form.getName(), form.getProvinceId(), form.getCityId(), form.getDistrictId(), form.getAddress());
    }

    @ResponseBody
    @SignTokenAuth(roleNameRequired = "EDIT_MISSION")
    @RequestMapping(value = BASE_PATH + "/update" , method = {RequestMethod.POST})
    public void update(@RequestBody Mission mission, HttpServletRequest request) throws Exception {

        //防止这个字段被修改
        mission.setCreateTime(null);

        User user = (User)request.getAttribute("user");
        mission.setAdminUserId(Integer.valueOf(user.getId()));
        checkValid(mission.getId(), mission.getAdminUserId());

        missionService.update(mission);
    }

    @ResponseBody
    @RequestMapping("/faultType")
    public List<FaultType> listFaultType(){
        return missionService.listFaultType();
    }

    @Autowired
    FaultTypeRepo faultTypeRepo;
    @ResponseBody
    @RequestMapping(value = "/faultType/add", method = RequestMethod.POST)
    public String addFaultType(@RequestParam(value = "name") String name){
        faultTypeRepo.create(name, 18);
        return name;
    }

    void checkValid(Integer missionId, Integer adminUserId) throws Exception{
        if(adminUserId == null){
            throw new BusinessException("没有处理人的id");
        }
        if(missionId == null){
            throw new BusinessException("没有目标任务id");
        }
    }

    public static class MissionWithAddress extends Mission{
        String contacterName;
        String contacterPhone;
        Integer provinceId;
        Integer cityId;
        Integer districtId;
        String address;

        public Integer getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Integer provinceId) {
            this.provinceId = provinceId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Integer districtId) {
            this.districtId = districtId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getContacterName() {
            return contacterName;
        }

        public void setContacterName(String contacterName) {
            this.contacterName = contacterName;
        }

        public String getContacterPhone() {
            return contacterPhone;
        }

        public void setContacterPhone(String contacterPhone) {
            this.contacterPhone = contacterPhone;
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
        String description;
        Integer provinceId;
        Integer cityId;
        Integer districtId;
        String address;
        String name;


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

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public Integer getProvinceId() {
            return provinceId;
        }

        public void setProvinceId(Integer provinceId) {
            this.provinceId = provinceId;
        }

        public Integer getCityId() {
            return cityId;
        }

        public void setCityId(Integer cityId) {
            this.cityId = cityId;
        }

        public Integer getDistrictId() {
            return districtId;
        }

        public void setDistrictId(Integer districtId) {
            this.districtId = districtId;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
