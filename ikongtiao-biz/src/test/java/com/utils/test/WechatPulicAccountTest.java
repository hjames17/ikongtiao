package com.utils.test;

import com.wetrack.ikongtiao.domain.wechat.WechatPublicAccount;
import com.wetrack.wechat.WechatPublicAccountService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhanghong on 16/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class WechatPulicAccountTest {

    @Autowired
    WechatPublicAccountService wechatPublicAccountService;

    @Before
    public void init(){

    }

    @Test
    public void get(){
        try {

            WechatPublicAccount account = wechatPublicAccountService.create("wx2a8ffab08a9c655f", "e5e6ee03a7ed22464b6261c0e457646a", "ydRhkecJX0HxkSTJByKFrwfgIa9ZZky8Uz3vozxB1Dl", "wetrack_token", "ewaids", "维大师之爱空调");
            System.out.println("Ok");
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
