package com.wetrack.ikongtiao.domain.admin;

import studio.wetrack.accountService.domain.Type;

/**
 * Created by zhanghong on 16/3/9.
 */
public enum UserType implements Type {

    //后台账号
    MANAGER("管理员", new Role[]{Role.VIEW_MISSION, Role.VIEW_CUSTOMER, Role.VIEW_REPAIR_ORDER, Role.VIEW_FIXER,
            Role.VIEW_ADMIN_KEFU, Role.EDIT_ADMIN_KEFU,Role.VIEW_ADMIN_SUPERVISOR,Role.EDIT_ADMIN_SUPERVISOR,
            Role.VIEW_ADMIN_MANAGER, Role.EDIT_ADMIN_MANAGER,
            Role.VIEW_SETTINGS, Role.VIEW_ADMIN_FINANCIAL, Role.VIEW_FINANCIAL, Role.EDIT_ADMIN_FINANCIAL,
            Role.EDIT_MISSION, Role.EDIT_REPAIR_ORDER, Role.EDIT_FIXER,
            Role.AUDIT_FIXER, Role.EDIT_FINANCIAL, Role.AUDIT_CUSTOMER, Role.EDIT_CUSTOMER,
            Role.JK_LEVEL_1, Role.JK_LEVEL_2, Role.JK_LEVEL_3,
            Role.VIEW_STATISTICS_CUSTOMER, Role.VIEW_STATISTICS_FIXER,Role.VIEW_STATISTICS_MISSION, Role.VIEW_STATISTICS_REPAIR_ORDER
            }),

    SUPERVISOR("检查员", new Role[]{Role.VIEW_MISSION, Role.VIEW_REPAIR_ORDER, Role.VIEW_FIXER, Role.VIEW_CUSTOMER,
            Role.AUDIT_REPAIR_ORDER, Role.AUDIT_CUSTOMER, Role.AUDIT_FIXER,
            Role.VIEW_STATISTICS_CUSTOMER, Role.VIEW_STATISTICS_FIXER,Role.VIEW_STATISTICS_MISSION, Role.VIEW_STATISTICS_REPAIR_ORDER}),

    FINANCIAL("财务审核员", new Role[]{Role.VIEW_FINANCIAL, Role.EDIT_FINANCIAL}),

    KEFU("客服", new Role[]{Role.CHAT, Role.VIEW_MISSION,Role.EDIT_MISSION, Role.VIEW_REPAIR_ORDER, Role.EDIT_REPAIR_ORDER,
            Role.VIEW_FIXER, Role.EDIT_FIXER, Role.AUDIT_FIXER, Role.VIEW_ADMIN_KEFU, Role.VIEW_ADMIN_FINANCIAL, Role.VIEW_ADMIN_SUPERVISOR
            , Role.VIEW_ADMIN_MANAGER, Role.VIEW_SETTINGS, Role.VIEW_CUSTOMER, Role.AUDIT_CUSTOMER, Role.EDIT_CUSTOMER, Role.VIEW_STATISTICS_MISSION, Role.VIEW_STATISTICS_REPAIR_ORDER}),

    EDITOR("资料录入员", new Role[]{Role.VIEW_MACHINE, Role.EDIT_MACHINE}),


    //维修和服务人员账号
    COMMON("普通维修员", new Role[]{Role.FIXER}),//维大师维修人员
    MAINTAINER("维保人员", new Role[]{Role.JK_LEVEL_2, Role.FIXER, Role.JK_LEVEL_1}),//集控系统


    //客户账号
    CUSTOMER("业主", new Role[]{
            Role.JK_LEVEL_1,
            Role.VIEW_MACHINE,
            Role.EDIT_MACHINE,
            Role.VIEW_MISSION_SELF,
            Role.EDIT_MISSION_SELF,
            Role.VIEW_REPAIR_ORDER_SELF,
            Role.EDIT_REPAIR_ORDER_SELF}),
    CUSTOMER_FACTORY("工厂", new Role[]{
            Role.JK_LEVEL_1,
            Role.VIEW_MACHINE,
            Role.EDIT_MACHINE,
            Role.VIEW_MISSION_SELF,
            Role.EDIT_MISSION_SELF,
            Role.VIEW_REPAIR_ORDER_SELF,
            Role.EDIT_REPAIR_ORDER_SELF}),
    CUSTOMER_AGENCY("代理", new Role[]{
            Role.JK_LEVEL_1,
            Role.VIEW_MACHINE,
            Role.EDIT_MACHINE,
            Role.VIEW_MISSION_SELF,
            Role.EDIT_MISSION_SELF,
            Role.VIEW_REPAIR_ORDER_SELF,
            Role.EDIT_REPAIR_ORDER_SELF}),

    ;

    String name;
    Role[] roles;

    UserType(String name, Role[] roles){
        this.name = name;
        this.roles = roles;
    }

    @Override
    public String[] getRolesStringArray(){
        String[] rolesStringArray = new String[roles.length];
        for(int i = 0; i < roles.length; i++){
            rolesStringArray[i] = roles[i].getRoleName();
        }
        return rolesStringArray;
    }

    @Override
    public String getName(){
        return name;
    }
}
