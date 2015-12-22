package com.utils.test;

import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
//import com.wetrack.wechat.deprecated.service.api.WechatTokenService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/11/13.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"classpath*:spring/*.xml"}
)
public class UtilsTest {

	@Resource
	private RedisOperations<String,String> redisTemplate;

//	@Resource
//	private WechatTokenService wechatTokenService;
	@Test
	public void test(){
		String result = Utils.get(HttpExecutor.class).get(
				"http://int.xiaokakeji.com:8090/ins/business/team/list/1.4?refId=19454&debugMode=1&type=1").executeAsString();
		System.out.println(result);
	}

	@Test
	public void testRedisMap(){
		ValueOperations<String,String> data = redisTemplate.opsForValue();
		data.getOperations().delete("name");
		data.getOperations().delete("age");
		data.getOperations().delete("sex");

		System.out.println(data.get("name")+":"+data.get("age")+":"+data.get("sex"));
	}

	@Test
	public void testRedisList(){
		//ListOperations<String,String> data = redisTemplate.opsForList();
		ListOperations<String,String> data =  redisTemplate.opsForList();
		data.getOperations().delete("data");
		System.out.println(data.leftPop("name") + ":" + data.leftPop("data"));

	}

//	@Test
//	public void testGetToken(){
//		System.out.println(wechatTokenService.getToken());
//	}
}
