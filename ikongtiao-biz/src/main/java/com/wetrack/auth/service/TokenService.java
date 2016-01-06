package com.wetrack.auth.service;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.domain.User;
import com.wetrack.base.utils.common.UUIDGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;

/**
 * Created by zhanghong on 15/11/18.
 * @业务无关的通用框架
 * token管理器
 * 支持单用户多个token管理
 * TODO token自动清理
 */
@Service
public class TokenService {


    @Resource(name="defaultTokenStorageService")
    TokenStorageService tokenStorageService;

    public Token login(User user){
        return doLogin(user);
    }

    public Token login(String id, String password){
        return doLogin(new User(id, password, User.ROLE_FULL));
    }

    private Token doLogin(User user){
        //found one valid token for this user
        Collection<Token> tokens = tokenStorageService.findAllByUserId(user.getId());
        if(tokens != null){
            for(Token token : tokens){
                if(!token.isExpired() && !token.isLoggedout()){
                    return token;
                }
            }
        }
        //otherwise, create one
        String tokenString = UUIDGenerator.generate().toUpperCase();
        Token token = new Token(tokenString, user);
        addToken(token);
        return token;
    }

    public void logout(String token){
        removeByTokenString(token);
    }

    private boolean addToken(Token token){
        return tokenStorageService.addToken(token);
    }

    private Token removeByTokenString(String tokenString){
        return tokenStorageService.removeByTokenString(tokenString);
    }

    public Token findByTokenString(String tokenString){
        return  tokenStorageService.findByTokenString(tokenString);
    }

    public Collection<Token> findAllByUserId(String userId){
        return tokenStorageService.findAllByUserId(userId);
    }


}
