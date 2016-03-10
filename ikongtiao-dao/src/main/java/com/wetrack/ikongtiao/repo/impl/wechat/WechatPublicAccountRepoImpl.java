package com.wetrack.ikongtiao.repo.impl.wechat;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.wechat.WechatPublicAccount;
import com.wetrack.ikongtiao.repo.api.wechat.WechatPublicAccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/3/10.
 */
@Service
public class WechatPublicAccountRepoImpl implements WechatPublicAccountRepo {
    @Autowired
    CommonDao commonDao;
    @Override
    public WechatPublicAccount create(String appId, String appSecret, String aesKey, String token, String wechatId, String name) throws Exception {
        WechatPublicAccount wechatPublicAccount = new WechatPublicAccount();
        wechatPublicAccount.setAppId(appId);
        wechatPublicAccount.setAppSecret(appSecret);
        wechatPublicAccount.setAesKey(aesKey);
        wechatPublicAccount.setToken(token);
        wechatPublicAccount.setWechatId(wechatId);
        wechatPublicAccount.setName(name);

        if(1 == commonDao.mapper(WechatPublicAccount.class).sql("insertSelective").session().insert(wechatPublicAccount)){
            return wechatPublicAccount;
        }else{
            throw new Exception("没有保存成功");
        }
    }

    @Override
    public WechatPublicAccount findByAppId(String appId) {
        return commonDao.mapper(WechatPublicAccount.class).sql("selectByAppId").session().selectOne(appId);
    }
}
