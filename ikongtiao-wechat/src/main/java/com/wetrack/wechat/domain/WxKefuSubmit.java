package com.wetrack.wechat.domain;

import me.chanjar.weixin.common.util.json.WxGsonBuilder;

import java.io.Serializable;

/**
 * Created by zhanghong on 15/12/23.
 * 创建，修改和删除客服账号的时候提交的数据结构
 * 微信接口：
 * https://api.weixin.qq.com/customservice/kfaccount/add
 * https://api.weixin.qq.com/customservice/kfaccount/update
 * https://api.weixin.qq.com/customservice/kfaccount/del
 *
 */
public class WxKefuSubmit implements Serializable {

    //格式: 名称@微信号
    String kf_account;
    String nickname;
    String password;

    public String toJson() {
        return WxGsonBuilder.create().toJson(this);
    }


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
}
