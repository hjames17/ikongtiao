package com.wetrack.ikongtiao.repo.impl;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ServiceLog;
import com.wetrack.ikongtiao.param.ServiceLogQueryParam;
import com.wetrack.ikongtiao.repo.api.ServiceLogRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 16/9/16.
 */
@Service
public class ServiceLogRepoImpl implements ServiceLogRepo {

    @Autowired
    CommonDao commonDao;

    @Override
    public ServiceLog create(ServiceLog serviceLog) {
        int count = commonDao.mapper(ServiceLog.class).sql("insert").session().insert(serviceLog);
        if(count == 1){
            return serviceLog;
        }else{
            return null;
        }
    }

    @Override
    public int delete(long id) {
        return commonDao.mapper(ServiceLog.class).sql("deleteByPrimaryKey").session().delete(id);
    }

    @Override
    public PageList<ServiceLog> searchWithParam(ServiceLogQueryParam param) {
        //set total
        PageList<ServiceLog> page = new PageList<>();
        page.setPage(param.getPage());
        page.setPageSize(param.getPageSize());
        param.setStart(page.getStart());
        page.setTotalSize(commonDao.mapper(ServiceLog.class).sql("countWithParam").session().selectOne(param));

        List<ServiceLog> list = commonDao.mapper(ServiceLog.class).sql("searchWithParam").session().selectList(param);

        page.setData(list);
        return page;
    }

    @Override
    public ServiceLog findOne(long id) {
        return commonDao.mapper(ServiceLog.class).sql("findByPrimaryKey").session().selectOne(id);
    }

    @Override
    public List<String> findMissionIdsWithParam(ServiceLogQueryParam param) {
        return commonDao.mapper(ServiceLog.class).sql("findMissionIdsWithParam").session().selectList(param);
    }
}
