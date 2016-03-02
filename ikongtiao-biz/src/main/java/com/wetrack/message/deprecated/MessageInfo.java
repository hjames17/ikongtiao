package com.wetrack.message.deprecated;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/2/4.
 */
public class MessageInfo implements Serializable{

	private Integer id; //messageId from MessageType

	private String messageTo;

	private String title; //web, 个推，微信

	private String content; //web, 个推, 微信，sms

	private String url; //微信, sms

	private String picUrl; //微信

	private Object data;

	private String[] args;

	public String getMessageTo() {
		return messageTo;
	}

	public void setMessageTo(String messageTo) {
		this.messageTo = messageTo;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPicUrl() {
		return picUrl;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
