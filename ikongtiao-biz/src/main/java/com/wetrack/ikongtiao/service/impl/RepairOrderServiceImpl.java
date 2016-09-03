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
import com.wetrack.ikongtiao.dto.RepairOrderDto;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.RepairOrderQueryParam;
import com.wetrack.ikongtiao.repo.api.fixer.FixerIncomeRepo;
import com.wetrack.ikongtiao.repo.api.mission.MissionRepo;
import com.wetrack.ikongtiao.repo.api.repairOrder.*;
import com.wetrack.ikongtiao.service.api.PaymentService;
import com.wetrack.ikongtiao.service.api.RepairOrderService;
import com.wetrack.ikongtiao.service.api.SettingsService;
import com.wetrack.ikongtiao.service.api.mission.MissionService;
import com.wetrack.ikongtiao.service.api.monitor.TaskMonitorService;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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


	@Autowired
	MissionRepo missionRepo;

	@Autowired
	MissionService missionService;

	public Mission queryMission(String id) throws Exception {
		Mission mission = new Mission();
		try{
			int iid = Integer.parseInt(id);
			mission.setId(iid);
		}catch (NumberFormatException e){
			mission.setSerialNumber(id);
		}
		return  mission;
	}
	public RepairOrder queryRepairOrder(String id) throws Exception {
		RepairOrder repairOrder = new RepairOrder();
		try{
			long iid = Long.parseLong(id);
			repairOrder.setId(iid);
		}catch (NumberFormatException e){
			repairOrder.setSerialNumber(id);
		}
		return  repairOrder;
	}

	@Override
	public List<RepairOrder> listForMission(String missionId, boolean includesAuditInfo) throws Exception {

		List<RepairOrder> list = repairOrderRepo.listForMission(queryMission(missionId));

		if(!includesAuditInfo && list != null){
			for(RepairOrder order : list){
				order.setAuditInfo(null);
			}
		}
		return list;
	}

	@Override
	public PageList<RepairOrderDto> list(RepairOrderQueryParam param) {
		PageList<RepairOrderDto> page = new PageList<RepairOrderDto>();
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
	public RepairOrder create(Integer creatorId, String missionId, String namePlateImg, String makeOrderNum,
			String repairOrderDesc, String accessoryContent, List<RoImage> images, boolean quick) throws Exception {
		Mission mission = missionService.getMission(missionId);
		RepairOrder repairOrder = new RepairOrder();
		if(creatorId == null) {
			repairOrder.setCreatorFixerId(mission.getFixerId());
		}else{
			repairOrder.setCreatorFixerId(creatorId);
		}
		repairOrder.setUserId(mission.getUserId());
		repairOrder.setMissionId(mission.getId());
		repairOrder.setMissionSerialNumber(mission.getSerialNumber());
		repairOrder.setAdminUserId(mission.getAdminUserId());
		repairOrder.setNamePlateImg(namePlateImg);
		repairOrder.setMakeOrderNum(makeOrderNum);
		repairOrder.setRepairOrderDesc(repairOrderDesc);
		repairOrder.setAccessoryContent(accessoryContent);
		repairOrder.setQuick(quick);

		//快速现场免费维修，把价格设为0，状态设为维修中
		if(quick) {
			repairOrder.setLaborCost(0);
			repairOrder.setFixerId(creatorId);
			repairOrder.setRepairOrderState(RepairOrderState.FIXING.getCode());
		}

		String mPadd;
		if(StringUtils.isEmpty(mission.getSerialNumber())){
			DateTime today = new DateTime(mission.getCreateTime());
			mPadd = today.toString("yyyyMMdd", Locale.CHINA) + missionId;
		}else{
			mPadd = mission.getSerialNumber().substring(1);
		}
		String serialNumber = String.format("r%s%02d", mPadd, repairOrderRepo.countByMission(mission) + 1);
		repairOrder.setSerialNumber(serialNumber);


		repairOrder = repairOrderRepo.create(repairOrder);


		if(images != null && images.size() > 0){
			int ordinal = 0;
			for(RoImage image : images){
				image.setRepairOrderId(repairOrder.getId());
				image.setOrdinal(ordinal++);
			}
			repairOrderImageRepo.insert(images);
		}

		//设置任务状态到维修中
		mission.setMissionState(MissionState.FIXING.getCode());
		mission.setUpdateTime(new Date());
		missionRepo.update(mission);

		//发送消息
		if(!quick) {
			notify(repairOrder.getId(), RepairOrderState.NEW, null, Initiator.INITIATOR_USER);
			try {
				//添加通知任务
				taskMonitorService.putTask(Constants.TASK_REPAIR_ORDER + repairOrder.getId());
			} catch (Exception e) {
				//ignore
			}
		}
		return repairOrder;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void addCost(String repairOrderId, List<Accessory> accessoryList, Integer laborCost, Boolean finishCost)
			throws Exception {
		//insert accessory list into accessory table
		if (accessoryList != null && accessoryList.size() > 0) {
			accessoryRepo.createMulti(accessoryList);
		}
		//update repairOrder
		if (laborCost != null) {
			RepairOrder repairOrder = queryRepairOrder(repairOrderId);
//			repairOrder.setId(repairOrderId);
			repairOrder.setLaborCost(laborCost);
			if (finishCost != null && finishCost == true) {
				handleCostFinished(repairOrder);
			}
			repairOrderRepo.update(repairOrder);
		}

	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void dispatchRepairOrder(Integer adminUserId, String repairOrderId, Integer fixerId) throws Exception {
		RepairOrder order = this.getById(repairOrderId, true);
		if (order == null) {
			throw new BusinessException("不存在的维修单"+repairOrderId);
		}

		RepairOrder repairOrder = new RepairOrder();
		repairOrder.setFixerId(fixerId);
		repairOrder.setId(order.getId());
		repairOrder.setSerialNumber(order.getSerialNumber());
		repairOrder.setAdminUserId(adminUserId);
		repairOrder.setRepairOrderState(RepairOrderState.FIXING.getCode());
		repairOrderRepo.update(repairOrder);

		Mission mission = new Mission();
		mission.setId(order.getMissionId());
		mission.setMissionState(MissionState.FIXING.getCode());
		missionRepo.update(mission);


		notify(order.getId(), RepairOrderState.FIXING, null, Initiator.INITIATOR_KEFU);
	}

	@Override
	public void setCostFinished(Integer adminUserId, String repairOrderId) throws Exception {
		RepairOrder repairOrder = this.queryRepairOrder(repairOrderId); //new RepairOrder();
//		repairOrder.setId(repairOrderId);
		repairOrder.setAdminUserId(adminUserId);
		handleCostFinished(repairOrder);
	}

	private void handleCostFinished(RepairOrder repairOrder) throws Exception{
		boolean autoAudit = false;
		if (settingsService.getBusinessSettings().isRepairOrderAutoAudit()) {
			autoAudit = true;
		}

		RepairOrderState newState;
		if (autoAudit) {
			RepairOrder retrieve = repairOrderRepo.getById(repairOrder.getId());
			List<Accessory> accList = accessoryRepo.listOfRepairOrderId(repairOrder.getId());
			int cost = 0;
			if(retrieve.getLaborCost() != null){
				cost += retrieve.getLaborCost();
			}
			if(accList != null && accList.size() > 0){
				for(Accessory accessory : accList){
					if(accessory.getPrice() != null && accessory.getCount() != null)
						cost += accessory.getPrice() * accessory.getCount();
				}
			}
			if(cost != 0){
				//1. 如果需要付费，则进入待确认状态
				newState = RepairOrderState.AUDIT_READY;
			}else{
				//2. 如果不需要付费，则直接进入已确认状态
				newState = RepairOrderState.CONFIRMED;
			}
		} else {
			newState = RepairOrderState.COST_READY;
		}
		repairOrder.setRepairOrderState(newState.getCode());
		repairOrderRepo.update(repairOrder);

		notify(repairOrder.getId(), newState, null, Initiator.INITIATOR_KEFU);

	}

	@Override
	public void setPrepared(Integer adminUserId, String repairOrderId) throws Exception {
		RepairOrder repairOrder = this.queryRepairOrder(repairOrderId); //new RepairOrder();
//		repairOrder.setId(repairOrderId);
		repairOrder.setAdminUserId(adminUserId);
		repairOrder.setRepairOrderState(RepairOrderState.PREPARED.getCode());
		repairOrderRepo.update(repairOrder);

	}


//	static final String ACTION_COMMENT = "comment";
	@Autowired
	FixerIncomeRepo fixerIncomeRepo;
	@Override
	public void setFinished(String repairOrderId) throws Exception {
		RepairOrder repairOrder = this.getById(repairOrderId, true); //new RepairOrder();
//		repairOrder.setId(repairOrderId);
		repairOrder.setRepairOrderState(RepairOrderState.COMPLETED.getCode());
		repairOrder.setUpdateTime(new Date());
		repairOrderRepo.update(repairOrder);
//		repairOrder = repairOrderRepo.getById(repairOrderId);

		/**
		 * 创建维修员收入记录
		 * TODO 失败的话，记录后重试
		 */
		fixerIncomeRepo.save(repairOrder.getFixerId(), repairOrder.getSerialNumber(), repairOrder.getLaborCost() == null ? 0 : repairOrder.getLaborCost());

		notify(repairOrder.getId(), RepairOrderState.COMPLETED, null, Initiator.INITIATOR_FIXER);
	}


	@Autowired
	AccessoryRepo accessoryRepo;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public void confirm(String repairOrderId, boolean deny, Integer payment, boolean needInvoice, String invoiceTitle, String taxNo) throws Exception {

//		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
//		repairOrder.setId(repairOrderId);
		RepairOrder repairOrder = this.getById(repairOrderId, true);

		if(repairOrder == null){
			throw new BusinessException("不存在的维修单"+repairOrderId);
		}

		boolean needPay = false;
		RepairOrderState newState;
		if (deny) {
			newState = RepairOrderState.CLOSED;
		} else {

			List<Accessory> accessories = accessoryRepo.listOfRepairOrderId(repairOrder.getId());
			Integer accessoryMoney = 0;
			if(accessories != null) {
				for (Accessory accessory : accessories) {
					accessoryMoney += accessory.getCount() * accessory.getPrice();
				}
			}

			if(repairOrder.getLaborCost() == null){
				repairOrder.setLaborCost(0);
			}

			if(needInvoice){
				repairOrder.setInvoiceTitle(invoiceTitle);
				repairOrder.setTaxNo(taxNo);

				Float taxAmount = ((repairOrder.getLaborCost() + accessoryMoney) * settingsService.getBusinessSettings().getTaxPoint())/100;
				repairOrder.setTaxAmount(taxAmount.intValue());
			}

			if((repairOrder.getLaborCost() + accessoryMoney) == 0){
				newState = RepairOrderState.PREPARED;
			}else{
				needPay = true;
				newState = RepairOrderState.CONFIRMED;
			}


		}
		repairOrder.setPayment(payment);
		RepairOrderState oldState = RepairOrderState.fromCode(repairOrder.getRepairOrderState());
		repairOrder.setRepairOrderState(newState.getCode());
		repairOrderRepo.update(repairOrder);
		/**
		 * TODO : 利用切面分离这些处理逻辑，解除代码耦合
		 */
		if(deny){
			//用户可能已经付款，要发起退款
			if(repairOrder.getPayment() != null && repairOrder.getPayment() == 1){
				paymentService.closed(PaymentInfo.Method.WECHAT, PaymentInfo.Type.RO, repairOrder.getId().toString());
			}
		}else if((repairOrder.getPayment() == 1) && needPay){
			List<Accessory> accessories = accessoryRepo.listOfRepairOrderId(repairOrder.getId());
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
		notify(repairOrder.getId(), newState, oldState, Initiator.INITIATOR_USER);
	}

	@Autowired
	AuditInfoRepo auditInfoRepo;
	@Transactional(rollbackFor = Exception.class)
	@Override
	public void audit(Integer adminId, String repairOrderId, Boolean pass, String reason) throws Exception {
//		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
		RepairOrder repairOrder = this.getById(repairOrderId, true);
		if(repairOrder == null){
			throw new BusinessException("不存在的维修单"+repairOrderId);
		}
		if(!repairOrder.getRepairOrderState().equals(RepairOrderState.COST_READY.getCode())){
			throw new BusinessException("维修单"+repairOrderId+"不处于待审核状态");
		}

		AuditInfo auditInfo = new AuditInfo();
		auditInfo.setRepairOrderId(repairOrder.getId());
		auditInfo.setPass(pass);
		auditInfo.setReason(reason);
		auditInfo.setAdminId(adminId);

		auditInfoRepo.create(auditInfo);

		RepairOrderState newState;
		if(pass){
			newState = RepairOrderState.AUDIT_READY;
		}else{
			newState = RepairOrderState.NEW;
		}
		repairOrder.setRepairOrderState(newState.getCode());
		repairOrderRepo.update(repairOrder);

		notify(repairOrder.getId(), newState, null, Initiator.INITIATOR_AUDITOR);
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
	public RepairOrder getById(String id, boolean brief) throws Exception {
		RepairOrder repairOrder = null;
		try{
			long iid = Long.parseLong(id);
			repairOrder = repairOrderRepo.getById(iid);
		}catch (NumberFormatException e){
			repairOrder = repairOrderRepo.getBySid(id);
		}
		if (!brief) {
			repairOrder.setAccessoryList(accessoryRepo.listOfRepairOrderId(repairOrder.getId()));
		}
		return repairOrder;
	}

	@Override
	public Accessory createAccessory(String repairOrderId, String name, Integer count, Integer price) throws Exception {
		RepairOrder repairOrder = this.getById(repairOrderId, true);
		Accessory accessory = new Accessory();
		accessory.setRepairOrderId(repairOrder.getId());
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


	enum Initiator{
		INITIATOR_USER,
		INITIATOR_KEFU,
		INITIATOR_FIXER,
		INITIATOR_AUDITOR;
	}


	private void notify(long repairOrderId, RepairOrderState newState, RepairOrderState oldState, Initiator initiator){
		try {
			switch (newState) {
				case NEW:
					if(Initiator.INITIATOR_AUDITOR == initiator){
						//TODO 报价审核驳回通知
					}else {
						sendNew(repairOrderId);
					}
					break;
				case COST_READY:
					sendWaitingForAuditEvent(repairOrderId);
					break;
				case AUDIT_READY:
					sendCostFinishEvent(repairOrderId);
					break;
				case CONFIRMED:
					sendUserConfirmed(repairOrderId);
					break;
				case PREPARED:
					if (initiator == Initiator.INITIATOR_USER) {
						sendUserConfirmed(repairOrderId);
					}
					break;
				case FIXING:
					sendAssignedFixer(repairOrderId);
					break;
				case CLOSED:
					sendUserDeniedEvent(repairOrderId);
					break;
				case COMPLETED:
					sendCompleteEvent(repairOrderId);
					break;
				default:
					break;
			}
		}catch (Exception e){
			logger.error("repair order notify event failed on state {}, repairOrderId {}, initiator {}", repairOrderId, newState, initiator);
		}
	}

	private void sendNew(long repairOrderId){
		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
		params.put(MessageParamKey.MISSION_SID, repairOrder.getMissionSerialNumber());
		params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrder.getId());
		params.put(MessageParamKey.REPAIR_ORDER_SID, repairOrder.getSerialNumber());
		params.put(MessageParamKey.ADMIN_ID, repairOrder.getAdminUserId());
		messageService.send(MessageId.NEW_FIX_ORDER, params);
	}

	private void sendUserConfirmed(long repairOrderId){
		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
		params.put(MessageParamKey.MISSION_SID, repairOrder.getMissionSerialNumber());
		params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
		params.put(MessageParamKey.REPAIR_ORDER_SID, repairOrder.getSerialNumber());
		params.put(MessageParamKey.ADMIN_ID, repairOrder.getAdminUserId());
		messageService.send(MessageId.CONFIRM_FIX_ORDER, params);
	}

	private void sendUserDeniedEvent(long repairOrderId){
		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
		params.put(MessageParamKey.MISSION_SID, repairOrder.getMissionSerialNumber());
		params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
		params.put(MessageParamKey.REPAIR_ORDER_SID, repairOrder.getSerialNumber());
		params.put(MessageParamKey.ADMIN_ID, repairOrder.getAdminUserId());
		messageService.send(MessageId.CANCEL_FIX_ORDER, params);
	}

	private void sendCompleteEvent(long repairOrderId){
		RepairOrder repairOrder = repairOrderRepo.getById(repairOrderId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.MISSION_ID, repairOrder.getMissionId());
		params.put(MessageParamKey.MISSION_SID, repairOrder.getMissionSerialNumber());
		params.put(MessageParamKey.USER_ID, repairOrder.getUserId());
		params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
		params.put(MessageParamKey.REPAIR_ORDER_SID, repairOrder.getSerialNumber());
		messageService.send(MessageId.COMPLETED_FIX_ORDER, params);
	}

	private void sendCostFinishEvent(long repairOrderId) {
		try {
			RepairOrder order = repairOrderRepo.getById(repairOrderId);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put(MessageParamKey.MISSION_ID, order.getMissionId());
			params.put(MessageParamKey.MISSION_SID, order.getMissionSerialNumber());
			params.put(MessageParamKey.USER_ID, order.getUserId());
			params.put(MessageParamKey.REPAIR_ORDER_ID, repairOrderId);
			params.put(MessageParamKey.REPAIR_ORDER_SID, order.getSerialNumber());
			messageService.send(MessageId.WAITING_CONFIRM_FIX_ORDER, params);
		} catch (Exception e) {
			logger.error("发送报价完成消息失败"+e.getMessage());
		}
	}

	private void sendWaitingForAuditEvent(long repairOrderId) {
		try {
			RepairOrder order = repairOrderRepo.getById(repairOrderId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put(MessageParamKey.MISSION_ID, order.getMissionId());
			params.put(MessageParamKey.MISSION_SID, order.getMissionSerialNumber());
			params.put(MessageParamKey.USER_ID, order.getUserId());
			params.put(MessageParamKey.REPAIR_ORDER_ID, order.getId());
			params.put(MessageParamKey.REPAIR_ORDER_SID, order.getSerialNumber());
			params.put(MessageParamKey.ADMIN_ID, order.getAdminUserId());
			messageService.send(MessageId.WAITING_AUDIT_REPAIR_ORDER, params);

			//添加通知任务
			taskMonitorService.putTask(Constants.TASK_REPAIR_ORDER + order.getId());
		} catch (Exception e) {
			logger.error("发送报价待审核消息失败"+e.getMessage());
		}
	}

	private void sendAssignedFixer(long repairOrderId){
		RepairOrder order = repairOrderRepo.getById(repairOrderId);

		Map<String, Object> params = new HashMap<String, Object>();
		params.put(MessageParamKey.FIXER_ID, order.getFixerId());
		params.put(MessageParamKey.MISSION_ID, order.getMissionId());
		params.put(MessageParamKey.MISSION_SID, order.getMissionSerialNumber());
		params.put(MessageParamKey.REPAIR_ORDER_ID, order.getId());
		params.put(MessageParamKey.REPAIR_ORDER_SID, order.getSerialNumber());
		messageService.send(MessageId.ASSIGNED_FIXER, params);
	}
}
