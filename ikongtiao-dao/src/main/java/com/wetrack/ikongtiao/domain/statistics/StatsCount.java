package com.wetrack.ikongtiao.domain.statistics;

import lombok.Data;

/**
 * Created by zhanghong on 16/7/4.
 *
 * 统计的返回数据结构，适应不同的分组方式（客户，客服，维修员，地区)
 */
@Data
public class StatsCount {

    String id;
    String name;
    Integer count;
}
