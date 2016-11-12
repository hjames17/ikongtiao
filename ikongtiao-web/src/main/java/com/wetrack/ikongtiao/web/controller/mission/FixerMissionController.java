package com.wetrack.ikongtiao.web.controller.mission;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.AccountType;
import com.wetrack.ikongtiao.domain.FaultType;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.FixerMissionQueryParam;
import com.wetrack.ikongtiao.repo.api.FaultTypeRepo;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhangsong on 15/12/15.
 */
@SignTokenAuth
@Controller
public class FixerMissionController {


	private static final String BASE_PATH = "/f/mission";

	@Resource
	private MissionService missionService;

	@Resource
	FixerService fixerService;


	@RequestMapping(value = BASE_PATH + "/list", method = RequestMethod.POST)
	@ResponseBody
	public PageList<MissionDto> listMission(HttpServletRequest request, @RequestBody FixerMissionQueryParam param) throws Exception{
		User user = (User)request.getAttribute("user");
		param.setFixerId(fixerService.getFixerIdFromTokenUser(user));
		return missionService.ListFixerMissionByParam(param);
	}


	@ResponseBody
	@RequestMapping(value = BASE_PATH + "/{id}" , method = {RequestMethod.GET})
	public MissionDto getMission(@PathVariable(value = "id") String id) throws Exception{
		return missionService.getMissionDto(id);
	}

	@Autowired
	FaultTypeRepo faultTypeRepo;

	@ResponseBody
	@RequestMapping(value = BASE_PATH + "/finish/{id}", method = {RequestMethod.POST})
	public void finishMission(@PathVariable(value = "id") String id, HttpServletRequest request ) throws Exception{
		User user = (User)request.getAttribute("user");

		//如果任务类型不是开机调试，不允许该操作
		Mission mission = missionService.getMission(id);
		if(mission == null){
			throw new BusinessException("找不到任务");
		}

		FaultType faultType = faultTypeRepo.findById(Integer.parseInt(mission.getFaultType()));
		if(faultType == null){
			throw new BusinessException("未知类型的故障，不能设置");
		}
		if(!"开机调试".equals(faultType.getName())){
			throw new BusinessException("非开机调试任务不允许服务人员设为完成");
		}
		missionService.finishMission(id, AccountType.FIXER, user.getId());
	}
}
