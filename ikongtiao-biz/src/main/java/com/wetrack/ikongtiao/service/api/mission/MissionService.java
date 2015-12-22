package com.wetrack.ikongtiao.service.api.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.MissionSubmitParam;

/**
 * Created by zhangsong on 15/12/15.
 */
public interface MissionService {
	/**
	 * 用户提交任务，存储
	 * @param param
	 * @return
	 */
	Mission saveMission(MissionSubmitParam param);

	/**
	 * 前台分页查询任务列表
	 * @param param
	 * @return
	 */
	PageList<MissionDto> listMissionByAppQueryParam(AppMissionQueryParam param);

}
