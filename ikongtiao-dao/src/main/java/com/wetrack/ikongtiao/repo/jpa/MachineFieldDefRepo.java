package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.machine.MachineFieldDef;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhanghong on 16/7/26.
 */
public interface MachineFieldDefRepo extends JpaRepository<MachineFieldDef, Long>{
    List<MachineFieldDef> findByNameLike(String name);

    int countByName(String name);
}
