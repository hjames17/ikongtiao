package com.wetrack.wechat.deprecated.bean.media;

/**
 * Created by zhangsong on 15/11/18.
 */
public class WechatMaterialBatchgetItemResult {
	private String media_id;

	private WechatNewItems content;

	private String name;

	private String update_time;

	public String getMedia_id() {
		return media_id;
	}

	public void setMedia_id(String media_id) {
		this.media_id = media_id;
	}

	public WechatNewItems getContent() {
		return content;
	}

	public void setContent(WechatNewItems content) {
		this.content = content;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUpdate_time() {
		return update_time;
	}

	public void setUpdate_time(String update_time) {
		this.update_time = update_time;
	}
}
