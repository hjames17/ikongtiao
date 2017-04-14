package com.wetrack.ikongtiao.repo.api;

import com.wetrack.ikongtiao.domain.customer.Organization;
import com.wetrack.ikongtiao.param.OrgQueryParam;

import java.util.List;

/**
 * Created by zhanghong on 17/4/1.
 */
public interface OrganizationRepoCustom {

    int countByQueryParam(OrgQueryParam queryParam);

    List<Organization> queryList(OrgQueryParam queryParam);

    boolean updateExistFields(Organization organization);
}
