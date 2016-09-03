package com.wetrack.ikongtiao.repo.impl;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.FaultType;
import com.wetrack.ikongtiao.repo.api.FaultTypeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 16/3/10.
 */
@Service
public class FaultTypeRepoImpl implements FaultTypeRepo{
    @Autowired
    CommonDao commonDao;


    @Override
    public List<FaultType> findAll() {
        return commonDao.mapper(FaultType.class).sql("findAll").session().selectList();

    }

    @Override
    public FaultType findById(int id) {
        return commonDao.mapper(FaultType.class).sql("findById").session().selectOne(id);

    }

    @Override
    public FaultType create(String name, Integer ordinal) {
        FaultType faultType = new FaultType();
        faultType.setName(name);
        faultType.setOrdinal(ordinal);
        int i = commonDao.mapper(FaultType.class).sql("insert").session().insert(faultType);
        if(i == 1) {
            return faultType;
        }else{
            return null;
        }
    }
}
