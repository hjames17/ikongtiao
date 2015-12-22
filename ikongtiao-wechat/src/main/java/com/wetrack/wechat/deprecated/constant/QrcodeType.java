package com.wetrack.wechat.deprecated.constant;

/**
 * Created by zhangsong on 15/11/22.
 */
public enum QrcodeType {
	QR_SCENE("QR_SCENE", "临时二维码"),
	QR_LIMIT_SCENE("QR_LIMIT_SCENE", "永久二维码"),
	QR_LIMIT_STR_SCENE("QR_LIMIT_STR_SCENE", "永久二维码(字符串)");

	private String code;
	private String message;

	QrcodeType(String code, String message) {
		this.code = code;
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}
}
