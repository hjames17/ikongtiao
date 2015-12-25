package com.wetrack.ikongtiao.domain.admin;

import java.io.Serializable;

/**
 * Created by zhanghong on 15/12/23.
 */
public class WeixinKefu implements Serializable {

    //关联的平台账号
    User user;

    //客服工号
    String id;

    //由于微信客服账号格式特殊 : 工号@微信号， 需要单独字段
    String weixinKefuAccount;

    String avatar;

    //已删除
    boolean deleted;

}
