package com.wetrack.ikongtiao.dao.test;

import com.wetrack.ikongtiao.repo.api.FaultTypeRepo;
import com.wetrack.ikongtiao.repo.api.im.ImSessionRepo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by zhanghong on 15/12/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class FaultTypeTest {

    @Autowired
    FaultTypeRepo faultTypeRepo;

    @Autowired
    ImSessionRepo imSessionRepo;

    @Before
    public void init(){
    }

    @After
    public void clear(){

    }

    @Test
    public void add(){

        List<String> list = Arrays.asList("不制冷或效果差", "不制热效果差", "不能启动",
                "凝露或者漏水（空调内机）", "凝露或者漏水（空调外机）", "启动后非正常停机", "室内机噪音大或异响", "室外机噪音大或异响",
                "通电不工作", "外观缺陷", "显示故障代码或者故障灯亮起", "自动开关机", "上门清洗保养滤芯", "上门清洗保养滤网", "清洗或保养", "其他");

        int i = 1;
        for(String name : list){
            faultTypeRepo.create(name, i++);
        }


    }

    @Test
    public void list(){
    }



}
