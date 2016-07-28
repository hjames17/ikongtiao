package com.wetrack.wechat.controller;

import com.thoughtworks.xstream.XStream;
import com.wetrack.base.utils.common.IpUtils;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.BusinessSettings;
import com.wetrack.ikongtiao.domain.PaymentInfo;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.PaymentService;
import com.wetrack.ikongtiao.service.api.SettingsService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import com.wetrack.ikongtiao.utils.time.DateTool;
import com.wetrack.message.MessageService;
import com.wetrack.wechat.config.Const;
import me.chanjar.weixin.common.util.xml.XStreamInitializer;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpPayCallback;
import me.chanjar.weixin.mp.bean.result.WxMpPayResult;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
@ResponseBody
//@RequestMapping("/pay")
public class PayController implements InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(PayController.class);

    @Autowired
    protected WxMpService weixinService;

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    PaymentService paymentService;

    @Autowired
    SettingsService settingsService;

    @Autowired
    MessageService messageService;

    String weixinHandlerUrl;

    private static final String MSG_SUCCESS = "SUCCESS";
    private static final String MSG_FAIL = "FAIL";
    private static final String MSG_OK = "OK";

    @RequestMapping(value = "/pay/sign", method = {RequestMethod.POST})
    public Map<String, String> sign(@RequestBody PrepayForm form, HttpServletRequest request) throws Exception{
        Map<String, String> packageParams = new HashMap<String, String>();
        UserInfo userInfo = userInfoService.getBasicInfoById(form.getUid());
        packageParams.put("body", form.getOrderId()); //自定义描述
        packageParams.put("out_trade_no", form.getOrderId());
        packageParams.put("total_fee", form.getMoney().toString());
        packageParams.put("spbill_create_ip", IpUtils.getIpAddr(request));
        packageParams.put("notify_url", weixinHandlerUrl + Const.PAY_CALLBACK_PATH);
        packageParams.put("trade_type", "JSAPI");
        packageParams.put("openid", userInfo.getWechatOpenId());

        return weixinService.getJSSDKPayInfo(packageParams);
    }

    @RequestMapping(value = Const.PAY_CALLBACK_PATH, method = {RequestMethod.POST})
    public String wechatPayNotify(HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {
        WxMpPayCallback payCallback = weixinService.getJSSDKCallbackData(IOUtils.toString(request.getInputStream()));

        CallbackReturn cbro = new CallbackReturn();
        cbro.setReturn_code(MSG_FAIL);
        if(payCallback == null){
            log.warn("微信支付回调通知数据无法解析");
            throw new BusinessException("无法解析的回调数据");
        }

        //首先校验签名，以防伪造
        if(!weixinService.checkJSSDKCallbackDataSignature(getPayCallbackDataMap(payCallback), payCallback.getSign())) {
            log.warn("微信支付回调通知签名验证未通过");
            cbro.setReturn_msg("签名验证未通过");
        }else {
            if (MSG_FAIL.equals(payCallback.getReturn_code())) {
                // 系统错误
                log.error("微信支付回调通知：支付失败!支付单{}, 错误信息为：{}", payCallback.getOut_trade_no(), payCallback.getReturn_msg());
                cbro.setReturn_msg("系统错误，错误代码" + payCallback.getReturn_code());
            } else {
                if (MSG_SUCCESS.equals(payCallback.getResult_code())) {
                    // 返回正确
                    String orderId = payCallback.getOut_trade_no();
                    if (StringUtils.isEmpty(orderId)) {
                        log.error("微信支付结果回调通知数据错误, out_trade_no为空");
                        throw new BusinessException("out_trade_no is empty");
                    }


                    PaymentInfo paymentInfo = paymentService.findByOutTradeNo(PaymentInfo.Method.WECHAT, payCallback.getOut_trade_no());
                    if (paymentInfo != null) {
                        if (!paymentInfo.getState().equals(PaymentInfo.State.SUCCESS)) {
                            if (paymentInfo.getState().equals(PaymentInfo.State.NOTPAY)) {
                                paymentInfo.setState(PaymentInfo.State.SUCCESS);
                                try {
                                    paymentInfo.setPaidTime(
                                            DateTool.parseWithTimezone(payCallback.getTime_end(), "yyyyMMddHHmmss", TimeZone.getTimeZone("GMT+8"))
                                    );
                                } catch (ParseException e) {
                                    log.warn("时间{}无法解析, 订单{}支付时间被设置为当前时间", payCallback.getTime_end(), payCallback.getOut_trade_no());
                                    paymentInfo.setPaidTime(new Date());
                                }
                                paymentService.update(paymentInfo);
                                log.info("订单{}已经完成支付，支付时间{}", paymentInfo.getOutTradeNo(), paymentInfo.getPaidTime());
                            } else {
                                log.error("订单{}状态为{}，不能变为完成支付状态", payCallback.getOut_trade_no(), paymentInfo.getState());
                            }
                        } else {
                            cbro.setReturn_code(MSG_SUCCESS);
                            cbro.setReturn_msg(MSG_OK);
                        }
                    }else{
                        PaymentInfo create = new PaymentInfo();
                        create.setMethod(PaymentInfo.Method.WECHAT);
                        create.setOutTradeNo(payCallback.getOut_trade_no());
                        create.setAmount(Integer.valueOf(payCallback.getTotal_fee()));
                        try {
                            create.setPaidTime(
                                    DateTool.parseWithTimezone(payCallback.getTime_end(), "yyyyMMddHHmmss", TimeZone.getTimeZone("GMT+8"))
                            );
                        } catch (ParseException e) {
                            log.warn("时间{}无法解析, 订单{}支付时间被设置为当前时间", payCallback.getTime_end(), payCallback.getOut_trade_no());
                            create.setPaidTime(new Date());
                        }
                        create = paymentService.create(create);
                        if(create != null) {
                            cbro.setReturn_code(MSG_SUCCESS);
                            cbro.setReturn_msg(MSG_OK);
                        }else{
                            cbro.setReturn_msg(MSG_FAIL);
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
        Field[] fields = WxMpPayCallback.class.getDeclaredFields();
        for(Field field : fields){
            if(field.getName().equals("sign")){
                continue;
            }
            try {
                field.setAccessible(true);
                String value = (String)field.get(payCallback);
                if(!StringUtils.isEmpty(value)) {
                    map.put(field.getName(), value);
                }
                field.setAccessible(false);
            } catch (IllegalAccessException e) {
                continue;
            }
        }
        return map;
    }



    @RequestMapping(value = { "pay/query/{orderId}" }, method = RequestMethod.GET)
    PaymentInfo queryOrder(@PathVariable(value = "orderId") String orderId) throws Exception{
        WxMpPayResult payResult = weixinService.getJSSDKPayResult(null, orderId);

        /**
         * 支付成功：
         * {"return_code":"SUCCESS","return_msg":"OK","appid":"wx2a8ffab08a9c655f","mch_id":"1318465701",
         *  "nonce_str":"eS8WLMZ3vQgNQr8b","sign":"2C158F2395951C0DC589FEE1798390D2","result_code":"SUCCESS",
         *  "trade_state":"SUCCESS","openid":"oTTbFwAHixiVO50htxI8QA2MTvnw","is_subscribe":"Y","trade_type":"JSAPI",
         *  "bank_type":"SPDB_CREDIT","total_fee":"1","fee_type":"CNY","transaction_id":"1007720260201603164032491868",
         *  "out_trade_no":"RO168","attach":"","time_end":"20160316161600"
         *  }
         */
        /**
         * 未支付:
         *{"return_code":"SUCCESS","return_msg":"OK","appid":"wx2a8ffab08a9c655f","mch_id":"1318465701",
         * "nonce_str":"efoDhyer13bqdRDN","sign":"D8164F336C0C3E81298C5A3D44CB1188","result_code":"SUCCESS",
         * "trade_state":"NOTPAY","out_trade_no":"RO234"
         * }
         */
        /**
         * 不存在的订单:
         * {"return_code":"SUCCESS","return_msg":"OK","appid":"wx2a8ffab08a9c655f","mch_id":"1318465701",
         * "nonce_str":"Bv7zPJUZUC1hgzxP","sign":"A19CDAB1221A48DBEB7FD4113293E952","result_code":"FAIL",
         * "err_code":"ORDERNOTEXIST","err_code_des":"order not exist"
         * }
         */
        if(payResult != null && MSG_SUCCESS.equals(payResult.getReturn_code()) && MSG_SUCCESS.equals(payResult.getResult_code())) {
            try {
                log.info("获取微信支付订单{}内容为:", Jackson.mobile().writeValueAsString(payResult));
            }catch (Exception e){
                log.info("获取微信支付订单{}，内容json解析失败， out_trade_no is {}", orderId, payResult.getOut_trade_no());
            }
            PaymentInfo paymentInfo = paymentService.findByOutTradeNo(PaymentInfo.Method.WECHAT, payResult.getOut_trade_no());
            if(paymentInfo != null){
                if(PaymentInfo.State.valueOf(payResult.getTrade_state()) == null){
                    log.error("微信支付订单{}状态无效! 内容{}", orderId, Jackson.mobile().writeValueAsString(payResult));
                }else if(!paymentInfo.getState().equals(payResult.getTrade_state())){
                    log.info("微信支付订单{}状态从{}同步到{}", orderId, paymentInfo.getState(), payResult.getTrade_state());
                    paymentInfo.setState(PaymentInfo.State.valueOf(payResult.getTrade_state()));
                    switch(PaymentInfo.State.valueOf(payResult.getTrade_state())){
                        case SUCCESS:
                            paymentInfo.setPaidTime(
                                    DateTool.parseWithTimezone(payResult.getTime_end(), "yyyyMMddHHmmss", TimeZone.getTimeZone("GMT+8"))
                            );
                            break;
                        default:
                            break;
                    }
                    paymentService.update(paymentInfo);
                }
            }
            return paymentInfo;
        }else{
            log.info("获取微信支付订单{}不存在", orderId);
            return null;
        }


    }

    @Override
    public void afterPropertiesSet() throws Exception {
        BusinessSettings settings = settingsService.getBusinessSettings();
        weixinHandlerUrl = settings.getWechatHandlerUrl();
    }


    public static class PrepayForm{
        String orderId;
        Integer money;
        String uid;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public Integer getMoney() {
            return money;
        }

        public void setMoney(Integer money) {
            this.money = money;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
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

    public static void main(String[] args){
//        WxMpPayCallback payCallback = new WxMpPayCallback();
//        payCallback.setOut_trade_no("RO234");
//        payCallback.setAppid("wx2a8ffab08a9c655f");
//        payCallback.setOpenid("16031018068700000038");
//        payCallback.setMch_id("1318465701");
//        payCallback.setSign("123123123");
//        Map<String,String> map = PayController.getPayCallbackDataMap(payCallback);
//        System.out.println(map);
    }

}
