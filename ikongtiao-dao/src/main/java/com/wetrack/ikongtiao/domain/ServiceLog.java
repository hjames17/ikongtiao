package com.wetrack.ikongtiao.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by zhanghong on 16/7/27.
 *
 * 服务日志
 */
@Data
public class ServiceLog {
    Long id;
    String missionId; //sid
    String repairOrderId;//sid
    String log;
    Date targetDate; //日志针对的服务时间
    Date createDate; //日志填写的时间
    OperatorType creatorType;
    String creatorId;
    Boolean missed;//是不是补写的
    String creatorName;
}
