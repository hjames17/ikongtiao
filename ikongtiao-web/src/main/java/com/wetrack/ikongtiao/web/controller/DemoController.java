package com.wetrack.ikongtiao.web.controller;

import com.wetrack.base.result.AjaxResult;
import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhangsong on 15/8/20.
 */
@Deprecated
@Controller
public class DemoController {

//    @Resource
//    WechatTokenService wechatTokenService;
    @RequestMapping("/we/demo")
    @ResponseBody
    public String demo(@RequestParam(value="userName")String userName,@RequestParam(value="pwd")String pwd){
        return userName+pwd;
    }

    @RequestMapping(value = "/demo")  //,produces = "application/json;charset=UTF-8"
    @ResponseBody
    public AjaxResult<String> demoVo(@RequestParam(value="id")Integer id){
        //UserAddress userAddress = userAddressService.findByUserId(id);
        String result = Utils.get(HttpExecutor.class).get(
                "http://int.xiaokakeji.com:8090/ins/business/team/list/1.4?refId=19454&debugMode=1&type=1").executeAsString();
        System.out.println(result);
        /*String s = Jackson.base().writeValueAsString(userAddress);
        System.out.println(s);*/
//        System.out.println("token:"+wechatTokenService.getToken());
        return new AjaxResult<String>("");//(wechatTokenService.getToken());
    }
}
