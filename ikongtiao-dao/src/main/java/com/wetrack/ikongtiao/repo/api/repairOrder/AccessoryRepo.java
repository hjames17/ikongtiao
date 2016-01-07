package com.wetrack.ikongtiao.repo.api.repairOrder;

import com.wetrack.ikongtiao.domain.repairOrder.Accessory;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface AccessoryRepo {
    void createMulti(List<Accessory> accessoryList) throws Exception;
}
