package com.utils.test;

import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

/**
 * Created by zhangsong on 15/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"classpath*:spring/*.xml"}
)
public class UserInfoServiceTest {

	@Resource
	private UserInfoRepo userInfoRepo;

	@Test
	public void testAddUserInfo(){
		UserInfo userInfo = new UserInfo();
		userInfo.setWechatOpenId("xiazhou");
		userInfo = userInfoRepo.save(userInfo);
		System.out.println(userInfo);
	}
}
