package com.wetrack.ikongtiao.param;

import com.wetrack.base.page.BaseCondition;

import java.util.Date;

/**
 * Created by zhanghong on 15/12/30.
 */
public class FixerQueryForm extends BaseCondition {

    Boolean inService;
    Boolean certificated;
    Boolean insured;
    Boolean jkMaintainer;
    String phone;
    String name;
    String address;
    Double longitude;
    Double latitude;
    Float distance; //范围，公里

    Date createTimeStart;
    Date createTimeEnd;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public Boolean isJkMaintainer() {
        return jkMaintainer;
    }

    public void setJkMaintainer(Boolean jkMaintainer) {
        this.jkMaintainer = jkMaintainer;
    }

    public Date getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(Date createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public Date getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(Date createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }
}
