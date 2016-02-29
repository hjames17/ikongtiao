package com.wetrack.ikongtiao.domain.admin;

/**
 * Created by zhanghong on 15/12/23.
 */
public enum Role {

//    MISSION_LS("mission_ls"),
//    MISSION_CR("mission_cr"),
//
//    REPAIR_ORDER_LS("repair_order_ls"),
//    REPAIR_ORDER_CR("repair_order_cr"),
//
//    WEIXIN_SET("WEIXIN_SET"),
    KEFU("kefu"),//客服
    FINANCIAL("financial"), //财务审核
    AUDITOR("auditor"), //业务审核
    ;

    String roleName;
    Role(String name){
        this.roleName = name;
    }

//    public String toString(){
//        return this.roleName;
//    }

    public static Role[] admin(){
        Role[] roles = {KEFU, FINANCIAL, AUDITOR};
        return roles;
    }
    public static Role[] auditor(){
        Role[] roles = {AUDITOR};
        return roles;
    }
    public static Role[] kefu(){
        Role[] roles = {KEFU};
        return roles;
    }

    public static Role[] financial(){
        Role[] roles = {FINANCIAL};
        return roles;
    }
}
