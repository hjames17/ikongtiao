package com.utils.test;

import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class RepairOrderTest {

    @Autowired
    RepairOrderService repairOrderService;

//    @Test
//    public void testAddRepairOrder(){
//        try {
//            RepairOrder repairOrder = repairOrderService.create(1123, "/images/namePlate", "kxtx-xttt201602", "芯片坏了，水冷器坏了", "芯片，水冷器");
//            System.out.println(repairOrder.getId());
//            Assert.assertTrue(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void testAddAccessoryList(){
        List<Accessory> accessoryList = new ArrayList<Accessory>();
        Accessory accessory = new Accessory();
        accessory.setName("芯片");
        accessory.setCount(1);
        accessory.setRepairOrderId(1L);
        accessory.setPrice((float) 100.8);
        accessory.setMissionId(1123);
        accessoryList.add(accessory);

        Accessory accessory2 = new Accessory();
        accessory2.setName("水冷器");
        accessory2.setCount(2);
        accessory2.setRepairOrderId(1L);
        accessory2.setMissionId(1123);
        accessory2.setPrice((float) 827.8);
        accessoryList.add(accessory2);
        try {
            repairOrderService.addCost(1L, accessoryList, (float)50.5);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
