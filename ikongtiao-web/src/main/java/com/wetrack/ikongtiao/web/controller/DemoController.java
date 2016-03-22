package com.wetrack.ikongtiao.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangsong on 15/8/20.
 */
@Deprecated
//@Controller
public class DemoController {

//    @Resource
//    WechatTokenService wechatTokenService;
    @RequestMapping("/we/demo")
    @ResponseBody
    public String demo(@RequestParam(value="userName")String userName,@RequestParam(value="pwd")String pwd){
        return userName+pwd;
    }

}
