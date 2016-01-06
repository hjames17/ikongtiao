package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;

/**
 * Created by zhanghong on 15/12/30.
 */
public class FixerQueryForm extends BaseCondition {

    Boolean inService;
    Boolean certificated;
    Boolean insured;
    String phone;

    public Boolean isInsured() {
        return insured;
    }

    public void setInsured(Boolean insured) {
        this.insured = insured;
    }

    public Boolean isInService() {
        return inService;
    }

    public void setInService(Boolean inService) {
        this.inService = inService;
    }

    public Boolean isCertificated() {
        return certificated;
    }

    public void setCertificated(Boolean certificated) {
        this.certificated = certificated;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
