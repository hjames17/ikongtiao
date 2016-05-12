package com.wetrack.ikongtiao.geo;


import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.utils.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Random;

/**
 * Created by zhanghong on 15/4/28.
 */
public class GeoUtil {
    private static final Logger log = LoggerFactory.getLogger(GeoUtil.class);
    protected final static String BAI_DU_KEY ="22f107d711f3ff7037ebb4e66f57b613";
    protected final static String GAO_DE_KEY ="6ff8f1e952d704ca83f3efff035856b6";
    protected final static String BAI_DU_LOCATION_SERVICE_URL = "http://api.map.baidu.com/geocoder/v2/?address=%s&output=json&ak=%s";
    protected final static String GAO_DE_LOCATION_SERVICE_URL = "http://restapi.amap.com/geocode/simple?resType=json&encode=utf-8&range=100&roadnum=3&crossnum=2&poinum=1&retvalue=1&sid=7000&rid=%d&address=%S&ia=1&key=%s";
    protected final static String GAODE_JSON_RESULT_APPEND="AMap.MAjaxResult[%d]=";
    protected final static Random rd=new Random();
    protected final static int seed=1000000;
//    protected static JsonBinder binder = JsonBinder.buildNonDefaultBinder();

    public static BaiduLocatonResult generaterLocationFromBaidu(String location) {
        String url = String
                .format(BAI_DU_LOCATION_SERVICE_URL,
                        location, BAI_DU_KEY);


        BaiduLocatonResult result = null;
        try {
            result = Jackson.simple().readValue(Util.readUrlContent(url, "UTF-8", 5000), BaiduLocatonResult.class);
//            result = binder.fromJson(Util.readUrlContent(url, "UTF-8", 5000),
//                    BaiduLocatonResult.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
        return result;
    }

    public static GaoDeLocation generaterLocationFromGaoDe(String location,int count) throws UnsupportedEncodingException {
        location= URLEncoder.encode(location, "UTF-8");
        int rand=rd.nextInt(seed);
        String url = String
                .format(GAO_DE_LOCATION_SERVICE_URL,
                        rand	,location, GAO_DE_KEY);
        GaoDeLocation result = null;
        try {
            String content=Util.readUrlContent(url, "UTF-8", 5000);
            content=content.replace(String
                    .format(GAODE_JSON_RESULT_APPEND,rand), "");
            result =Jackson.simple().readValue(content,
                    GaoDeLocation.class);
        } catch (Exception e) {
            count++;
            if(count<=3){
                result=generaterLocationFromGaoDe(location,count);
            }
            log.info(e.getMessage());
        }
        return result;
    }

    public static GeoLocation getGeoLocationFromAddress(String address) throws UnsupportedEncodingException {
        BaiduLocatonResult baiDuResult = GeoUtil.generaterLocationFromBaidu(address);
        if(baiDuResult==null||baiDuResult.getStatus()!=0||baiDuResult.getResult().getConfidence()<5){
            //百度地址不可用，尝试高德
            GaoDeLocation result=GeoUtil.generaterLocationFromGaoDe(address, 0);
            if (result == null || (!result.getStatus().equals("E0")) || result.getList()== null||result.getList().size()==0) {
                return null;
            }else{
                GaoDeLocation.GaoDeResult l=result.getList().get(0);
                return new GeoLocation(l.getX(), l.getY());
            }

        }else{
            return new GeoLocation(baiDuResult.getResult().getLocation().getLng()
                    ,baiDuResult.getResult().getLocation().getLat());

        }
    }


}
