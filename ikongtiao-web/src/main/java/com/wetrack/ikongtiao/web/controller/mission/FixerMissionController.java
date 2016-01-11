package com.wetrack.ikongtiao.web.controller.mission;

import com.wetrack.auth.domain.User;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.FixerMissionQueryParam;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

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


	@RequestMapping(value = BASE_PATH + "/list", method = RequestMethod.POST)
	@ResponseBody
	public PageList<MissionDto> listMission(HttpServletRequest request, @RequestBody FixerMissionQueryParam param) throws Exception{
		User user = (User)request.getAttribute("user");
		param.setFixerId(Integer.valueOf(user.getId()));
		return missionService.ListFixerMissionByParam(param);
	}
}
