package com.wetrack.wechat.service.impl;

import com.wetrack.wechat.domain.WxKefu;
import com.wetrack.wechat.service.KefuService;
import com.wetrack.wechat.utils.AvatorUploadExecutor;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

/**
 * Created by zhanghong on 15/12/23.
 */
@Service
public class KefuServiceImpl implements KefuService{

    @Autowired
    WxMpService weixinService;

    @Override
    public void createAccount(WxKefu wxKefu) throws WxErrorException {

    }

    @Override
    public void modifyAccount(WxKefu wxKefu) throws WxErrorException {

    }

    @Override
    public void deleteAccount(WxKefu wxKefu) throws WxErrorException {

    }

    @Override
    public void setAvatar(WxKefu wxKefu, File avatorFile) throws WxErrorException {
        String url = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?kf_account=" + wxKefu.getKf_account();
        weixinService.execute(new AvatorUploadExecutor(), url, avatorFile);
    }

    @Override
    public List<WxKefu> listAccounts() throws WxErrorException {
        return null;
    }
}
