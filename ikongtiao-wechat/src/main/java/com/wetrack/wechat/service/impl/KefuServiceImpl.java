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
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.StringReader;
import java.text.ParseException;
import java.util.*;

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
        return "kf" + id + "@" + weixinId;
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

    public static void main(String[] args){
//        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
//        wxMpConfigStorage.setAppId("wx2a8ffab08a9c655f");
//        wxMpConfigStorage.setSecret("e5e6ee03a7ed22464b6261c0e457646a");
//        wxMpConfigStorage.setToken("wetrack_token");
//        wxMpConfigStorage.setAesKey("VtPI5tYeLQUmnWz3zIugbDzsugUR7TIxRkTc7M4IpNa");
//        wxMpConfigStorage.setPartnerId("1318465701");
//        wxMpConfigStorage.setPartnerKey("weidashizhiaikongtiaodelta160310");
//        WxMpService weixinService = new WxMpServiceImpl();
//        weixinService.setWxMpConfigStorage(wxMpConfigStorage);
//        String nonce_str = System.currentTimeMillis() + "";
//
//        SortedMap<String, String> packageParams = new TreeMap<String, String>();
//        packageParams.put("appid", wxMpConfigStorage.getAppId());
//        packageParams.put("mch_id", wxMpConfigStorage.getPartnerId());
//        packageParams.put("out_trade_no", "RO141");
//        packageParams.put("nonce_str", nonce_str);
//
//        WxMpPayResult wxMpPayResult = null;
//        String sign = WxCryptUtil.createSign(packageParams, wxMpConfigStorage.getPartnerKey());
//        String xml = "<xml>" +
//                "<appid>" + wxMpConfigStorage.getAppId() + "</appid>" +
//                "<mch_id>" + wxMpConfigStorage.getPartnerId() + "</mch_id>" +
//                "<out_trade_no>" + "RO141" + "</out_trade_no>" +
//                "<nonce_str>" + nonce_str + "</nonce_str>" +
//                "<sign>" + sign + "</sign>" +
//                "</xml>";
//
//        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/pay/orderquery");
//
//        StringEntity entity = new StringEntity(xml, Consts.UTF_8);
//        httpPost.setEntity(entity);
//        try {
//            // http 连接池的总大小
//            int poolMaxTotal = 50;
//            // 设计每个站点路由下允许的最大连接占用数（底层默认值为2）
//            int poolMaxPerRoute = 50;
//            // 网络失败后，重试请求的次数，默认值3次
//            int retryTimes = 3;
//            // 与目标站点创建连接的最大时间（底层默认值无限等待）
//            int connectTimeout = 10 * 1000;
//            // 设置与目标站点的等待数据的最大时间，包括连续数据传输的时间（底层默认值为-1，表示操作系统默认）
//            int socketTimeout = 30 * 1000;
//            // 从池中获取请求连接的时间（底层默认值无限等待）
//            int connectionRequestTimeout = 5 * 1000;
//            PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
//            connManager.setMaxTotal(poolMaxTotal);
//            connManager.setDefaultMaxPerRoute(poolMaxPerRoute);
//
//            // HttpRequestRetryHandler retryHandler = new
//            // HttpRequestRetryHandlerExtend(retryTimes, false, httpRetryExtend);
//            HttpRequestRetryHandler retryHandler = new DefaultHttpRequestRetryHandler(retryTimes, false);
//
//            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(connectTimeout)
//                    .setSocketTimeout(socketTimeout).setConnectionRequestTimeout(connectionRequestTimeout).build();
//
//            CloseableHttpClient client = HttpClientBuilder.create().setConnectionManager(connManager)
//                    .setRetryHandler(retryHandler).setDefaultRequestConfig(requestConfig).build();
//
//            CloseableHttpResponse response = client.execute(httpPost);
//            String responseContent = Utf8ResponseHandler.INSTANCE.handleResponse(response);
//            XStream xstream = XStreamInitializer.getInstance();
//            xstream.alias("xml", WxMpPayResult.class);
//            wxMpPayResult = (WxMpPayResult) xstream.fromXML(responseContent);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        WxMpPayResult result = weixinService.getJSSDKPayResult(null, "RO234");
//        System.out.println(Jackson.mobile().writeValueAsString(wxMpPayResult));

        TimeZone.setDefault(TimeZone.getTimeZone("GMT"));
        TimeZone systemTimeZone = TimeZone.getDefault();
        TimeZone cnTimeZone = TimeZone.getTimeZone("GMT+8");
        System.out.printf("system time zone %s, cn time zone %s\n", systemTimeZone.getDisplayName(), cnTimeZone.getDisplayName());
        System.out.printf("system timezone offset %d,  cn timezone offset %d\n", systemTimeZone.getRawOffset(), cnTimeZone.getRawOffset());
        Date date = null;

        try {
            date = DateUtils.parseDate("20160615153000", Locale.CHINA, "yyyyMMddHHmmss");
        } catch (ParseException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        date = DateUtils.addMilliseconds(date, systemTimeZone.getRawOffset() - cnTimeZone.getRawOffset());
        System.out.println(date.toString() + ", year number " + date.getYear());

//        System.out.println(MD5.encryptHex("123456"));
    }
}
