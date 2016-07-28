import com.wetrack.ikongtiao.admin.WebConfig;
import com.wetrack.ikongtiao.service.redis.RedisSentinelConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;

/**
 * Created by zhanghong on 16/7/1.
 */
@Configuration
@ContextConfiguration(locations = "classpath*:/spring/service.xml")
@Import({WebConfig.class, RedisSentinelConfig.class})
@ComponentScan("com.wetrack")
public class Config {
}
