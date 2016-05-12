package com.wetrack.ikongtiao.notification.controllers;

import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.notification.services.MessageMultiChannelService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

/**
 * Created by zhanghong on 16/4/11.
 */
@Controller
public class PushController {

    private static final Logger logger = LoggerFactory.getLogger(PushController.class);

    @Autowired
    @Qualifier("defaultMessageService")
    MessageMultiChannelService messageService;

    /**
     * TODO 接口的访问安全性
     * @param messageId
     * @param params
     */
    @RequestMapping(value = "/{messageId}")
    @ResponseBody
    void push(@PathVariable int messageId,  @RequestBody Map<String, Object> params){
        logger.debug("received message {}, params {}", messageId, Jackson.mobile().writeValueAsString(params));
        messageService.send(messageId, params);
    }
}
