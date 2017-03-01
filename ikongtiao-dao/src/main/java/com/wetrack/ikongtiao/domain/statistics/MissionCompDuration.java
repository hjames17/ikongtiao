package com.wetrack.ikongtiao.domain.statistics;

import lombok.Data;

/**
 * Created by zhanghong on 17/2/7.
 */
@Data
public class MissionCompDuration {
    //1天之内
    long day1;
    //3天之内
    long day3;
    //7天之内
    long day7;
    //7天到30天
    long day15;
    //7天到15天
    long day30;
    //30天以上
    long dayMax;

    public long countAll(){
        return day1 + day3 + day7 + day15 + day30 + dayMax;
    }
}
