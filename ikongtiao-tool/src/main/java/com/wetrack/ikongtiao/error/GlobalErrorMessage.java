package com.wetrack.ikongtiao.error;

import com.wetrack.base.result.ErrorMessage;

/**
 * Created by zhangsong on 15/11/29.
 */
public enum GlobalErrorMessage implements ErrorMessage {

	GLOBAL_ERROR("服务器异常，请稍后重试。"),
	;
	private String message;


	GlobalErrorMessage(String message){
		this.message = message;
	}

	@Override public String getCode() {
		return this.toString();
	}

	@Override public String getMessage() {
		return this.message;
	}
}
