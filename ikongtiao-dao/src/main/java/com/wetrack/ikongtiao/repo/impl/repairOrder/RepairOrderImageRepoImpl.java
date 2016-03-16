package com.wetrack.ikongtiao.repo.impl.repairOrder;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.repairOrder.RoImage;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 16/3/16.
 */
@Service
public class RepairOrderImageRepoImpl implements RepairOrderImageRepo {
    @Autowired
    CommonDao commonDao;

    @Override
    public void insert(List<RoImage> images) {
        for(RoImage image : images) {
            commonDao.mapper(RoImage.class).sql("insert").session().insert(image);
        }
    }
}
