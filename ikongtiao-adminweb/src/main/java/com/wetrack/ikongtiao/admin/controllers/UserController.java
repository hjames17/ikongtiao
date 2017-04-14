package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.UserQueryParam;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhanghong on 15/12/28.
 */

@Controller
public class UserController {

    static final String BASE_PATH = "/user";

    @Autowired
    UserInfoService userInfoService;

    @SignTokenAuth(roleNameRequired = "VIEW_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/list" , method = {RequestMethod.GET})
    public PageList<UserInfoDto> listMission(@RequestParam(required = false, value = "name") String name,
                                             @RequestParam(required = false, value = "orgId") Long orgId,
                                             @RequestParam(required = false, value = "phone") String phone,
                                             @RequestParam(required = false, value = "address") String address,
                                             @RequestParam(required = false, value = "districtId") Integer districtId,
                                             @RequestParam(required = false, value = "page") Integer page,
                                             @RequestParam(required = false, value = "pageSize") Integer pageSize) throws Exception{
        UserQueryParam userQueryParam = new UserQueryParam();
        userQueryParam.setAddress(address);
        userQueryParam.setOrgId(orgId);
        userQueryParam.setPhone(phone);
        userQueryParam.setUserName(name);
        userQueryParam.setDistrictId(districtId);
        userQueryParam.setPage(page == null ? 0 : page);
        userQueryParam.setPageSize(pageSize == null ? 10 : pageSize);
        return userInfoService.listUserByQueryParam(userQueryParam);
    }

    @SignTokenAuth(roleNameRequired = "VIEW_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/listIn" , method = {RequestMethod.POST})
    public List<UserInfo> list(@RequestBody List<String> ids) throws Exception{

        return userInfoService.listInIds(ids);
    }

    @SignTokenAuth(roleNameRequired = "VIEW_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{userId}" , method = {RequestMethod.GET})
    public UserInfo get(@PathVariable String userId) throws Exception{
        return userInfoService.getBasicInfoById(userId);
    }
    @SignTokenAuth(roleNameRequired = "EDIT_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{userId}" , method = {RequestMethod.DELETE})
    public void del(@PathVariable String userId) throws Exception{
        userInfoService.deleteById(userId);
    }


    @SignTokenAuth(roleNameRequired = "EDIT_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String create(@RequestBody UserInfo userInfo) throws Exception{
        if(userInfo.getOrgId() == null){
            throw new BusinessException("未指定所属客户id");
        }
        userInfo.setId(null);
        UserInfo created = userInfoService.create(userInfo);


        return created.getId();
    }

    @SignTokenAuth(roleNameRequired = "EDIT_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/update" , method = {RequestMethod.POST})
    public void update(@RequestBody UserInfo userInfo) throws Exception {
        if(org.springframework.util.StringUtils.isEmpty(userInfo.getId())){
            throw new BusinessException("未知用户id");
        }
        //email字段不允许修改
        userInfo.setAccountEmail(null);
        userInfoService.update(userInfo);
    }



}
