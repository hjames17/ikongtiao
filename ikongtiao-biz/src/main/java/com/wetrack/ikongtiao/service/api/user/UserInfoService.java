package com.wetrack.ikongtiao.service.api.user;

import com.wetrack.ikongtiao.domain.UserInfo;

/**
 * Created by zhanghong on 15/12/26.
 */
public interface UserInfoService {
    UserInfo CreateFromWeChatOpenId(String weChatOpenId);
    UserInfo getByWeChatOpenId(String weChatOpenId);
}

