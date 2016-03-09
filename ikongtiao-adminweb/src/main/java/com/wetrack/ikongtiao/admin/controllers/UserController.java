package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.param.UserQueryParam;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
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


}
