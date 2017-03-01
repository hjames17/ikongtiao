package com.wetrack.ikongtiao.domain;

import studio.wetrack.accountService.domain.Type;

/**
 * Created by zhanghong on 17/2/22.
 */
public class AccountType implements Type {

    public static final String ADMIN = "admin";
    public static final String CUSTOMER = "customer";
    public static final String FIXER = "fixer";


    String name;

    public AccountType(){

    }

    public AccountType(String name){
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String[] getRolesStringArray() {
        return null;
    }

    public void setName(String name) {
        this.name = name;
    }
}
