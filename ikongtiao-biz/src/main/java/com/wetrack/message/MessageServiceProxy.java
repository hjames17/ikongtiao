package com.wetrack.message;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Map;

/**
 * Created by zhanghong on 16/4/11.
 */
//@Service
public class MessageServiceProxy implements MessageService{

    static final Logger log = LoggerFactory.getLogger(MessageServiceProxy.class);

    @Value("${host.push}")
    private String hostPush;

    @Override
    public void send(int messageId, Map<String, Object> params) {

//        HttpExecutor.PostExecutor post = Utils.get(HttpExecutor.class).post(hostPush + "/" + messageId);
//        if(params != null) {
//            for (String key : params.keySet()) {
//                post.addFormParam(key, params.get(key).toString());
//            }
//        }
//        String result = post.executeAsString();
//        log.info(String.format("message %d send result %s", messageId, result));

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);
            RestTemplate restTemplate = new RestTemplate();
            URI uri = restTemplate.postForLocation(hostPush + "/" + messageId, entity);
        }catch (Exception e){

        }
    }
}
