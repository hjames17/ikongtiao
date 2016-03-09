package com.wetrack.ikongtiao.service.api.user;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.Address;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.param.UserQueryParam;
import com.wetrack.ikongtiao.repo.api.im.ImMessageQueryParam;
import com.wetrack.ikongtiao.service.api.im.ImTokenDto;

import java.util.List;

/**
 * Created by zhanghong on 15/12/26.
 */
public interface UserInfoService {
    UserInfo CreateFromWeChatOpenId(String weChatOpenId);
    UserInfo getByWeChatOpenId(String weChatOpenId);

    UserInfoDto getUser(String id) throws Exception;

    void update(UserInfo userInfo, Address address) throws Exception;

    Address createAddress(Address address) throws Exception;

    PageList<UserInfoDto> listUserByQueryParam(UserQueryParam param) throws Exception;

    ImTokenDto getImTokenDtoByUserId(String userId,String srcType,String desType) throws Exception;

    PageList<ImMessage> listMessageByParam(ImMessageQueryParam param);

    String saveImMessage(ImMessage imMessage);

    List<UserInfo> listInIds(List<String> ids);
}

