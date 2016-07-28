package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.Mission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhanghong on 16/7/26.
 */
public interface MissionJpaRepo extends JpaRepository<Mission, Integer>{
    //select id from mission where mission_state=#{MissionState.Fixing.getCode()}
    List<Mission> findByMissionState(int missionState);
}
