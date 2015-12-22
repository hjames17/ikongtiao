package com.wetrack.auth.service;

import com.wetrack.auth.domain.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collection;

/**
 * Created by zhanghong on 15/11/21.
 * redis中存储token
 */

//@Service("defaultTokenStorageService")
public class TokenRedisStorageService implements TokenStorageService {


    static final String TOKEN_HASH_KEY = "TOKEN_HASH";
    static final String USER_TOKEN_HASH_KEY_PREFIX = "USER_TOKEN_HASH_";

    RedisTemplate<String, Token> redisTemplate;
    BoundHashOperations<String, String, Token> tokenHashOps;

    @Autowired
    public TokenRedisStorageService(RedisTemplate<String, Token> redisTemplate){
        this.redisTemplate = redisTemplate;
        tokenHashOps = redisTemplate.boundHashOps(TOKEN_HASH_KEY);
    }

    @Override
    public boolean addToken(Token token) {
        tokenHashOps.put(token.getToken(), token);
        BoundHashOperations<String, String, Token> userHashOps = redisTemplate.boundHashOps(USER_TOKEN_HASH_KEY_PREFIX + token.getUser().getId());
        userHashOps.put(token.getToken(), token);
        return true;
    }

    @Override
    public Token removeByTokenString(String tokenString) {
        Token token = tokenHashOps.get(tokenString);
        tokenHashOps.delete(tokenString);
        BoundHashOperations<String, String, Token> userHashOps = redisTemplate.boundHashOps(USER_TOKEN_HASH_KEY_PREFIX + token.getUser().getId());
        userHashOps.delete(tokenString);
        return token;
    }

    @Override
    public Token findByTokenString(String tokenString) {
        return tokenHashOps.get(tokenString);
    }

    @Override
    public Collection<Token> findAllByUserId(String userId) {
        BoundHashOperations<String, String, Token> userHashOps = redisTemplate.boundHashOps(USER_TOKEN_HASH_KEY_PREFIX + userId);
        return userHashOps.values();
    }
}
