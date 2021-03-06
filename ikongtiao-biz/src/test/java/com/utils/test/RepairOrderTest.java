package com.utils.test;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.RoImage;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import org.junit.Assert;
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
//            RepairOrder repairOrder = repairOrderService.create(111, 1123, "/images/namePlate", "kxtx-xttt201602", "芯片坏了，水冷器坏了", "芯片，水冷器");
//            System.out.println(repairOrder.getId());
//            Assert.assertTrue(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @Test
    public void get(){
        try {
//            RepairOrderQueryParam queryParam = new RepairOrderQueryParam();
//            queryParam.setType(0);
//            queryParam.setNotForAdminUserId(1);
            List<RepairOrder> orders = repairOrderService.listForMission("157", false);
//            PageList<RepairOrder> page = repairOrderService.list(queryParam);
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

    @Test
    public void update(){
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setId(210L);
        List<RoImage> images = new ArrayList<>();
        RoImage image = new RoImage();
        image.setUrl("/test/image/url1");
        images.add(image);
        RoImage image1 = new RoImage();
        image1.setUrl("/test/image/url3");
        images.add(image1);
        repairOrder.setImages(images);
        try {
            repairOrderService.update(repairOrder, repairOrderService.getById("210", true));
            System.out.println("ok");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Test
//    public void testAddAccessoryList(){
//        List<Accessory> accessoryList = new ArrayList<Accessory>();
//        Accessory accessory = new Accessory();
//        accessory.setName("芯片");
//        accessory.setCount(1);
//        accessory.setRepairOrderId(1L);
//        accessory.setPrice((float) 100.8);
//        accessory.setMissionId(1123);
//        accessoryList.add(accessory);
//
//        Accessory accessory2 = new Accessory();
//        accessory2.setName("水冷器");
//        accessory2.setCount(2);
//        accessory2.setRepairOrderId(1L);
//        accessory2.setMissionId(1123);
//        accessory2.setPrice((float) 827.8);
//        accessoryList.add(accessory2);
//        try {
//            repairOrderService.addCost(1L, accessoryList, (float)50.5, false);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
