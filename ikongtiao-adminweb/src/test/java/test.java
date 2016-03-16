import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by zhanghong on 16/3/15.
 */
public class test {
    public static void main(final String[] args) {

        new ClassPathXmlApplicationContext("mvc-dispatcher-servlet.xml");

        System.out.println("app.log.name: "+System.getProperty("app.log.name"));

    }
}
