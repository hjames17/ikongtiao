package com.wetrack.ikongtiao.service.api;

import com.wetrack.ikongtiao.domain.PaymentInfo;

/**
 * Created by zhanghong on 16/3/8.
 */
public interface PaymentService {

    PaymentInfo create(PaymentInfo.Method method, PaymentInfo.Type type, String orderId, int payAmount) throws Exception;
    PaymentInfo create(PaymentInfo paymentInfo) throws Exception;

    void update(PaymentInfo paymentInfo) throws Exception;

    void closed(PaymentInfo.Method method, PaymentInfo.Type type, String orderId) throws Exception;

    PaymentInfo findByOutTradeNo(PaymentInfo.Method method, String outTradeNo);

    boolean getOperationLock(String paymentKey, String operation);

    void unlock(String paymentKey);

}
