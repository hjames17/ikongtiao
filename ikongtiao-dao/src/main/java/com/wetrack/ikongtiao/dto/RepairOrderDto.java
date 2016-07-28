/*
 * Copyright (C), 2014-2015, xiazhou
 * FileName: RepairOrder.java
 * Author: xiazhou
 * Date: 2015年12月15日 下午 00:50:23
 * Description:
 */
package com.wetrack.ikongtiao.dto;

import com.wetrack.ikongtiao.domain.RepairOrder;
import lombok.Data;

@Data
public class RepairOrderDto extends RepairOrder {
    String fixerName;
    String organization;
}