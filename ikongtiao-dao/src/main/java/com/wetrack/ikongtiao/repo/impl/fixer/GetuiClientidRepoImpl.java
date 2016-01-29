package com.wetrack.ikongtiao.repo.impl.fixer;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.fixer.GetuiClientId;
import com.wetrack.ikongtiao.repo.api.fixer.GetuiClientIdRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/1/21.
 */
@Service
public class GetuiClientidRepoImpl implements GetuiClientIdRepo {

    @Autowired
    CommonDao commonDao;

    @Override
    public GetuiClientId getByUid(Integer uid) throws Exception {
        return commonDao.mapper(GetuiClientId.class).sql("selectByPrimaryKey").session().selectOne(uid);
    }

    @Override
    public int update(GetuiClientId getuiClientId) throws Exception {
        return commonDao.mapper(GetuiClientId.class).sql("update").session().update(getuiClientId);
    }

    @Override
    public GetuiClientId insert(GetuiClientId getuiClientId) throws Exception {
        commonDao.mapper(GetuiClientId.class).sql("insert").session().insert(getuiClientId);
        return null;
    }
}
