package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.ikongtiao.domain.Address;
import com.wetrack.ikongtiao.domain.UserInfo;
import com.wetrack.ikongtiao.dto.UserInfoDto;
import com.wetrack.ikongtiao.service.api.fixer.FixerService;
import com.wetrack.ikongtiao.service.api.user.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


/**
 * Created by zhanghong on 16/1/4.
 */

@Controller
public class UserController {

    @Autowired
    UserInfoService userInfoService;

    @Autowired
    FixerService fixerService;

    private static final String BASE_PATH = "/user";


    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{id}", method = RequestMethod.GET)
    UserInfoDto info(@PathVariable(value = "id") String id) throws Exception{
        return  userInfoService.getUser(id);
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/update", method = RequestMethod.POST)
    void updateInfo(@RequestBody UpdateForm updateForm) throws Exception{
        if(updateForm == null || updateForm.getId() == null){
            throw new Exception("无效提交内容");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setId(updateForm.getId());
        userInfo.setType(updateForm.getType());
        userInfo.setPhone(updateForm.getPhone());
        userInfo.setAvatar(updateForm.getAvatar());
        userInfo.setAccountName(updateForm.getAccountName());
        userInfo.setAccountEmail(updateForm.getAccountEmail());
        userInfoService.update(userInfo, updateForm.getAddress());

    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/address/create", method = RequestMethod.POST)
    String createAddress(@RequestBody Address address) throws Exception{
        if(address == null || address.getUserId() == null){
            throw new Exception("无效地址信息");
        }
        Address created = userInfoService.createAddress(address);
        return created.getId().toString();
    }



        static class UpdateForm{
            String id;
            Integer type;
            String avatar;
            String accountName;
            String accountEmail;
            String phone;
            //修改地址需要提交原地址id，否则会创建新联系人
            Address address;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public Integer getType() {
                return type;
            }

            public void setType(Integer type) {
                this.type = type;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getAccountName() {
                return accountName;
            }

            public void setAccountName(String accountName) {
                this.accountName = accountName;
            }

            public String getAccountEmail() {
                return accountEmail;
            }

            public void setAccountEmail(String accountEmail) {
                this.accountEmail = accountEmail;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }

            public Address getAddress() {
                return address;
            }

            public void setAddress(Address address) {
                this.address = address;
            }
        }

}
