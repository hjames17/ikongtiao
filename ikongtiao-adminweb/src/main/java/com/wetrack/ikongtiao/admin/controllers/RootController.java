package com.wetrack.ikongtiao.admin.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by zhanghong on 15/12/28.
 */
@Controller
public class RootController {

    @ResponseBody
    @RequestMapping(value = "/" , method = {RequestMethod.GET})
    public String hello() {
        return "hello admin!";
    }
}
