package com.wetrack.wechat.controller;

import com.wetrack.base.container.ContainerContext;
import com.wetrack.base.result.AjaxResult;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
public class ApiController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    protected WxMpConfigStorage weixinConfigStorage;
    @Autowired
    protected WxMpService weixinService;
    @Autowired
    protected WxMpMessageRouter wexinMessageRouter;

    @RequestMapping(value = "/menu/create" , method = {RequestMethod.POST, RequestMethod.GET})
    public AjaxResult<String> weixinMessage(HttpServletRequest request, HttpServletResponse response) throws Exception{
        AjaxResult<String> result = new AjaxResult<String>();
//      InputStream is = ClassLoader.getSystemResourceAsStream("classpath:/menu.json");
        Resource resource = ContainerContext.get().getContext().getResource("classpath:/menu.json");
        InputStream is = resource.getInputStream();
        WxMenu menu = WxMenu.fromJson(is);
        weixinService.menuCreate(menu);
        result.setMessage("菜单创建完成: ");
        return result;
    }

}
