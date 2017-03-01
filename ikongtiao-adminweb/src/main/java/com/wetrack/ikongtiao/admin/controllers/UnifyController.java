package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.ikongtiao.domain.AccountType;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.admin.UserType;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import com.wetrack.message.MessageService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.service.TokenService;
import studio.wetrack.accountService.domain.Type;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhanghong on 15/12/28.
 */

@Controller
@RequestMapping(value = "/unify")
public class UnifyController {

    @Autowired
    TokenService tokenService;

    @Autowired
    AdminService adminService;

    @Autowired
    MessageService messageService;

    @Autowired
    AdminController adminController;


    @Autowired
    FixerService fixerService;
    @Autowired
    UserInfoService userInfoService;
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    studio.wetrack.accountService.domain.LoginOut unifyLogin(@RequestBody UnifiedLoginForm unifiedLoginForm) throws Exception{

        switch (unifiedLoginForm.getType().getName()){
            case AccountType.ADMIN:
                AdminController.LoginForm alf = new AdminController.LoginForm();
                alf.setEmail(unifiedLoginForm.getEmail());
                alf.setPassword(unifiedLoginForm.getPassword());
                AdminController.LoginOut lo = adminController.login(alf);
                studio.wetrack.accountService.domain.LoginOut out = new studio.wetrack.accountService.domain.LoginOut();
                out.setToken(lo.getToken());
                out.setId(lo.getId());
                out.setName(lo.getUserInfo().getName());
                out.setType(toUserType(lo.getUserInfo().getAdminType()));
                return out;
            case AccountType.FIXER:
                return fixerLogin(unifiedLoginForm.getPhone(), unifiedLoginForm.getPassword());
            case AccountType.CUSTOMER:
                return customerLogin(unifiedLoginForm.getPhone() == null ? unifiedLoginForm.getEmail() : unifiedLoginForm.getPhone(), unifiedLoginForm.getPassword());
            default:
                throw new BusinessException("无效的用户类型" + unifiedLoginForm.getType().getName());
        }

    }

    private Type toUserType(UserType userType) {
        return new Type() {
            @Override
            public String getName() {
                return userType.getName();
            }

            @Override
            public String[] getRolesStringArray() {
                return userType.getRolesStringArray();
            }
        };
    }


    studio.wetrack.accountService.domain.LoginOut fixerLogin(String phone, String password) throws Exception{
        Token token = fixerService.login(phone, password);
        studio.wetrack.accountService.domain.LoginOut out = new studio.wetrack.accountService.domain.LoginOut();
        out.setToken(token.getToken());
        out.setId(fixerService.getFixerIdFromTokenUser(token.getUser()).toString());
        Fixer fixer = fixerService.getFixer(fixerService.getFixerIdFromTokenUser(token.getUser()));
        out.setName(fixer.getName());
        out.setType(toUserType(fixer.getType()));
        return out;
    }

    studio.wetrack.accountService.domain.LoginOut customerLogin(String accountName, String password) throws Exception{
        Token token = userInfoService.login(accountName, password);
        UserInfo userInfo = userInfoService.findByAccountName(accountName);
        studio.wetrack.accountService.domain.LoginOut out = new studio.wetrack.accountService.domain.LoginOut();
        out.setToken(token.getToken());
        out.setId(userInfoService.userIdFromToken(token.getUser().getId()));
        out.setName(userInfo.getOrganization() + ":" + userInfo.getContacterName());
        out.setType(toUserType(userInfo.getUserType()));
        return out;
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    void login(@RequestBody studio.wetrack.accountService.domain.LoginOut form, HttpServletRequest request) throws Exception{
        tokenService.logout(form.getToken());
    }

    @Data
    static class UnifiedLoginForm{
        String email;
        String phone;
        String password;
        AccountType type;
    }
}
