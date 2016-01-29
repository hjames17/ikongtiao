package com.wetrack.ikongtiao.service.impl.user;

import com.wetrack.ikongtiao.domain.Address;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.utils.SequenceGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

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

    @Override
    public UserInfoDto getUser(String id) throws Exception {
        return userInfoRepo.getDtoById(id);
    }

    @Override
    public void update(UserInfo userInfo, Address address) throws Exception {
        if(userInfo.getPhone() == null && userInfo.getAccountEmail() == null && userInfo.getAccountName() == null
                && userInfo.getAuthImg() == null && userInfo.getAuthState() == null && userInfo.getAvatar() == null
                && userInfo.getType() == null && userInfo.getLicenseNo() == null){

        }else {
            userInfo.setUpdateTime(new Date());
            userInfoRepo.update(userInfo);
        }
        if(address != null)
            address.setUpdateTime(new Date());
            userInfoRepo.updateAddress(address);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Address createAddress(Address address) throws Exception {
        Address created = userInfoRepo.saveAddress(address);
        if(created != null){
            UserInfo userInfo = new UserInfo();
            userInfo.setId(created.getUserId());
            userInfo.setAddressId(created.getId());
            userInfoRepo.update(userInfo);
        }
        return created;
    }
}
