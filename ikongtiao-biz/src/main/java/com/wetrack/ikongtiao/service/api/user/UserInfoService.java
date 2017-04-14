package com.wetrack.ikongtiao.service.api.user;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.param.UserQueryParam;
import studio.wetrack.accountService.auth.domain.Token;

import java.util.List;

/**
 * Created by zhanghong on 15/12/26.
 */
public interface UserInfoService {
    UserInfo CreateFromWeChatOpenId(String weChatOpenId);

    UserInfo create(UserInfo userInfo);
    UserInfo getByWeChatOpenId(String weChatOpenId);

    //    UserInfoDto getUser(String id) throws Exception;

    UserInfo getBasicInfoById(String id);

    String userIdFromToken(String tokenId);

    void update(UserInfo userInfo);

    //    Address createAddress(Address address) throws Exception;

    PageList<UserInfoDto> listUserByQueryParam(UserQueryParam param) throws Exception;

    List<UserInfo> listInIds(List<String> ids);

    Token login(String accountName, String password) throws Exception;

    UserInfo findByOrganizationOrContacterPhone(String organization, String contacterPhone);
    UserInfo findByAccountName(String accountName);

    Token tokenForCustomer(UserInfo userInfo);

    boolean deleteById(String userId);
}

