package com.wetrack.ikongtiao.repo.api.wechat;

import com.wetrack.ikongtiao.domain.wechat.WechatWelcomeNews;

import java.util.List;

/**
 * Created by zhanghong on 16/3/10.
 */
public interface WechatWelcomeNewsRepo {
    WechatWelcomeNews create(WechatWelcomeNews news) throws Exception;
    List<WechatWelcomeNews> findAll();
}
