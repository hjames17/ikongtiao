package com.wetrack.wechat.deprecated.bean.message.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhangsong on 15/11/17.
 */
@XStreamAlias("xml")
public class WechatResMessage {

	/**
	 * 发给谁的
	 */
	private String ToUserName;
	/**
	 * 谁发出的
	 */
	private String FromUserName;
	/**
	 * 创建时间
	 */
	private long CreateTime;
	/**
	 * 消息类型
	 */
	private String MsgType;

	/**
	 * 文本消息
	 */
	private String Content;

	/**
	 * 文章数量
	 */
	private String ArticleCount;

	/**
	 * 图文消息
	 */
	private ImageMessage Image;

	/**
	 * 语音消息
	 */
	private VoiceMessage Voice;

	/**
	 * 视频消息
	 */
	private VedioMessage Vedio;

	/**
	 * 音乐消息
	 */
	private MusicMessage Music;

	/**
	 * 文章消息
	 */
	private ArticleMessage Articles;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public long getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(long createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public ImageMessage getImage() {
		return Image;
	}

	public void setImage(ImageMessage image) {
		Image = image;
	}

	public VoiceMessage getVoice() {
		return Voice;
	}

	public void setVoice(VoiceMessage voice) {
		Voice = voice;
	}

	public VedioMessage getVedio() {
		return Vedio;
	}

	public void setVedio(VedioMessage vedio) {
		Vedio = vedio;
	}

	public MusicMessage getMusic() {
		return Music;
	}

	public void setMusic(MusicMessage music) {
		Music = music;
	}

	public ArticleMessage getArticles() {
		return Articles;
	}

	public void setArticles(ArticleMessage articles) {
		Articles = articles;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
