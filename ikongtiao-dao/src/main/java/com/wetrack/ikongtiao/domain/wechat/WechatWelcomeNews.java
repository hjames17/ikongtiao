package com.wetrack.ikongtiao.domain.wechat;

/**
 * Created by zhanghong on 16/4/8.
 * 关注回复的图文消息
 */
public class WechatWelcomeNews {
    Integer id;
    String title;
    String description;
    String url;
    String picUrl;
    Integer ordinal;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
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

    public Integer getOrdinal() {
        return ordinal;
    }

    public void setOrdinal(Integer ordinal) {

        this.ordinal = ordinal;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
