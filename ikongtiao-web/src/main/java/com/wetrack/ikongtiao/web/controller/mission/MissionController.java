package com.wetrack.ikongtiao.web.controller.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.ikongtiao.domain.FaultType;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.error.MissionErrorMessage;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.repo.api.FaultTypeRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
@Controller
public class MissionController {

	@Resource
	private MissionService missionService;

	@RequestMapping("/mission/save")
	@ResponseBody
	public Integer addMission(@RequestBody Mission param) throws Exception{
		if (param == null || StringUtils.isEmpty(param.getContacterPhone()) || StringUtils.isEmpty(param.getFaultType())) {
			throw new BusinessException("任务参数缺失");
		}
		Mission mission = missionService.saveMissionFromUser(param);
		return mission.getId();
	}

	@RequestMapping("/mission/list")
	@ResponseBody
	public PageList<MissionDto> listMission(@RequestBody AppMissionQueryParam param) {
		if (param == null || StringUtils.isEmpty(param.getUserId())) {
			throw new AjaxException(MissionErrorMessage.MISSION_LIST_PARAM_ERROR);
		}
		return missionService.listMissionByAppQueryParam(param);
	}


	@ResponseBody
	@RequestMapping(value = "/mission/{id}" , method = {RequestMethod.GET})
	public MissionDto getMissionOfPath(@PathVariable(value = "id") String id) throws Exception{
		return missionService.getMissionDto(id);
	}

	@RequestMapping("/mission/detail")
	@ResponseBody
	public MissionDto getMission(@RequestParam(value="id") String id) throws Exception{
		return missionService.getMissionDto(id);
	}

	@RequestMapping("/mission/finish")
	@ResponseBody
	public void finishMission(@RequestBody UpdateForm form) throws Exception{
		String id = form.getSerialNumber();
		if(StringUtils.isEmpty(id)){
			id = form.getMissionId().toString();
		}
		Mission mission = missionService.getMission(id);
		if(!mission.getUserId().equals(form.getUserId())){
			throw new BusinessException("不是您的任务!");
		}
		missionService.finishMission(id);
	}

	@ResponseBody
	@RequestMapping("/faultType")
	public List<FaultType> listFaultType(){
		return missionService.listFaultType();
	}

	@Autowired
	FaultTypeRepo faultTypeRepo;
	@Autowired
	MessageService messageService;
//	@ResponseBody
//	@RequestMapping(value = "/faultType/add", method = RequestMethod.POST)
//	public String addFaultType(@RequestParam(value = "name") String name){
//		faultTypeRepo.create(name, 18);
//		return name;
//	}

	static class UpdateForm {
		@Deprecated
		Integer missionId;

		String serialNumber;
		String userId;

		public Integer getMissionId() {
			return missionId;
		}

		public void setMissionId(Integer missionId) {
			this.missionId = missionId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getSerialNumber() {
			return serialNumber;
		}

		public void setSerialNumber(String serialNumber) {
			this.serialNumber = serialNumber;
		}
	}
}
