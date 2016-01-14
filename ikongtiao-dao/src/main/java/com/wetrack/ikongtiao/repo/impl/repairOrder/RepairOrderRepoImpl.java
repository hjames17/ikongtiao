package com.wetrack.ikongtiao.repo.impl.repairOrder;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhanghong on 16/1/7.
 */
@Service
public class RepairOrderRepoImpl implements RepairOrderRepo {
    @Autowired
    CommonDao commonDao;
    @Override
    public List<RepairOrder> listForMission(Integer missionId) throws Exception {
        List<RepairOrder> list = commonDao.mapper(RepairOrder.class).sql("listByMissionId").session().selectList(missionId);
        //add accessoryList
        if(list != null && list.size() > 0){
            List<Long> ids = new ArrayList<Long>();
            for(RepairOrder order : list){
                ids.add(order.getId());
            }
            List<Accessory> allAccessories = commonDao.mapper(Accessory.class).sql("selectByRepairOrderIds").session().selectList(ids);
            Map<Long, List<Accessory>> mapForRepiarOrder = new HashMap<Long, List<Accessory>>();
            for(Accessory accessory : allAccessories){
                List<Accessory> subList = mapForRepiarOrder.get(accessory.getRepairOrderId());
                if(subList == null){
                    subList = new ArrayList<Accessory>();
                    mapForRepiarOrder.put(accessory.getRepairOrderId(), subList);
                }
                subList.add(accessory);
            }
            for(RepairOrder order : list){
                order.setAccessoryList(mapForRepiarOrder.get(order.getId()));
            }
        }

        return list;
    }

    @Override
    public RepairOrder create(RepairOrder repairOrder) throws Exception {
        int count = commonDao.mapper(RepairOrder.class).sql("insertSelective").session().insert(repairOrder);
        if(count != 1){
            throw new Exception("创建失败，插入 " + count + "条");
        }

        return repairOrder;
    }

    @Override
    public void update(RepairOrder repairOrder) throws Exception {
        commonDao.mapper(RepairOrder.class).sql("updateByPrimaryKeySelective").session().update(repairOrder);
    }

    @Override
    public RepairOrder getById(Long id) throws Exception {
        return commonDao.mapper(RepairOrder.class).sql("selectByPrimaryKey").session().selectOne(id);
    }
}
