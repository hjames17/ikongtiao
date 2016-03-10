package com.wetrack.wechat;

import com.wetrack.ikongtiao.domain.wechat.WechatPublicAccount;
import com.wetrack.ikongtiao.repo.api.wechat.WechatPublicAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/3/10.
 */
@Service
public class WechatPublicAccountServiceImpl implements WechatPublicAccountService{

    @Autowired
    WechatPublicAccountRepo wechatPublicAccountRepo;

    @Override
    public WechatPublicAccount create(String appId, String appSecret, String aesKey, String token, String wechatId, String name) throws Exception {
        return wechatPublicAccountRepo.create(appId, appSecret, aesKey, token, wechatId, name);
    }

    @Override
    public WechatPublicAccount findByAppId(String appId) {
        return wechatPublicAccountRepo.findByAppId(appId);
    }
}
