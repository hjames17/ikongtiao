package com.utils.test;

import com.wetrack.base.utils.Utils;
import com.wetrack.base.utils.http.HttpExecutor;
import com.wetrack.base.utils.jackson.Jackson;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.param.MissionSubmitParam;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.apache.http.entity.StringEntity;
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
public class MissionServiceTest {

	@Resource
	private MissionService missionService;

	@Test
	public void testSaveMission(){
		MissionSubmitParam param = new MissionSubmitParam();
		param.setUserId("15121522052300000010");
		param.setMachineTypeId(3);
		param.setPhone("18768125668");
		Mission mission = missionService.saveMission(param);
		System.out.println(mission);
	}


	@Test
	public void testListMission(){
		AppMissionQueryParam param = new AppMissionQueryParam();
		param.setUserId("15121522052300000010");
		param.setType(0);
		String s = Utils.get(HttpExecutor.class).post("http://localhost:8081/ikongtiao/mission/list").setEntity(new StringEntity(
				Jackson.base().writeValueAsString(param),"utf-8")).addHeader("Content-Type","application/json").executeAsString();

		System.out.println(s);
	}
}
