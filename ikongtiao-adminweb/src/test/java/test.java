import com.wetrack.message.MessageParamKey;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhanghong on 16/3/15.
 */
public class test {
    public static void main(final String[] args) {

//        new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");
        int messageId = 2;
        Map<String, Object> params = new HashMap<>();
        params.put(MessageParamKey.USER_ID, "16031018068700000038");
        params.put(MessageParamKey.MISSION_ID, 247);
//
//        System.out.println("app.log.name: "+System.getProperty("app.log.name"));
//        HttpExecutor.PostExecutor post = Utils.get(HttpExecutor.class).post();
//        post.addHeader(new BasicHeader("Content-Type", MediaType.APPLICATION_JSON_VALUE));
//        if(params != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(params, headers);
            RestTemplate restTemplate = new RestTemplate();
            URI uri = restTemplate.postForLocation("http://localhost:8084" + "/" + messageId, entity);
//        }
//        String result = post.executeAsString();
        System.out.print(uri.toString());
    }
}
