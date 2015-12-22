package com.wetrack.wechat.deprecated.bean.message.req;

import com.thoughtworks.xstream.annotations.XStreamAlias;

import java.io.Serializable;

/**
 * Created by zhangsong on 15/11/17.
 */
@XStreamAlias("xml")
public class WechatReqMessage implements Serializable{
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
	 * 文本消息字段
	 */
	private String Content;
	/**
	 * 图片消息字段
	 */
	private String PicUrl;
	/**
	 * 媒体下载地址id
	 */
	private String MediaId;
	/**
	 * 语音格式
	 */
	private String Format;
	/**
	 * 语音识别结果
	 */
	private String Recognition;
	/**
	 * 视频消息缩略图的媒体id
	 */
	private String ThumbMediaId;
	/**
	 * 地理位置
	 */
	private double Location_X;
	private double Location_Y;
	private int Scale;
	private String Label;
	/**
	 *链接消息
	 */
	private String Title;
	private String Description;
	private String Url;
	/**
	 * 消息id
	 */
	private long MsgId;
	/**
	 * 事件中的哪个类型
	 */
	private String Event;
	/**
	 * 对应的key
	 */
	private String EventKey;
	/**
	 * 对应的二维码
	 */
	private String Ticket;
	/**
	 * 对应的经纬度
	 */
	private double Latitude;

	private double Longitude;

	private double Precision;

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

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getRecognition() {
		return Recognition;
	}

	public void setRecognition(String recognition) {
		Recognition = recognition;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public double getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(double location_X) {
		Location_X = location_X;
	}

	public double getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(double location_Y) {
		Location_Y = location_Y;
	}

	public int getScale() {
		return Scale;
	}

	public void setScale(int scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public long getMsgId() {
		return MsgId;
	}

	public void setMsgId(long msgId) {
		MsgId = msgId;
	}

	public String getEvent() {
		return Event;
	}

	public void setEvent(String event) {
		Event = event;
	}

	public String getEventKey() {
		return EventKey;
	}

	public void setEventKey(String eventKey) {
		EventKey = eventKey;
	}

	public String getTicket() {
		return Ticket;
	}

	public void setTicket(String ticket) {
		Ticket = ticket;
	}

	public double getLatitude() {
		return Latitude;
	}

	public void setLatitude(double latitude) {
		Latitude = latitude;
	}

	public double getLongitude() {
		return Longitude;
	}

	public void setLongitude(double longitude) {
		Longitude = longitude;
	}

	public double getPrecision() {
		return Precision;
	}

	public void setPrecision(double precision) {
		Precision = precision;
	}
}
