package com.wetrack.wechat.controller;

import com.wetrack.wechat.domain.WxKefuOut;
import com.wetrack.wechat.service.KefuService;
import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by zhanghong on 15/12/14.
 */
@Controller
//@AjaxResponseWrapper
@ResponseBody
public class KefuController {

    private static final Logger log = LoggerFactory.getLogger(KefuController.class);

    static final String BASE_PATH = "/kefu";
    @Autowired
    KefuService kefuService;

    /**
     * 客服账号微信限制最多添加10个
     * @return
     * @throws Exception
     */
    @RequestMapping(value = BASE_PATH + "/create" , method = {RequestMethod.POST})
    public String create(@RequestBody CreateForm form) throws Exception{
        kefuService.createAccount(kefuService.createOneAccountName(), form.getNickName(), form.getPassword());
        //TODO 保存到平台数据库中？暂时不需要，以后要记录添加记录的时候，再来加
        return "创建成功!";
    }

    @RequestMapping(value = BASE_PATH + "/delete" , method = {RequestMethod.GET})
    public String delete(@RequestParam(value = "account") String account,
                       @RequestParam(value = "nickName") String nickName) throws Exception{
        kefuService.deleteAccount(account, nickName);
        return "已删除!";
    }

    @RequestMapping(value = BASE_PATH + "/modify" , method = {RequestMethod.POST})
    public String modify(@RequestParam(value = "account") String account,
                       @RequestParam(value = "nickName") String nickName,
                       @RequestParam(value = "password", required = false) String password
                       ) throws Exception{
        kefuService.modifyAccount(account, nickName, password);
        return "修改成功!";
    }

//    @RequestMapping(value = BASE_PATH + "/password" , method = {RequestMethod.POST})
//    public String password(@RequestParam(value = "account") String account,
//                         @RequestParam(value = "nickName") String nickName,
//                         @RequestParam(value = "password") String password) throws Exception{
//        kefuService.modifyAccount(account, nickName, password);
//        return "修改成功!";
//    }

    /**
     * 上传图片作为客服人员的头像，头像图片文件必须是jpg格式，推荐使用640*640大小的图片
     * 格式为 form
     * @return
     * @throws Exception
     */
    @RequestMapping(value = BASE_PATH + "/avatar" , method = {RequestMethod.POST})
    public void avatar() throws Exception{
    }

    @RequestMapping(value = BASE_PATH + "/list" , method = {RequestMethod.GET})
    public List<WxKefuOut> list() throws Exception{
        return kefuService.listAccounts();
    }

    @RequestMapping(value = BASE_PATH + "/sendMessage" , method = {RequestMethod.POST})
    public void sendMessage() throws Exception{

    }


    public static class CreateForm {
        String nickName;
        String password;

        public void setNickName(String nickName){
            this.nickName = nickName;
        }
        public void setPassword(String password){
            this.password = password;
        }

        public String getNickName(){
            return this.nickName;
        }

        public String getPassword(){
            return this.password;
        }
    }


}
