package com.wetrack.ikongtiao.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhanghong on 16/7/27.
 *
 * 服务记录
 */
@Data
public class ServiceRecord {
    Long id;
    Integer missionId;
    Long repairOrderId;
    Date appointmentTime;//预约的上门时间
    Date serviceTime; //实际的上门时间
    String serviceTicketImg; //有用户签字的纸质服务单照片
}
