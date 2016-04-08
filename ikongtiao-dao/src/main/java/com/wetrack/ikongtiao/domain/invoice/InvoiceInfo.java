package com.wetrack.ikongtiao.domain.invoice;

import java.util.Date;

/**
 * Created by zhanghong on 16/3/14.
 */
public class InvoiceInfo {
    Long id;
    Long orderId; //单号
    String title; //单位名称
    String name; //服务名称
    Integer amount; //开票金额，单位为分
    Date date;//开票时间
    String taxNo;//税号

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTaxNo() {
        return taxNo;
    }

    public void setTaxNo(String taxNo) {
        this.taxNo = taxNo;
    }
}
