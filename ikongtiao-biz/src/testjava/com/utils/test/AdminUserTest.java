package com.utils.test;

import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zhanghong on 16/1/7.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        locations = {"classpath*:spring/*.xml"}
)
public class AdminUserTest {

    @Autowired
    AdminService adminService;

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
            User user = adminService.getByAdminId("linchangmao@wetrack.studio");
            Assert.assertTrue(true);
        } catch (Exception e) {
            e.printStackTrace();
            Assert.fail();
        }
    }

//    @Test
//    public void add(){
//        User user = new User();
//        user.setEmail("test@test.test");
//        user.setPhone("13322321232");
//        user.setName("test");
//        user.setPassword("112343");
//        user.setAdminType(AdminType.SUPERVISOR);
//        try {
//            adminService.create(user);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}
