package com.wetrack.ikongtiao.repo.impl;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.customer.Organization;
import com.wetrack.ikongtiao.param.OrgQueryParam;
import com.wetrack.ikongtiao.repo.api.OrganizationRepoCustom;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhanghong on 17/4/1.
 */
@Service
public class OrganizationRepoImpl implements OrganizationRepoCustom{


    @Resource
    protected CommonDao commonDao;

    @Override
    public int countByQueryParam(OrgQueryParam param) {
        if(param.getName() != null){
            //加上sql like查询通配符
            param.setName("%" + param.getName() + "%");
        }
        if(param.getAddress() != null){
            //加上sql like查询通配符
            param.setAddress("%" + param.getAddress() + "%");
        }
        BaseCondition baseCondition = commonDao.mapper(Organization.class).sql("countByParam").session().selectOne(param);
        return baseCondition == null ? 0 : baseCondition.getTotalSize();
    }

    @Override
    public List<Organization> queryList(OrgQueryParam param) {
        if(param.getType() != null){
            //加上sql like查询通配符
            param.setType("%" + param.getType() + "%");
        }
        if(param.getName() != null){
            //加上sql like查询通配符
            param.setName("%" + param.getName() + "%");
        }
        if(param.getAddress() != null){
            //加上sql like查询通配符
            param.setAddress("%" + param.getAddress() + "%");
        }
        return commonDao.mapper(Organization.class).sql("listByParam").session().selectList(param);
    }

    @Override
    public boolean updateExistFields(Organization organization) {
        return (1 == commonDao.mapper(Organization.class).sql("updateExistFields").session().update(organization));
    }
}
