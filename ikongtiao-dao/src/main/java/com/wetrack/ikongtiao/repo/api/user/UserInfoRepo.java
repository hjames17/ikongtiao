package com.wetrack.ikongtiao.repo.api.user;

import com.wetrack.ikongtiao.domain.Address;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.param.UserQueryParam;

import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
public interface UserInfoRepo {

	UserInfo save(UserInfo userInfo);

	int update(UserInfo userInfo);

	int updateByContacterPhone(UserInfo userInfo);

	/**
	 * 根据用户ID获取用户信息
	 *
	 * @param userId
	 * @return
	 */
	UserInfo getById(String userId);

	/**
	 * 根据用户手机号，获取用户信息
	 *
	 * @param phone
	 * @return
	 */
	UserInfo getByPhone(String phone);

	/**
	 * 根据用户微信openId获取用户信息
	 *
	 * @param openId
	 * @return
	 */
	UserInfo getByOpenId(String openId);

//	UserInfoDto getDtoById(String userId);

	Address saveAddress(Address address);

	boolean updateAddress(Address address);

	List<UserInfoDto> listByQueryParam(UserQueryParam param) throws Exception;

	int countByQueryParam(UserQueryParam param) throws Exception;

	List<UserInfo> listInIds(List<String> ids);

	UserInfo findByOrganizationOrContacterPhone(String organization, String contacterPhone);

	UserInfo findByAccountEmail(String email);
}
