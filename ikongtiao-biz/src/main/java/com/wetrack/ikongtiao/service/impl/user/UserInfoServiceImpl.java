package com.wetrack.ikongtiao.service.impl.user;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.ImMessage;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.geo.GeoLocation;
import com.wetrack.ikongtiao.geo.GeoUtil;
import com.wetrack.ikongtiao.param.UserQueryParam;
import com.wetrack.ikongtiao.repo.api.im.ImMessageQueryParam;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.service.api.im.ImMessageService;
import com.wetrack.ikongtiao.service.api.im.ImTokenDto;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import com.wetrack.ikongtiao.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

	@Override
	public UserInfo CreateFromWeChatOpenId(String weChatOpenId) {
		UserInfo userInfo = new UserInfo();
		userInfo.setId(SequenceGenerator.generateUserId());
		userInfo.setWechatOpenId(weChatOpenId);
		userInfoRepo.save(userInfo);
		return userInfo;
	}

	@Override
	public UserInfo create(UserInfo userInfo) {
		userInfo.setId(SequenceGenerator.generateUserId());
		userInfoRepo.save(userInfo);
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
	public void update(UserInfo userInfo) throws Exception {
		if (!StringUtils.isEmpty(userInfo.getAddress()) && (userInfo.getLongitude() == null || userInfo.getLatitude() == null)) {
			try {
				GeoLocation geoLocation = GeoUtil.getGeoLocationFromAddress(userInfo.getAddress());
				if(geoLocation != null) {
					userInfo.setLongitude(BigDecimal.valueOf(geoLocation.getLongitude()));
					userInfo.setLatitude(BigDecimal.valueOf(geoLocation.getLatitude()));
				}else{
					log.warn(String.format("用户%d地址经纬度解析失败%s", userInfo.getId(), userInfo.getAddress()));
				}
			} catch (Exception e) {
				log.warn(String.format("用户%d地址经纬度解析失败%s, 原因:%s", userInfo.getId(), userInfo.getAddress(), e.getMessage()));
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
		page.setTotalSize(userInfoRepo.countByQueryParam(param));

		List<UserInfoDto> data = userInfoRepo.listByQueryParam(param);

		page.setData(data);
		return page;
	}

	@Override public ImTokenDto getImTokenDtoByUserId(String userId, String srcType, String desType) throws Exception {
		ImToken imToken = imTokenService.getTokenByUserId(userId, srcType);
		int id = adminService.getAvailableAdminId();
		ImTokenDto imTokenDto = new ImTokenDto();
		if (imToken != null) {
			imTokenDto.setUserId(imToken.getUserId());
			imTokenDto.setToken(imToken.getToken());
			imTokenDto.setTargetId(desType + id);
		}
		return imTokenDto;
	}

	@Override public PageList<ImMessage> listMessageByParam(ImMessageQueryParam param) {
		return imMessageService.listImMessageByParam(param);
	}

	@Override public String saveImMessage(ImMessage imMessage) {
		imMessageService.save(imMessage);
		return "ok";
	}

	@Override
	public List<UserInfo> listInIds(List<String> ids) {
		return userInfoRepo.listInIds(ids);
	}
}
