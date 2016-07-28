package com.wetrack.ikongtiao.repo.jpa.statistics;

import com.wetrack.ikongtiao.domain.statistics.MonthStats;
import org.springframework.data.repository.CrudRepository;

/**
 * Created by zhanghong on 16/7/23.
 */
public interface MonthStatsRepo extends CrudRepository<MonthStats, Integer>{

    MonthStats findByMonth(int month);
}
