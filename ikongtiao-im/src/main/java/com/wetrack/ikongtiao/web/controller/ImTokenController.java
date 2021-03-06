package com.wetrack.ikongtiao.web.controller;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.result.AjaxResult;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.ImToken;
import com.wetrack.ikongtiao.service.api.im.ImTokenService;
import com.wetrack.ikongtiao.service.api.im.dto.ImRoleType;
import com.wetrack.rong.RongCloudApiService;
import com.wetrack.rong.models.FormatType;
import com.wetrack.rong.models.SdkHttpResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

/**
 * Created by zhangsong on 16/3/17.
 */
@Controller
public class ImTokenController {

	@Resource
	private ImTokenService imTokenService;

	@Resource
	private RongCloudApiService rongCloudApiService;

	@RequestMapping("/token/get")
	@ResponseBody
	public AjaxResult<ImToken> getToken(@RequestParam(value = "systemUserId", required = false) String systemUserId,
										@RequestParam(value = "roleType", required = false) Integer roleType) {
		return new AjaxResult<>(
				imTokenService.getTokenBySystemIdAndRoleType(systemUserId, ImRoleType.parseCode(roleType)));
	}

	/**
	 * 为请求者分配一个聊天对象（就是客服人员)
	 * @param systemUserId
	 * @param roleType
	 * @return
	 */
	@RequestMapping("/cloud/id/get")
	@ResponseBody
	public AjaxResult<String> getCloudId(@RequestParam(value = "systemUserId", required = false) String systemUserId,
										 @RequestParam(value = "roleType", required = false) Integer roleType) throws Exception{
		ImRoleType imRoleType = ImRoleType.parseCode(roleType);
		if (imRoleType == null) {
			throw new AjaxException("CLOUD_ROLE_TYPE_IS_NULL", "获取角色id为空");
		}
		if (StringUtils.isEmpty(systemUserId)) {
			systemUserId = imTokenService.allocateKefuForRole(imRoleType);
		}else{
			systemUserId = imRoleType.getPrex() + systemUserId;
		}
		// todo 检查用户是否存在
		return new AjaxResult<>(systemUserId);
	}

	@RequestMapping("/cloud/checkOnline")
	@ResponseBody
	public AjaxResult<String> checkCouldOnline(@RequestParam(value = "cloudId", required = false) String cloudId) {
		SdkHttpResult result = null;
		try {
			result = rongCloudApiService.checkOnline(cloudId, FormatType.json);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result == null) {
			throw new AjaxException("CHECK_CLOUD_ON_LINE_ERROR", "检查融云id对应的用户是否在线发生异常");
		}
		Map<String, Object> map = Jackson.base().readValue(result.getResult(), Map.class);
		return new AjaxResult<>(map.get("status").toString());
	}
}
