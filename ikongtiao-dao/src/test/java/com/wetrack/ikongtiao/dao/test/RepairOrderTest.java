package com.wetrack.ikongtiao.dao.test;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import org.junit.Before;
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
public class RepairOrderTest {

//    List<Fixer> list;

    @Autowired
    RepairOrderRepo repo;

    @Before
    public void init(){
//        list = new ArrayList<>();
    }


    @Test
    public void list(){
        RepairOrder order = repo.getById(144L);
        System.out.println("ok");
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
