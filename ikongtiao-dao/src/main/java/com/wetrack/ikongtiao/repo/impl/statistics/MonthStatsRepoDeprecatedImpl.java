package com.wetrack.ikongtiao.repo.impl.statistics;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.statistics.MonthStats;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by zhanghong on 16/7/23.
 */
public class MonthStatsRepoDeprecatedImpl {

    @Autowired
    CommonDao commonDao;

    public MonthStats create(MonthStats monthStats) {
        int count = commonDao.mapper(MonthStats.class).sql("insertSelective").session().insert(monthStats);
        if(count != 1){
            return null;
        }

        return monthStats;
    }
}
