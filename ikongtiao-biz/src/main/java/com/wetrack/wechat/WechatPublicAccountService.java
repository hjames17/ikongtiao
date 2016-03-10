package com.wetrack.wechat;

import com.wetrack.ikongtiao.domain.wechat.WechatPublicAccount;

/**
 * Created by zhanghong on 16/3/10.
 */
public interface WechatPublicAccountService {

    WechatPublicAccount create(String appId, String appSecret, String aesKey, String token, String wechatId, String name) throws Exception;
    WechatPublicAccount findByAppId(String appId);
}
