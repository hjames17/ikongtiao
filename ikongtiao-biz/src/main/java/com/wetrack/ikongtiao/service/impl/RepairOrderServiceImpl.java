package com.wetrack.ikongtiao.service.impl;

import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.repo.api.repairOrder.AccessoryRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.CommentRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
@Service
public class RepairOrderServiceImpl implements RepairOrderService{

    @Autowired
    RepairOrderRepo repairOrderRepo;

    @Autowired
    AccessoryRepo accessoryRepo;

    @Autowired
    CommentRepo commentRepo;

    @Override
    public List<RepairOrder> listForMission(Integer missionId) throws Exception{
        return repairOrderRepo.listForMission(missionId);
    }

    @Override
    public RepairOrder create(Integer missionId, String namePlateImg, String makeOrderNum, String repairOrderDesc, String accessoryContent) throws Exception{
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setMissionId(missionId);
        repairOrder.setNamePlateImg(namePlateImg);
        repairOrder.setMakeOrderNum(makeOrderNum);
        repairOrder.setRepairOrderDesc(repairOrderDesc);
        repairOrder.setAccessoryContent(accessoryContent);
        return repairOrderRepo.create(repairOrder);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void addCost(Long repairOrderId, List<Accessory> accessoryList, Float laborCost) throws Exception{
        //insert accessory list into accessory table
        accessoryRepo.createMulti(accessoryList);
        //update repairOrder
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setId(repairOrderId);
        repairOrder.setLaborCost(laborCost);
        repairOrderRepo.update(repairOrder);

        //TODO 发送通知
    }


    @Override
    public void dispatchRepairOrder(Integer adminUserId, Long repairOrderId, Integer fixerId) throws Exception{
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setFixerId(fixerId);
        repairOrder.setId(repairOrderId);
        repairOrder.setAdminUserId(adminUserId);
        repairOrderRepo.update(repairOrder);

        //TODO 发送通知
    }

    @Override
    public void setPrepared(Integer adminUserId, Long repairOrderId) throws Exception{
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setId(repairOrderId);
        repairOrder.setAdminUserId(adminUserId);
        repairOrder.setRepairOrderState((byte)4);
        repairOrderRepo.update(repairOrder);

    }

    @Override
    public void setFinished(Long repairOrderId) throws Exception{
        RepairOrder repairOrder = new RepairOrder();
        repairOrder.setId(repairOrderId);
        repairOrder.setRepairOrderState((byte)6);
        repairOrderRepo.update(repairOrder);

        //TODO 发送通知
    }

    @Override
    public RepairOrder getById(Long id) throws Exception{
        return repairOrderRepo.getById(id);
    }

    @Override
    public void comment(Long repairOrderId, Integer rate, String commentString) throws Exception{
        Comment comment = new Comment();
        comment.setRepairOrderId(repairOrderId);
        comment.setRate(rate);
        comment.setComment(commentString);
        commentRepo.create(comment);

        //TODO 发送通知
    }
}