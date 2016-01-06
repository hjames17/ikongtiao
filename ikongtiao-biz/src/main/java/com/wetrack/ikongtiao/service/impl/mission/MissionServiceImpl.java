package com.wetrack.ikongtiao.service.impl.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.domain.MachineType;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.error.UserErrorMessage;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.MissionSubmitParam;
import com.wetrack.ikongtiao.repo.api.machine.MachineTypeRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
	private UserInfoRepo userInfoRepo;

	@Resource
	private MachineTypeRepo machineTypeRepo;

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
		Mission mission = null;
		// 提交的手机号和已经绑定的手机号要一致
		if (userInfo.getPhone().equals(param.getPhone())) {
			MachineType machineType = machineTypeRepo.getMachineTypeById(param.getMachineTypeId());
			// 检查机器类型是否存在
			if (machineType == null) {
				throw new AjaxException(CommonErrorMessage.MACHINE_TYPE_NOT_EXITS);
			}
			mission = new Mission();
			mission.setMachineTypeId(param.getMachineTypeId());
			mission.setUserId(param.getUserId());
			mission = missionRepo.save(mission);
		}
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

		//TODO 发送通知, 记录操作
	}

	@Override
	public void submitMissionDescription(Integer missionId, String description) throws Exception {
		//修改状态
		Mission mission = new Mission();
		mission.setId(missionId);
		mission.setMissionDesc(description);
		missionRepo.update(mission);

	}
}
