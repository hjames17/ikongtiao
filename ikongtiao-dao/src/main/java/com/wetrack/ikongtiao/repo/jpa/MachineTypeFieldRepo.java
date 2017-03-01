package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.machine.MachineTypeField;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by zhanghong on 16/7/26.
 */
public interface MachineTypeFieldRepo extends JpaRepository<MachineTypeField, Long>{
    void deleteByTypeId(Long typeId);
}
