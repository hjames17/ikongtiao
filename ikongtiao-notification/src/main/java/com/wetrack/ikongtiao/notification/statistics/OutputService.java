package com.wetrack.ikongtiao.notification.statistics;

import java.util.Map;

/**
 * Created by zhanghong on 15/5/6.
 */
public interface OutputService<T> {
    T getOutput(Map<String, Object> dataMap, String templateName);
}
