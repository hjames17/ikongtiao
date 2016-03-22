package com.wetrack.ikongtiao.aspect;

import org.aspectj.lang.annotation.Aspect;

/**
 * Created by zhanghong on 16/3/22.
 */
@Aspect
public class RepairOrderStateAspect {

    /**
     * 切点切入维修单创建，维修单报价， 维修单确认，维修单配件就绪， 维修单指派， 维修单完成
     */
    public void stateChanged(){

    }

    /**
     * 切点切入客服对维修单进行的操作
     */
    public void kefuOperation(){

    }

    /**
     * 切点切入维修单支付消息
     */
    public void paymentMessage(){

    }
}
