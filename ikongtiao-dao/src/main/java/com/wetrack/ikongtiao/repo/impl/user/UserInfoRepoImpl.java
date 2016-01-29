package com.wetrack.ikongtiao.repo.impl.user;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.Address;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.utils.SequenceGenerator;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/12/15.
 */
@Repository("userInfoRepo")
public class UserInfoRepoImpl implements UserInfoRepo {

	@Resource
	protected CommonDao commonDao;

	@Override public UserInfo save(UserInfo userInfo) {
		if(userInfo!=null){
			userInfo.setId(SequenceGenerator.generateUserId());
			commonDao.mapper(UserInfo.class).sql("insertSelective").session().insert(userInfo);
		}
		return userInfo;
	}

	@Override public int update(UserInfo userInfo) {
		return commonDao.mapper(UserInfo.class).sql("updateByPrimaryKeySelective").session().update(userInfo);
	}

	@Override public UserInfo getById(String userId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(userId);
		return this.getByParam(userInfo);
	}

	@Override public UserInfo getByPhone(String phone) {
		UserInfo userInfo = new UserInfo();
		userInfo.setPhone(phone);
		return this.getByParam(userInfo);
	}

	@Override public UserInfo getByOpenId(String openId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setWechatOpenId(openId);
		return this.getByParam(userInfo);
	}


	private UserInfo getByParam(UserInfo param){
		return commonDao.mapper(UserInfo.class).sql("getUserInfoByParam").session().selectOne(param);
	}

	@Override
	public UserInfoDto getDtoById(String userId) {
		return commonDao.mapper(UserInfoDto.class).sql("selectByPrimaryKey").session().selectOne(userId);
	}

	@Override
	public Address saveAddress(Address address) {
		if(commonDao.mapper(Address.class).sql("insert").session().insert(address) == 1)
			return address;
		return null;
	}

	@Override
	public boolean updateAddress(Address address) {
		if(commonDao.mapper(Address.class).sql("updateByPrimaryKeySelective").session().update(address) == 1)
			return true;
		return false;
	}


}
