package com.wetrack.ikongtiao.domain.repairOrder;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by zhanghong on 16/1/6.
 */
//@Entity(name = "accessory")
public class Accessory implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @Column(name = "repair_order_id")
    Long repairOrderId;

    @Column(name = "mission_id")
    Integer missionId; //冗余字段，便以统计

    String name;
    Integer count;
    Integer price; //分为单位
    Integer discount; //折扣金额，分为单位

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getRepairOrderId() {
        return repairOrderId;
    }

    public void setRepairOrderId(Long repairOrderId) {
        this.repairOrderId = repairOrderId;
    }

    public Integer getMissionId() {
        return missionId;
    }

    public void setMissionId(Integer missionId) {
        this.missionId = missionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Integer getDiscount() {
        return discount;
    }

    public void setDiscount(Integer discount) {
        this.discount = discount;
    }
}
