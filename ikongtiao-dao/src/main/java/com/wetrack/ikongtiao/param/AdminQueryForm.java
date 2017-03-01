package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.admin.UserType;

/**
 * Created by zhanghong on 15/12/30.
 */
public class AdminQueryForm extends BaseCondition {

    Boolean inService;
    String phone;
    String name;
    String email;
    UserType adminType;


    public Boolean isInService() {
        return inService;
    }

    public void setInService(Boolean inService) {
        this.inService = inService;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getAdminType() {
        return adminType;
    }

    public void setAdminType(UserType adminType) {
        this.adminType = adminType;
    }
}
