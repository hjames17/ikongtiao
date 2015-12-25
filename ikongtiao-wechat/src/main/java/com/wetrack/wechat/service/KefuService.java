package com.wetrack.wechat.service;

import com.wetrack.wechat.domain.WxKefuOut;
import me.chanjar.weixin.common.exception.WxErrorException;

import java.io.File;
import java.util.List;

/**
 * Created by zhanghong on 15/12/23.
 */
public interface KefuService {

    String createOneAccountName() throws Exception;

    void createAccount(String kf_account, String nickname, String password) throws WxErrorException;

    void modifyAccount(String kf_account, String nickname, String password) throws WxErrorException;

    void deleteAccount(String kf_account, String nickname) throws WxErrorException;

    void setAvatar(String kf_account, File avatarFile) throws WxErrorException;

    List<WxKefuOut> listAccounts() throws WxErrorException;
}
