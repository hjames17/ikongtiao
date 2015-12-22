package com.wetrack.ikongtiao.error;

import com.wetrack.base.result.ErrorMessage;

/**
 * Created by zhangsong on 15/12/15.
 */
public enum CommonErrorMessage implements ErrorMessage {

	MACHINE_TYPE_NOT_EXITS("机器类型不存在"),

	;

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
