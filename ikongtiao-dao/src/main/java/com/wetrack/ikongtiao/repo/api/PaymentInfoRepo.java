package com.wetrack.ikongtiao.repo.api;

import com.wetrack.ikongtiao.domain.PaymentInfo;

/**
 * Created by zhanghong on 16/3/10.
 */
public interface PaymentInfoRepo {
    PaymentInfo findByMatch(PaymentInfo paymentInfo);

    PaymentInfo create(PaymentInfo paymentInfo);

    void update(PaymentInfo paymentInfo);
}
