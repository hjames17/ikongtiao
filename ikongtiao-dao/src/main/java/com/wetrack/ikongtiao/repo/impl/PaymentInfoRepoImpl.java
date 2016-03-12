package com.wetrack.ikongtiao.repo.impl;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.PaymentInfo;
import com.wetrack.ikongtiao.repo.api.PaymentInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/3/10.
 */
@Service
public class PaymentInfoRepoImpl implements PaymentInfoRepo{
    @Autowired
    CommonDao commonDao;

    @Override
    public PaymentInfo findByMatch(PaymentInfo paymentInfo) {
        return commonDao.mapper(PaymentInfo.class).sql("findByMatch").session().selectOne(paymentInfo);
    }

    @Override
    public PaymentInfo create(PaymentInfo paymentInfo) {
        int i = commonDao.mapper(PaymentInfo.class).sql("insertSelective").session().insert(paymentInfo);
        if(i == 1) {
            return paymentInfo;
        }else{
            return null;
        }
    }

    @Override
    public void update(PaymentInfo paymentInfo) {
        commonDao.mapper(PaymentInfo.class).sql("updateSelective").session().update(paymentInfo);
    }
}
