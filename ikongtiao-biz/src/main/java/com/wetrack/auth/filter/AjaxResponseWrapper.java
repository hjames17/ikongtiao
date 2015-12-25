package com.wetrack.auth.filter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *	@Author zhanghong
 *  把一个web请求包装成一个AjaxResult<T>对象返回
 *
 */
@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface AjaxResponseWrapper {
}
