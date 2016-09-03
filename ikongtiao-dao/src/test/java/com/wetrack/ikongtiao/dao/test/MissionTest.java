package com.wetrack.ikongtiao.dao.test;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.dto.MissionDto;
import com.wetrack.ikongtiao.param.AppMissionQueryParam;
import com.wetrack.ikongtiao.repo.api.mission.MissionAddressRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import org.joda.time.DateTime;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

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

    @Autowired
    RepairOrderRepo repairOrderRepo;

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
        form.setPageSize(100);
        DateTime start = DateTime.parse("2016-07-09T00:00");
        DateTime end = DateTime.parse("2016-07-09T23:59");
        form.setCreateTimeStart(start.toDate());
        form.setCreateTimeEnd(end.toDate());
//        form.setInService(false);
//        form.setHistory(true);
        int roCount = 1;
        try {
            String dayStr = start.toString("yyyyMMdd", Locale.CHINA);
            int missionCount = 1;
            List<MissionDto> list = missionRepo.listMissionByAppQueryParam(form);
            for(int count = list.size() - 1; count >= 0 ; count--){
                System.out.println("++++++++++++++++++++++++");
                Mission md = new Mission();
                md.setId(list.get(count).getId());
                String serialNumber = String.format("m%s%03d", dayStr, missionCount);
                List<RepairOrder> repairOrders = repairOrderRepo.listForMission(md);
                md.setSerialNumber(list.get(count).getSerialNumber());
                System.out.printf("mission id %d, original sid %s, sid %s\n", md.getId(), md.getSerialNumber(), serialNumber);
                if(StringUtils.isEmpty(list.get(count).getSerialNumber())) {
                    md.setSerialNumber(serialNumber);
                }
                if(repairOrders != null && repairOrders.size() > 0){
                    roCount = 1;
                    for(RepairOrder ro : repairOrders){
                        RepairOrder repairOrder = new RepairOrder();
                        repairOrder.setId(ro.getId());
                        repairOrder.setMissionSerialNumber(ro.getMissionSerialNumber());
                        if(StringUtils.isEmpty(repairOrder.getMissionSerialNumber())) {
                            repairOrder.setMissionSerialNumber(serialNumber);
                        }
                        repairOrder.setSerialNumber(ro.getSerialNumber());
                        if(StringUtils.isEmpty(repairOrder.getSerialNumber())) {
                            repairOrder.setSerialNumber(String.format("r%s%03d%02d", dayStr, missionCount, roCount));
                        }
                        System.out.printf("\torder id %d, original sid %s, msid is %s, sid %s\n", ro.getId(), ro.getSerialNumber(), ro.getMissionSerialNumber(), repairOrder.getSerialNumber());
//                            repairOrderRepo.updateSid(repairOrder);
                        roCount++;
                    }
                }
                missionCount++;
                System.out.println("------------------------");
//                missionRepo.updateSid(md);
            }
            System.out.println(Arrays.toString(list.toArray()));
        } catch (Exception e) {
            Assert.fail("failed");
            e.printStackTrace();
        }
    }



}
