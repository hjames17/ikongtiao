package com.wetrack.ikongtiao.dao.test;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.param.FixerQueryForm;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghong on 15/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class FixerTest {

    List<Fixer> list;

    @Autowired
    FixerRepo fixerRepo;

    @Before
    public void init(){
        list = new ArrayList<>();
    }

    @After
    public void clear(){
        for(Fixer fixer : list){
            try {
                fixerRepo.delete(fixer.getId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void list(){
        FixerQueryForm form = new FixerQueryForm();
        form.setStart(0);
//        form.setInService(false);
        form.setInsured(false);
        try {
            int count = fixerRepo.countFixerByQueryParam(form);
            Assert.assertEquals(0, count);
        } catch (Exception e) {
            Assert.fail("failed");
            e.printStackTrace();
        }
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
