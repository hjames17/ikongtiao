package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.machine.MachineType;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhanghong on 16/12/6.
 */
public interface MachineTypeJpaRepo extends JpaRepository<MachineType, Long> {


    int countByName(String name);
}
