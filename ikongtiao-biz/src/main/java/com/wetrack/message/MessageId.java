package com.wetrack.message;

/**
 * Created by zhanghong on 16/3/1.
 */
public class MessageId {

    public static final int NEW_COMMISSION = 0;
    public static final int ACCEPT_MISSION = 1;
    public static final int REJECT_MISSION = 2;
    public static final int ASSIGNED_MISSION = 3;
    public static final int COMPLETED_MISSION = 4;
    public static final int CANCELL_MISSION = 5;


    public static final int NEW_FIX_ORDER = 100;
    public static final int WAITING_CONFIRM_FIX_ORDER = 101;
    public static final int CONFIRM_FIX_ORDER = 102;
    public static final int CANCEL_FIX_ORDER = 103;
    public static final int ASSIGNED_FIXER = 104;
    public static final int COMPLETED_FIX_ORDER = 105;

    public static final int FIXER_SUBMIT_AUDIT = 200;
    public static final int USER_SUBMIT_AUDIT = 201;
    public static final int FIXER_SUCCESS_AUDIT = 202;
    public static final int FIXER_FAILED_AUDIT = 203;

    public static final int KEFU_NOTIFY_WECHAT = 301;
    public static final int FIXER_NOTIFY_WECHAT = 302;
    public static final int KEFU_NOTIFY_FIXER = 303;


    //维修员认证类型
    public static final int FIXER_AUDIT_TYPE_CERT = 1;
    public static final int FIXER_AUDIT_TYPE_INSURANCE = 2;
    public static final int FIXER_AUDIT_TYPE_WELDER = 3;
    public static final int FIXER_AUDIT_TYPE_ELECTRICIAN = 4;



}
