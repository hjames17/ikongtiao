package com.wetrack.ikongtiao.service.impl;

import com.wetrack.ikongtiao.domain.AccountType;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import studio.wetrack.accountService.AbstrackAccountService;
import studio.wetrack.accountService.AccountException;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.service.TokenService;
import studio.wetrack.accountService.domain.LoginForm;
import studio.wetrack.accountService.domain.LoginOut;
import studio.wetrack.accountService.domain.Type;

/**
 * Created by zhanghong on 17/2/22.
 * 当前无用
 */
//@Service
public class UnifiedAccountServiceImpl extends AbstrackAccountService {

    @Autowired
    AdminService adminService;

    @Autowired
    public UnifiedAccountServiceImpl(TokenService tokenService) {
        super(tokenService, null);
    }



    @Override
    public LoginOut login(LoginForm form) throws AccountException {
        if(StringUtils.isEmpty(form.getEmail()) && StringUtils.isEmpty(form.getPhone()) && StringUtils.isEmpty(form.getWeixinId())){
            throw new AccountException("无效的用户");
        }
        if(StringUtils.isEmpty(form.getPassword())){
            throw new AccountException("没有输入密码");
        }

        String id = findUserAndReturnId(form);
        if(id == null){
            throw new AccountException("用户名或者密码错误");
        }
        Token token;
        AccountType actualType;
        switch (form.getType().getName()){
            case AccountType.ADMIN:
                try {
                    token = adminService.login(form.getEmail(), form.getPassword());
                } catch (Exception e) {
                    throw new AccountException(e.getMessage());
                }
                break;
            case AccountType.FIXER:
                break;
            case AccountType.CUSTOMER:
                break;
        }

        LoginOut loginOut = new LoginOut();
        loginOut.setId(id);

        return loginOut;
    }

    @Override
    protected int getLoginLifeTime(LoginForm form) throws AccountException {
        return 0;
    }

    @Override
    protected String findUserAndCheckPassAndReturnId(LoginForm form) throws AccountException {
//        switch (form.getType().getName()){
//            case AccountType.ADMIN:
//                try {
//                    Token token = adminService.login(form.getEmail(), form.getPassword());
//                } catch (Exception e) {
//                    throw new AccountException(e.getMessage());
//                }
//                break;
//            case AccountType.FIXER:
//                break;
//            case AccountType.CUSTOMER:
//                break;
//        }
        return null;
    }

    @Override
    protected String findUserAndReturnId(LoginForm form) throws AccountException {
//        switch (form.getType().getName()){
//            case AccountType.ADMIN:
//                try {
//                    Token token = adminService.login(form.getEmail(), form.getPassword());
//                } catch (Exception e) {
//                    throw new AccountException(e.getMessage());
//                }
//                break;
//            case AccountType.FIXER:
//                break;
//            case AccountType.CUSTOMER:
//                break;
//        }
        return null;
    }

    @Override
    protected void updatePassword(String id, String password, Type type) throws AccountException {

    }

    @Override
    protected boolean checkPassword(String id, String password, Type type) throws AccountException {
        return false;
    }

    @Override
    protected void updatePasswordByEmail(String email, String password, Type type) throws AccountException {

    }

    @Override
    protected void updatePasswordByPhone(String phone, String password, Type type) throws AccountException {

    }



}
