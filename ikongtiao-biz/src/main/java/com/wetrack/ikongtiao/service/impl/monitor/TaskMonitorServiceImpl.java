package com.wetrack.ikongtiao.service.impl.monitor;

import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.service.api.monitor.TaskMonitorService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * Created by zhanghong on 16/4/11.
 * 任务监控服务，提供业务把需要监控的服务进行管理的接口
 */
@Service
public class TaskMonitorServiceImpl implements TaskMonitorService, InitializingBean {

    /**
     * 线程安全性在这里不重要
     */

    @Autowired
    RedisTemplate<String, Long> jedisTemplate;

    BoundHashOperations<String, String, Long> hashOps;

    @Override
    public void putTask(String id) {
        hashOps.putIfAbsent(id, System.currentTimeMillis());
    }

    @Override
    public long getTaskLiveTime(String id) {
        return hashOps.get(id);
    }

    @Override
    public void deleteTask(String id) {
        hashOps.delete(id);
    }

    @Override
    public Collection<String> getTaskIds() {
        return hashOps.keys();
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        hashOps = jedisTemplate.boundHashOps(Constants.NOTIFICATION_MAP);
    }
}
