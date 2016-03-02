package com.wetrack.ikongtiao.service.impl;

import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.domain.repairOrder.AuditInfo;
import com.wetrack.ikongtiao.domain.repairOrder.Comment;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.AccessoryRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.AuditInfoRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.CommentRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.RepairOrderRepo;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.SettingsService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@Autowired
	SettingsService settingsService;

	@Autowired
	MessageService messageService;

	@Override
	public List<RepairOrder> listForMission(Integer missionId, boolean includesAuditInfo) throws Exception {
		List<RepairOrder> list = repairOrderRepo.listForMission(missionId);

		if(!includesAuditInfo && list != null){
			for(RepairOrder order : list){
				order.setAuditInfo(null);
			}
		}
		return list;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public RepairOrder create(Integer creatorId, Integer missionId, String namePlateImg, String makeOrderNum,
			String repairOrderDesc, String accessoryContent) throws Exception {
		Mission mission = missionRepo.getMissionById(missionId);
		RepairOrder repairOrder = new RepairOrder();
		if(creatorId == null) {
			repairOrder.setCreatorFixerId(mission.getFixerId());
		}else{
			repairOrder.setCreatorFixerId(creatorId);
		}
		repairOrder.setUserId(mission.getUserId());
		repairOrder.setMissionId(missionId);
		repairOrder.setAdminUserId(mission.getAdminUserId());
		repairOrder.setNamePlateImg(namePlateImg);
		repairOrder.setMakeOrderNum(makeOrderNum);
		repairOrder.setRepairOrderDesc(repairOrderDesc);
		repairOrder.setAccessoryContent(accessoryContent);
		repairOrder = repairOrderRepo.create(repairOrder);

		//创建维修单状态
		mission.setMissionState(MissionState.FIXING.getCode());
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);

		//发送消息
		try {
//			MessageSimple messageSimple = new MessageSimple();
//			messageSimple.setFixerId(mission.getFixerId());
//			messageSimple.setRepairOrderId(repairOrder.getId());
//			messageProcess.process(MessageType.NEW_FIX_ORDER, messageSimple);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(MessageParamKey.MISSION_ID, mission.getId());
			params.put(MessageParamKey.USER_ID, mission.getUserId());
			params.put(MessageParamKey.FIXER_ID, mission.getFixerId());
			params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrder.getId());
			messageService.send(MessageId.NEW_FIX_ORDER, params);
		}catch (Exception e){
			//ignore
		}
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
				handleCostFinished(repairOrder);
			}
			repairOrderRepo.update(repairOrder);
		}

	}

	@Transactional(rollbackFor = Exception.class)
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
		repairOrder.setRepairOrderState(RepairOrderState.FIXING.getCode());
		repairOrderRepo.update(repairOrder);

		Mission mission = new Mission();
		mission.setId(order.getMissionId());
		mission.setMissionState(MissionState.FIXING.getCode());
		missionRepo.update(mission);

//		MessageSimple messageSimple = new MessageSimple();
//		messageSimple.setUserId(order.getUserId());
//		messageSimple.setFixerId(fixerId);
//		messageSimple.setMissionId(order.getMissionId());
//		messageSimple.setRepairOrderId(repairOrder.getId());
//		messageProcess.process(MessageType.ASSIGNED_FIXER, messageSimple);


		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, mission.getId());
		params.put(MessageParamKey.USER_ID, mission.getUserId());
		params.put(MessageParamKey.FIXER_ID, mission.getFixerId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrder.getId());
		messageService.send(MessageId.ASSIGNED_FIXER, params);
	}

	@Override
	public void setCostFinished(Integer adminUserId, Long repairOrderId) throws Exception {
		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setId(repairOrderId);
		repairOrder.setAdminUserId(adminUserId);
		handleCostFinished(repairOrder);
	}

	private void handleCostFinished(RepairOrder repairOrder) throws Exception{
		boolean autoAudit = false;
		if(settingsService.getBusinessSettings().isRepairOrderAutoAudit()){
			autoAudit = true;
		}

		if(autoAudit){
			repairOrder.setRepairOrderState(RepairOrderState.AUDIT_READY.getCode());
		}else{
			repairOrder.setRepairOrderState(RepairOrderState.COST_READY.getCode());
		}
		repairOrderRepo.update(repairOrder);

		if(autoAudit){
			//通知
			sendCostFinishEvent(repairOrder.getId());
		}
	}

	@Override
	public void setPrepared(Integer adminUserId, Long repairOrderId) throws Exception {
		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setId(repairOrderId);
		repairOrder.setAdminUserId(adminUserId);
		repairOrder.setRepairOrderState(RepairOrderState.PREPARED.getCode());
		repairOrderRepo.update(repairOrder);

	}


//	static final String ACTION_COMMENT = "comment";
	@Override
	public void setFinished(Long repairOrderId) throws Exception {
		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setId(repairOrderId);
		repairOrder.setRepairOrderState(RepairOrderState.COMPLETED.getCode());
		repairOrderRepo.update(repairOrder);
		repairOrder = repairOrderRepo.getById(repairOrderId);


//		MessageSimple messageSimple = new MessageSimple();
//		messageSimple.setUserId(repairOrder.getUserId());
//		messageSimple.setRepairOrderId(repairOrder.getId());
//		String url = String
//				.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_COMMENT,
//						repairOrder.getUserId(), repairOrderId);
//		messageSimple.setUrl(url);
//		messageProcess.process(MessageType.COMPLETED_FIX_ORDER,messageSimple);



		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
		params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
		messageService.send(MessageId.COMPLETED_FIX_ORDER, params);
	}

	@Override
	public void confirm(Long repairOrderId, boolean deny, Integer payment) throws Exception {

		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setId(repairOrderId);
		if (deny) {
			repairOrder.setRepairOrderState(RepairOrderState.CLOSED.getCode());
		} else {
			repairOrder.setRepairOrderState(RepairOrderState.CONFIRMED.getCode());
		}
		repairOrder.setPayment(payment);
		repairOrderRepo.update(repairOrder);

		repairOrder = repairOrderRepo.getById(repairOrderId);
//		MessageSimple messageSimple = new MessageSimple();
//		messageSimple.setUserId(repairOrder.getUserId());
//		messageSimple.setRepairOrderId(repairOrder.getId());
//		if(!deny)
//			messageProcess.process(MessageType.CONFIRM_FIX_ORDER,messageSimple);
//		else
//			messageProcess.process(MessageType.CANCEL_FIX_ORDER,messageSimple);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
		params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
		if(!deny) {
			messageService.send(MessageId.CONFIRM_FIX_ORDER, params);
		}else{
			messageService.send(MessageId.CANCEL_FIX_ORDER, params);
		}
	}

	@Autowired
	AuditInfoRepo auditInfoRepo;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void audit(Integer adminId, Long repairOrderId, Boolean pass, String reason) throws Exception {
		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
		if(repairOrder == null){
			throw new Exception("无效的维修单");
		}
		if(!repairOrder.getRepairOrderState().equals(RepairOrderState.COST_READY.getCode())){
			throw new Exception("该维修单不处于待审核状态");
		}

		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setRepairOrderId(repairOrderId);
		auditInfo.setPass(pass);
		auditInfo.setReason(reason);
		auditInfo.setAdminId(adminId);

		auditInfoRepo.create(auditInfo);

		if(pass){
			repairOrder.setRepairOrderState(RepairOrderState.AUDIT_READY.getCode());
			repairOrderRepo.update(repairOrder);

			//通知
			sendCostFinishEvent(repairOrder.getId());
		}else{
			repairOrder.setRepairOrderState(RepairOrderState.NEW.getCode());
			repairOrderRepo.update(repairOrder);
			//TODO 通知有被驳回的待审核维修单
		}
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

//	@Value("${weixin.page.host}")
//	String weixinPageHost;
//	@Value("${weixin.page.mission}")
//	String weixinMissionPage;
//	static final String ACTION_CONFIRMATION = "repairOderId";

//	@Resource
//	private MessageProcess messageProcess;

	@Autowired
	MissionRepo missionRepo;

	private void sendCostFinishEvent(Long repairOrderId) {
		try {
			RepairOrder order = repairOrderRepo.getById(repairOrderId);
//			Mission mission = missionRepo.getMissionById(order.getMissionId());
//			MessageSimple pushData = new MessageSimple();
//			pushData.setUserId(mission.getUserId());
//			String url = String
//					.format("%s%s?action=%s&uid=%s&id=%s", weixinPageHost, weixinMissionPage, ACTION_CONFIRMATION,
//							mission.getUserId(), repairOrderId);
//			pushData.setUrl(url);
//			messageProcess.process(MessageType.WAITING_CONFIRM_FIX_ORDER, pushData);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(MessageParamKey.MISSION_ID, order.getMissionId());
			params.put(MessageParamKey.USER_ID, order.getUserId());
			params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
			messageService.send(MessageId.WAITING_CONFIRM_FIX_ORDER, params);
		} catch (Exception e) {
			//TODO 报警
		}
	}
}
