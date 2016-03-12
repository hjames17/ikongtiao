package com.wetrack.ikongtiao.domain.admin;

/**
 * Created by zhanghong on 16/3/9.
 */
public enum AdminType {
    MANAGER("管理员", new Role[]{Role.VIEW_MISSION, Role.VIEW_CUSTOMER, Role.VIEW_REPAIR_ORDER, Role.VIEW_FIXER,
            Role.VIEW_ADMIN_KEFU, Role.VIEW_ADMIN_FINANCIAL, Role.VIEW_ADMIN_SUPERVISOR, Role.VIEW_ADMIN_MANAGER,
            Role.VIEW_SETTINGS, Role.VIEW_FINANCIAL,
            Role.EDIT_MISSION, Role.EDIT_REPAIR_ORDER, Role.EDIT_FIXER,
            Role.AUDIT_FIXER, Role.EDIT_FINANCIAL, Role.AUDIT_CUSTOMER, Role.EDIT_CUSTOMER
            }),

    SUPERVISOR("检查员", new Role[]{Role.VIEW_MISSION, Role.VIEW_REPAIR_ORDER, Role.VIEW_FIXER, Role.VIEW_CUSTOMER,
            Role.AUDIT_REPAIR_ORDER, Role.AUDIT_CUSTOMER, Role.AUDIT_FIXER, Role.AUDIT_FINANCIAL}),

    FINANCIAL("财务审核员", new Role[]{Role.VIEW_FINANCIAL, Role.EDIT_FINANCIAL}),

    KEFU("客服", new Role[]{Role.CHAT, Role.VIEW_MISSION,Role.EDIT_MISSION, Role.VIEW_REPAIR_ORDER, Role.EDIT_REPAIR_ORDER,
            Role.VIEW_FIXER, Role.EDIT_FIXER, Role.AUDIT_FIXER, Role.VIEW_ADMIN_KEFU, Role.VIEW_ADMIN_FINANCIAL, Role.VIEW_ADMIN_SUPERVISOR
            , Role.VIEW_ADMIN_MANAGER, Role.VIEW_SETTINGS, Role.VIEW_CUSTOMER, Role.AUDIT_CUSTOMER, Role.EDIT_CUSTOMER}),
    ;

    String name;
    Role[] roles;

    AdminType(String name, Role[] roles){
        this.name = name;
        this.roles = roles;
    }

    String[] getRolesStringArray(){
        String[] rolesStringArray = new String[roles.length];
        for(int i = 0; i < roles.length; i++){
            rolesStringArray[i] = roles[i].getRoleName();
        }
        return rolesStringArray;
    }
}
