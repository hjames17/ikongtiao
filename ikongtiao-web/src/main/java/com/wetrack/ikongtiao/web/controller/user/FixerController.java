package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;


/**
 * Created by zhanghong on 16/1/4.
 */

@Controller("userFixerController")
public class FixerController {


    @Value("${file.location.images}")
    String imageLocation;

    @Value("${host.static}")
    String host;

    @Value("${wechat.app.token}")
    String token;

    @Autowired
    FixerService fixerService;

    private static final String BASE_PATH = "/u/fixer";


    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/info", method = RequestMethod.GET)
    Fixer info(HttpServletRequest request, @RequestParam(value = "fixerId") Integer fixerId) throws Exception{
        Fixer fixer = fixerService.getFixer(fixerId);
        if(fixer != null){
            fixer.setPassword(null);
        }
        return fixer;
    }



}
