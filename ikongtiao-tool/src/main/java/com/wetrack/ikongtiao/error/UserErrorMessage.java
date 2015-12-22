package com.wetrack.ikongtiao.error;

import com.wetrack.base.result.ErrorMessage;

/**
 * Created by zhangsong on 15/12/15.
 */
public enum UserErrorMessage implements ErrorMessage {

	USER_NOT_EXITS("用户不存在"),

	;

	private String message;

	UserErrorMessage(String message){
		this.message = message;
	}
	@Override public String getCode() {
		return this.toString();
	}

	@Override public String getMessage() {
		return this.message;
	}
}
