package com.wetrack.ikongtiao.service.impl;

import com.wetrack.ikongtiao.domain.PaymentInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.repo.api.PaymentInfoRepo;
import com.wetrack.ikongtiao.service.api.PaymentService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 16/3/10.
 */
@Service
public class PaymentServiceImpl implements PaymentService, InitializingBean {
    @Autowired
    PaymentInfoRepo paymentInfoRepo;

    static final String PAYMENT_LOCK_HASH = "PAYMENT_LOCK_HASH";

    @Autowired
    WxMpService weixinService;

    @Autowired
    MessageService messageService;


    @Autowired
    @Qualifier("redisTemplate")
    RedisTemplate<String, String> redisTemplate;
    BoundHashOperations<String, String, String> tokenHashOps;

    @Override
    public PaymentInfo create(PaymentInfo.Method method, PaymentInfo.Type type, String orderId, int payAmount) throws Exception{


        String paymentKey = String.format("%s%s%s", method, type, orderId);
        if(!getOperationLock(paymentKey, "cr")){
            throw new BusinessException("多次创建的支付信息: " + paymentKey);
        }

        try {
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setMethod(method);
            paymentInfo.setOutTradeNo(type + orderId);
            paymentInfo.setAmount(payAmount);
            PaymentInfo existed = paymentInfoRepo.findByMatch(paymentInfo);
            if(existed != null){
                if(existed.getState().compareTo(PaymentInfo.State.NOTPAY) > 0){
                    throw new BusinessException("订单已经完成或者关闭，不能再次创建:" + paymentKey);
                }else{
                    return existed;
                }
            }

            PaymentInfo created = paymentInfoRepo.create(paymentInfo);

            return created;
        }catch (Exception e){
            throw e;
        }finally {
            unlock(paymentKey);
        }

    }

    @Override
    public PaymentInfo create(PaymentInfo paymentInfo) throws Exception {
        if(paymentInfo.getMethod() == null || paymentInfo.getOutTradeNo() == null || paymentInfo.getAmount() == null){
            throw new BusinessException("订单缺少必要字段,不能创建");
        }

        String paymentKey = String.format("%s%s", paymentInfo.getMethod(), paymentInfo.getOutTradeNo());
        if(!getOperationLock(paymentKey, "cr")){
            throw new BusinessException("多次创建的支付信息: " + paymentKey);
        }

        try {
            PaymentInfo existed = paymentInfoRepo.findByMatch(paymentInfo);
            if (existed != null) {
                if (existed.getState().compareTo(PaymentInfo.State.NOTPAY) > 0) {
                    throw new BusinessException("订单已经完成或者关闭，不能再次创建:" + paymentKey);
                } else {
                    return existed;
                }
            }
            PaymentInfo created = paymentInfoRepo.create(paymentInfo);

            checkSendMessage(paymentInfo);

            return created;
        }catch (Exception e){
            throw e;
        }finally {
            unlock(paymentKey);
        }

    }

    private void checkSendMessage(PaymentInfo paymentInfo){
        if(paymentInfo.getState() == PaymentInfo.State.SUCCESS) {
            Map<String, Object> params = new HashMap<String, Object>();
            //TODO 目前只有一种订单类型，头部都是RO,以后可能会扩展
            params.put(MessageParamKey.REPAIR_ORDER_ID, paymentInfo.getOutTradeNo().substring("RO".length()));
            messageService.send(MessageId.REPAIR_ORDER_PAID, params);
        }
    }

    @Override
    public void update(PaymentInfo paymentInfo) throws Exception{
        String paymentKey = String.format("%s%s", paymentInfo.getMethod(), paymentInfo.getOutTradeNo());
        if(!getOperationLock(paymentKey, "w")){
            throw new BusinessException("重复操作支付订单: " + paymentKey);
        }
        try{
            paymentInfoRepo.update(paymentInfo);
        }catch (Exception e){
            throw e;
        }finally {
            unlock(paymentKey);
        }

        checkSendMessage(paymentInfo);
    }

    @Override
    public void closed(PaymentInfo.Method method, PaymentInfo.Type type, String orderId) throws Exception{
        String paymentKey = String.format("%s%s%s", method, type, orderId);
        if(!getOperationLock(paymentKey, "w")){
            throw new BusinessException("重复操作支付订单: " + paymentKey);
        }

        try{
            PaymentInfo paymentInfo = new PaymentInfo();
            paymentInfo.setOutTradeNo(type + orderId);
            paymentInfo.setMethod(method);
            PaymentInfo found = paymentInfoRepo.findByMatch(paymentInfo);
            if(found != null){
                if(found.getState().equals(PaymentInfo.State.SUCCESS)){
                    //TODO 退款发起
                }else if(found.getState().equals(PaymentInfo.State.NOTPAY)){
                    paymentInfo.setState(PaymentInfo.State.CLOSED);
                    paymentInfo.setCloseTime(new Date());
                }else{
                    throw new BusinessException(String.format("支付订单处于%s状态，不能关闭:%s", found.getState(), paymentKey));
                }
            }
        }catch (Exception e) {
            throw e;
        }finally {
            unlock(paymentKey);
        }
    }

    @Override
    public PaymentInfo findByOutTradeNo(PaymentInfo.Method method, String outTradeNo) {
        PaymentInfo paymentInfo = new PaymentInfo();
        paymentInfo.setMethod(method);
        paymentInfo.setOutTradeNo(outTradeNo);
        return paymentInfoRepo.findByMatch(paymentInfo);
    }

    @Override
    public boolean getOperationLock(String paymentKey, String operation) {
        return tokenHashOps.putIfAbsent(paymentKey, operation);
    }

    @Override
    public void unlock(String paymentKey) {
        tokenHashOps.delete(paymentKey);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        tokenHashOps = redisTemplate.boundHashOps(PAYMENT_LOCK_HASH);
    }
}
