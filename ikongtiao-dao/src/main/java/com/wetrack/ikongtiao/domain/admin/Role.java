package com.wetrack.ikongtiao.domain.admin;

/**
 * Created by zhanghong on 15/12/23.
 */
public enum Role {

    MISSION_LS("mission_ls"),
    MISSION_CR("mission_cr"),

    REPAIR_ORDER_LS("repair_order_ls"),
    REPAIR_ORDER_CR("repair_order_cr"),

    WEIXIN_SET("WEIXIN_SET"),
    ;

    String roleName;
    Role(String name){
        this.roleName = name;
    }

    public String toString(){
        return this.roleName;
    }

    public static Role[] admin(){
        Role[] roles = {MISSION_LS, MISSION_CR, REPAIR_ORDER_LS, REPAIR_ORDER_CR, WEIXIN_SET};
        return roles;
    }
    public static Role[] kefu(){
        Role[] roles = {WEIXIN_SET};
        return roles;
    }

    public static Role[] weixinAdmin(){
        Role[] roles = {WEIXIN_SET};
        return roles;
    }
}
