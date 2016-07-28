package com.wetrack.ikongtiao.web.controller;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.geo.GeoLocation;
import com.wetrack.ikongtiao.geo.GeoUtil;
import com.wetrack.ikongtiao.param.UserQueryParam;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import com.wetrack.ikongtiao.utils.RegExUtil;
import com.wetrack.verification.VerificationCodeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;


/**
 * Created by zhanghong on 16/1/4.
 * 集控维保人员用户操作接口
 */

@Controller
@RequestMapping("/fixer/user")
@SignTokenAuth(roleNameRequired = "JK_LEVEL_2")
@ResponseBody
public class FixerUserController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    VerificationCodeService verificationCodeService;


    void checkFields(CreateForm form) throws BusinessException{
        if(!RegExUtil.isMobilePhone(form.getContacterPhone())){
            throw new BusinessException("联系人手机号码无效");
        }
        if(StringUtils.isEmpty(form.getOrganization())){
            throw new BusinessException("单位名称不能为空");
        }
        if(!StringUtils.isEmpty(form.getAccountEmail()) && !RegExUtil.isValidEmail(form.getAccountEmail())){
            throw new BusinessException("email无效");
        }
        if(StringUtils.isEmpty(form.getPassword())){
            throw new BusinessException("请设置一个密码");
        }
        if(StringUtils.isEmpty(form.getCode())){
            throw new BusinessException("验证码未填写");
        }
        if(form.getProvinceId() == null || form.getCityId() == null || form.getDistrictId() == null || StringUtils.isEmpty(form.getAddress())){
            throw new BusinessException("地址信息不完整");
        }
        try {
            GeoLocation geoLocation = GeoUtil.getGeoLocationFromAddress(form.getAddress());
            if(geoLocation == null){
                throw new BusinessException("地址精度太低，请检查！");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new BusinessException("无法获取地址的经纬度");

        }
    }

    /**
     * 创建一个集控系统用户账号,此时并未关联微信号
     * @param form
     * @param request
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public String createUser(@RequestBody CreateForm form, HttpServletRequest request){
        checkFields(form);

        UserInfo exist = userInfoService.findByOrganizationOrContacterPhone(form.getOrganization(), form.getContacterPhone());
        if(exist != null){
            if(form.getOrganization().equals(exist.getOrganization())){
                throw new BusinessException("单位"+form.getOrganization()+"已经存在，不要重复创建");
            }
            if(form.getContacterPhone().equals(exist.getContacterPhone())){
                throw new BusinessException("联系人手机"+form.getContacterPhone()+"已被占用，不能重复使用");
            }
        }

        if(!verificationCodeService.verifyCode(form.getContacterPhone(), form.getCode())){
            throw new BusinessException("联系人手机验证码无效");
        }

        UserInfo user = new UserInfo();
        BeanUtils.copyProperties(form, user);
        user.setId(null);
        User authUser = (User)request.getAttribute("user");
        user.setMaintainerId(Integer.valueOf(
                authUser.getId().substring(Constants.TOKEN_ID_PREFIX_FIXER.length())));
        userInfoService.create(user);
        return user.getId();
    }

    /**
     * 升级一个用户账号为集控系统用户
     * @param form
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upgrade", method = RequestMethod.POST)
    public String upgradeUser(@RequestBody CreateForm form, HttpServletRequest request) throws Exception{
        checkFields(form);

        UserInfo exist = userInfoService.findByOrganizationOrContacterPhone(form.getOrganization(), form.getContacterPhone());
        if(exist == null){
            throw new BusinessException("没有指定单位:"+form.getOrganization());
        }

        if(!verificationCodeService.verifyCode(form.getContacterPhone(), form.getCode())){
            throw new BusinessException("联系人手机验证码无效");
        }

        BeanUtils.copyProperties(form, exist);
        User authUser = (User)request.getAttribute("user");
        exist.setMaintainerId(Integer.valueOf(
                authUser.getId().substring(Constants.TOKEN_ID_PREFIX_FIXER.length())));
        userInfoService.update(exist);
        return exist.getId();

    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public void updateUser(@RequestBody UserInfo userInfo) throws Exception{
        if(StringUtils.isEmpty(userInfo.getId())){
            throw new BusinessException("未指定用户id");
        }
        //不能修改密码
        userInfo.setPassword(null);
        userInfoService.update(userInfo);
    }

    @RequestMapping(value = "/listIncharge", method = RequestMethod.GET)
    public PageList<UserInfoDto> listUsersIncharge(
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "pageSize") Integer pageSize,
            HttpServletRequest request) throws Exception{

        User authUser = (User)request.getAttribute("user");
        UserQueryParam param = new UserQueryParam();
        param.setPage(page == null ? 1 : page);
        param.setPageSize(pageSize == null ? 10 : pageSize);
        param.setMaintainerId(Integer.valueOf(
                authUser.getId().substring(Constants.TOKEN_ID_PREFIX_FIXER.length())));
        return userInfoService.listUserByQueryParam(param);

    }

    @RequestMapping(value = "/list" , method = {RequestMethod.GET})
    public PageList<UserInfoDto> listMission(@RequestParam(required = false, value = "name") String name,
                                             @RequestParam(required = false, value = "phone") String phone,
                                             @RequestParam(required = false, value = "address") String address,
                                             @RequestParam(required = false, value = "districtId") Integer districtId,
                                             @RequestParam(required = false, value = "page") Integer page,
                                             @RequestParam(required = false, value = "pageSize") Integer pageSize,
                                             HttpServletRequest request) throws Exception{
        User authUser = (User)request.getAttribute("user");
        UserQueryParam userQueryParam = new UserQueryParam();
        userQueryParam.setAddress(address);
        userQueryParam.setPhone(phone);
        userQueryParam.setUserName(name);
        userQueryParam.setDistrictId(districtId);
        userQueryParam.setPage(page == null ? 0 : page);
        userQueryParam.setPageSize(pageSize == null ? 10 : pageSize);
        userQueryParam.setMaintainerId(Integer.valueOf(
                authUser.getId().substring(Constants.TOKEN_ID_PREFIX_FIXER.length())));
        return userInfoService.listUserByQueryParam(userQueryParam);
    }

    public static class CreateForm extends UserInfo{
        String code;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
