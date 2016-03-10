package com.wetrack.wechat;

import me.chanjar.weixin.mp.api.WxMpService;

/**
 * Created by zhanghong on 16/3/10.
 */
public interface WeixinServiceProvider {

    WxMpService getWeixinService(String appId);
}
