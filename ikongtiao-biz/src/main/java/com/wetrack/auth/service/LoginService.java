package com.wetrack.auth.service;

import com.wetrack.auth.domain.Token;

/**
 * Created by zhanghong on 15/11/18.
 * @业务无关的通用框架接口
 * 基于token的权限控制的用户登录接口
 * 提供登录和登出服务
 */
public interface LoginService {

    Token login(String userId, String password);

    boolean logout(String userId);
}
