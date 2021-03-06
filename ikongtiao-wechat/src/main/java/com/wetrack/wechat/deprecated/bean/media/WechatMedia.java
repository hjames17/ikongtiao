package com.wetrack.wechat.deprecated.bean.media;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

public class WechatMedia extends WechatBaseResult {

	private String type;
	
	private String media_id;
	
	private Integer created_at;

	private String url;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String mediaId) {
		media_id = mediaId;
	}

	public Integer getCreated_at() {
		return created_at;
	}

	public void setCreated_at(Integer createdAt) {
		created_at = createdAt;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
