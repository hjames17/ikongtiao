package com.wetrack.wechat.controller;

import com.wetrack.base.container.ContainerContext;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
@ResponseBody
public class MenuController {

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    protected WxMpConfigStorage weixinConfigStorage;
    @Autowired
    protected WxMpService weixinService;
    @Autowired
    protected WxMpMessageRouter wexinMessageRouter;

    static final String BASE_PATH = "/menu";

//    @AjaxResponseWrapper
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST, RequestMethod.GET})
    public String create() throws Exception{
        Resource resource = ContainerContext.get().getContext().getResource("classpath:/menu.json");
        InputStream is = resource.getInputStream();
        WxMenu menu = WxMenu.fromJson(is);
        weixinService.menuCreate(menu);
        return "菜单创建完成";
    }



    public static void main(String[] args) throws UnsupportedEncodingException {
        String api = "http://weixin.weiwaisong.com/wechat/redirect";
        String appId = "wxfb738dfd1f310531";
        String state = String.format("A:%s", "Mission");


        String params = String.format("appid=%s&redirect_uri=%s&response_type=code&scope=snsapi_base&state=%s#wechat_redirect",
                URLEncoder.encode(appId, "utf-8"), URLEncoder.encode(api, "utf-8"), URLEncoder.encode(state, "utf-8"));
        System.out.println(URLEncoder.encode(params, "utf-8"));

        String weixinAuthEntry = "https://open.weixin.qq.com/connect/oauth2/authorize";
        String url = String.format("%s?%s", weixinAuthEntry, params);

        System.out.println(url);
    }


}
