package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.CommentService;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Created by zhanghong on 16/1/15.
 */
@Controller(value = "userRepairOrderController")
public class RepairOrderController {

    static final String BASE_PATH = "/u/repairOrder";

    @Autowired
    MissionService missionService;

    @Autowired
    RepairOrderService repairOrderService;

    @Autowired
    CommentService commentService;

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/comment" , method = {RequestMethod.POST})
    public void create(@RequestBody Comment comment) throws Exception{
        commentService.createComment(comment.getRepairOrderId(), comment.getRate(), comment.getComment());
    }

    /**
     * 先确认订单，再发起付款，如果时线下付款，则不发起付款
     * @param form
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/confirm" , method = {RequestMethod.POST})
    public String confirm(@RequestBody ConfirmationForm form) throws Exception{
        if(form.getUserId() == null){
            throw new BusinessException("无效的用户id");
        }
        RepairOrder repairOrder = repairOrderService.getById(form.getRepairOrderId(), true);
        if(!repairOrder.getUserId().equals(form.getUserId())){
            throw new BusinessException("这不是您的维修单!");
        }
        if(form.getPayment() == null){
            throw new BusinessException("未选择支付方式!");
        }
        repairOrderService.confirm(form.getRepairOrderId(), form.isDeny(), form.getPayment());
        return "ok";
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/pay", method = {RequestMethod.POST})
    public void pay(){
        //TODO 线上付款
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH , method = {RequestMethod.GET})
    public RepairOrder repairOrder(@RequestParam(value = "uid") String uid,
                              @RequestParam(value = "repairOrderId") Long repairOrderId) throws Exception{
        return repairOrderService.getById(repairOrderId, false);
    }

    static class ConfirmationForm{
        String userId;
        Long repairOrderId;
        Integer payment; //0线下， 1 线上
        boolean deny;

        public Long getRepairOrderId() {
            return repairOrderId;
        }

        public void setRepairOrderId(Long repairOrderId) {
            this.repairOrderId = repairOrderId;
        }

        public boolean isDeny() {
            return deny;
        }

        public void setDeny(boolean deny) {
            this.deny = deny;
        }

        public Integer getPayment() {
            return payment;
        }

        public void setPayment(Integer payment) {
            this.payment = payment;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }
}
