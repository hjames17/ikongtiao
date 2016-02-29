package com.wetrack.ikongtiao.repo.api.repairOrder;

import com.wetrack.ikongtiao.domain.repairOrder.AuditInfo;

/**
 * Created by zhanghong on 16/1/7.
 */
public interface AuditInfoRepo {

    AuditInfo create(AuditInfo auditInfo) throws Exception;

    AuditInfo getLatestOfRepairOrder(long repairOrderId) throws Exception;

    AuditInfo getById(Long id) throws Exception;
}
