package com.wetrack.ikongtiao.web.controller.mission;

import com.wetrack.base.page.PageList;
import com.wetrack.base.result.AjaxException;
import com.wetrack.ikongtiao.domain.OperatorType;
import com.wetrack.ikongtiao.domain.FaultType;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.error.MissionErrorMessage;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.repo.api.FaultTypeRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.ikongtiao.utils.RegExUtil;
import com.wetrack.message.MessageService;
import com.wetrack.verification.VerificationCodeService;
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

	@Autowired
	VerificationCodeService verificationCodeService;

	@RequestMapping("/mission/save")
	@ResponseBody
	public Integer addMission(@RequestBody MissionSubmit param) throws Exception{
		if(StringUtils.isEmpty(param.getSmsCode())){
			throw new BusinessException("需要填写报修人手机验证码");
		}
		if(!verificationCodeService.verifyCode(param.getContacterPhone(), param.getSmsCode())){
			throw new BusinessException("报修人手机验证码无效");

		}
		if (param == null || StringUtils.isEmpty(param.getContacterPhone()) || StringUtils.isEmpty(param.getFaultType())) {
			throw new BusinessException("任务参数缺失");
		}
		Mission mission = missionService.saveMissionFromUser(param);
		return mission.getId();
	}

	@RequestMapping("/mission/smsCode")
	@ResponseBody
	public void getSmsCode(@RequestParam String mobilePhone) throws Exception{
		if(!RegExUtil.isMobilePhone(mobilePhone)){
			throw new BusinessException("无效的手机号码");
		}
		verificationCodeService.sendVericationCode(mobilePhone);
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
		Mission mission = missionService.getMission(form.getMissionId());
		if(!mission.getUserId().equals(form.getUserId())){
			throw new BusinessException("不是您的任务!");
		}
		missionService.finishMission(form.getMissionId(), OperatorType.CUSTOMER, mission.getUserId());
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
//	@RequestMapping(value = "/faultType/add", method = RequestMethod.POST)
//	public String addFaultType(@RequestParam(value = "name") String name){
//		faultTypeRepo.create(name, 18);
//		return name;
//	}

	static class UpdateForm {
		String missionId;

		String userId;

		public String getMissionId() {
			return missionId;
		}

		public void setMissionId(String missionId) {
			this.missionId = missionId;
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}
	}

	static class MissionSubmit extends Mission{
		String smsCode;

		public String getSmsCode() {
			return smsCode;
		}

		public void setSmsCode(String smsCode) {
			this.smsCode = smsCode;
		}
	}
}
