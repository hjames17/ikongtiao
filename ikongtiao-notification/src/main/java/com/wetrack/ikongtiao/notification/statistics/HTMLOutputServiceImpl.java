package com.wetrack.ikongtiao.notification.statistics;

import lombok.Data;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.velocity.VelocityConfig;

import java.io.StringWriter;
import java.util.Map;

/**
 * Created by zhanghong on 15/5/6.
 */
@Service
@Data
public class HTMLOutputServiceImpl  implements OutputService<String>{
    //velocity
    @Autowired(required = false)
    VelocityConfig velocityConfig;

    @Override
    public String getOutput(Map<String, Object> dataMap, String templateName){
        try {
            //初始化并取得Velocity引擎

            VelocityEngine ve = velocityConfig.getVelocityEngine();//new VelocityEngine();

            ve.init();


            //取得velocity的模版

            Template t = ve.getTemplate(templateName + ".vm");

            //取得velocity的上下文context
            VelocityContext context = new VelocityContext();

            //把数据填入上下文
            for(String key : dataMap.keySet()){
                context.put(key, dataMap.get(key));
            }

            //输出流

            StringWriter writer = new StringWriter();

            //转换输出

            t.merge(context, writer);

            System.out.println(writer.toString());
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
