package com.utils.test;

import com.gexin.rp.sdk.base.IPushResult;
import com.gexin.rp.sdk.base.impl.SingleMessage;
import com.gexin.rp.sdk.base.impl.Target;
import com.gexin.rp.sdk.exceptions.RequestException;
import com.gexin.rp.sdk.http.IGtPush;
import com.gexin.rp.sdk.template.LinkTemplate;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhangsong on 15/12/15.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
		locations = {"classpath*:spring/*.xml"}
)
public class MissionServiceTest {

	@Resource
	private MissionService missionService;

	@Autowired
	MissionRepo missionRepo;


	List<Mission> addedList;

	@Before
	public void init(){
		addedList = new ArrayList<>();
	}

//	@Test
//	public void testSaveMission(){
//		MissionSubmitParam param = new MissionSubmitParam();
//		param.setUserId("15121522052300000010");
//		param.setMachineTypeId(3);
//		param.setPhone("18768125668");
//		Mission mission = missionService.saveMission(param);
//		System.out.println(mission);
//	}
//
//
//
//
//	@Test
//	public void testListMission(){
//		AppMissionQueryParam param = new AppMissionQueryParam();
//		param.setUserId("15121522052300000010");
//		param.setType(0);
//		String s = Utils.get(HttpExecutor.class).post("http://localhost:8081/ikongtiao/mission/list").setEntity(new StringEntity(
//				Jackson.base().writeValueAsString(param),"utf-8")).addHeader("Content-Type","application/json").executeAsString();
//
//		System.out.println(s);
//	}

//	@Test
//	public void testAcceptMission() throws Exception{
//		MissionSubmitParam param = new MissionSubmitParam();
//		param.setUserId("15121522052300000010");
//		param.setMachineTypeId(3);
//		param.setPhone("18768125668");
//		Mission mission = missionService.saveMission(param);
//		addedList.add(mission);
//		System.out.println("CREATED :" + mission);
//
//		Integer testAdminUserId = 12345;
//		String testDescribe = "describe";
//		//do test
//		missionService.acceptMission(mission.getId(), testAdminUserId);
//
//		Mission read = missionRepo.getMissionById(mission.getId());
//		Assert.assertEquals(MissionState.ACCEPT.getCode(), read.getMissionState());
//
//
//		Mission readAgain = missionRepo.getMissionById(mission.getId());
//		Assert.assertEquals(testDescribe, readAgain.getMissionDesc());
//
//	}

	@Test
	public void testDispatchMission() throws Exception{

		Integer testAdminUserId = 12345;
		//do test
		missionService.dispatchMission(27, 3, testAdminUserId);

		Thread.sleep(60000);
	}

//	@After
//	public void afterTest(){
//		for(Mission mission : addedList){
//			try {
//				missionRepo.deleteMission(mission.getId());
//			} catch (Exception e) {
//				Assert.fail("error on deleting added mission with id " + mission.getId());
//				e.printStackTrace();
//			}
//		}
//	}
	private static  String appId = "GlxU0ZuGNjAe239Ovzrec5";
	private static String appKey = "Eiqu6khvAV8FyITq5GXdz2";
	private static String masterSecret = "qhFzO17pvH6HmslXeCaMP";
	private static String url = "http://sdk.open.api.igexin.com/serviceex";
	@Test
	public void testPush() throws IOException {

		IGtPush push = new IGtPush(url, appKey, masterSecret);

		LinkTemplate template = linkTemplateDemo();
		SingleMessage message = new SingleMessage();
		message.setOffline(true);
		//离线有效时间，单位为毫秒，可选
		message.setOfflineExpireTime(24 * 3600 * 1000);
		message.setData(template);
		message.setPushNetWorkType(0); //可选。判断是否客户端是否wifi环境下推送，1为在WIFI环境下，0为不限制网络环境。
		Target target = new Target();

		target.setAppId(appId);
		target.setClientId("28b4aea5993b12583a068c21d4b5d79c");
		//用户别名推送，cid和用户别名只能2者选其一
		//String alias = "个";
		//target.setAlias(alias);
		IPushResult ret = null;
		try{
			ret = push.pushMessageToSingle(message, target);
		}catch(RequestException e){
			e.printStackTrace();
			ret = push.pushMessageToSingle(message, target, e.getRequestId());
		}
		if(ret != null){
			System.out.println(ret.getResponse().toString());
		}else{
			System.out.println("服务器响应异常");
		}
	}
	public static LinkTemplate linkTemplateDemo() {
		LinkTemplate template = new LinkTemplate();
		// 设置APPID与APPKEY
		template.setAppId(appId);
		template.setAppkey(appKey);
		// 设置通知栏标题与内容
		template.setTitle("小钟－－－－－－");
		template.setText("xiaozhong ，");
		// 配置通知栏图标
		template.setLogo("icon.png");
		// 配置通知栏网络图标，填写图标URL地址
		template.setLogoUrl("");
		// 设置通知是否响铃，震动，或者可清除
		template.setIsRing(true);
		template.setIsVibrate(true);
		template.setIsClearable(true);
		// 设置打开的网址地址
		template.setUrl("http://www.baidu.com");
		return template;
	}

}
