/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: RepairOrder.java
 * Author: xiazhou
 * Date: 2015年12月15日 下午 00:50:23
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import com.wetrack.ikongtiao.domain.repairOrder.Accessory;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class RepairOrder implements Serializable {
    /**
     * id:
     */
    private Long id;

    /**
     * mission_id:任务id
     */
    private Integer missionId;

    /**
     * 创建该维修单的维修员id
     */
    Integer creatorFixerId;

    /**
     * fixer_id:维修人id
     */
    private Integer fixerId;

    /**
     * repair_order_desc:维修单描述描述
     */
    private String repairOrderDesc;

    /**
     * repair_order_state:维修单状态:0－待报价；1－待确认；2－已确认；3－等待配件；4－等待指派；5－维修中；6－完成；7－已关闭；
     */
    private Byte repairOrderState;

    /**
     * operator:操作人
     */
    @Deprecated
    private String operator;
    Integer adminUserId;

    /**
     * create_time:
     */
    private Date createTime;

    /**
     * update_time:
     */
    private Date updateTime;

    /**
     * 铭牌图片
     */
    String namePlateImg;

    /**
     * 制令号
     */
    String makeOrderNum;

    /**
     * 维修员提交的配件清单描述，
     * 不是精确的清单，供客服制定精确清单的依据
     */
    String accessoryContent;

    /**
     * 人工费
     */
    Float laborCost;

    /**
     * 配件清单，单独存表
     * 关联accessory表
     */
    List<Accessory> accessoryList;

    Float discount; //折扣

    /**
     * 付款方式， 0 线下， 1 线上
     */
    Integer payment;

    //以下只在线上付款情况下有效
    /**
     * 已付款
     */
    boolean paid;

    /**
     * 关联字段，关联支付信息表
     */
    String paymentInfoId;


    /**
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database table repair_order
     *
     * @xiazhougenerated Tue Dec 15 12:50:23 CST 2015
     */
    private static final long serialVersionUID = 1L;

    /**
     * @return the value of id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the value for id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the value of mission_id
     */
    public Integer getMissionId() {
        return missionId;
    }

    /**
     * @param missionId the value for mission_id
     */
    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    /**
     * @return the value of fixer_id
     */
    public Integer getFixerId() {
        return fixerId;
    }

    /**
     * @param fixerId the value for fixer_id
     */
    public void setFixerId(Integer fixerId) {
        this.fixerId = fixerId;
    }

    /**
     * @return the value of repair_order_desc
     */
    public String getRepairOrderDesc() {
        return repairOrderDesc;
    }

    /**
     * @param repairOrderDesc the value for repair_order_desc
     */
    public void setRepairOrderDesc(String repairOrderDesc) {
        this.repairOrderDesc = repairOrderDesc == null ? null : repairOrderDesc.trim();
    }

    /**
     * @return the value of repair_order_state
     */
    public Byte getRepairOrderState() {
        return repairOrderState;
    }

    /**
     * @param repairOrderState the value for repair_order_state
     */
    public void setRepairOrderState(Byte repairOrderState) {
        this.repairOrderState = repairOrderState;
    }

    /**
     * @return the value of operator
     */
    public String getOperator() {
        return operator;
    }

    /**
     * @param operator the value for operator
     */
    public void setOperator(String operator) {
        this.operator = operator == null ? null : operator.trim();
    }

    /**
     * @return the value of create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime the value for create_time
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return the value of update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime the value for update_time
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getNamePlateImg() {
        return namePlateImg;
    }

    public void setNamePlateImg(String namePlateImg) {
        this.namePlateImg = namePlateImg;
    }

    public String getMakeOrderNum() {
        return makeOrderNum;
    }

    public void setMakeOrderNum(String makeOrderNum) {
        this.makeOrderNum = makeOrderNum;
    }

    public String getAccessoryContent() {
        return accessoryContent;
    }

    public void setAccessoryContent(String accessoryContent) {
        this.accessoryContent = accessoryContent;
    }

    public Float getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(Float laborCost) {
        this.laborCost = laborCost;
    }

    public List<Accessory> getAccessoryList() {
        return accessoryList;
    }

    public void setAccessoryList(List<Accessory> accessoryList) {
        this.accessoryList = accessoryList;
    }

    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Integer getPayment() {
        return payment;
    }

    public void setPayment(Integer payment) {
        this.payment = payment;
    }

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getPaymentInfoId() {
        return paymentInfoId;
    }

    public void setPaymentInfoId(String paymentInfoId) {
        this.paymentInfoId = paymentInfoId;
    }

    public Integer getAdminUserId() {
        return adminUserId;
    }

    public void setAdminUserId(Integer adminUserId) {

        this.adminUserId = adminUserId;
    }

    public Integer getCreatorFixerId() {
        return creatorFixerId;
    }

    public void setCreatorFixerId(Integer creatorFixerId) {
        this.creatorFixerId = creatorFixerId;
    }

    /**
     * This method corresponds to the database table repair_order
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", missionId=").append(missionId);
        sb.append(", fixerId=").append(fixerId);
        sb.append(", repairOrderDesc=").append(repairOrderDesc);
        sb.append(", repairOrderState=").append(repairOrderState);
        sb.append(", operator=").append(operator);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}