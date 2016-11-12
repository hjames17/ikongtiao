package com.wetrack.ikongtiao.events;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;

/**
 * Created by zhanghong on 16/9/16.
 */
@Deprecated
//@Service
public class EventPublishServiceImpl implements EventPublishService,ApplicationEventPublisherAware {

    ApplicationEventPublisher eventPublisher;

    static class AppEvent extends ApplicationEvent{

        /**
         * Create a new ApplicationEvent.
         *
         * @param source the object on which the event initially occurred (never {@code null})
         */
        public AppEvent(Event source) {
            super(source);
        }
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.eventPublisher = applicationEventPublisher;
    }

    @Override
    public void publishEvent(Event event){
        AppEvent appEvent = new AppEvent(event);

        eventPublisher.publishEvent(appEvent);

    }
}
