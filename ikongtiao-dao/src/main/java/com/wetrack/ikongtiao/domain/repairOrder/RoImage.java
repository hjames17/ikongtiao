package com.wetrack.ikongtiao.domain.repairOrder;

/**
 * Created by zhanghong on 16/3/14.
 * 维修单的补充图片
 */
public class RoImage {
    Long id;
    Long repairOrderId;
    String url;
    Integer ordinal;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Long getRepairOrderId() {
        return repairOrderId;
    }

    public void setRepairOrderId(Long repairOrderId) {
        this.repairOrderId = repairOrderId;
    }

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {
        this.ordinal = ordinal;
    }
}
