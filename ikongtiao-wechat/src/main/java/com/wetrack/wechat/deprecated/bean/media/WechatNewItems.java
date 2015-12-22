package com.wetrack.wechat.deprecated.bean.media;

import com.wetrack.wechat.deprecated.bean.WechatBaseResult;

import java.util.List;

/**
 * Created by zhangsong on 15/11/18.
 */
public class WechatNewItems  extends WechatBaseResult{
	private List<WechatArticle> news_item;

	public List<WechatArticle> getNews_item() {
		return news_item;
	}

	public void setNews_item(List<WechatArticle> news_item) {
		this.news_item = news_item;
	}
}
