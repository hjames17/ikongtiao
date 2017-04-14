package com.wetrack.ikongtiao.repo.jpa;

import com.wetrack.ikongtiao.domain.customer.Organization;
import com.wetrack.ikongtiao.repo.api.OrganizationRepoCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by zhanghong on 16/7/26.
 */
public interface OrganizationRepo extends JpaRepository<Organization, Long>, OrganizationRepoCustom{
    List<Organization> findByIdIn(List<Long> ids);

    int countById(Long orgId);

    Organization findByName(String organization);
}
