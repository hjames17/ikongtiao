package com.wetrack.wechat.service;

import me.chanjar.weixin.common.bean.WxMenu;

/**
 * Created by zhanghong on 16/3/9.
 */
public interface WeixinMenuGenerator {
    WxMenu generateMenu() throws Exception;
}
