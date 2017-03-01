package com.wetrack.verification;

import com.wetrack.ikongtiao.utils.RegExUtil;
import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghong on 16/1/15.
 */
@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {


    public static final String MOBILE_CODE_ = "MOBILE_CODE_";

    private static final int TIME_TO_LIVE = 5*60;
    @Value("${sms.username}")
    String userName;
    @Value("${sms.password}")
    String password;
    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate<Serializable, Serializable> jedisTemplate;

    @Override
    public String sendVericationCode(String phone) {
        String code = generateACode();
        String smsResult = sendCodeToMobilePhone(phone, code);
        // 整数，0，发送成功；-1、帐号未注册；-2、其他错误；-3、密码错误；-4、手机号格式不对；-5、余额不足；-6、定时发送时间不是有效的时间格式；
        if ("0".equals(smsResult)) {
            storeVericationCode(phone, code);
            return null;
        } else if ("-1".equals(smsResult)) {
            return "账号未注册";
        } else if ("-2".equals(smsResult)) {
            return "其它错误";
        } else if ("-3".equals(smsResult)) {
            return "密码错误";
        } else if ("-4".equals(smsResult)) {
            return "手机号格式不对";
        } else if ("-5".equals(smsResult)) {
            return "余额不足";
        } else if ("-6".equals(smsResult)) {
            return "定时发送时间不是有效的时间格式";
        } else {
            return "其它错误";
        }
    }

    @Override
    public boolean verifyCode(String phone, String code) {
        if(RegExUtil.isMobilePhone(phone)){
            String storedCode = findStoredCode(phone);
            return storedCode != null && storedCode.equals(code);
        }
        return false;
    }

    private String generateACode(){
        final int mobile_code = (int) ((Math.random() * 9 + 1) * 1000);
        return String.valueOf(mobile_code);
    }


    private String sendCodeToMobilePhone(String mobilePhone, String code){
        String URL  = "http://www.106551.com/ws/Send.aspx";
        String content = new String("您的验证码是："+code+"，切勿将验证码透漏于他人。如非本人操作，请忽略此消息。验证码有效时间5分钟。");
        HttpClient httpClient = new DefaultHttpClient();
        HttpPost httpRequest = new HttpPost(URL);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("CorpID", userName));
        params.add(new BasicNameValuePair("Pwd", password));
        params.add(new BasicNameValuePair("Mobile", mobilePhone));
        params.add(new BasicNameValuePair("Content", content));
        String returnStr = null;
        try {
            HttpEntity httpentity = new UrlEncodedFormEntity(params, "UTF-8");
            httpRequest.setEntity(httpentity);
            HttpResponse httpResponse = httpClient.execute(httpRequest);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                //整数，0，发送成功；-1、帐号未注册；-2、其他错误；-3、密码错误；-4、手机号格式不对；-5、余额不足；-6、定时发送时间不是有效的时间格式；
                returnStr = EntityUtils.toString(httpResponse.getEntity());
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnStr;
    }


    private void storeVericationCode(String phone, final String code){
        final String final_phone = MOBILE_CODE_ + phone;
        jedisTemplate.execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection connection)
                    throws DataAccessException {
                connection.set(
                        jedisTemplate.getStringSerializer().serialize(
                                final_phone),
                        jedisTemplate.getStringSerializer().serialize(code));
                //过期后，验证码自动从redis中移除
                connection.expire(jedisTemplate.getStringSerializer().serialize(
                        final_phone), TIME_TO_LIVE);
                return null;
            }
        });
    }

    @Override
    public String findStoredCode(String mobilePhone){
        final String final_key = MOBILE_CODE_ + mobilePhone;
        return jedisTemplate.execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection connection)
                    throws DataAccessException {
                byte[] keys = jedisTemplate.getStringSerializer().serialize(final_key);
                if (connection.exists(keys)) {
                    byte[] value = connection.get(keys);
                    return jedisTemplate.getStringSerializer().deserialize(value);
                }
                return null;
            }
        });

    }
}
