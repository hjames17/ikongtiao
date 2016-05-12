package com.utils.test;

import com.wetrack.ikongtiao.domain.fixer.FixerIncome;
import com.wetrack.ikongtiao.repo.api.fixer.FixerIncomeRepo;
import com.wetrack.ikongtiao.service.api.fixer.FixerIncomeService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * Created by zhanghong on 16/1/5.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class FixerIncomeTest {

    @Autowired
    FixerIncomeService fixerIncomeService;

    @Autowired
    FixerIncomeRepo fixerIncomeRepo;



    @Before
    public void init(){
    }

    @Test
    public void testAllInterface(){
        //add
        FixerIncome fixerIncome = fixerIncomeRepo.save(1, 2L, 3);
        //list
        List<FixerIncome> list = fixerIncomeService.listIncome(1, null, null, false);
        Assert.assertTrue(list.size() == 1);
        //delete
        boolean result = fixerIncomeRepo.remove(fixerIncome.getId());
        Assert.assertTrue(result);
    }

    @After
    public void tearDown(){

    }
}
