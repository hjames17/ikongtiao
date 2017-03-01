package com.wetrack.ikongtiao.service.api;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.statistics.MissionCompDuration;
import com.wetrack.ikongtiao.dto.MissionDetail;
import com.wetrack.ikongtiao.param.StatsQueryParam;

import java.util.Date;

/**
 * Created by zhanghong on 17/2/7.
 */
public interface StatisticService {


    MissionCompDuration statsDuration(Date start, Date end);

    PageList<MissionDetail> listByDuration(StatsQueryParam queryParam);
}
