package com.wetrack.ikongtiao.repo.impl.repairOrder;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.repairOrder.RoImage;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderImageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
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

    @Override
    public void insert(Collection<String> imagesUrl, Long repairOrderId) {
        RoImage top = commonDao.mapper(RoImage.class).sql("selectTopOrdinalByRepairOrderId").session().selectOne(repairOrderId);
        int topOrdinal = ((top == null) ? 0 : top.getOrdinal() + 1);
        for(String url : imagesUrl) {
            RoImage image = new RoImage();
            image.setUrl(url);
            image.setRepairOrderId(repairOrderId);
            image.setOrdinal(topOrdinal++);
            commonDao.mapper(RoImage.class).sql("insert").session().insert(image);
        }
    }

    @Override
    public void removeIn(Collection<String> imagesUrl, Long repairOrderId) {
        for(String url : imagesUrl){
            RoImage image = new RoImage();
            image.setUrl(url);
            image.setRepairOrderId(repairOrderId);
            commonDao.mapper(RoImage.class).sql("removeByUrlAndRepairOrderIdIn").session().delete(image);
        }
    }
}
