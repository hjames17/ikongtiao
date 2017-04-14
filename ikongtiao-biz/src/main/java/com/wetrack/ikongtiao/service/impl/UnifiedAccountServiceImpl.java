package com.wetrack.ikongtiao.service.impl;

import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.domain.AccountType;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.admin.UserType;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import com.wetrack.ikongtiao.repo.jpa.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import studio.wetrack.accountService.AbstrackAccountService;
import studio.wetrack.accountService.AccountException;
import studio.wetrack.accountService.SmsCodeService;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.domain.User;
import studio.wetrack.accountService.auth.service.TokenService;
import studio.wetrack.accountService.domain.LoginForm;
import studio.wetrack.accountService.domain.LoginOut;
import studio.wetrack.accountService.domain.Type;

/**
 * Created by zhanghong on 17/2/22.
 * 当前无用
 */
@Service
public class UnifiedAccountServiceImpl extends AbstrackAccountService {

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepo adminRepo;

    @Autowired
    FixerService fixerService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    UserInfoRepo userInfoRepo;

    @Autowired
    public UnifiedAccountServiceImpl(TokenService tokenService, SmsCodeService smsCodeService) {
        super(tokenService, smsCodeService);
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
        if(StringUtils.isEmpty(id)){
            throw new AccountException("不存在账号");
        }

        Object account = null;
        if(isKindOfCustomer(type)){
            account = userInfoRepo.findOne(id);
        }else if(isKindOfFixer(type)){
            try {
                account = fixerService.getFixer(Integer.parseInt(id));
            } catch (Exception e) {
                throw new AccountException("密码修改失败:" + e.getMessage());
            }
        }else{
            account = adminRepo.findById(Integer.parseInt(id));
        }
        updatePassword(account, password, type);
    }

    @Override
    protected boolean checkPassword(String id, String password, Type type) throws AccountException {
        if(isKindOfCustomer(type)){
            UserInfo userInfo = userInfoRepo.findOne(id);
            return userInfo != null && password.equals(userInfo.getPassword());
        }else if(isKindOfFixer(type)){
            try {
                Fixer fixer = fixerService.getFixer(Integer.parseInt(id));
                return fixer != null && password.equals(fixer.getPassword());
            } catch (Exception e) {
                return false;
            }
        }else{
            com.wetrack.ikongtiao.domain.admin.User admin = adminRepo.findById(Integer.parseInt(id));
            return admin != null && password.equals(admin.getPassword());
        }
    }

    @Override
    protected void updatePasswordByEmail(String email, String password, Type type) throws AccountException {
        Object account = null;
        if(isKindOfAdmin(type)) {
            account = adminRepo.findByEmailOrPhone(email, null);
        }
        updatePassword(account, password, type);
    }

    @Override
    protected void updatePasswordByPhone(String phone, String password, Type type) throws AccountException {
        Object account = null;
        if(isKindOfCustomer(type)){
            account = userInfoRepo.getByPhone(phone);

        }else if(isKindOfFixer(type)){
            account = fixerService.getFixerByPhone(phone);
        }else{
            account = adminRepo.findByEmailOrPhone(null, phone);
        }
        updatePassword(account, password, type);
    }

    protected void updatePassword(Object account, String password, Type type){
        if(account == null){
            throw new AccountException("不存在账号");
        }
        try {
            if(isKindOfCustomer(type)){
                UserInfo userInfo = (UserInfo)account;
                userInfo.setPassword(password);
                userInfoRepo.update(userInfo);
            }else if(isKindOfFixer(type)){
                Fixer fixer = (Fixer)account;
                fixer.setPassword(password);
                fixerService.updateInfo(fixer);
            }else{
                com.wetrack.ikongtiao.domain.admin.User admin = (com.wetrack.ikongtiao.domain.admin.User)account;
                admin.setPassword(password);
                adminRepo.update(admin);
            }
        }catch (Exception e){
            throw new AccountException("密码修改失败:" + e.getMessage());
        }
    }

    public boolean isKindOfCustomer(Type type){
        String n = type.getName();
        return n.equals(UserType.CUSTOMER) || n.equals(UserType.CUSTOMER_ADMIN) || n.equals(AccountType.CUSTOMER);
    }

    public boolean isKindOfFixer(Type type){
        String n = type.getName();
        return n.equals(UserType.COMMON_FIXER) || n.equals(UserType.MAINTAINER) || n.equals(AccountType.FIXER);
    }
    public boolean isKindOfAdmin(Type type){
        String n = type.getName();
        return n.equals(UserType.KEFU) || n.equals(UserType.EDITOR) || n.equals(UserType.SUPERVISOR) || n.equals(UserType.MANAGER) || n.equals(UserType.FINANCIAL) || n.equals(AccountType.ADMIN);
    }



    public String getRealUserIdFromAuthUser(User user){
        if(user.getId().startsWith(Constants.TOKEN_ID_PREFIX_FIXER)){
            return user.getId().substring(Constants.TOKEN_ID_PREFIX_FIXER.length());
        }else if(user.getId().startsWith(Constants.TOKEN_ID_PREFIX_CUSTOMER)){
            return user.getId().substring(Constants.TOKEN_ID_PREFIX_CUSTOMER.length());
        }else if(user.getId().startsWith(Constants.TOKEN_ID_PREFIX_ADMIN)){
            return user.getId().substring(Constants.TOKEN_ID_PREFIX_ADMIN.length());
        }else return user.getId();
    }



}
