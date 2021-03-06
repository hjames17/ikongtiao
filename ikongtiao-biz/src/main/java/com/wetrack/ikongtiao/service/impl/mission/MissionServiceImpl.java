package com.wetrack.ikongtiao.service.impl.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.domain.FaultType;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.OperatorType;
import com.wetrack.ikongtiao.domain.customer.UserInfo;
import com.wetrack.ikongtiao.domain.statistics.StatsCount;
import com.wetrack.ikongtiao.dto.MissionDetail;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.error.UserErrorMessage;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.geo.GeoLocation;
import com.wetrack.ikongtiao.geo.GeoUtil;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.FixerMissionQueryParam;
import com.wetrack.ikongtiao.param.StatsQueryParam;
import com.wetrack.ikongtiao.repo.api.FaultTypeRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.repo.jpa.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionSerialNumberService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.ikongtiao.service.api.monitor.TaskMonitorService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class MissionServiceImpl implements MissionService{

	private final static Logger logger = LoggerFactory.getLogger(MissionServiceImpl.class);

	@Resource
	private MissionRepo missionRepo;

	@Resource
	private UserInfoRepo userInfoRepo;

	@Resource
	MessageService messageService;

	@Autowired
	TaskMonitorService taskMonitorService;

	@Autowired
	MissionSerialNumberService missionSerialNumberService;

	@Override public Mission saveMissionFromUser(Mission param) throws Exception{
		UserInfo userInfo = userInfoRepo.getById(param.getUserId());
		if (userInfo == null) {
			throw new AjaxException(UserErrorMessage.USER_NOT_EXITS);
		}
		//用户手机为空，就绑定手机号
		if (StringUtils.isEmpty(userInfo.getContacterPhone())) {
			UserInfo bindUserInfo = new UserInfo();
			bindUserInfo.setId(userInfo.getId());
			bindUserInfo.setContacterPhone(param.getContacterPhone());
			userInfoRepo.update(bindUserInfo);
		}

		Mission mission = doSave(param, userInfo);

		//发送消息
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(MessageParamKey.MISSION_ID, mission.getId());
//		params.put(MessageParamKey.MISSION_SID, mission.getSerialNumber());
//		params.put(MessageParamKey.USER_ID, mission.getUserId());
//		messageService.send(MessageId.NEW_COMMISSION, params);
		notify(mission.getId(), MissionState.NEW, null, OperatorType.CUSTOMER, param.getUserId());


		//加入提醒任务
		taskMonitorService.putTask(Constants.TASK_MISSION + mission.getId());
		return mission;
	}


	@Override
	public Mission saveMission(Mission mission) throws Exception{
		UserInfo userInfo = userInfoRepo.getById(mission.getUserId());
		if (userInfo == null) {
			throw new AjaxException(UserErrorMessage.USER_NOT_EXITS);
		}
		return doSave(mission, userInfo);
	}

	private Mission doSave(Mission mission, UserInfo userInfo) throws Exception{
		// 检查机器类型是否存在
//		MachineType machineType = machineTypeRepo.getMachineTypeById(mission.getMachineTypeId());
//		if (machineType == null) {
//			throw new AjaxException(CommonErrorMessage.MACHINE_TYPE_NOT_EXITS);
//		}
		//如果没有指定故障单位名称，则默认使用客户的单位名称
		if(StringUtils.isEmpty(mission.getOrganization()) && !StringUtils.isEmpty(userInfo.getOrganization())) {
			mission.setOrganization(userInfo.getOrganization());
		}
		//如果没有指定故障联系人，则默认使用客户的联系人
		if(StringUtils.isEmpty(mission.getContacterName()) && !StringUtils.isEmpty(userInfo.getContacterName())) {
			mission.setContacterName(userInfo.getContacterName());
		}
		//如果没有制定故障联系电话，则默认使用客户的联系人电话
		if(StringUtils.isEmpty(mission.getContacterPhone()) && !StringUtils.isEmpty(userInfo.getContacterPhone())) {
			mission.setContacterPhone(userInfo.getContacterPhone());
		}
		//如果有地址，验证地址有效性,否则，默认使用客户地址
		if(!StringUtils.isEmpty(mission.getAddress())){
			GeoLocation geoLocation = null;
			try {
				geoLocation = GeoUtil.getGeoLocationFromAddress(mission.getAddress());
			} catch (UnsupportedEncodingException e) {
				throw new BusinessException("地址内容编码错误");
			}
			if(geoLocation == null){
				throw new BusinessException(String.format("地址:%s无法获取经纬度，需要重新填写!", mission.getAddress()));
			}else{
				mission.setLatitude(BigDecimal.valueOf(geoLocation.getLatitude()));
				mission.setLongitude(BigDecimal.valueOf(geoLocation.getLongitude()));
			}
		}else{
			mission.setAddress(userInfo.getAddress());
			mission.setProvinceId(userInfo.getProvinceId());
			mission.setCityId(userInfo.getCityId());
			mission.setDistrictId(userInfo.getDistrictId());
			mission.setLatitude(userInfo.getLatitude());
			mission.setLongitude(userInfo.getLongitude());
		}

		mission.setSerialNumber(missionSerialNumberService.getNextSerialNumber());


		return missionRepo.save(mission);
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
		page.setData(missionDtos);
		return page;
	}
	@Override public List<MissionDetail> listMissionFullByAppQueryParam(AppMissionQueryParam param) {
		return  missionRepo.listMissionFullByAppQueryParam(param);
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
	public void acceptMission(String id, Integer adminUserId) throws Exception {
		//先读取任务状态，防止多人抢单，导致最后一个来的抢成功了
		Mission mission = getMission(id);
		if(mission == null){
			throw new BusinessException("任务不存在");
		}
		if(mission.getMissionState() > MissionState.NEW.getCode()){
			throw new BusinessException("任务已经被受理");
		}
		//修改状态
		mission.setAdminUserId(adminUserId);
		mission.setMissionState(MissionState.ACCEPT.getCode());
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);

		//发送消息
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(MessageParamKey.MISSION_ID, mission.getId());
//		params.put(MessageParamKey.MISSION_SID, mission.getSerialNumber());
//		params.put(MessageParamKey.USER_ID, mission.getUserId());
//		params.put(MessageParamKey.ADMIN_ID, adminUserId);
//		messageService.send(MessageId.ACCEPT_MISSION, params);


		notify(mission.getId(), MissionState.ACCEPT, null, OperatorType.ADMIN, adminUserId.toString());
	}

	@Override
	public void denyMission(String id, Integer adminUserId, String reason) throws Exception {

		//先读取任务状态，不能拒绝已经被处理的订单
		Mission mission = getMission(id);
		if(mission == null){
			throw new BusinessException("不存在任务" + id);
		}
		if(mission.getMissionState() >= MissionState.FIXING.getCode()){
			throw new BusinessException("任务"+id+"已不能取消");
		}
		//修改状态
		mission.setMissionState(MissionState.REJECT.getCode());
		mission.setCloseReason(reason);
		mission.setAdminUserId(adminUserId);
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);

		//发送消息
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(MessageParamKey.MISSION_ID, mission.getId());
//		params.put(MessageParamKey.MISSION_SID, mission.getSerialNumber());
//		params.put(MessageParamKey.USER_ID, mission.getUserId());
//		params.put(MessageParamKey.ADMIN_ID, adminUserId);
//		messageService.send(MessageId.REJECT_MISSION, params);

		notify(mission.getId(), MissionState.CLOSED, null, OperatorType.ADMIN, adminUserId.toString());

	}

	@Override
	public void dispatchMission(String id, Integer fixerId, Integer adminUserId) throws Exception {

		Mission mission = getMission(id);

		//修改状态
		if(mission.getMissionState() != MissionState.DISPATCHED.getCode() || mission.getFixerId() != fixerId) {
			mission.setFixerId(fixerId);
			mission.setMissionState(MissionState.DISPATCHED.getCode());
			mission.setUpdateTime(new Date());
			missionRepo.update(mission);
		}

		//发送消息
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put(MessageParamKey.MISSION_ID, mission.getId());
//		params.put(MessageParamKey.MISSION_SID, mission.getSerialNumber());
//		params.put(MessageParamKey.USER_ID, mission.getUserId());
//		params.put(MessageParamKey.FIXER_ID, fixerId);
//		params.put(MessageParamKey.ADMIN_ID, adminUserId);
//		messageService.send(MessageId.ASSIGNED_MISSION, params);

		notify(mission.getId(), MissionState.DISPATCHED, null, OperatorType.ADMIN, adminUserId.toString());

	}


	@Override
	public void submitMissionDescription(String id, String description, String name, Integer provinceId, Integer cityId, Integer districtId, String address) throws Exception {

		Mission mission = getMission(id);

		if(mission == null){
			throw new BusinessException("不存在任务" + id);
		}

		if(!StringUtils.isEmpty(name)) {
			mission.setContacterName(name);
		}
		if(!StringUtils.isEmpty(address) && !address.equals(mission.getAddress())) {
			GeoLocation geoLocation = GeoUtil.getGeoLocationFromAddress(address);
			if(geoLocation == null){
				throw new BusinessException(String.format("任务%s地址:%s无法获取经纬度，需要重新填写!", id, address));
			}else{
				mission.setLatitude(BigDecimal.valueOf(geoLocation.getLatitude()));
				mission.setLongitude(BigDecimal.valueOf(geoLocation.getLongitude()));
			}
			mission.setAddress(address);
		}
		//修改状态
		if(description != null) {
			mission.setMissionDesc(description);
		}

		missionRepo.update(mission);
	}

	@Override
	public MissionDto getMissionDto(String id) throws Exception {

		MissionDto mission;
		try{
			int iid = Integer.parseInt(id);
			mission = missionRepo.getMissionDetailById(iid);
		}catch (NumberFormatException e){
			mission = missionRepo.getMissionDetailBySid(id);
		}
		return  mission;
	}

	@Override
	public Mission getMission(String id) throws Exception {
		Mission mission;
		try{
			int iid = Integer.parseInt(id);
			mission = missionRepo.getMissionById(iid);
		}catch (NumberFormatException e){
			mission = missionRepo.getMissionBySid(id);
		}
		return  mission;
	}

	@Autowired
	RepairOrderRepo repairOrderRepo;

	@Override
	public void finishMission(String id, OperatorType operatorType, String operatorId) throws Exception {

		Mission mission = getMission(id);

		int unfinished = repairOrderRepo.countUnfinishedForMission(mission);
		if(unfinished > 0){
			throw new BusinessException("任务中有" + unfinished + "个未完成的维修单，请先联系维修员完成相关维修单!");
		}


		mission.setMissionState(MissionState.COMPLETED.getCode());
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);


		notify(mission.getId(), MissionState.COMPLETED, null, operatorType, operatorId);
	}

	@Override
	public void update(Mission mission) {
		missionRepo.update(mission);
	}


	@Autowired
	FaultTypeRepo faultTypeRepo;
	@Override
	public List<FaultType> listFaultType() {
		return faultTypeRepo.findAll();
	}

	@Override
	public List<StatsCount> statsMission(StatsQueryParam queryParam) {
		return missionRepo.statsMissions(queryParam);
	}

	@Override
	public void notify(int missionId, MissionState newState, MissionState oldState, OperatorType operatorType, String operatorId){

		Integer messageId = null;
		try {
			switch (newState) {
				case NEW:
					messageId = MessageId.NEW_COMMISSION;
					break;
				case ACCEPT:
					messageId = MessageId.ACCEPT_MISSION;
					break;
				case DISPATCHED:
					messageId = MessageId.ASSIGNED_MISSION;
				case FIXING:
					break;
				case CLOSED:
//					sendUserDeniedEvent(repairOrder);
					messageId = MessageId.REJECT_MISSION;
					break;
				case COMPLETED:
//					sendCompleteEvent(repairOrder);
					messageId = MessageId.COMPLETED_MISSION;
					break;
				default:
					break;
			}
			if(messageId != null) {
				Mission mission = missionRepo.getMissionById(missionId);
				Map<String, Object> params = new HashMap<String, Object>();
				params.put(MessageParamKey.MISSION_STATE, newState.getCode());
				params.put(MessageParamKey.MISSION_ID, missionId);
				params.put(MessageParamKey.MISSION_SID, mission.getSerialNumber());
				if(!StringUtils.isEmpty(mission.getUserId())) {
					params.put(MessageParamKey.USER_ID, mission.getUserId());
				}
				if(mission.getAdminUserId() != null) {
					params.put(MessageParamKey.ADMIN_ID, mission.getAdminUserId());
				}
				if(mission.getFixerId() != null){
					params.put(MessageParamKey.FIXER_ID, mission.getFixerId());
				}
				params.put(MessageParamKey.OPERATOR_ID, operatorId);
				params.put(MessageParamKey.OPERATOR_TYPE, operatorType);
				params.put(MessageParamKey.TIME, System.currentTimeMillis());

				messageService.send(messageId, params);
			}
		}catch (Exception e){
			logger.error("mission notify event failed on state {}, operatorType {}", newState, operatorType);
		}


	}


}
