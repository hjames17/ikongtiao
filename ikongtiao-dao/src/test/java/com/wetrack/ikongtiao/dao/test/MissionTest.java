package com.wetrack.ikongtiao.dao.test;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.MissionAddress;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.repo.api.mission.MissionAddressRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by zhanghong on 15/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class MissionTest {

    List<Fixer> list;

    @Autowired
    MissionRepo missionRepo;

    @Autowired
    MissionAddressRepo missionAddressRepo;

    @Before
    public void init(){
        list = new ArrayList<>();
    }

    @After
    public void clear(){

    }

    @Test
    public void list(){
//        FixerMissionQueryParam form = new FixerMissionQueryParam();
        AppMissionQueryParam form = new AppMissionQueryParam();
        form.setStart(0);
        form.setPageSize(300);
//        form.setInService(false);
//        form.setHistory(true);
        try {
            List<MissionDto> list = missionRepo.listMissionByAppQueryParam(form);
            for(MissionDto missionDto : list){
                Mission mission = new Mission();
                BeanUtils.copyProperties(missionDto, mission);
                MissionAddress missionAddress = missionAddressRepo.getMissionAddressById(mission.getId());
                if(missionAddress != null) {
                    mission.setContacterName(missionAddress.getName());
                    mission.setContacterPhone(missionAddress.getPhone());
                    mission.setProvinceId(missionAddress.getProvinceId());
                    mission.setCityId(missionAddress.getCityId());
                    mission.setDistrictId(missionAddress.getDistrictId());
                    mission.setAddress(missionAddress.getAddress());
                    mission.setLongitude(missionAddress.getLongitude());
                    mission.setLatitude(missionAddress.getLatitude());
                    missionRepo.update(mission);
                }
            }
            System.out.println(Arrays.toString(list.toArray()));
        } catch (Exception e) {
            Assert.fail("failed");
            e.printStackTrace();
        }
    }



}
