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
    VIEW_MISSION("VIEW_MISSION"),
    EDIT_MISSION("EDIT_MISSION"),
    VIEW_REPAIR_ORDER("VIEW_REPAIR_ORDER"),
    EDIT_REPAIR_ORDER("EDIT_REPAIR_ORDER"),
    AUDIT_REPAIR_ORDER("AUDIT_REPAIR_ORDER"),
    VIEW_FIXER("VIEW_FIXER"),
    EDIT_FIXER("EDIT_FIXER"),
    AUDIT_FIXER("AUDIT_FIXER"),
    VIEW_CUSTOMER("VIEW_CUSTOMER"),
    EDIT_CUSTOMER("EDIT_CUSTOMER"),
    AUDIT_CUSTOMER("AUDIT_CUSTOMER"),
    VIEW_FINANCIAL("VIEW_FINANCIAL"),
    EDIT_FINANCIAL("EDIT_FINANCIAL"),
    AUDIT_FINANCIAL("AUDIT_FINANCIAL"),
    VIEW_ADMIN_KEFU("VIEW_ADMIN_KEFU"), //客服
    EDIT_ADMIN_KEFU("EDIT_ADMIN_KEFU"),
    VIEW_ADMIN_FINANCIAL("VIEW_ADMIN_FINANCIAL"), //客服
    EDIT_ADMIN_FINANCIAL("EDIT_ADMIN_FINANCIAL"),
    VIEW_ADMIN_SUPERVISOR("VIEW_ADMIN_SUPERVISOR"), //业务检查员
    EDIT_ADMIN_SUPERVISOR("EDIT_ADMIN_SUPERVISOR"),
    VIEW_ADMIN_MANAGER("VIEW_ADMIN_MANAGER"), //超级管理员
    EDIT_ADMIN_MANAGER("EDIT_ADMIN_MANAGER"),
    VIEW_SETTINGS("VIEW_SETTINGS"),
    EDIT_SETTINGS("EDIT_SETTINGS"),
    VIEW_STATISTICS_MISSION("VIEW_STATISTICS_MISSION"),
    VIEW_STATISTICS_REPAIR_ORDER("VIEW_STATISTICS_REPAIR_ORDER"),
    VIEW_STATISTICS_CUSTOMER("VIEW_STATISTICS_CUSTOMER"),
    VIEW_STATISTICS_FIXER("VIEW_STATISTICS_FIXER"),
    CHAT("CHAT"),
    ;

    private final String roleName;
    Role(String name){
        this.roleName = name;
    }

    public final String getRoleName(){
        return roleName;
    }


//    public String toString(){
//        return this.adminType;
//    }

//    public static Role[] admin(){
//        Role[] roles = {KEFU, FINANCIAL, AUDITOR};
//        return roles;
//    }
//    public static Role[] auditor(){
//        Role[] roles = {AUDITOR};
//        return roles;
//    }
//    public static Role[] kefu(){
//        Role[] roles = {KEFU};
//        return roles;
//    }
//
//    public static Role[] financial(){
//        Role[] roles = {FINANCIAL};
//        return roles;
//    }
}
