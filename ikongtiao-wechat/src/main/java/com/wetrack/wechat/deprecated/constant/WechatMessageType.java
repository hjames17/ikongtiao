package com.wetrack.wechat.deprecated.constant;

/**
 * Created by zhangsong on 15/11/17.
 */
public enum WechatMessageType {

	TEXT("text", "文本类型"),
	IMAGE("image", "图片类型"),
	VOICE("voice", "语音类型"),
	VIDEO("video", "视频类型"),
	SHORT_VIDEO("shortvideo", "短视频类型"),
	LOCATION("location", "地理位置类型"),
	LINK("link", "链接类型"),
	EVENT("event", "事件"),
	MUSIT("music", "音乐类型（返回消息使用）"),
	NEWS("news", "文章类型（返回消息使用）"),;

	private String code;
	private String message;

	WechatMessageType(String code, String message) {
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
