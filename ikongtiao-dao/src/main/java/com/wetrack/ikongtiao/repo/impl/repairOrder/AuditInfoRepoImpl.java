package com.wetrack.ikongtiao.repo.impl.repairOrder;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.repairOrder.AuditInfo;
import com.wetrack.ikongtiao.repo.api.repairOrder.AuditInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/1/7.
 */
@Service
public class AuditInfoRepoImpl implements AuditInfoRepo {
    @Autowired
    CommonDao commonDao;


    @Override
    public AuditInfo create(AuditInfo auditInfo) throws Exception {
        int count = commonDao.mapper(AuditInfo.class).sql("insertSelective").session().insert(auditInfo);
        if(count != 1){
            throw new Exception("创建失败，插入 " + count + "条");
        }

        return auditInfo;
    }

    @Override
    public AuditInfo getLatestOfRepairOrder(long repairOrderId) throws Exception {
        return commonDao.mapper(AuditInfo.class).sql("selectLatestByRepairOrderId").session().selectOne(repairOrderId);
    }

    @Override
    public AuditInfo getById(Long id) throws Exception {
        return commonDao.mapper(AuditInfo.class).sql("selectByPrimaryKey").session().selectOne(id);
    }

}
