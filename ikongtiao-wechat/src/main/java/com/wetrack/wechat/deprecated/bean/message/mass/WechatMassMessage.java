package com.wetrack.wechat.deprecated.bean.message.mass;

import java.util.Set;

/**
 * Created by zhangsong on 15/11/21.
 */
public class WechatMassMessage {

	protected String msgtype;

	private WechatMassText text;

	private WechatMassMedia mpnews;

	private WechatMassMedia voice;

	private WechatMassMedia image;

	private WechatMassMedia mpvideo;

	private WechatMassMessageFilter filter;//用于特定组

	private Set<String> touser;//用于指定用户

	public String getMsgtype() {
		return msgtype;
	}

	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}

	public WechatMassText getText() {
		return text;
	}

	public void setText(WechatMassText text) {
		this.text = text;
	}

	public WechatMassMedia getMpnews() {
		return mpnews;
	}

	public void setMpnews(WechatMassMedia mpnews) {
		this.mpnews = mpnews;
	}

	public WechatMassMedia getVoice() {
		return voice;
	}

	public void setVoice(WechatMassMedia voice) {
		this.voice = voice;
	}

	public WechatMassMedia getImage() {
		return image;
	}

	public void setImage(WechatMassMedia image) {
		this.image = image;
	}

	public WechatMassMedia getMpvideo() {
		return mpvideo;
	}

	public void setMpvideo(WechatMassMedia mpvideo) {
		this.mpvideo = mpvideo;
	}

	public WechatMassMessageFilter getFilter() {
		return filter;
	}

	public void setFilter(WechatMassMessageFilter filter) {
		this.filter = filter;
	}

	public Set<String> getTouser() {
		return touser;
	}

	public void setTouser(Set<String> touser) {
		this.touser = touser;
	}
}
