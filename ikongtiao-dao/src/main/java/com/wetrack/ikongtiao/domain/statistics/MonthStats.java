package com.wetrack.ikongtiao.domain.statistics;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by zhanghong on 16/7/1.
 */
@Data
@Entity(name = "month_stats")
public class MonthStats {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Integer id;
    @Column(name = "new_mission_count")
    Integer newMissionCount;
    @Column(name = "finished_mission_count")
    Integer finishedMissionCount;
    @Column(name = "unfinished_mission_count")
    Integer unfinishedMissionCount;

    Integer income;//单位为分

    Date time;

    int month; //pattern : yyyyMM

}
