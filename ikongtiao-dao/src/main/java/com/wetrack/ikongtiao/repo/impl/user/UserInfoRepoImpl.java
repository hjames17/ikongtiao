package com.wetrack.ikongtiao.repo.impl.user;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.Address;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.param.UserQueryParam;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepoCustom;
import com.wetrack.ikongtiao.utils.SequenceGenerator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
//@Repository("userInfoRepo")
@Service
public class UserInfoRepoImpl implements UserInfoRepoCustom {

	@Resource
	protected CommonDao commonDao;

	@Override public UserInfo create(UserInfo userInfo) {
		if(userInfo!=null){
			userInfo.setId(SequenceGenerator.generateUserId());
			commonDao.mapper(UserInfo.class).sql("insertSelective").session().insert(userInfo);
		}
		return userInfo;
	}

	/**
	 * 可以通过id，或者联系人手机来更新客户信息
	 * @param userInfo
	 * @return
	 */
	@Override public int update(UserInfo userInfo) {
		return commonDao.mapper(UserInfo.class).sql("updateByIdOrContacterPhone").session().update(userInfo);
	}

	@Override
	public int updateByContacterPhone(UserInfo userInfo) {
		return commonDao.mapper(UserInfo.class).sql("updateByContacterPhone").session().update(userInfo);
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

//	@Override
//	public UserInfoDto getDtoById(String userId) {
//		return commonDao.mapper(UserInfoDto.class).sql("selectByPrimaryKey").session().selectOne(userId);
//	}

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

	@Override
	public List<UserInfoDto> queryList(UserQueryParam param) throws Exception {
		if(param.getPhone() != null){
			//加上sql like查询通配符
			param.setPhone("%" + param.getPhone() + "%");
		}
		if(param.getUserName() != null){
			//加上sql like查询通配符
			param.setUserName("%" + param.getUserName() + "%");
		}
		if(param.getAddress() != null){
			//加上sql like查询通配符
			param.setAddress("%" + param.getAddress() + "%");
		}
		return commonDao.mapper(UserInfoDto.class).sql("listUserByParam").session().selectList(param);
	}

	@Override
	public int queryCount(UserQueryParam param) throws Exception {

		if(param.getPhone() != null){
			//加上sql like查询通配符
			param.setPhone("%" + param.getPhone() + "%");
		}
		if(param.getUserName() != null){
			//加上sql like查询通配符
			param.setUserName("%" + param.getUserName() + "%");
		}
		if(param.getAddress() != null){
			//加上sql like查询通配符
			param.setAddress("%" + param.getAddress() + "%");
		}
		BaseCondition baseCondition = commonDao.mapper(UserInfoDto.class).sql("countUserByParam").session().selectOne(param);
		return baseCondition == null ? 0 : baseCondition.getTotalSize();
	}

	@Override
	public List<UserInfo> listInIds(List<String> ids) {
		return commonDao.mapper(UserInfo.class).sql("selectInIds").session().selectList(ids);
	}

	@Override
	public UserInfo findByOrganizationOrContacterPhone(String organization, String contacterPhone) {
		UserInfo user = new UserInfo();
		user.setOrganization(organization);
		user.setContacterPhone(contacterPhone);
		return commonDao.mapper(UserInfo.class).sql("selectByAccountField").session().selectOne(user);
	}

	@Override
	public UserInfo findByAccountEmail(String email) {
		UserInfo user = new UserInfo();
		user.setAccountEmail(email);
		return commonDao.mapper(UserInfo.class).sql("selectByAccountField").session().selectOne(user);
	}


}
