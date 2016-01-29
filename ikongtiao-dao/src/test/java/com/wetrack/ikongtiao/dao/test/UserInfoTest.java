package com.wetrack.ikongtiao.dao.test;

import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.repo.api.user.UserInfoRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhanghong on 15/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class UserInfoTest {


//    List<Fixer> list;

    @Autowired
    UserInfoRepo userInfoRepo;


    @Test
    public void test(){
        UserInfoDto u = userInfoRepo.getDtoById("15121510004100000008");
        System.out.println(u.getAddress().toString());
    }


//    @Test
//    public void testSave(){
//        Fixer fixer = new Fixer();
//        fixer.setName("Mr. Zhang");
//        fixer.setPhone("13588002001");
//        try {
//            fixer = fixerRepo.save(fixer);
//
//        } catch (Exception e) {
//            Assert.fail("save failed " + e.getMessage());
//            e.printStackTrace();
//        }
//    }
}