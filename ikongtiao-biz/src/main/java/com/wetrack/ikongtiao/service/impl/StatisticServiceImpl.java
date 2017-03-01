package com.wetrack.ikongtiao.service.impl;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.statistics.MissionCompDuration;
import com.wetrack.ikongtiao.dto.MissionDetail;
import com.wetrack.ikongtiao.param.StatsQueryParam;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.AccessoryRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.service.api.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 17/2/7.
 */
@Service
public class StatisticServiceImpl implements StatisticService{


    @Autowired
    MissionRepo missionRepo;

    @Autowired
    RepairOrderRepo repairOrderRepo;

    @Autowired
    AccessoryRepo accessoryRepo;

    @Override
    public MissionCompDuration statsDuration(Date start, Date end){
        //统计时间段内完成的任务的时间分布
        StatsQueryParam pDuration = new StatsQueryParam();
        pDuration.setUpdateTimeStart(start);
        pDuration.setUpdateTimeEnd(end);
        pDuration.setFinished(true);
        //1天之内
        pDuration.setDurationStart(0);
        pDuration.setDurationEnd(24);
        long day1 = missionRepo.countMissionDuration(pDuration);
        //3天之内
        pDuration.setDurationStart(24);
        pDuration.setDurationEnd(72); //24*3
        long day3 = missionRepo.countMissionDuration(pDuration);
        //7天之内
        pDuration.setDurationStart(72);
        pDuration.setDurationEnd(168); //24*7
        long day7 = missionRepo.countMissionDuration(pDuration);
        //15天之内
        pDuration.setDurationStart(168);
        pDuration.setDurationEnd(360); //24*15
        long day15 = missionRepo.countMissionDuration(pDuration);
        //15天到30天
        pDuration.setDurationStart(360);
        pDuration.setDurationEnd(720); //24*30
        long day30 = missionRepo.countMissionDuration(pDuration);
        //30天以上
        pDuration.setDurationStart(720);
        long dayMax = missionRepo.countMissionDuration(pDuration);

//        Long all = day1 + day3 + day7 + day30 + dayMax;

        MissionCompDuration result = new MissionCompDuration();
        result.setDay1(day1);
        result.setDay3(day3);
        result.setDay7(day7);
        result.setDay15(day15);
        result.setDay30(day30);
        result.setDayMax(dayMax);

        return result;
    }

    @Override
    public PageList<MissionDetail> listByDuration(StatsQueryParam queryParam) {
        PageList<MissionDetail> page = new PageList<>();
        page.setPage(queryParam.getPage());
        page.setPageSize(queryParam.getPageSize());
        queryParam.setStart(page.getStart());
        queryParam.setFinished(true);
        // 设置总数量
        page.setTotalSize(missionRepo.countMissionDuration(queryParam).intValue());
        // 获取内容
        List<MissionDetail> list = missionRepo.listMissionFullByDuration(queryParam);
        page.setData(list);

        return page;
    }
}
