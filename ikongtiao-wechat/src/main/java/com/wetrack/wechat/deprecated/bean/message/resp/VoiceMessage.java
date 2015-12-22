package com.wetrack.wechat.deprecated.bean.message.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhangsong on 15/11/25.
 */
@XStreamAlias("Voice")
public class VoiceMessage {
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}