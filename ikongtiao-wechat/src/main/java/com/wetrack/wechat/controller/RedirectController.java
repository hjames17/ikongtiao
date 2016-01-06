package com.wetrack.wechat.controller;

import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpOAuth2AccessToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
public class RedirectController {

    private static final Logger log = LoggerFactory.getLogger(RedirectController.class);

    @Value("${weixin.page.host}")
    String host;
    @Value("${weixin.page.mission}")
    String mission;
    @Value("${weixin.page.setting}")
    String setting;

    private static final String WEIXIN_REDIRECT = "/redirect";

    @Autowired
    protected WxMpConfigStorage weixinConfigStorage;
    @Autowired
    protected WxMpService weixinService;
    @Autowired
    protected WxMpMessageRouter wexinMessageRouter;

    @Autowired
    UserInfoService userInfoService;



    enum Action{
        MISSION_LIST(1),
        MISSION_NEW(2),
        USER_SETTING(3),
        ;

        int value;
        Action(int value){
            this.value = value;
        }
    }

    private class State{

        State(String stateString){
            parseFromString(stateString);
        }

        static final String W = "W"; //代表微信公众号在数据库里的id
        static final String A = "A"; //代表请求的动作
        static final String AD = "AD";
        static final String DELIMITER = ",";
        static final String PARTDELIMITER = ":";
        void parseFromString(String stateString){
            if(stateString == null || stateString.length() == 0 ){
                return;
            }

            String[] pairs = stateString.split(DELIMITER);
            if(pairs != null){
                for(String pair : pairs){
                    String[] kv = pair.split(PARTDELIMITER);
                    if(kv != null && kv.length >= 2){
                        if(kv[0].equals(W)){
                            wxpaId = kv[1];
                        }else if(kv[0].equals(A)){
                            action = Action.valueOf(kv[1]);
                        }else if(kv[0].equals(AD)){
                            addtional = kv[1];
                        }
                    }
                }
            }
        }

        String wxpaId;
        Action action;
        String addtional;


    }


    @RequestMapping(value = WEIXIN_REDIRECT, method = RequestMethod.GET)
    void WeixinRedirect(@RequestParam(required = true, value = "state") String stateString
            , @RequestParam(required = true, value = "code") String code,
                        HttpServletResponse response){

        State state = new State(stateString);
        WxMpOAuth2AccessToken token = null;
        try {
            token = weixinService.oauth2getAccessToken(code);
        } catch (WxErrorException e) {
            log.error("oauth2token get failed! " + e.getMessage());
            try {
                response.getWriter().write("oauth2token get failed! " + e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
            return;
        }
        try {
            if(token != null) {
                String redirectUrl = null;
                UserInfo userInfo = userInfoService.getByWeChatOpenId(token.getOpenId());
                if(userInfo == null){
                    userInfo = userInfoService.CreateFromWeChatOpenId(token.getOpenId());
                }
                switch (state.action){
                    case MISSION_NEW:
                        redirectUrl = host + mission + "?action=new&uid=" + userInfo.getId();
                        break;
                    case USER_SETTING:
                        redirectUrl = host + setting + "?uid=" + userInfo.getId();
                        break;
                    case MISSION_LIST:
                    default:
                        redirectUrl = host + mission + "?action=list&uid=" + userInfo.getId();

                }
                response.sendRedirect(redirectUrl);
                return;
            }
        } catch (IOException e) {
            log.error("open page get failed! " + e.getMessage());
            try {
                response.getWriter().write("open page get faile! " + e.getMessage());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

}