package com.wetrack.ikongtiao.domain.repairOrder;

/**
 * Created by zhanghong on 16/1/6.
 */
public class Accessory {
    Integer id;
    Integer repairOrderId;
    Integer missionId; //冗余字段，便以统计
    String name;
    Integer count;
    Float price;
    Float discount; //折扣金额
}
