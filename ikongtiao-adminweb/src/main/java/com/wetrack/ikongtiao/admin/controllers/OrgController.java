package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.customer.Organization;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.OrgQueryParam;
import com.wetrack.ikongtiao.service.api.OrganizationService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * Created by zhanghong on 15/12/28.
 */

@Controller
public class OrgController {

    static final String BASE_PATH = "/org";

    @Autowired
    OrganizationService organizationService;


    @SignTokenAuth(roleNameRequired = "VIEW_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/list" , method = {RequestMethod.GET})
    public PageList<Organization> listOrg(@RequestParam(required = false, value = "name") String name,
                                             @RequestParam(required = false, value = "address") String address,
                                             @RequestParam(required = false, value = "districtId") Integer districtId,
                                             @RequestParam(required = false, value = "page") Integer page,
                                             @RequestParam(required = false, value = "pageSize") Integer pageSize) {
        OrgQueryParam qp = new OrgQueryParam();
        qp.setAddress(address);
        qp.setName(name);
        qp.setDistrictId(districtId);
        qp.setPage(page == null ? 0 : page);
        qp.setPageSize(pageSize == null ? 10 : pageSize);
        return organizationService.listOrgByQueryParam(qp);
    }

//    @SignTokenAuth(roleNameRequired = "VIEW_CUSTOMER")
//    @ResponseBody
//    @RequestMapping(value = BASE_PATH + "/listIn" , method = {RequestMethod.POST})
//    public List<Organization> list(@RequestBody List<Long> ids){
//
//        return organizationService.listInIds(ids);
//    }

    @SignTokenAuth(roleNameRequired = "VIEW_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{orgId}" , method = {RequestMethod.GET})
    public Organization get(@PathVariable long orgId){
        return organizationService.getBasicInfoById(orgId);
    }


    @SignTokenAuth(roleNameRequired = "EDIT_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{orgId}" , method = {RequestMethod.DELETE})
    public void del(@PathVariable long orgId){
        boolean success = organizationService.deleteComplete(orgId);
        if(!success){
            throw new BusinessException("删除组织失败");
        }

    }


//    @SignTokenAuth(roleNameRequired = "EDIT_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST})
    public Long create(@RequestBody Organization organization){
        if(StringUtils.isEmpty(organization.getName())){
            throw new BusinessException("组织名称未填");
        }
        if(organization.getParentId() != null){
            if(organizationService.countById(organization.getParentId()) == 0){
                throw new BusinessException("父组织id无效");
            }
        }
        organization.setId(null);
        Organization created = organizationService.create(organization);


        return created.getId();
    }

    @SignTokenAuth(roleNameRequired = "EDIT_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/update" , method = {RequestMethod.POST})
    public void update(@RequestBody Organization organization){
        if(organization.getId() == null){
            throw new BusinessException("未指定组织id");
        }
        organizationService.update(organization);
    }

    @Autowired
    UserInfoService userInfoService;

    /**
     * 为客户orgId创建管理员账号，如果该orgId已经有管理员账号，原管理员账号将会被降为普通账号
     * @param userInfo
     * @return
     */
    @SignTokenAuth(roleNameRequired = "EDIT_CUSTOMER")
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/create/admin" , method = {RequestMethod.POST})
    public String createOrgAdmin(@RequestBody UserInfo userInfo, HttpServletRequest request){
        if(userInfo.getOrgId() == null){
            throw new BusinessException("未指定机构id");
        }
        if(StringUtils.isEmpty(userInfo.getPhone())){
            throw new BusinessException("手机号码为空");
        }
        Organization org = organizationService.getById(userInfo.getOrgId());
        if(org == null){
            throw new BusinessException("该机构不存在");
        }
        userInfo.setType(1);
        userInfo = userInfoService.create(userInfo);
        if(org.getAdminId() != null){
            //如果客户存在管理员，原管理员应该要降权
            UserInfo oldAdmin = new UserInfo();
            oldAdmin.setId(org.getAdminId());
            oldAdmin.setType(0); //从1改成0
            oldAdmin.setUpdateTime(new Date());
            userInfoService.update(userInfo);
            //todo 已登录的token需要降权
        }

        org.setAdminId(userInfo.getId());
        org.setUpdateTime(new Date());
        organizationService.update(org);

        return userInfo.getId();
    }




}
