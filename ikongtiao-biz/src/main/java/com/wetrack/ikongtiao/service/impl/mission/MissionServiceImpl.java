package com.wetrack.ikongtiao.service.impl.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.domain.*;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.error.UserErrorMessage;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.FixerMissionQueryParam;
import com.wetrack.ikongtiao.param.MissionSubmitParam;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import com.wetrack.ikongtiao.repo.api.machine.MachineTypeRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionAddressRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.message.push.PushData;
import com.wetrack.message.push.PushProcess;
import com.wetrack.message.push.PushEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

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
	private PushProcess pushProcess;

	@Resource
	private FixerRepo fixerRepo;
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
			bindUserInfo.setPhone(param.getPhone());
			if (userInfoRepo.update(bindUserInfo) > 0) {
				userInfo.setPhone(param.getPhone());
			}
		}

		//创建一个地址信息
		MissionAddress missionAddress = new MissionAddress();
		missionAddress.setPhone(param.getPhone());
		missionAddressRepo.save(missionAddress);

		Mission mission = null;
		// 提交的手机号和已经绑定的手机号要一致

//		if (userInfo.getPhone().equals(param.getPhone())) {
		MachineType machineType = machineTypeRepo.getMachineTypeById(param.getMachineTypeId());
		// 检查机器类型是否存在
		if (machineType == null) {
			throw new AjaxException(CommonErrorMessage.MACHINE_TYPE_NOT_EXITS);
		}
		mission = new Mission();
		mission.setMissionAddressId(missionAddress.getId());
		mission.setMachineTypeId(param.getMachineTypeId());
		mission.setUserId(param.getUserId());
		mission = missionRepo.save(mission);
//		}

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
			throw new Exception("任务不存在");
		}
		if(mission.getMissionState() > MissionState.NEW.getCode()){
			throw new Exception("任务已经被受理");
		}
		//修改状态
		mission.setMissionState(MissionState.ACCEPET.getCode());
		missionRepo.update(mission);

		PushData pushData = new PushData();
		pushData.setUserId(mission.getUserId());
		pushProcess.post(PushEventType.ACCEPT_MISSION,pushData);
		//TODO 发送通知, 记录操作
	}

	@Override
	public void denyMission(Integer missionId, Integer adminUserId, String reason) throws Exception {

		//先读取任务状态，不能拒绝已经被处理的订单
		Mission mission = missionRepo.getMissionById(missionId);
		if(mission == null){
			throw new Exception("任务不存在");
		}
		if(mission.getMissionState() > MissionState.NEW.getCode()){
			throw new Exception("任务已经被受理,不能拒绝");
		}
		//修改状态
		mission.setMissionState(MissionState.REJECT.getCode());
		missionRepo.update(mission);

		//TODO，发送通知, 记录操作
	}

	@Override
	public void dispatchMission(Integer missionId, Integer fixerId, Integer adminUserId) throws Exception {
		//修改状态
		Mission mission = new Mission();
		mission.setId(missionId);
		mission.setFixerId(fixerId);
		missionRepo.update(mission);
		PushData pushData = new PushData();
		pushData.setUserId(mission.getUserId());
		pushData.setFixId(fixerId);
		pushProcess.post(PushEventType.ASSIGNED_MISSION,pushData);
		//TODO 发送通知, 记录操作
	}


	@Override
	public void submitMissionDescription(Integer id, String description, String name, Integer provinceId, Integer cityId, Integer districtId, String address, Double longitude, Double latitude) throws Exception {
		Mission mission = missionRepo.getMissionById(id);
		if(mission == null){
			throw new Exception("不存在该任务");
		}

		MissionAddress missionAddress = new MissionAddress();
		missionAddress.setId(mission.getMissionAddressId());
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
			missionAddress.setDetail(address);
			addressChanged = true;
		}
		if(latitude != null) {
			missionAddress.setLatitude(BigDecimal.valueOf(latitude));
			addressChanged = true;
		}
		if(longitude != null) {
			missionAddress.setLongitude(BigDecimal.valueOf(longitude));
			addressChanged = true;
		}
		if(addressChanged) {
			missionAddressRepo.update(missionAddress);
		}
		//修改状态
		if(description != null) {
			mission.setMissionDesc(description);
			missionRepo.update(mission);
		}


	}
}
