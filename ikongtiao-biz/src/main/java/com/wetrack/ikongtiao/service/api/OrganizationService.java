package com.wetrack.ikongtiao.service.api;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.customer.Organization;
import com.wetrack.ikongtiao.param.OrgQueryParam;

import java.util.List;

/**
 * Created by zhanghong on 15/12/26.
 */
public interface OrganizationService {
    PageList<Organization> listOrgByQueryParam(OrgQueryParam queryParam);

    List<Organization> listInIds(List<Long> ids);

    Organization getById(Long orgId);

    int countById(Long orgId);

    Organization create(Organization organization);

    boolean update(Organization organization);

    Organization getBasicInfoById(long orgId);

    boolean deleteComplete(long orgId);
}

