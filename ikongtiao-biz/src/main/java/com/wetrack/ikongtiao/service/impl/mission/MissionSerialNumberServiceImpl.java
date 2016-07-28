package com.wetrack.ikongtiao.service.impl.mission;

import com.wetrack.ikongtiao.service.api.mission.MissionSerialNumberService;
import org.joda.time.DateTime;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundValueOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * Created by zhanghong on 16/7/6.
 */
@Service
public class MissionSerialNumberServiceImpl implements MissionSerialNumberService, InitializingBean {

    @Autowired
    RedisTemplate<String, Long> redisTemplate;

    private static final String REDIS_PREFIX = "MS_";

    private static final String SERIAL_PREFIX = "m";

    @Override
    public String getNextSerialNumber() {
        DateTime today = new DateTime();
        String day = today.toString("yyyyMMdd", Locale.CHINA);

        BoundValueOperations<String, Long> ops =  redisTemplate.boundValueOps(REDIS_PREFIX + day);
        long daySerial = 1;
        //如果今天还没有序列号，设为1，否则增加1，并且过期时间设置为明天
        if(!ops.setIfAbsent(1l)){
            daySerial = ops.increment(1);
        }else{
            ops.expireAt(today.plusDays(1).toDate());
        }
        return String.format("%s%s%03d", SERIAL_PREFIX, day, daySerial);
    }

    public static void main(String[] args){

        DateTime today = new DateTime();
        String day = today.toString("yyyyMMdd", Locale.CHINA);
        String output = String.format("%s%s%03d", SERIAL_PREFIX, day, 5l);
        System.out.println(output);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        redisTemplate.setValueSerializer(LongSerializer.INSTANCE);
    }

    public enum LongSerializer implements RedisSerializer<Long>{
        INSTANCE;

        @Override
        public byte[] serialize(Long aLong) throws SerializationException {
            if(null != aLong){
                return aLong.toString().getBytes();
            }else{
                return new byte[0];
            }
        }

        @Override
        public Long deserialize(byte[] bytes) throws SerializationException {
            if(bytes.length > 0) {
                return Long.parseLong(new String(bytes));
            }else{
                return null;
            }
        }
    }
}
