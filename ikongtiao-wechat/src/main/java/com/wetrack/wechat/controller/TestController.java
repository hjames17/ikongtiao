package com.wetrack.wechat.controller;

import com.wetrack.ikongtiao.domain.admin.User;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
@ResponseBody
//@AjaxResponseWrapper
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    protected WxMpConfigStorage weixinConfigStorage;
    @Autowired
    protected WxMpService weixinService;
//    @Autowired
//    protected WxMpMessageRouter wexinMessageRouter;

    static final String BASE_PATH = "/menu";

    @RequestMapping(value = BASE_PATH + "/test" , method = {RequestMethod.POST, RequestMethod.GET})
    public String test(@RequestBody Map<String, Object> model) throws Exception{

        return "菜单创建完成";
    }

    @RequestMapping(value = BASE_PATH + "/user" , method = {RequestMethod.POST, RequestMethod.GET})
    public User testUser() throws Exception{
        User u = new User();
        u.setEmail("a@b.c");
        return u;
    }

    @RequestMapping(value = BASE_PATH + "/ex" , method = {RequestMethod.POST, RequestMethod.GET})
    public User testEx() throws Exception{
        throw new Exception("跑了跑了！");
    }


}
