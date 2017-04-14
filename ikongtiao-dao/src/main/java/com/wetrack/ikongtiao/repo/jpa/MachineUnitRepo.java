package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.machine.MachineUnit;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhanghong on 16/7/26.
 */
public interface MachineUnitRepo extends JpaRepository<MachineUnit, Long>{
    int countByBuildNo(String buildNo);
}
