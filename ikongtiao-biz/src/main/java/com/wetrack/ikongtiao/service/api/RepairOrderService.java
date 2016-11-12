package com.wetrack.ikongtiao.service.api;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.RepairOrder;
import com.wetrack.ikongtiao.domain.repairOrder.Accessory;
import com.wetrack.ikongtiao.domain.repairOrder.RoImage;
import com.wetrack.ikongtiao.domain.statistics.StatsCount;
import com.wetrack.ikongtiao.dto.RepairOrderDto;
import com.wetrack.ikongtiao.dto.RepairOrderFull;
import com.wetrack.ikongtiao.param.RepairOrderQueryParam;
import com.wetrack.ikongtiao.param.StatsQueryParam;

import java.util.List;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface RepairOrderService {
    List<RepairOrder> listForMission(String missionId, boolean includesAuditInfo) throws Exception;
    PageList<RepairOrderDto> list(RepairOrderQueryParam param);

    RepairOrder create(Integer creatorId, String missionId, String namePlateImg,
                       String makeOrderNum, String repairOrderDesc, String accessoryContent, List<RoImage> images, boolean quick) throws Exception;


    void addCost(String repairOrderId, List<Accessory> accessoryList, Integer laborCost, Boolean finishCost) throws Exception;

    void dispatchRepairOrder(Integer adminUserId, String repairOrderId, Integer fixerId) throws Exception;

    void setCostFinished(Integer adminUserId, String repairOrderId) throws Exception;
    void setPrepared(Integer adminUserId, String repairOrderId) throws Exception;

    void setFinished(String repairOrderId) throws Exception;
    RepairOrder getById(String id, boolean brief) throws Exception;

//    void comment(Long repairOrderId, Integer rate, String comment) throws Exception;

    Accessory createAccessory(String repairOrderId, String name, Integer count, Integer price) throws Exception;

    boolean updateAccessory(Accessory accessory) throws Exception;

    boolean deleteAccessory(Long accessoryId) throws Exception;

    void confirm(String repairOrderId, boolean deny, Integer payment, boolean needInvoice, String invoiceTitle, String taxNo) throws Exception;

    void audit(Integer adminId, String repairOrderId, Boolean pass, String reason) throws Exception;

    void update(RepairOrder newOrder, RepairOrder oldOrder) throws Exception;

    List<StatsCount> statsRepairOrder(StatsQueryParam queryParam);

    List<RepairOrderFull> listFull(RepairOrderQueryParam param);
}
