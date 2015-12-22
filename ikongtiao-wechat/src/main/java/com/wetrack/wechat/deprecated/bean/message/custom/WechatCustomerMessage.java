package com.wetrack.wechat.deprecated.bean.message.custom;

/**
 * Created by zhangsong on 15/11/21.
 */
public class WechatCustomerMessage {

	/**
	 * 发送给谁
	 */
	private String touser;

	/**
	 * 消息类型
	 */
	private String msgtype;

	/**
	 * 文本消息
	 */
	private WechatText text;
	/**
	 * 图片消息
	 */
	private WechatImage image;
	/**
	 * 语音消息
	 */
	private WechatVoice voice;
	/**
	 * 视频消息
	 */
	private WechatVideo video;
	/**
	 * 音乐消息
	 */
	private WechatMusic music;
	/**
	 * 图文消息
	 */
	private WechatNews news;

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public WechatText getText() {
		return text;
	}

	public void setText(WechatText text) {
		this.text = text;
	}

	public WechatImage getImage() {
		return image;
	}

	public void setImage(WechatImage image) {
		this.image = image;
	}

	public WechatVoice getVoice() {
		return voice;
	}

	public void setVoice(WechatVoice voice) {
		this.voice = voice;
	}

	public WechatVideo getVideo() {
		return video;
	}

	public void setVideo(WechatVideo video) {
		this.video = video;
	}

	public WechatMusic getMusic() {
		return music;
	}

	public void setMusic(WechatMusic music) {
		this.music = music;
	}

	public WechatNews getNews() {
		return news;
	}

	public void setNews(WechatNews news) {
		this.news = news;
	}
}
