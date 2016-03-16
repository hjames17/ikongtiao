package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.UserQueryParam;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.apache.commons.lang3.StringUtils;
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
                                             @RequestParam(required = false, value = "phone") String phone,
                                             @RequestParam(required = false, value = "address") String address,
                                             @RequestParam(required = false, value = "page") Integer page,
                                             @RequestParam(required = false, value = "pageSize") Integer pageSize) throws Exception{
        UserQueryParam userQueryParam = new UserQueryParam();
        userQueryParam.setAddress(address);
        userQueryParam.setPhone(phone);
        userQueryParam.setUserName(name);
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


    @SignTokenAuth(roleNameRequired = "EDIT_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST}, produces = MediaType.TEXT_PLAIN_VALUE)
    public String create(@RequestBody UserInfo userInfo) throws Exception{
        if(StringUtils.isEmpty(userInfo.getAccountName())){
            throw new BusinessException("客户单位名称未填");
        }
        if(StringUtils.isEmpty(userInfo.getContacterName())){
            throw new BusinessException("联系人姓名姓名未填");
        }
        if(StringUtils.isEmpty(userInfo.getContacterPhone())){
            throw new BusinessException("联系人电话未填");
        }
        userInfo.setId(null);
        UserInfo created = userInfoService.create(userInfo);


        return created.getId();
    }



}
