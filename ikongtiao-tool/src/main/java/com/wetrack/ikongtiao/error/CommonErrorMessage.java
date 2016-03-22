package com.wetrack.ikongtiao.error;

import com.wetrack.base.result.ErrorMessage;

/**
 * Created by zhangsong on 15/12/15.
 */
public enum CommonErrorMessage implements ErrorMessage {

	MACHINE_TYPE_NOT_EXITS("机器类型不存在"),

	IM_ROLE_TYPE_IS_NULL("IM的角色类型为空"),
	IM_ROLE_TYPE_WRONG("IM角色类型不存在"),
	IM_CLOUD_IS_NULL("IM的融云id为空"),
	IM_SESSION_IS_NOT_EXITS("会话不存在"),
	IM_SESSION_IS_CLOSED("会话已经关闭"),
	IM_MESSAGE_PARAM_ERROR("保存聊天记录参数不正确"),
	IM_MESSAGE_LIST_PARAM_NULL("获取消息记录参数为空"),
	FIXER_IS_NOT_EXIST("维修员不存在"),
	USER_IS_NOT_EXIST("用户不存在"),
	IM_SESSION_LIST_PARAM_ERROR("获取会话列表参数不全"),
	IM_MESSAGE_USER_CLOUD_IS_BULL("获取没有关闭会话的和当前聊天人的用户列表参数有误");

	private String message;

	CommonErrorMessage(String message){
		this.message = message;
	}
	@Override public String getCode() {
		return this.toString();
	}

	@Override public String getMessage() {
		return this.message;
	}
}
