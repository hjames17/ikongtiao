package com.wetrack.ikongtiao.service.api.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.domain.OperatorType;
import com.wetrack.ikongtiao.domain.FaultType;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.statistics.StatsCount;
import com.wetrack.ikongtiao.dto.MissionDetail;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.FixerMissionQueryParam;
import com.wetrack.ikongtiao.param.StatsQueryParam;

import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
public interface MissionService {
	/**
	 * 用户提交任务，存储
	 *
	 * @param param
	 * @return
	 */
	Mission saveMissionFromUser(Mission param) throws Exception;

	Mission saveMission(Mission mission) throws Exception;

	/**
	 * 用户或者管理员分页查询任务列表
	 *
	 * @param param
	 * @return
	 */
	PageList<MissionDto> listMissionByAppQueryParam(AppMissionQueryParam param);

	List<MissionDetail> listMissionFullByAppQueryParam(AppMissionQueryParam param);

	/**
	 * 维修员查询任务列表
	 *
	 * @param param
	 * @return
	 * @throws Exception
	 */
	PageList<MissionDto> ListFixerMissionByParam(FixerMissionQueryParam param) throws Exception;

	void acceptMission(String id, Integer adminUserId) throws Exception;

	void denyMission(String id, Integer adminUser, String reason) throws Exception;

	void dispatchMission(String id, Integer fixerId, Integer adminUserId) throws Exception;

	void submitMissionDescription(String id, String description, String name, Integer provinceId, Integer cityId, Integer districtId, String address) throws Exception;

	MissionDto getMissionDto(String id) throws Exception;

	Mission getMission(String id) throws Exception;

	void finishMission(String id, OperatorType operatorType, String operatorId) throws Exception;

	void update(Mission mission);

	List<FaultType> listFaultType();

	List<StatsCount> statsMission(StatsQueryParam queryParam);

	void notify(int missionId, MissionState newState, MissionState oldState, OperatorType operatorType, String operatorId);
}