package com.wetrack.ikongtiao.aspect;


import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.domain.User;
import studio.wetrack.accountService.auth.service.TokenService;

/**
 * Created by zhanghong on 16/4/6.
 */
//@Aspect
//@Service
public class LoginAspect {

//    @Autowired
//    MessageService messageService;

    //连接所tokenService中所有login方法
    @AfterReturning(value = "execution(com.wetrack.auth.domain.Token com.wetrack.auth.service.TokenService.login(..))", returning = "token")
    public void afterLogin(JoinPoint jp, Token token){
        TokenService tokenService = (TokenService)jp.getTarget();

        if(tokenService.isOnlyOnePermitted()){
            User user = token.getUser();

        }
    }
}
