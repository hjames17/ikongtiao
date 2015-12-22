package com.wetrack.wechat.deprecated.bean.message.resp;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * Created by zhangsong on 15/11/25.
 */
@XStreamAlias("Item")
public class ArticleItem {
	private String Title;
	private String Description;
	private String PicUrl;
	private String Url;

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

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}
}
