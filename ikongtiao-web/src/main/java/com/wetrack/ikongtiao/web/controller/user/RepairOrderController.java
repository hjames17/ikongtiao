package com.wetrack.ikongtiao.web.controller.user;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.service.api.CommentService;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.wetrack.ikongtiao.constant.RepairOrderState.CLOSED;

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
    @RequestMapping(value = BASE_PATH + "/confirm" , method = {RequestMethod.POST},
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public void confirm(@RequestBody ConfirmationForm form) throws Exception{
        if(form.getUserId() == null){
            throw new BusinessException("无效的用户id");
        }
        RepairOrder repairOrder = repairOrderService.getById(form.getRepairOrderId(), true);
        if(!repairOrder.getUserId().equals(form.getUserId())){
            throw new BusinessException("这不是您的维修单!");
        }
        if(form.isDeny() && repairOrder.getRepairOrderState().equals(CLOSED.getCode())){
            throw new BusinessException("不要重复关闭维修单!");
        }else if(form.getPayment() == null){
            throw new BusinessException("未选择支付方式!");
        }
        repairOrderService.confirm(form.getRepairOrderId(), form.isDeny(), form.getPayment(), form.isNeedInvoice(), form.getInvoiceTitle());
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/{id}", method = {RequestMethod.GET})
    public RepairOrder repairOrder(@RequestParam(value = "uid") String uid,
                                   @PathVariable(value = "id") long id) throws Exception{
        return repairOrderService.getById(id, false);
    }


    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/listOfMission" , method = {RequestMethod.GET})
    public List<RepairOrder> listForMission(@RequestParam(value = "missionId") Integer missionId,
                                            @RequestParam(value = "uid") String uid) throws Exception{
        return repairOrderService.listForMission(missionId, false);
    }

    static class ConfirmationForm{
        String userId;
        Long repairOrderId;
        Integer payment; //0线下， 1 线上
        boolean deny;
        boolean needInvoice;
        String invoiceTitle;//发票抬头

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

        public boolean isNeedInvoice() {
            return needInvoice;
        }

        public void setNeedInvoice(boolean needInvoice) {
            this.needInvoice = needInvoice;
        }

        public String getInvoiceTitle() {
            return invoiceTitle;
        }

        public void setInvoiceTitle(String invoiceTitle) {
            this.invoiceTitle = invoiceTitle;
        }
    }
}
