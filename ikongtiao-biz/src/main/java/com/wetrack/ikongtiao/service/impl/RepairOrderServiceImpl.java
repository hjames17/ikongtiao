package com.wetrack.ikongtiao.service.impl;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.Constants;
import com.wetrack.ikongtiao.constant.MissionState;
import com.wetrack.ikongtiao.constant.RepairOrderState;
import com.wetrack.ikongtiao.domain.Mission;
import com.wetrack.ikongtiao.domain.PaymentInfo;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.domain.repairOrder.AuditInfo;
import com.wetrack.ikongtiao.domain.repairOrder.RoImage;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.RepairOrderQueryParam;
import com.wetrack.ikongtiao.repo.api.fixer.FixerIncomeRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.*;
import com.wetrack.ikongtiao.service.api.PaymentService;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.SettingsService;
import com.wetrack.ikongtiao.service.api.monitor.TaskMonitorService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by zhanghong on 16/1/7.
 */
@Service
public class RepairOrderServiceImpl implements RepairOrderService {

	private final static Logger logger = LoggerFactory.getLogger(RepairOrderServiceImpl.class);

	@Autowired
	RepairOrderRepo repairOrderRepo;

	@Autowired
	CommentRepo commentRepo;

	@Autowired
	SettingsService settingsService;

	@Autowired
	MessageService messageService;

	@Autowired
	PaymentService paymentService;

	@Autowired
	RepairOrderImageRepo repairOrderImageRepo;

	@Autowired
	TaskMonitorService taskMonitorService;

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

	@Override
	public PageList<RepairOrder> list(RepairOrderQueryParam param) {
		PageList<RepairOrder> page = new PageList<RepairOrder>();
		page.setPage(param.getPage());
		page.setPageSize(param.getPageSize());
		param.setStart(page.getStart());
		// 设置总数量
		page.setTotalSize(repairOrderRepo.countByParam(param));
		page.setData(repairOrderRepo.listByQueryParam(param));
		return page;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public RepairOrder create(Integer creatorId, Integer missionId, String namePlateImg, String makeOrderNum,
			String repairOrderDesc, String accessoryContent, List<RoImage> images) throws Exception {
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

		if(images != null && images.size() > 0){
			int ordinal = 0;
			for(RoImage image : images){
				image.setRepairOrderId(repairOrder.getId());
				image.setOrdinal(ordinal++);
			}
			repairOrderImageRepo.insert(images);
		}

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
			params.put(MessageParamKey.ADMIN_ID, mission.getAdminUserId());
			messageService.send(MessageId.NEW_FIX_ORDER, params);
			//添加通知任务
			taskMonitorService.putTask(Constants.TASK_REPAIR_ORDER + repairOrder.getId());
		}catch (Exception e){
			//ignore
		}
		return repairOrder;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addCost(Long repairOrderId, List<Accessory> accessoryList, Integer laborCost, Boolean finishCost)
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
			throw new BusinessException("不存在的维修单"+repairOrderId);
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
		params.put(MessageParamKey.FIXER_ID, fixerId);
		params.put(MessageParamKey.MISSION_ID, order.getMissionId());
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
		}else{
			sendWaitingForAuditEvent(repairOrder.getId());
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
	@Autowired
	FixerIncomeRepo fixerIncomeRepo;
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

		/**
		 * 创建维修员收入记录
		 * TODO 失败的话，记录后重试
		 */
		fixerIncomeRepo.save(repairOrder.getFixerId(), repairOrder.getId(), repairOrder.getLaborCost() == null ? 0 : repairOrder.getLaborCost());



		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
		params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
		messageService.send(MessageId.COMPLETED_FIX_ORDER, params);
	}


	@Autowired
	AccessoryRepo accessoryRepo;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void confirm(Long repairOrderId, boolean deny, Integer payment, boolean needInvoice, String invoiceTitle, String taxNo) throws Exception {

		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
		repairOrder.setId(repairOrderId);
		if (deny) {
			repairOrder.setRepairOrderState(RepairOrderState.CLOSED.getCode());
		} else {
			if(needInvoice){
				repairOrder.setInvoiceTitle(invoiceTitle);
				repairOrder.setTaxNo(taxNo);
				List<Accessory> accessories = accessoryRepo.listOfRepairOrderId(repairOrderId);
				Integer accessoryMoney = 0;
				if(accessories != null) {
					for (Accessory accessory : accessories) {
						accessoryMoney += accessory.getCount() * accessory.getPrice();
					}
				}
				Float taxAmount = ((repairOrder.getLaborCost() + accessoryMoney) * settingsService.getBusinessSettings().getTaxPoint())/100;
				repairOrder.setTaxAmount(taxAmount.intValue());
			}
			repairOrder.setRepairOrderState(RepairOrderState.CONFIRMED.getCode());
		}
		repairOrder.setPayment(payment);
		repairOrderRepo.update(repairOrder);
		/**
		 * TODO : 利用切面分离这些处理逻辑，解除代码耦合
		 */
		if(deny){
			//用户可能已经付款，要发起退款
			if(repairOrder.getPayment() != null && repairOrder.getPayment() == 1){
				paymentService.closed(PaymentInfo.Method.WECHAT, PaymentInfo.Type.RO, repairOrder.getId().toString());
			}
		}else if(repairOrder.getPayment() == 1){
			List<Accessory> accessories = accessoryRepo.listOfRepairOrderId(repairOrderId);
			int price = 0;
			if(accessories != null && accessories.size() > 0){
				for(Accessory accessory : accessories){
					price += accessory.getPrice() * accessory.getCount();
				}
			}
			price += repairOrder.getLaborCost();
			paymentService.create(PaymentInfo.Method.WECHAT, PaymentInfo.Type.RO, repairOrder.getId().toString(), price);
		}

		//消息发送

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
		params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
		params.put(MessageParamKey.ADMIN_ID, repairOrder.getAdminUserId());
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
			throw new BusinessException("不存在的维修单"+repairOrderId);
		}
		if(!repairOrder.getRepairOrderState().equals(RepairOrderState.COST_READY.getCode())){
			throw new BusinessException("维修单"+repairOrderId+"不处于待审核状态");
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
	public void update(RepairOrder newOrder, RepairOrder oldOrder) throws Exception {
		boolean imageUpdated = updateImages(newOrder, oldOrder);
		repairOrderRepo.update(newOrder);
	}

	private boolean updateImages(RepairOrder newOrder, RepairOrder oldOrder){
		//把维修单中的images数组的url字段放入集合中
		Set<String> oldSet = new HashSet<String>();
		if(oldSet != null)
			oldSet.addAll(oldOrder.getImages().stream().map(RoImage::getUrl).collect(Collectors.toSet()));

		Set<String> newSet = new HashSet<String>();
		if(newSet != null)
			newSet.addAll(newOrder.getImages().stream().map(RoImage::getUrl).collect(Collectors.toSet()));

		//分别创建一个移除图片和增加图片的列表
		Set<String> removingSet = new HashSet<String>(oldSet);
		Set<String> addingSet = new HashSet<String>(newSet);

		//存在在新的列表里的图片不用删除，从移除队列中移出
		removingSet.removeAll(newSet);
		//存在在老的列表里的图片不用增加,从增加队列中移出
		addingSet.removeAll(oldSet);

		if(removingSet.size() > 0) {
			//删除图片
			repairOrderImageRepo.removeIn(removingSet, oldOrder.getId());
		}
		if(addingSet.size() > 0) {
			//增加图片
			repairOrderImageRepo.insert(addingSet, newOrder.getId());
		}

		return ((removingSet.size() > 0) && (addingSet.size() > 0));

	}

	@Override
	public RepairOrder getById(Long id, boolean brief) throws Exception {
		RepairOrder repairOrder = repairOrderRepo.getById(id);
		if (!brief) {
			repairOrder.setAccessoryList(accessoryRepo.listOfRepairOrderId(repairOrder.getId()));
		}
		return repairOrder;
	}

//	@Override
//	public void comment(Long repairOrderId, Integer rate, String commentString) throws Exception {
//		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
//		if(repairOrder == null){
//			throw new BusinessException("维修单"+repairOrderId+"不存在");
//		}
//		Comment comment = new Comment();
//		comment.setRepairOrderId(repairOrderId);
//		comment.setMissionId(repairOrder.getMissionId());
//		comment.setRate(rate);
//		comment.setComment(commentString);
//		commentRepo.create(comment);
//
//		//TODO 发送通知
//	}

	@Override
	public Accessory createAccessory(Long repairOrderId, String name, Integer count, Integer price) throws Exception {
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

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(MessageParamKey.MISSION_ID, order.getMissionId());
			params.put(MessageParamKey.USER_ID, order.getUserId());
			params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
			messageService.send(MessageId.WAITING_CONFIRM_FIX_ORDER, params);
		} catch (Exception e) {
			logger.error("发送报价完成消息失败"+e.getMessage());
		}
	}

	private void sendWaitingForAuditEvent(Long repairOrderId) {
		try {
			RepairOrder order = repairOrderRepo.getById(repairOrderId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(MessageParamKey.MISSION_ID, order.getMissionId());
			params.put(MessageParamKey.USER_ID, order.getUserId());
			params.put(MessageParamKey.REPAIR_ORDER_ID, order.getId());
			params.put(MessageParamKey.ADMIN_ID, order.getAdminUserId());
			messageService.send(MessageId.WAITING_AUDIT_REPAIR_ORDER, params);

			//添加通知任务
			taskMonitorService.putTask(Constants.TASK_REPAIR_ORDER + order.getId());
		} catch (Exception e) {
			logger.error("发送报价待审核消息失败"+e.getMessage());
		}
	}
}
