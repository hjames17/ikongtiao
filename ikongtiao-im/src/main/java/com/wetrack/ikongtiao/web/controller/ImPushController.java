package com.wetrack.ikongtiao.web.controller;

import com.wetrack.base.result.AjaxException;
import com.wetrack.base.result.AjaxResult;
import com.wetrack.ikongtiao.error.CommonErrorMessage;
import com.wetrack.ikongtiao.service.api.im.dto.ImRoleType;
import com.wetrack.ikongtiao.service.api.im.dto.PushNotifyDto;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhangsong on 16/3/17.
 */
@Controller
public class ImPushController {

	@Resource
	private MessageService messageService;


	@RequestMapping(value = "/push/notify", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult<String> pushNotify(@RequestBody PushNotifyDto pushNotifyDto) {
		ImRoleType type = ImRoleType.parseCode(pushNotifyDto.getRoleType());

		if (type == null) {
			throw new AjaxException(CommonErrorMessage.IM_ROLE_TYPE_IS_NULL);
		}
		Map<String, Object> params = new HashMap<String, Object>();
		switch (type) {
		case FIXER:
			params.put(MessageParamKey.FIXER_ID, pushNotifyDto.getSystemUserId());
			params.put(MessageParamKey.IM_PUSH_NOTIFY_SENDER_ID, pushNotifyDto.getFromUserId());
			params.put(MessageParamKey.IM_PUSH_NOTIFY_SESSION_ID, pushNotifyDto.getSessionId());
			messageService.send(MessageId.IM_NOTIFY_FIXER, params);
			break;
		case WECHAT:
			params.put(MessageParamKey.USER_ID, pushNotifyDto.getSystemUserId());
			params.put(MessageParamKey.IM_PUSH_NOTIFY_FROM_RLE, pushNotifyDto.getFromRoleType());
			params.put(MessageParamKey.IM_PUSH_NOTIFY_SENDER_ID, pushNotifyDto.getFromUserId());
			params.put(MessageParamKey.IM_PUSH_NOTIFY_SESSION_ID, pushNotifyDto.getSessionId());
			params.put(MessageParamKey.IM_PUSH_NOTIFY_MESSAGE_UID, pushNotifyDto.getMessageUid());
			messageService.send(MessageId.IM_NOTIFY_WECHAT, params);
			break;
		case KEFU:
			//  todo 客服的推送暂时还没有
			break;
		default:
			throw new AjaxException(CommonErrorMessage.IM_ROLE_TYPE_WRONG);
		}

		return new AjaxResult<>("success");
	}
}
