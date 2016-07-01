package com.wetrack.ikongtiao.domain.fixer;

import com.wetrack.ikongtiao.domain.admin.Role;

/**
 * Created by zhanghong on 16/3/9.
 */
public enum FixerType {
    COMMON("普通维修员", new Role[]{Role.FIXER}),
    MAINTAINER("维保人员", new Role[]{Role.JK_LEVEL_2, Role.FIXER, Role.JK_LEVEL_1}),
    ;

    String name;
    Role[] roles;

    FixerType(String name, Role[] roles){
        this.name = name;
        this.roles = roles;
    }

    public String[] getRolesStringArray(){
        String[] rolesStringArray = new String[roles.length];
        for(int i = 0; i < roles.length; i++){
            rolesStringArray[i] = roles[i].getRoleName();
        }
        return rolesStringArray;
    }

    public String getName(){
        return name;
    }
}
