package com.wetrack.ikongtiao.domain.admin;

import java.io.Serializable;

/**
 * Created by zhanghong on 15/12/23.
 */
public class WexinKefu implements Serializable {

    //关联的平台账号
    User user;

    //由于微信客服账号格式特殊 : 工号@微信号， 需要单独字段
    String weixinKefuAccount;

    //客服工号，由微信分配
    String id;

    String avatar;

    //已删除
    boolean deleted;

}
