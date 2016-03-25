package com.utils.test;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
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
	@Test
	public void testListMission(){
		AppMissionQueryParam param = new AppMissionQueryParam();
//		param.setUserId();
		param.setUserId("16031018068700000038");
//		param.setType(0);
		param.setPage(0);
		param.setPageSize(10);
		PageList<MissionDto> page = missionService.listMissionByAppQueryParam(param);
		System.out.println("ok");
	}

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


}
