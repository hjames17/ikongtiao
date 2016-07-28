package com.wetrack.ikongtiao.domain.statistics;

import lombok.Data;

/**
 * Created by zhanghong on 16/7/4.
 */
@Data
public class AreaCount {

    Integer provinceId;
    String provinceName;
    Integer cityId;
    Integer districtId;
    Integer count;
}
