package com.wetrack.ikongtiao.service.impl;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.customer.Organization;
import com.wetrack.ikongtiao.param.OrgQueryParam;
import com.wetrack.ikongtiao.repo.jpa.OrganizationRepo;
import com.wetrack.ikongtiao.repo.jpa.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 17/4/1.
 */
@Service
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    OrganizationRepo organizationRepo;

    @Autowired
    UserInfoRepo userInfoRepo;


    @Override
    public PageList<Organization> listOrgByQueryParam(OrgQueryParam queryParam) {

        //set total
        PageList<Organization> page = new PageList<Organization>();
        page.setPage(queryParam.getPage());
        page.setPageSize(queryParam.getPageSize());
        queryParam.setStart(page.getStart());
        page.setTotalSize(organizationRepo.countByQueryParam(queryParam));

        List<Organization> data = organizationRepo.queryList(queryParam);

        if(data.size() > 0){
            for (Organization organization : data) {
                organization.deserializeOrgTypes();
            }
        }

        page.setData(data);
        return page;
    }



    @Override
    public List<Organization> listInIds(List<Long> ids) {
        return organizationRepo.findByIdIn(ids);
    }

    @Override
    public Organization getById(Long orgId) {
        return organizationRepo.findOne(orgId);
    }

    @Override
    public int countById(Long orgId) {
        return organizationRepo.countById(orgId);
    }

    @Override
    public Organization create(Organization organization) {
        organization.serializeOrgTypes();
        return organizationRepo.save(organization);
    }

    @Override
    public boolean update(Organization organization) {
        return organizationRepo.updateExistFields(organization);
    }

    @Override
    public Organization getBasicInfoById(long orgId) {
        return organizationRepo.findOne(orgId);
    }

    @Override
    public boolean deleteComplete(long orgId) {
        if(!organizationRepo.exists(orgId)){
            return false;
        }
        //删除组织
        organizationRepo.delete(orgId);
        //删除名下所有账号
        userInfoRepo.removeByOrgId(orgId);
        return true;
    }
}
