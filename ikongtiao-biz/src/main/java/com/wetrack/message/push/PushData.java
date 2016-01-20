package com.wetrack.message.push;

/**
 * Created by zhangsong on 16/1/14.
 */
public class PushData {

	private String userId;
	private Integer fixId;

	private String url = "";

	private String firstData = "";

	private String secondData = "";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public Integer getFixId() {
		return fixId;
	}

	public void setFixId(Integer fixId) {
		this.fixId = fixId;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getFirstData() {
		return firstData;
	}

	public void setFirstData(String firstData) {
		this.firstData = firstData;
	}

	public String getSecondData() {
		return secondData;
	}

	public void setSecondData(String secondData) {
		this.secondData = secondData;
	}
}
