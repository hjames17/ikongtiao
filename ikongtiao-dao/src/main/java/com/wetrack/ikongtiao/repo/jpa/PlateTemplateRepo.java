package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.machine.PlateTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhanghong on 16/7/26.
 */
public interface PlateTemplateRepo extends JpaRepository<PlateTemplate, Long>{
    List<PlateTemplate> findByTypeId(Long machineTypeId);
}
