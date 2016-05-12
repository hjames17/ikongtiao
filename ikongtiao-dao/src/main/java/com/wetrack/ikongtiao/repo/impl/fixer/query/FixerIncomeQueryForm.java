package com.wetrack.ikongtiao.repo.impl.fixer.query;

import java.util.Date;

/**
 * Created by zhanghong on 16/4/8.
 */
public class FixerIncomeQueryForm {
    Integer fixerId;
    Date startDate;
    Date endDate;
    boolean withPaymentInfo;

    public Integer getFixerId() {
        return fixerId;
    }

    public void setFixerId(Integer fixerId) {
        this.fixerId = fixerId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public boolean isWithPaymentInfo() {
        return withPaymentInfo;
    }

    public void setWithPaymentInfo(boolean withPaymentInfo) {
        this.withPaymentInfo = withPaymentInfo;
    }
}
