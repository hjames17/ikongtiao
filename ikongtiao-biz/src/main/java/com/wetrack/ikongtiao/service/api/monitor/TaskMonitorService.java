package com.wetrack.ikongtiao.service.api.monitor;

import java.util.Collection;

/**
 * Created by zhanghong on 16/4/11.
 */
public interface TaskMonitorService {

    void putTask(String id);
    long getTaskLiveTime(String id);
    void deleteTask(String id);
    Collection<String> getTaskIds();

}
