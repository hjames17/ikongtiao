package com.wetrack.ikongtiao.notification.services;

/**
 * Created by zhanghong on 16/4/11.
 */
public interface ScheduledTaskService {
    void NotificationOntime();
    void finishUnattendedMissions();
    void serviceLogNotification();
}
