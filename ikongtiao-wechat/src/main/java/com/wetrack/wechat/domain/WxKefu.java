package com.wetrack.wechat.domain;

import java.io.Serializable;

/**
 * Created by zhanghong on 15/12/23.
 * !!!!!!该数据结构不可持久化，因为创建客服账号需要设置密码，该密码是明文
 */
public class WxKefu implements Serializable {

    //格式: 名称@微信号
    String kf_account;
    String nickname;
    String password;
    String kf_headimgurl;



    public String getKf_account() {
        return kf_account;
    }

    public void setKf_account(String kf_account) {
        this.kf_account = kf_account;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKf_headimgurl() {
        return kf_headimgurl;
    }

    public void setKf_headimgurl(String kf_headimgurl) {
        this.kf_headimgurl = kf_headimgurl;
    }
}
