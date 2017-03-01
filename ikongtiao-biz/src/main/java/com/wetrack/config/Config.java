package com.wetrack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.service.TokenService;
import studio.wetrack.accountService.auth.service.TokenStorageService;
import studio.wetrack.accountService.auth.service.impl.TokenRedisStorageService;

/**
 * Created by zhanghong on 16/11/12.
 */
@Configuration
@ComponentScan({"com.wetrack", "studio.wetrack"})
public class Config {

    @Autowired
    @Qualifier("tokenRedisTemplate")
    RedisTemplate<String, Token> tokenRedisTemplate;

    @Bean
    public TokenStorageService tokenStorageService() {
        return new TokenRedisStorageService(tokenRedisTemplate);
    }

    @Bean
    public TokenService tokenService() {
        return new TokenService(tokenStorageService());
    }
}
