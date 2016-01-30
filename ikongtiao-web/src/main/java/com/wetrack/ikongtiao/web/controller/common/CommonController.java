package com.wetrack.ikongtiao.web.controller.common;

import com.wetrack.auth.domain.User;
import com.wetrack.base.result.AjaxException;
import com.wetrack.base.result.AjaxResult;
import com.wetrack.ikongtiao.domain.FixerDevice;
import com.wetrack.ikongtiao.service.api.fixer.PushBindService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by zhangsong on 16/1/30.
 */
@Controller
@RequestMapping("/common")
public class CommonController {

	@Resource
	private PushBindService pushBindService;

	@RequestMapping("/push/bind")
	public AjaxResult<String> pushBind(HttpServletRequest request, @RequestBody PushBindForm pushBindForm) {
		FixerDevice fixerDevice = new FixerDevice();
		User user = (User) request.getAttribute("user");
		pushBindForm.setFixerId(Integer.valueOf(user == null ? "" : user.getId()));
		if (pushBindForm.getFixerId() == null) {
			throw new AjaxException("1", "参数错误");
		}
		fixerDevice.setClientId(pushBindForm.getClientId());
		fixerDevice.setFixerId(pushBindForm.getFixerId());
		fixerDevice.setOsType(pushBindForm.getOsType());
		fixerDevice.setOsVersion(pushBindForm.getOsVersion());
		fixerDevice.setDeviceType(pushBindForm.getDeviceType());
		fixerDevice = pushBindService.save(fixerDevice);
		return new AjaxResult<>("绑定成功");
	}
}
