package com.utils.test;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.fixer.GetuiClientIdService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghong on 16/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class FixerTest {

    @Autowired
    FixerService fixerService;

    @Autowired
    GetuiClientIdService getuiClientIdService;


    List<Fixer> fixerList;
    List<FixerCertInfo> fixerCertInfos;
    @Before
    public void init(){
        fixerList = new ArrayList<>();
        fixerCertInfos = new ArrayList<>();
    }

    @Test
    public void get(){
        try {
            fixerService.createAccount("13111111111", "tttt", "123123123");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testUpdateCertInfoTest(){
//        try {
//            Fixer fixer = new Fixer();
//            fixer.setId(1);
//            fixer.setInService(false);
//            fixerService.updateInfo(fixer);
//        } catch (Exception e) {
//            Assert.fail();
//            e.printStackTrace();
//        }
//    }

//    @Test
//    public void createFixer(){
//
//    }
//
//    @Test
//    public void submitCertInfoTest(){
//        FixerInsuranceInfo insuranceInfo = new FixerInsuranceInfo();
//        insuranceInfo.setFixerId(2);
//        insuranceInfo.setInsuranceNum("30326198910013241");
//        insuranceInfo.setInsuranceImg("/insuranceImg");
//        insuranceInfo.setInsuranceDate(new Date());
//        Date ex = DateUtils.addYears(new Date(), 2);
//        insuranceInfo.setExpiresAt(ex);
//        try {
//            fixerService.submitInsuranceInfo(insuranceInfo);
//            Assert.assertTrue(true);
//        } catch (Exception e) {
//            Assert.fail("测试保险认证失败, " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        FixerProfessionInfo electricianInfo = new FixerProfessionInfo();
//        electricianInfo.setFixerId(2);
//        electricianInfo.setProfessNum("30326198910013241");
//        electricianInfo.setProfessImg("/electricianImg");
//        electricianInfo.setProfessType(0);
//        try {
//            fixerService.submitProfessInfo(electricianInfo);
//            Assert.assertTrue(true);
//        } catch (Exception e) {
//            Assert.fail("测试提交电工认证失败, " + e.getMessage());
//            e.printStackTrace();
//        }
//
//        FixerProfessionInfo welderInfo = new FixerProfessionInfo();
//        welderInfo.setFixerId(2);
//        welderInfo.setProfessNum("30326198910013241");
//        welderInfo.setProfessImg("/welderImg");
//        welderInfo.setProfessType(1);
//        try {
//            fixerService.submitProfessInfo(welderInfo);
//            Assert.assertTrue(true);
//        } catch (Exception e) {
//            Assert.fail("测试提交焊工认证失败, " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}
