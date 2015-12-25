package com.wetrack.wechat.service.impl;

import com.google.gson.JsonElement;
import com.google.gson.internal.Streams;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.wetrack.base.container.ContainerContext;
import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.utils.encrypt.MD5;
import com.wetrack.ikongtiao.domain.Sequence;
import com.wetrack.wechat.domain.WxKefuOut;
import com.wetrack.wechat.domain.WxKefuSubmit;
import com.wetrack.wechat.service.KefuService;
import com.wetrack.wechat.utils.AvatorUploadExecutor;
import me.chanjar.weixin.common.exception.WxErrorException;
import me.chanjar.weixin.common.util.http.SimpleGetRequestExecutor;
import me.chanjar.weixin.common.util.http.SimplePostRequestExecutor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.util.json.WxMpGsonBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringReader;
import java.util.List;

/**
 * Created by zhanghong on 15/12/23.
 */
@Service
public class KefuServiceImpl implements KefuService{

    private static final String KEFU_ACCOUNT_SEQUENCE = "weixinKefu";

    @Value("${wechat.id}")
    String weixinId;

    @Autowired
    WxMpService weixinService;

    @Override
    public String createOneAccountName() throws Exception {
        CommonDao commonDao = (CommonDao) ContainerContext.get().getContext().getBean("commonDao");
        long id = commonDao.mapper(Sequence.class).sql("getNextVal").session().selectOne(KEFU_ACCOUNT_SEQUENCE);
        return id + "@" + weixinId;
    }

    @Override
    public void createAccount(String kf_account, String nickname, String password) throws WxErrorException {
        String url = "https://api.weixin.qq.com/customservice/kfaccount/add";
        WxKefuSubmit kefu = new WxKefuSubmit();
        kefu.setKf_account(kf_account);
        kefu.setNickname(nickname);
        kefu.setPassword(MD5.encryptHex(password));
        weixinService.execute(new SimplePostRequestExecutor(), url, kefu.toJson());
    }

    @Override
    public void modifyAccount(String kf_account, String nickname, String password) throws WxErrorException {
        String url = "https://api.weixin.qq.com/customservice/kfaccount/update";
        WxKefuSubmit kefu = new WxKefuSubmit();
        kefu.setKf_account(kf_account);
        kefu.setNickname(nickname);
        if(password != null) {
            kefu.setPassword(MD5.encryptHex(password));
        }
        weixinService.execute(new SimplePostRequestExecutor(), url, kefu.toJson());
    }

    @Override
    public void deleteAccount(String kf_account, String nickname) throws WxErrorException {

        //耍一个花招，由于不知道设置的密码是多少，所以在删除前，把密码改一下，再用这个密码删除
        String delingPass = "deleting";
        modifyAccount(kf_account, nickname, delingPass);


        String url = "https://api.weixin.qq.com/customservice/kfaccount/del";
        WxKefuSubmit kefu = new WxKefuSubmit();
        kefu.setKf_account(kf_account);
        kefu.setNickname(nickname);
        kefu.setPassword(MD5.encryptHex(delingPass));
        weixinService.execute(new SimplePostRequestExecutor(), url, kefu.toJson());
    }

    @Override
    public void setAvatar(String kf_account, File avatarFile) throws WxErrorException {
        String url = "http://api.weixin.qq.com/customservice/kfaccount/uploadheadimg?kf_account=" + kf_account;
        weixinService.execute(new AvatorUploadExecutor(), url, avatarFile);
    }

    @Override
    public List<WxKefuOut> listAccounts() throws WxErrorException {
        String url = "https://api.weixin.qq.com/cgi-bin/customservice/getkflist";
        String responseContent = weixinService.execute(new SimpleGetRequestExecutor(), url, null);
        /**
         * 正确时的JSON返回结果
         * {
         "kf_list": [
         {
         "kf_account": "test1@test",
         "kf_nick": "ntest1",
         "kf_id": "1001"
         "kf_headimgurl": " http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjfUS8Ym0GSaLic0FD3vN0V8PILcibEGb2fPfEOmw/0"
         },
         {
         "kf_account": "test2@test",
         "kf_nick": "ntest2",
         "kf_id": "1002"
         "kf_headimgurl": " http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjfUS8Ym0GSaLic0FD3vN0V8PILcibEGb2fPfEOmw/0"
         },
         {
         "kf_account": "test3@test",
         "kf_nick": "ntest3",
         "kf_id": "1003"
         "kf_headimgurl": " http://mmbiz.qpic.cn/mmbiz/4whpV1VZl2iccsvYbHvnphkyGtnvjfUS8Ym0GSaLic0FD3vN0V8PILcibEGb2fPfEOmw/0"
         }
         ]
         }
         */
        JsonElement tmpJsonElement = Streams.parse(new JsonReader(new StringReader(responseContent)));
        return WxMpGsonBuilder.INSTANCE.create().fromJson(tmpJsonElement.getAsJsonObject().get("kf_list"),
                new TypeToken<List<WxKefuOut>>() {
                }.getType());
    }
}
