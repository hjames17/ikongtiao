/*
 * Copyright (C), 2014-2015, 杭州小卡科技有限公司
 * FileName: ImSession.java
 * Author: mbg
 * Date: 2016年03月17日 下午 11:23:55
 * Description:
 */
package com.wetrack.ikongtiao.domain;

import java.io.Serializable;

public class ImSessionCount implements Serializable {

    private String peerId;

    int count;

    public String getPeerId() {
        return peerId;
    }

    public void setPeerId(String peerId) {
        this.peerId = peerId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}