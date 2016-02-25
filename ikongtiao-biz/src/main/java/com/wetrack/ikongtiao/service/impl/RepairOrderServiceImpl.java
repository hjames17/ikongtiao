package com.wetrack.ikongtiao.service.impl;

import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.AccessoryRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.CommentRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.message.MessageProcess;
import com.wetrack.message.MessageSimple;
import com.wetrack.message.MessageType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
@Service
public class RepairOrderServiceImpl implements RepairOrderService {

	@Autowired
	RepairOrderRepo repairOrderRepo;

	@Autowired
	AccessoryRepo accessoryRepo;

	@Autowired
	CommentRepo commentRepo;

	@Override
	public List<RepairOrder> listForMission(Integer missionId) throws Exception {
		return repairOrderRepo.listForMission(missionId);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public RepairOrder create(Integer creatorId, Integer missionId, String namePlateImg, String makeOrderNum,
			String repairOrderDesc, String accessoryContent) throws Exception {
		Mission mission = missionRepo.getMissionById(missionId);
		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setCreatorFixerId(creatorId);
		repairOrder.setUserId(mission.getUserId());
		repairOrder.setMissionId(missionId);
		repairOrder.setNamePlateImg(namePlateImg);
		repairOrder.setMakeOrderNum(makeOrderNum);
		repairOrder.setRepairOrderDesc(repairOrderDesc);
		repairOrder.setAccessoryContent(accessoryContent);
		repairOrder = repairOrderRepo.create(repairOrder);

		//更新维修单状态
		mission.setMissionState(MissionState.FIXING.getCode());
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);
		MessageSimple messageSimple = new MessageSimple();
		messageSimple.setFixerId(mission.getFixerId());
		messageProcess.process(MessageType.NEW_FIX_ORDER,messageSimple);
		return repairOrder;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addCost(Long repairOrderId, List<Accessory> accessoryList, Float laborCost, Boolean finishCost)
			throws Exception {
		//insert accessory list into accessory table
		if (accessoryList != null && accessoryList.size() > 0) {
			accessoryRepo.createMulti(accessoryList);
		}
		//update repairOrder
		if (laborCost != null) {
			RepairOrder repairOrder = new RepairOrder();
			repairOrder.setId(repairOrderId);
			repairOrder.setLaborCost(laborCost);
			if (finishCost != null && finishCost == true) {
				repairOrder.setRepairOrderState((byte) 1);
				sendCostFinishEvent(repairOrderId);
			}
			repairOrderRepo.update(repairOrder);
		}

	}

	@Override
	public void dispatchRepairOrder(Integer adminUserId, Long repairOrderId, Integer fixerId) throws Exception {
		RepairOrder order = repairOrderRepo.getById(repairOrderId);
		if (order == null) {
			throw new Exception("不存在的维修单");
		}

		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setFixerId(fixerId);
		repairOrder.setId(repairOrderId);
		repairOrder.setAdminUserId(adminUserId);
		repairOrder.setRepairOrderState((byte) 4);
		repairOrderRepo.update(repairOrder);

		Mission mission = new Mission();
		mission.setId(order.getMissionId());
		mission.setMissionState(MissionState.FIXING.getCode());
		MessageSimple messageSimple = new MessageSimple();
		messageSimple.setUserId(order.getUserId());
		messageSimple.setFixerId(fixerId);
		messageSimple.setMissionId(order.getMissionId());
		messageProcess.process(MessageType.ASSIGNED_FIXER,messageSimple);
		//TODO 发送通知
	}

	@Override
	public void setCostFinished(Integer adminUserId, Long repairOrderId) throws Exception {
		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setId(repairOrderId);
		repairOrder.setAdminUserId(adminUserId);
		repairOrder.setRepairOrderState((byte) 1);
		repairOrder.setUpdateTime(new Date());
		repairOrderRepo.update(repairOrder);

		sendCostFinishEvent(repairOrderId);
	}

	@Override
	public void setPrepared(Integer adminUserId, Long repairOrderId) throws Exception {
		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setId(repairOrderId);
		repairOrder.setAdminUserId(adminUserId);
		repairOrder.setRepairOrderState((byte) 3);
		repairOrder.setUpdateTime(new Date());
		repairOrderRepo.update(repairOrder);

	}

	@Override
	public void setFinished(Long repairOrderId) throws Exception {
		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setId(repairOrderId);
		repairOrder.setRepairOrderState((byte) 5);
		repairOrder.setUpdateTime(new Date());
		repairOrderRepo.update(repairOrder);
		repairOrder = repairOrderRepo.getById(repairOrderId);
		MessageSimple messageSimple = new MessageSimple();
		messageSimple.setUserId(repairOrder.getUserId());
		messageProcess.process(MessageType.COMPLETED_FIX_ORDER,messageSimple);
		//TODO 发送通知
	}

	@Override
	public void confirm(Long repairOrderId, boolean deny, Integer payment) throws Exception {

		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setId(repairOrderId);
		if (deny) {
			repairOrder.setRepairOrderState((byte) -1);
		} else {
			repairOrder.setRepairOrderState((byte) 2);
		}
		repairOrder.setPayment(payment);
		repairOrder.setUpdateTime(new Date());
		repairOrderRepo.update(repairOrder);
		repairOrder = repairOrderRepo.getById(repairOrderId);
		MessageSimple messageSimple = new MessageSimple();
		messageSimple.setUserId(repairOrder.getUserId());
		if(!deny)
			messageProcess.process(MessageType.CONFIRM_FIX_ORDER,messageSimple);
		else
			messageProcess.process(MessageType.CANCEL_FIX_ORDER,messageSimple);
	}

	@Override
	public RepairOrder getById(Long id, boolean brief) throws Exception {
		RepairOrder repairOrder = repairOrderRepo.getById(id);
		if (!brief) {
			repairOrder.setAccessoryList(accessoryRepo.listOfRepairOrderId(repairOrder.getId()));
		}
		return repairOrder;
	}

	@Override
	public void comment(Long repairOrderId, Integer rate, String commentString) throws Exception {
		Comment comment = new Comment();
		comment.setRepairOrderId(repairOrderId);
		comment.setRate(rate);
		comment.setComment(commentString);
		commentRepo.create(comment);

		//TODO 发送通知
	}

	@Override
	public Accessory createAccessory(Long repairOrderId, String name, Integer count, Float price) throws Exception {
		Accessory accessory = new Accessory();
		accessory.setRepairOrderId(repairOrderId);
		accessory.setCount(count);
		accessory.setName(name);
		accessory.setPrice(price);
		return accessoryRepo.create(accessory);
	}

	@Override
	public boolean updateAccessory(Accessory accessory) throws Exception {
		return accessoryRepo.update(accessory);
	}

	@Override
	public boolean deleteAccessory(Long accessoryId) throws Exception {
		return accessoryRepo.delete(accessoryId);
	}

	@Value("${weixin.page.host}")
	String weixinPageHost;
	@Value("${weixin.page.mission}")
	String weixinMissionPage;
	static final String ACTION_CONFIRMATION = "repairOderId";

	@Resource
	private MessageProcess messageProcess;

	@Autowired
	MissionRepo missionRepo;

	private void sendCostFinishEvent(Long repairOrderId) {
		try {
			RepairOrder order = repairOrderRepo.getById(repairOrderId);
			Mission mission = missionRepo.getMissionById(order.getMissionId());
			MessageSimple pushData = new MessageSimple();
			pushData.setUserId(mission.getUserId());
			String url = String
					.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_CONFIRMATION,
							mission.getUserId(), repairOrderId);
			pushData.setUrl(url);
			messageProcess.process(MessageType.WAITING_CONFIRM_FIX_ORDER, pushData);
		} catch (Exception e) {
			//TODO 报警
		}
	}
}
