package com.wetrack.wechat.deprecated.bean.message.custom;

/**
 * 客服 图文消息
 * Created by zhangsong on 15/11/25.
 */

import java.util.List;

public class WechatNews {

	private List<WechatArticle> articles;

	public List<WechatArticle> getArticles() {
		return articles;
	}

	public void setArticles(List<WechatArticle> articles) {
		this.articles = articles;
	}
}