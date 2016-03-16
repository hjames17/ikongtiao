package com.wetrack.ikongtiao.service.impl.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.domain.MachineType;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.MissionAddress;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.error.UserErrorMessage;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.geo.GeoLocation;
import com.wetrack.ikongtiao.geo.GeoUtil;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.FixerMissionQueryParam;
import com.wetrack.ikongtiao.param.MissionSubmitParam;
import com.wetrack.ikongtiao.repo.api.machine.MachineTypeRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionAddressRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhangsong on 15/12/15.
 */
@Service("missionService")
public class MissionServiceImpl implements MissionService {

	private final static Logger LOGGER = LoggerFactory.getLogger(MissionServiceImpl.class);

	@Resource
	private MissionRepo missionRepo;

	@Resource
	MissionAddressRepo missionAddressRepo;

	@Resource
	private UserInfoRepo userInfoRepo;

	@Resource
	private MachineTypeRepo machineTypeRepo;

	@Resource
	MessageService messageService;

//	@Resource
//	private MessageProcess messageProcess;

//	@Resource
//	private FixerRepo fixerRepo;

//	@Value("${weixin.page.host}")
//	String weixinPageHost;
//	@Value("${weixin.page.mission}")
//	String weixinMissionPage;
//	static final String ACTION_DETAIL = "detail";



	@Transactional
	@Override public Mission saveMission(MissionSubmitParam param) {
		UserInfo userInfo = userInfoRepo.getById(param.getUserId());
		if (userInfo == null) {
			throw new AjaxException(UserErrorMessage.USER_NOT_EXITS);
		}
		//用户手机为空，就绑定手机号
		if (StringUtils.isEmpty(userInfo.getPhone())) {
			UserInfo bindUserInfo = new UserInfo();
			bindUserInfo.setId(userInfo.getId());
			bindUserInfo.setContacterPhone(param.getPhone());
			userInfoRepo.update(bindUserInfo);
		}



		Mission mission = null;
		// 提交的手机号和已经绑定的手机号要一致

//		if (userInfo.getPhone().equals(param.getPhone())) {
		MachineType machineType = machineTypeRepo.getMachineTypeById(param.getMachineTypeId());
		// 检查机器类型是否存在
		if (machineType == null) {
			throw new AjaxException(CommonErrorMessage.MACHINE_TYPE_NOT_EXITS);
		}
		mission = new Mission();
//		mission.setMissionAddressId(missionAddress.getId());
		mission.setMachineTypeId(param.getMachineTypeId());
		mission.setUserId(param.getUserId());
		mission = missionRepo.save(mission);
//		}


		//创建一个地址信息
		MissionAddress missionAddress = new MissionAddress();
		missionAddress.setPhone(param.getPhone());
		//如果没有指定故障单位名称，则按照默认顺序使用客户的 1 单位名称 2 联系人
		if(!StringUtils.isEmpty(userInfo.getAccountName())){
			missionAddress.setName(userInfo.getAccountName());
		}else if(!StringUtils.isEmpty(userInfo.getContacterName())){
			missionAddress.setName(userInfo.getContacterName());
		}
		missionAddress.setId(mission.getId());
		missionAddressRepo.save(missionAddress);

		//发送消息
//		MessageSimple messageSimple = new MessageSimple();
//		messageSimple.setMissionId(mission.getId());
//		messageProcess.process(MessageType.NEW_COMMISSION,messageSimple);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, mission.getId());
		params.put(MessageParamKey.USER_ID, mission.getUserId());
		messageService.send(MessageId.NEW_COMMISSION, params);
		return mission;
	}


	@Transactional(rollbackFor = Exception.class)
	@Override
	public Mission saveMission(Mission mission, MissionAddress address) throws Exception{
		mission = missionRepo.save(mission);
		GeoLocation geoLocation = null;
		try {
			geoLocation = GeoUtil.getGeoLocationFromAddress(address.getAddress());
		} catch (UnsupportedEncodingException e) {
			throw new BusinessException("地址内容编码错误");
		}
		if(geoLocation == null){
			throw new BusinessException(String.format("地址:%s无法获取经纬度，需要重新填写!", address));
		}else{
			address.setLatitude(BigDecimal.valueOf(geoLocation.getLatitude()));
			address.setLongitude(BigDecimal.valueOf(geoLocation.getLongitude()));
		}
		address.setId(mission.getId());
		missionAddressRepo.save(address);

		return mission;
	}

	//TODO 缓存
	@Override public PageList<MissionDto> listMissionByAppQueryParam(AppMissionQueryParam param) {
		PageList<MissionDto> page = new PageList<>();
		page.setPage(param.getPage());
		page.setPageSize(param.getPageSize());
		param.setStart(page.getStart());
		// 设置总数量
		page.setTotalSize(missionRepo.countMissionByAppQueryParam(param));
		// 获取内容
		List<MissionDto> missionDtos = missionRepo.listMissionByAppQueryParam(param);
		for(MissionDto missionDto:missionDtos){
			//填充机器类型
			MachineType machineType = machineTypeRepo.getMachineTypeById(missionDto.getMachineTypeId());
			missionDto.setMachineImg(machineType.getImg());
			missionDto.setMachineName(machineType.getName());
			missionDto.setMachineRemark(machineType.getRemark());
			missionDto.setMachineTypeParentId(machineType.getParentId());
		}
		page.setData(missionDtos);
		return page;
	}

	@Override
	public PageList<MissionDto> ListFixerMissionByParam(FixerMissionQueryParam param) throws Exception {

		//set total
		PageList<MissionDto> page = new PageList<>();
		page.setPage(param.getPage());
		page.setPageSize(param.getPageSize());
		param.setStart(page.getStart());
		page.setTotalSize(missionRepo.countMissionByFixer(param));

		//1. mission in which repairOrder is created or dispatched to the fixer
		//2. mission which is dispatched to the fixer
		//ordered by time desc
		List<MissionDto> missionList = missionRepo.listMissionIdByFixer(param);

		//return the selected page data

		page.setData(missionList);
		return page;
	}

	@Transactional
	@Override
	public void acceptMission(Integer missionId, Integer adminUserId) throws Exception {
		//先读取任务状态，防止多人抢单，导致最后一个来的抢成功了
		Mission mission = missionRepo.getMissionById(missionId);
		if(mission == null){
			throw new BusinessException("任务不存在");
		}
		if(mission.getMissionState() > MissionState.NEW.getCode()){
			throw new BusinessException("任务已经被受理");
		}
		//修改状态
		mission.setMissionState(MissionState.ACCEPT.getCode());
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);

		//发送消息
//		MessageSimple messageSimple = new MessageSimple();
//		messageSimple.setUserId(mission.getUserId());
//		messageSimple.setMissionId(missionId);
//		String url = String.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_DETAIL, mission.getUserId(), mission.getId());
//		messageSimple.setUrl(url);
//		messageProcess.process(MessageType.ACCEPT_MISSION, messageSimple);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, missionId);
		params.put(MessageParamKey.USER_ID, mission.getUserId());
		params.put(MessageParamKey.ADMIN_ID, adminUserId);
		messageService.send(MessageId.ACCEPT_MISSION, params);
	}

	@Override
	public void denyMission(Integer missionId, Integer adminUserId, String reason) throws Exception {

		//先读取任务状态，不能拒绝已经被处理的订单
		Mission mission = missionRepo.getMissionById(missionId);
		if(mission == null){
			throw new BusinessException("不存在任务" + missionId);
		}
		if(mission.getMissionState() > MissionState.NEW.getCode()){
			throw new BusinessException("任务"+missionId+"已经被受理,不能拒绝");
		}
		//修改状态
		mission.setMissionState(MissionState.REJECT.getCode());
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);

		//发送消息
//		MessageSimple pushData = new MessageSimple();
//		pushData.setUserId(mission.getUserId());
//		String url = String.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_DETAIL, mission.getUserId(), mission.getId());
//		pushData.setUrl(url);
//		messageProcess.process(MessageType.REJECT_MISSION, pushData);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, missionId);
		params.put(MessageParamKey.USER_ID, mission.getUserId());
		params.put(MessageParamKey.ADMIN_ID, adminUserId);
		messageService.send(MessageId.REJECT_MISSION, params);
	}

	@Override
	public void dispatchMission(Integer missionId, Integer fixerId, Integer adminUserId) throws Exception {
		Mission mission = missionRepo.getMissionById(missionId);

		//修改状态
		if(mission.getMissionState() != MissionState.DISPATCHED.getCode() || mission.getFixerId() != fixerId) {
			mission.setId(missionId);
			mission.setFixerId(fixerId);
			mission.setMissionState(MissionState.DISPATCHED.getCode());
			mission.setUpdateTime(new Date());
			missionRepo.update(mission);
		}

		//发送消息
//		MessageSimple pushData = new MessageSimple();
//		pushData.setUserId(mission.getUserId());
//		pushData.setFixerId(fixerId);
//		pushData.setMissionId(missionId);
//		String url = String.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_DETAIL, mission.getUserId(), mission.getId());
//		pushData.setUrl(url);
//		messageProcess.process(MessageType.ASSIGNED_MISSION, pushData);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, missionId);
		params.put(MessageParamKey.USER_ID, mission.getUserId());
		params.put(MessageParamKey.FIXER_ID, fixerId);
		params.put(MessageParamKey.ADMIN_ID, adminUserId);
		messageService.send(MessageId.ASSIGNED_MISSION, params);
	}


	@Override
	public void submitMissionDescription(Integer id, String description, String name, Integer provinceId, Integer cityId, Integer districtId, String address) throws Exception {
		Mission mission = missionRepo.getMissionById(id);
		if(mission == null){
			throw new BusinessException("不存在任务" + id);
		}

//		MissionAddress missionAddress = new MissionAddress();
//		missionAddress.setId(mission.getMissionAddressId());
		MissionAddress missionAddress = missionAddressRepo.getMissionAddressById(mission.getId());
		boolean addressChanged = false;
		if(name != null) {
			missionAddress.setName(name);
			addressChanged = true;
		}
		if(provinceId != null) {
			missionAddress.setProvinceId(provinceId);
			addressChanged = true;
		}
		if(cityId != null) {
			missionAddress.setCityId(cityId);
			addressChanged = true;
		}
		if(districtId != null) {
			missionAddress.setDistrictId(districtId);
			addressChanged = true;
		}
		if(address != null) {
			missionAddress.setAddress(address);
			GeoLocation geoLocation = GeoUtil.getGeoLocationFromAddress(missionAddress.getAddress());
			if(geoLocation == null){
				throw new BusinessException(String.format("任务%d地址:%s无法获取经纬度，需要重新填写!", id, address));
			}else{
				missionAddress.setLatitude(BigDecimal.valueOf(geoLocation.getLatitude()));
				missionAddress.setLongitude(BigDecimal.valueOf(geoLocation.getLongitude()));
			}
			addressChanged = true;
		}
//		if(latitude != null) {
//			missionAddress.setLatitude(BigDecimal.valueOf(latitude));
//			addressChanged = true;
//		}
//		if(longitude != null) {
//			missionAddress.setLongitude(BigDecimal.valueOf(longitude));
//			addressChanged = true;
//		}
		if(addressChanged) {
			missionAddressRepo.update(missionAddress);
		}
		//修改状态
		if(description != null) {
			mission.setMissionDesc(description);
			missionRepo.update(mission);
		}

	}

	@Override
	public MissionDto getMissionDto(Integer id) throws Exception {
		return missionRepo.getMissionDetailById(id);
	}

	@Override
	public Mission getMission(Integer id) throws Exception {
		return missionRepo.getMissionById(id);
	}

	@Override
	public void finishMission(Integer missionId) throws Exception {
		Mission mission = new Mission();
		mission.setId(missionId);
		mission.setMissionState(MissionState.COMPLETED.getCode());
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);

		//发送消息
//		mission = missionRepo.getMissionById(missionId);
//		MessageSimple messageSimple = new MessageSimple();
//		messageSimple.setMissionId(missionId);
//		messageSimple.setFixerId(mission.getFixerId());
//		messageProcess.process(MessageType.COMPLETED_MISSION, messageSimple);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, missionId);
		params.put(MessageParamKey.USER_ID, mission.getUserId());
		params.put(MessageParamKey.FIXER_ID, mission.getFixerId());
		params.put(MessageParamKey.ADMIN_ID, mission.getAdminUserId());
		messageService.send(MessageId.COMPLETED_MISSION, params);
	}

	@Override
	public void update(Mission mission) {
		missionRepo.update(mission);
	}
}
