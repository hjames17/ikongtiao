package com.wetrack.wechat.deprecated.bean.message.custom;

/**
 * Created by zhangsong on 15/11/25.
 * 客服 视频消息
 */
public class WechatVideo {
	private String media_id;
	private String title;
	private String description;

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}

