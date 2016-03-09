package com.wetrack.ikongtiao.service.api;

import com.wetrack.ikongtiao.domain.PaymentInfo;

/**
 * Created by zhanghong on 16/3/8.
 */
public interface PaymentInfoService {

    PaymentInfo create(PaymentInfo.Method method, PaymentInfo.Type type, String orderId, int payAmount);
    PaymentInfo findByOrderId(PaymentInfo.Type type, String orderId);

    PaymentInfo update(PaymentInfo paymentInfo);

    void paid(PaymentInfo.Type type, String orderId);
    void closed(PaymentInfo.Type type, String orderId);
    void refund(PaymentInfo.Type type, String orderId);

    PaymentInfo findByOutTradeNo(String outTradeNo);
}
