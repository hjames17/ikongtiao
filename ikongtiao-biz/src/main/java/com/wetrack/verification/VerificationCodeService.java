package com.wetrack.verification;

import studio.wetrack.base.utils.sms.SmsService;

/**
 * Created by zhanghong on 16/1/15.
 */
public interface VerificationCodeService extends SmsService {

    String sendVericationCode(String phone);
    boolean verifyCode(String phone, String code);
    String findStoredCode(String mobilePhone);
}