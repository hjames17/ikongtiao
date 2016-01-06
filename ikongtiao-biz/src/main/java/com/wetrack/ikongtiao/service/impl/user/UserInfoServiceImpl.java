package com.wetrack.ikongtiao.service.impl.user;

import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.utils.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;

/**
 * Created by zhanghong on 15/12/26.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService{

    @Autowired
    UserInfoRepo userInfoRepo;

    @Override
    public UserInfo CreateFromWeChatOpenId(String weChatOpenId) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(SequenceGenerator.generateUserId());
        userInfo.setWechatOpenId(weChatOpenId);
        userInfoRepo.save(userInfo);
        return userInfo;
    }

    @Override
    public UserInfo getByWeChatOpenId(String weChatOpenId) {
        return userInfoRepo.getByOpenId(weChatOpenId);
    }
}
