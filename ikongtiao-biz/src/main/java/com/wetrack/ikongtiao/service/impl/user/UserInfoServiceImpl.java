package com.wetrack.ikongtiao.service.impl.user;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.geo.GeoLocation;
import com.wetrack.ikongtiao.geo.GeoUtil;
import com.wetrack.ikongtiao.param.UserQueryParam;
import com.wetrack.ikongtiao.repo.jpa.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import com.wetrack.ikongtiao.utils.RegExUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import studio.wetrack.accountService.auth.domain.Token;
import studio.wetrack.accountService.auth.domain.User;
import studio.wetrack.accountService.auth.service.TokenService;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 15/12/26.
 */
@Service
public class UserInfoServiceImpl implements UserInfoService {

	static final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

	@Autowired
	UserInfoRepo userInfoRepo;

	@Resource
	private AdminService adminService;

	@Resource
	private ImTokenService imTokenService;

	@Resource
	private ImMessageService imMessageService;

	@Autowired
	TokenService tokenService;

	@Override
	public UserInfo CreateFromWeChatOpenId(String weChatOpenId) {
		UserInfo userInfo = new UserInfo();
//		userInfo.setId(SequenceGenerator.generateUserId());
		userInfo.setWechatOpenId(weChatOpenId);
		userInfo.setType(0);
		userInfoRepo.create(userInfo);
		return userInfo;
	}

	@Override
	public UserInfo create(UserInfo userInfo) {

//		userInfo.setId(SequenceGenerator.generateUserId());
		if(!StringUtils.isEmpty(userInfo.getPhone()) && userInfoRepo.findByPhone(userInfo.getPhone()) != null){
			throw new BusinessException("重复的手机号码");
		}
		userInfoRepo.create(userInfo);
		return userInfo;
	}

	//TODO 缓存
	@Override
	public UserInfo getByWeChatOpenId(String weChatOpenId) {
		return userInfoRepo.getByOpenId(weChatOpenId);
	}

	//	@Override
	//	public UserInfoDto getUser(String id) throws Exception {
	//		return userInfoRepo.getDtoById(id);
	//	}

	@Override
	public UserInfo getBasicInfoById(String id) {
		return userInfoRepo.getById(id);
	}

	@Override
	public String userIdFromToken(String tokenId) {
		return tokenId.substring(Constants.TOKEN_ID_PREFIX_CUSTOMER.length());

	}

	@Override
	public void update(UserInfo userInfo){
//		if(!StringUtils.isEmpty(userInfo.getContacterPhone())){
//			UserInfo exist = findByOrganizationOrContacterPhone(null, userInfo.getContacterPhone());
//
//			if(userInfo.getContacterPhone().equals(exist.getContacterPhone())){
//				throw new BusinessException("联系人手机"+userInfo.getContacterPhone()+"已被占用，不能重复使用");
//			}
//		}
		if (!StringUtils.isEmpty(userInfo.getAddress()) && (userInfo.getLongitude() == null
				|| userInfo.getLatitude() == null)) {
			try {
				GeoLocation geoLocation = GeoUtil.getGeoLocationFromAddress(userInfo.getAddress());
				if (geoLocation != null) {
					userInfo.setLongitude(BigDecimal.valueOf(geoLocation.getLongitude()));
					userInfo.setLatitude(BigDecimal.valueOf(geoLocation.getLatitude()));
				} else {
					log.warn(String.format("用户%d地址经纬度解析失败%s", userInfo.getId(), userInfo.getAddress()));
				}
			} catch (Exception e) {
				log.warn(String.format("用户%d地址经纬度解析失败%s, 原因:%s", userInfo.getId(), userInfo.getAddress(),
						e.getMessage()));
			}
		}
		userInfo.setUpdateTime(new Date());
		userInfoRepo.update(userInfo);

	}

	//	@Transactional(rollbackFor = Exception.class)
	//	@Override
	//	public Address createAddress(Address address) throws Exception {
	//		Address created = userInfoRepo.saveAddress(address);
	//		if (created != null) {
	//			UserInfo userInfo = new UserInfo();
	//			userInfo.setId(created.getUserId());
	//			userInfo.setAddressId(created.getId());
	//			userInfoRepo.update(userInfo);
	//		}
	//		return created;
	//	}

	@Override
	public PageList<UserInfoDto> listUserByQueryParam(UserQueryParam param) throws Exception {

		//set total
		PageList<UserInfoDto> page = new PageList<UserInfoDto>();
		page.setPage(param.getPage());
		page.setPageSize(param.getPageSize());
		param.setStart(page.getStart());
		page.setTotalSize(userInfoRepo.queryCount(param));

		List<UserInfoDto> data = userInfoRepo.queryList(param);

		page.setData(data);
		return page;
	}

	@Override
	public List<UserInfo> listInIds(List<String> ids) {
		return userInfoRepo.listInIds(ids);
	}

	@Override
	public Token login(String accountName, String password) throws Exception {
		UserInfo userInfo = null;
		if(RegExUtil.isMobilePhone(accountName)){
			userInfo = userInfoRepo.findByPhone(accountName);
			if (userInfo == null) {
				throw new BusinessException("不存在的联系人号码");
			}
		}else if(RegExUtil.isValidEmail(accountName)){
			userInfo = userInfoRepo.findByAccountEmail(accountName);
			if (userInfo == null) {
				throw new BusinessException("不存在的帐号邮箱");
			}
		}else {
			throw new BusinessException("不存在的帐号");
		}
		if(StringUtils.isEmpty(userInfo.getPassword())){
			throw new BusinessException("未设置密码，请先重置密码");
		}
		if(!password.equals(userInfo.getPassword())){
			throw new BusinessException("密码错误");
		}
		if(userInfo.getUserType() == null){
			throw new BusinessException("无效的用户类型" + userInfo.getType());
		}

		userInfo.setPassword(password);
		return tokenForCustomer(userInfo);
	}

	@Override
	public Token tokenForCustomer(UserInfo userInfo){
		User authUser = new User(Constants.TOKEN_ID_PREFIX_CUSTOMER + userInfo.getId(), userInfo.getPassword(), User.NEVER_EXPIRED, userInfo.getUserType().getRolesStringArray());
		return tokenService.login(authUser);
	}

	@Override
	public boolean deleteById(String userId) {
		userInfoRepo.delete(userId);
		return true;
	}

	@Override
	public UserInfo findByOrganizationOrContacterPhone(String organization, String contacterPhone) {
		return userInfoRepo.findByOrganizationOrContacterPhone(organization, contacterPhone);
	}

	@Override
	public UserInfo findByAccountName(String accountName) {
		if(RegExUtil.isMobilePhone(accountName)){
			return userInfoRepo.findByPhone(accountName);

		}else if(RegExUtil.isValidEmail(accountName)){
			return userInfoRepo.findByAccountEmail(accountName);
		}

		return null;
	}
}
