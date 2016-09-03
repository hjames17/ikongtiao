package com.wetrack.ikongtiao.repo.impl.repairOrder;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.dto.RepairOrderDto;
import com.wetrack.ikongtiao.param.RepairOrderQueryParam;
import com.wetrack.ikongtiao.param.StatsQueryParam;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by zhanghong on 16/1/7.
 */
@Service
public class RepairOrderRepoImpl implements RepairOrderRepo {
    @Autowired
    CommonDao commonDao;
    @Override
    public List<RepairOrder> listForMission(Mission mission) throws Exception {
        List<RepairOrder> list = commonDao.mapper(RepairOrder.class).sql("listByMission").session().selectList(mission);
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
    public List<RepairOrderDto> listByQueryParam(RepairOrderQueryParam param) {

        if(param.getPhone() != null){
            //加上sql like查询通配符
            param.setPhone("%" + param.getPhone() + "%");
        }
        if(param.getUserName() != null){
            //加上sql like查询通配符
            param.setUserName("%" + param.getUserName() + "%");
        }
        if(param.getFixerName() != null){
            //加上sql like查询通配符
            param.setFixerName("%" + param.getFixerName() + "%");
        }

        return commonDao.mapper(RepairOrder.class).sql("listByQueryParam").session().selectList(param);
    }


    @Override public int countByParam(RepairOrderQueryParam param) {
        if(param.getPhone() != null){
            //加上sql like查询通配符
            param.setPhone("%" + param.getPhone() + "%");
        }
        if(param.getUserName() != null){
            //加上sql like查询通配符
            param.setUserName("%" + param.getUserName() + "%");
        }
        if(param.getFixerName() != null){
            //加上sql like查询通配符
            param.setFixerName("%" + param.getFixerName() + "%");
        }
        BaseCondition baseCondition = commonDao.mapper(RepairOrder.class).sql("countByQueryParam").session()
                .selectOne(param);
        Integer count = baseCondition == null ? 0 : baseCondition.getTotalSize();
        return count == null ? 0 : count;
    }

    @Override
    public List<RepairOrder> listByStatsParam(StatsQueryParam param) {

        return commonDao.mapper(RepairOrder.class).sql("listByStatsParam").session().selectList(param);
    }

    @Override
    public int countLaborCostByStatsParam(StatsQueryParam param) {
        return commonDao.mapper(RepairOrder.class).sql("countLaborCostByStatsParam").session()
                .selectOne(param);
    }

    @Override
    public int countByMission(Mission mission) {

        return commonDao.mapper(RepairOrder.class).sql("countByMission").session()
                .selectOne(mission);
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
    public int countUnfinishedForMission(Mission mission) {
        return commonDao.mapper(RepairOrder.class).sql("countUnfinishedForMission").session()
                .selectOne(mission);
    }

    @Override
    public void update(RepairOrder repairOrder) throws Exception {
        repairOrder.setUpdateTime(new Date());
        commonDao.mapper(RepairOrder.class).sql("updateByPrimaryKeySelective").session().update(repairOrder);
    }

    @Override
    public void updateSid(RepairOrder repairOrder) {
        commonDao.mapper(RepairOrder.class).sql("updateSid").session().update(repairOrder);
    }

    @Override
    public RepairOrder getById(Long id){
        return commonDao.mapper(RepairOrder.class).sql("selectByPrimaryKey").session().selectOne(id);
    }

    @Override
    public RepairOrder getBySid(String sid) {
        return commonDao.mapper(RepairOrder.class).sql("selectBySid").session().selectOne(sid);
    }
}
