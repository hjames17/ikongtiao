package com.wetrack.ikongtiao.repo.api.repairOrder;

import com.wetrack.ikongtiao.domain.repairOrder.RoImage;

import java.util.Collection;
import java.util.List;

/**
 * Created by zhanghong on 16/3/16.
 */
public interface RepairOrderImageRepo {
    void insert(List<RoImage> images);


    void insert(Collection<String> imagesUrl, Long repairOrderId);

    void removeIn(Collection<String> imagesUrl, Long repairOrderId);
}
