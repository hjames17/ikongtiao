package com.wetrack.ikongtiao.repo.impl.repairOrder;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.repo.api.repairOrder.AccessoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
@Service
public class AccessoryRepoImpl implements AccessoryRepo {
    @Autowired
    CommonDao commonDao;
    @Override
    public void createMulti(List<Accessory> accessoryList) throws Exception {
        int count = 0;
        CommonDao.SimpleSqlSession session = null;
        for(Accessory accessory : accessoryList){
            if(session == null){
                session = commonDao.mapper(Accessory.class).sql("insert").session();
            }
            count += session.insert(accessory);
        }

        if(count != accessoryList.size()){
            throw new Exception("错误！共有" + accessoryList.size() + "条配件信息，实际插入" + count + "条");
        }
    }

    @Override
    public Accessory create(Accessory accessory) throws Exception {
        int count = commonDao.mapper(Accessory.class).sql("insert").session().insert(accessory);
        if(count != 1){
            throw new Exception("创建配件错误！实际插入" + count + "条");
        }
        return accessory;
    }

    @Override
    public boolean update(Accessory accessory) throws Exception {
        int count = commonDao.mapper(Accessory.class).sql("updateByPrimaryKeySelective").session().update(accessory);
        if(count != 1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public boolean delete(Long accessoryId) throws Exception {
        int count = commonDao.mapper(Accessory.class).sql("deleteById").session().delete(accessoryId);
        if(count != 1){
            return false;
        }else{
            return true;
        }
    }

    @Override
    public List<Accessory> listOfRepairOrderId(Long repairOrderId) throws Exception {
        return commonDao.mapper(Accessory.class).sql("selectByRepairOrderId").session().selectList(repairOrderId);
    }
}
