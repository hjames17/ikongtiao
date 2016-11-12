package com.wetrack.ikongtiao.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;

/**
 * Created by zhanghong on 16/9/16.
 */
@Deprecated
//@Service
public class EventProcessor implements ApplicationListener<ApplicationEvent> {

    @Autowired
    RepairOrderEventHandler repairOrderEventHandler;

    @Autowired
    MissionEventHandler missionEventHandler;

    @Async
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if(!(event.getSource() instanceof Event)){
            return;
        }

        if(event.getSource() instanceof RepairOrderEvent){
            repairOrderEventHandler.handle((Event)event.getSource());
        }else if(event.getSource() instanceof MissionEvent){
            missionEventHandler.handle((Event)event.getSource());
        }
    }
}
