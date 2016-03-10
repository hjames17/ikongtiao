package com.wetrack.wechat.controller;

import com.wetrack.wechat.WeixinServiceProvider;
import com.wetrack.wechat.service.WechatMessageRouterProvider;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.util.StringUtils;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
public class EndPointController {

    private static final Logger log = LoggerFactory.getLogger(EndPointController.class);

    private static final String WEIXIN = "/";
    private final static String REQUEST_DEFAULT_JSSDKCONFIG_PARAMS = "/jssdkconfig";

    @Autowired
    protected WxMpConfigStorage weixinConfigStorage;
    @Autowired
    protected WxMpService weixinService;
    @Autowired
    protected WxMpMessageRouter wexinMessageRouter;

    @RequestMapping(value = WEIXIN , method = {RequestMethod.POST, RequestMethod.GET})
    public void weixinMessage(HttpServletRequest request, HttpServletResponse response) throws Exception{

        response.setContentType("text/html;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        String signature = request.getParameter("signature");
        String nonce = request.getParameter("nonce");
        String timestamp = request.getParameter("timestamp");

//        WxMpService weixinService = weixinServiceProvider.getWeixinService(appId);

        if (!weixinService.checkSignature(timestamp, nonce, signature)) {
            // 消息签名不正确，说明不是公众平台发过来的消息
            response.getWriter().println("非法请求");
            return;
        }

        String echostr = request.getParameter("echostr");
        if (StringUtils.isNotBlank(echostr)) {
            // 说明是一个仅仅用来验证的请求，回显echostr
            response.getWriter().println(echostr);
            return;
        }

        String encryptType = StringUtils.isBlank(request.getParameter("encrypt_type")) ?
                "raw" :
                request.getParameter("encrypt_type");

        if ("raw".equals(encryptType)) {
            // 明文传输的消息
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(request.getInputStream());
            WxMpXmlOutMessage outMessage = wexinMessageRouter.route(inMessage);
            if (outMessage != null) {
                response.getWriter().write(outMessage.toXml());
            }
            return;
        }

        if ("aes".equals(encryptType)) {
            // 是aes加密的消息
            String msgSignature = request.getParameter("msg_signature");
            WxMpXmlMessage inMessage = WxMpXmlMessage.fromEncryptedXml(request.getInputStream(), weixinConfigStorage, timestamp, nonce, msgSignature);
            WxMpXmlOutMessage outMessage = wexinMessageRouter.route(inMessage);
            response.getWriter().write(outMessage.toEncryptedXml(weixinConfigStorage));
            return;
        }

        response.getWriter().println("不可识别的加密类型");
        return;
    }

    @ResponseBody
    @RequestMapping(value = REQUEST_DEFAULT_JSSDKCONFIG_PARAMS, method = RequestMethod.GET)
    WxJsapiSignature defaultjssdkconfigparams(
            @RequestParam(required = true, value = "url") String url,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {

//            WxMpService weixinService = weixinServiceProvider.getWeixinService(appId);

            return weixinService.createJsapiSignature(url);

    }



}
