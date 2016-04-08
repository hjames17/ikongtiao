package com.wetrack.ikongtiao.aspect;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.domain.User;
import com.wetrack.auth.service.TokenService;
import com.wetrack.message.MessageService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/4/6.
 */
@Aspect
@Service
public class LoginAspect {

    @Autowired
    MessageService messageService;

    //连接所tokenService中所有login方法
    @AfterReturning(value = "execution(com.wetrack.auth.domain.Token com.wetrack.auth.service.TokenService.login(..))", returning = "token")
    public void afterLogin(JoinPoint jp, Token token){
        TokenService tokenService = (TokenService)jp.getTarget();

        if(tokenService.isOnlyOnePermitted()){
            User user = token.getUser();

        }
    }
}
