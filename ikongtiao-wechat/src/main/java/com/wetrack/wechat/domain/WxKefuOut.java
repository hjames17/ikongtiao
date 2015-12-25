package com.wetrack.wechat.domain;

import java.io.Serializable;

/**
 * Created by zhanghong on 15/12/23.
 * 调用微信客服接口https://api.weixin.qq.com/cgi-bin/customservice/getkflist
 * 返回值的数据结构
 */
public class WxKefuOut implements Serializable {

    //格式: 名称@微信号
    String kf_account;
    String kf_id;
    String kf_nick;
    String kf_headimgurl;


    public String getKf_account() {
        return kf_account;
    }

    public void setKf_account(String kf_account) {
        this.kf_account = kf_account;
    }

    public String getKf_id() {
        return kf_id;
    }

    public void setKf_id(String kf_id) {
        this.kf_id = kf_id;
    }

    public String getKf_nick() {
        return kf_nick;
    }

    public void setKf_nick(String kf_nick) {
        this.kf_nick = kf_nick;
    }

    public String getKf_headimgurl() {
        return kf_headimgurl;
    }

    public void setKf_headimgurl(String kf_headimgurl) {
        this.kf_headimgurl = kf_headimgurl;
    }
}
