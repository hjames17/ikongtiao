package com.wetrack.ikongtiao.web.controller.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.base.result.AjaxResult;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.error.MissionErrorMessage;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.MissionSubmitParam;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/12/15.
 */
@Controller
public class MissionController {

	@Resource
	private MissionService missionService;

	@RequestMapping("/mission/save")
	@ResponseBody
	public AjaxResult<Boolean> addMission(@RequestBody MissionSubmitParam param) {
		if (param == null || StringUtils.isEmpty(param.getPhone()) || StringUtils.isEmpty(param.getPhone())
				|| param.getMachineTypeId() == null) {
			throw new AjaxException(MissionErrorMessage.MISSION_SUBMIT_PARAM_ERROR);
		}
		Mission mission = missionService.saveMission(param);
		if (mission == null) {
			return new AjaxResult<>(Boolean.FALSE);
		}
		return new AjaxResult<>(Boolean.TRUE);
	}

	@RequestMapping("/mission/list")
	@ResponseBody
	public PageList<MissionDto> listMission(@RequestBody AppMissionQueryParam param) {
		if (param == null || StringUtils.isEmpty(param.getUserId())) {
			throw new AjaxException(MissionErrorMessage.MISSION_LIST_PARAM_ERROR);
		}
		return missionService.listMissionByAppQueryParam(param);
	}

	@RequestMapping("/mission/detail")
	@ResponseBody
	public MissionDto getMission(@RequestParam(value="id") Integer id) throws Exception{
		return missionService.getMission(id);
	}
}
