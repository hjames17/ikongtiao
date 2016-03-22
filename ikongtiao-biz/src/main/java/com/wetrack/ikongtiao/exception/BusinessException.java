package com.wetrack.ikongtiao.exception;

/**
 * Created by zhanghong on 16/3/3.
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message){
        super(message);
    }
}
