package com.wetrack.wechat.service;

import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;

/**
 * Created by zhanghong on 16/3/10.
 */
public interface WechatMessageRouterProvider {
    WxMpMessageRouter getWeixinMessageRouter(WxMpService weixinService);

}
