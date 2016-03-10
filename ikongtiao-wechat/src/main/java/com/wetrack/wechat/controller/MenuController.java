package com.wetrack.wechat.controller;

import com.wetrack.wechat.service.WeixinMenuGenerator;
import me.chanjar.weixin.common.bean.WxMenu;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
@ResponseBody
public class MenuController {

//    @Autowired
//    WeixinServiceProvider weixinServiceProvider;

    private static final Logger log = LoggerFactory.getLogger(MenuController.class);

//    @Autowired
//    protected WxMpConfigStorage weixinConfigStorage;
    @Autowired
    protected WxMpService weixinService;
//    @Autowired
//    protected WxMpMessageRouter wexinMessageRouter;

    static final String BASE_PATH = "/menu";


    @Autowired
    WeixinMenuGenerator weixinMenuGenerator;
//    @AjaxResponseWrapper
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST, RequestMethod.GET})
    public String create(HttpServletRequest request) throws Exception{
//        WxMpService weixinService = weixinServiceProvider.getWeixinService(appId);
        WxMenu menu = weixinMenuGenerator.generateMenu();
        weixinService.menuCreate(menu);
        return "菜单创建完成";
    }




}
