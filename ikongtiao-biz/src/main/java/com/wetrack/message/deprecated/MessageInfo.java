package com.wetrack.message.deprecated;

import java.io.Serializable;

/**
 * Created by zhangsong on 16/2/4.
 */
public class MessageInfo implements Serializable{

	private Integer id; //messageId from MessageType

	/**
	 * 发送消息给谁
	 */
	private String messageTo;
	/**
	 * 消息的标题
	 */
	private String title; //web, 个推，微信
	/**
	 * 消息的内容
	 */
	private String content; //web, 个推, 微信，sms

	/**
	 * 点击消息跳转的地址
	 */
	private String url; //微信, sms

	/**
	 * 消息需要图片时，图片的地址
	 */
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
