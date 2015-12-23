package com.wetrack.wechat.service;

import com.wetrack.wechat.domain.WxKefu;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.File;
import java.util.List;

/**
 * Created by zhanghong on 15/12/23.
 */
public interface KefuService {

    void createAccount(WxKefu wxKefu) throws WxErrorException;

    void modifyAccount(WxKefu wxKefu) throws WxErrorException;

    void deleteAccount(WxKefu wxKefu) throws WxErrorException;

    void setAvatar(WxKefu wxKefu, File avatorFile) throws WxErrorException;

    List<WxKefu> listAccounts() throws WxErrorException;
}
