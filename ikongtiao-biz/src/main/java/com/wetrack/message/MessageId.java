package com.wetrack.message;

/**
 * Created by zhanghong on 16/3/1.
 */
public class MessageId {

    /**
     * 任务相关消息
     */
    public static final int NEW_COMMISSION = 1;
    public static final int ACCEPT_MISSION = 2;
    public static final int REJECT_MISSION = 3;
    public static final int ASSIGNED_MISSION = 4;
    public static final int COMPLETED_MISSION = 5;
    public static final int CANCELL_MISSION = 6;


    /**
     * 维修单相关消息
     */
    public static final int NEW_FIX_ORDER = 101;
    public static final int WAITING_CONFIRM_FIX_ORDER = 102;
    public static final int CONFIRM_FIX_ORDER = 103;
    public static final int CANCEL_FIX_ORDER = 104;
    public static final int ASSIGNED_FIXER = 105;
    public static final int COMPLETED_FIX_ORDER = 106;
    public static final int COMMENT_REPAIR_ORDER = 107;

    /**
     * 认证提交
     */
    public static final int FIXER_SUBMIT_CERT_AUDIT = 201;
    public static final int FIXER_SUBMIT_INSURANCE_AUDIT = 202;
    public static final int FIXER_SUBMIT_PROFESS_AUDIT = 203;

    /**
     * 认证通过
     */
    public static final int FIXER_CERT_AUDIT_PASS = 211;
    public static final int FIXER_INSURANCE_AUDIT_PASS = 212;
    public static final int FIXER_PROFESS_AUDIT_PASS = 213;

    /**
     * 认证驳回
     */
    public static final int FIXER_CERT_AUDIT_DENY = 221;
    public static final int FIXER_INSURANCE_AUDIT_DENY = 222;
    public static final int FIXER_PROFESS_AUDIT_DENY = 223;

    /**
     * 认证过期或者失效
     */
    public static final int USER_SUBMIT_AUDIT = 231;
    public static final int USER_INSURANCE_AUDIT = 232;
    public static final int USER_PROFESS_AUDIT = 233;

    /**
     * 客户认证信息
     */
    public static final int USER_SUBMIT_CERT_AUDIT = 251;
    public static final int USER_CERT_AUDIT_PASS = 252;
    public static final int USER_CERT_AUDIT_DENY = 253;

    public static final int KEFU_NOTIFY_WECHAT = 301;
    public static final int FIXER_NOTIFY_WECHAT = 302;
    public static final int KEFU_NOTIFY_FIXER = 303;
    public static final int WECHAT_NOTIFY_FIXER = 304;


    //维修员认证类型
//    public static final int FIXER_AUDIT_TYPE_CERT = 1;
//    public static final int FIXER_AUDIT_TYPE_INSURANCE = 2;
//    public static final int FIXER_AUDIT_TYPE_WELDER = 3;
//    public static final int FIXER_AUDIT_TYPE_ELECTRICIAN = 4;

}
