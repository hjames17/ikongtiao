package com.wetrack.wechat.controller;

import com.thoughtworks.xstream.XStream;
import com.wetrack.base.utils.common.IpUtils;
import com.wetrack.ikongtiao.domain.PaymentInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.PaymentInfoService;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpPayCallback;
import me.chanjar.weixin.mp.bean.result.WxMpPayResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
@ResponseBody
@RequestMapping("/pay")
public class PayController {

    private static final Logger log = LoggerFactory.getLogger(PayController.class);

    @Autowired
    protected WxMpService weixinService;

    PaymentInfoService paymentInfoService;


    @RequestMapping(value = "/sign", method = {RequestMethod.POST})
    public Map<String, String> sign(@RequestBody PrepayForm form, HttpServletRequest request) throws Exception{
        Map<String, String> packageParams = new HashMap<String, String>();
        packageParams.put("body", form.getOrderId());
        packageParams.put("out_trade_no", form.getOrderId());
        packageParams.put("total_fee", form.getMoney().toString());
        packageParams.put("spbill_create_ip", IpUtils.getIpAddr(request));
//        packageParams.put("notify_url", callbackUrl);

        return weixinService.getJSSDKPayInfo(packageParams);
    }


    @RequestMapping(value = "/payCallback", method = {RequestMethod.POST})
    public String wechatPayNotify(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        WxMpPayCallback payCallback = weixinService.getJSSDKCallbackData(IOUtils.toString(request.getInputStream()));
        CallbackReturn cbro = new CallbackReturn();
        cbro.setReturn_code("FAIL");
        if(payCallback == null){
            log.warn("微信支付回调通知数据无法解析");
            throw new Exception("无法解析的回调数据");
        }
        //首先校验签名，以防伪造
        if(!weixinService.checkJSSDKCallbackDataSignature(getPayCallbackDataMap(payCallback), payCallback.getSign())) {
            log.warn("微信支付回调通知签名验证未通过");
            cbro.setReturn_msg("签名验证未通过");
        }else {
            if ("FAIL".equals(payCallback.getReturn_code())) {
                // 系统错误
                log.error("微信支付回调通知：支付失败!支付单%s, 错误信息为：%s", payCallback.getOut_trade_no(), payCallback.getReturn_msg());
                cbro.setReturn_msg("系统错误，错误代码" + payCallback.getReturn_code());
            } else {
                if ("SUCCESS".equals(payCallback.getResult_code())) {
                    // 返回正确
                    String orderId = payCallback.getOut_trade_no();
                    if (StringUtils.isEmpty(orderId)) {
                        log.error("wechat callback error, out_trade_no is empty");
                        throw new BusinessException("out_trade_no is empty");
                    }
                    //加锁，防止重入
                    synchronized (this){
                        PaymentInfo paymentInfo = paymentInfoService.findByOutTradeNo(payCallback.getOut_trade_no());
                        if(paymentInfo != null){
                            if(paymentInfo.getPayState() != PaymentInfo.State.PAID.getCode()){
                                if(paymentInfo.getPayState() == PaymentInfo.State.WAIT.getCode()){
                                    paymentInfo.setPayState(((Integer) PaymentInfo.State.PAID.getCode()).byteValue());
                                    try {
                                        paymentInfo.setPaidTime(new SimpleDateFormat().parse(payCallback.getTime_end()));
                                    } catch (ParseException e) {
                                        log.warn("时间%s无法解析, 订单%s支付时间被设置为当前时间", payCallback.getTime_end(), payCallback.getOut_trade_no());
                                        paymentInfo.setPaidTime(new Date());
                                    }                                    paymentInfoService.update(paymentInfo);
                                    log.info("订单%s已经完成支付，支付时间%s", paymentInfo.getOutTradeNo(), paymentInfo.getPaidTime());
                                }else{
                                    log.error("订单%s状态为%s，不能变为完成支付状态", payCallback.getOut_trade_no(), paymentInfo.getPayState());
                                }
                            }else{
                                cbro.setReturn_code("SUCCESS");
                                cbro.setReturn_msg("OK");
                            }
                        }
                    }
                } else {
                    // 业务错误
                    log.error("业务错误，错误代码：" + payCallback.getErr_code() + ";错误原因：" + payCallback.getErr_code_des());
                    cbro.setReturn_msg("业务错误，错误代码" + payCallback.getErr_code());
                }
            }
        }

        XStream xstream = XStreamInitializer.getInstance();
        /**
         * alias让xml根节点名称由
         * <com.wetrac.wechat.controller.PayController.CallbackReturn></com.wetrac.wechat.controller.PayController.CallbackReturn>
         * 变为
         * <xml></xml>
         */
        xstream.alias("xml", CallbackReturn.class);
        return xstream.toXML(cbro);

    }


    private Map<String,String> getPayCallbackDataMap(WxMpPayCallback payCallback) {
        Map<String, String > map = new HashMap<String, String>();
        return map;
    }

    @RequestMapping(value = { "/v2/order/query/{orderId}" }, method = RequestMethod.GET)
    PaymentInfo queryOrder(@PathVariable(value = "orderId") String orderId){
        WxMpPayResult payResult = weixinService.getJSSDKPayResult(null, orderId);

        synchronized (this){
            PaymentInfo paymentInfo = paymentInfoService.findByOutTradeNo(payResult.getOut_trade_no());
            if(paymentInfo != null){
                if(paymentInfo.getPayState() != PaymentInfo.State.PAID.getCode()){
                    if(paymentInfo.getPayState() == PaymentInfo.State.WAIT.getCode()){
                        paymentInfo.setPayState(((Integer)PaymentInfo.State.PAID.getCode()).byteValue());
                        try {
                            paymentInfo.setPaidTime(new SimpleDateFormat().parse(payResult.getTime_end()));
                        } catch (ParseException e) {
                            log.warn("时间%s无法解析, 订单%s支付时间被设置为当前时间", payResult.getTime_end(), payResult.getOut_trade_no());
                            paymentInfo.setPaidTime(new Date());
                        }
                        paymentInfoService.update(paymentInfo);
                        log.info("订单%s已经完成支付，支付时间%s", paymentInfo.getOutTradeNo(), paymentInfo.getPaidTime());
                    }else{
                        log.error("订单%s状态为%s，不能变为完成支付状态", payResult.getOut_trade_no(), paymentInfo.getPayState());
                    }
                }else{
                }
            }
            return paymentInfo;
        }

    }


    public static class PrepayForm{
        String orderId;
        Double money;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Double getMoney() {
            return money;
        }

        public void setMoney(Double money) {
            this.money = money;
        }
    }

    public static class CallbackReturn{
        String return_code;
        String return_msg;

        public String getReturn_code() {
            return return_code;
        }

        public void setReturn_code(String return_code) {
            this.return_code = return_code;
        }

        public String getReturn_msg() {
            return return_msg;
        }

        public void setReturn_msg(String return_msg) {
            this.return_msg = return_msg;
        }
    }

}
