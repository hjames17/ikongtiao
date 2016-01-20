package com.wetrack.verification;

/**
 * Created by zhanghong on 16/1/15.
 */
public interface VerificationCodeService {

    String sendVericationCode(String phone);
    boolean verifyCode(String phone, String code);
    String findStoredCode(String mobilePhone);
}