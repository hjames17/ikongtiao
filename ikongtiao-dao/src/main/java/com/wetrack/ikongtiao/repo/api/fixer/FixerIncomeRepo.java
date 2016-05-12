package com.wetrack.ikongtiao.repo.api.fixer;

import com.wetrack.ikongtiao.domain.fixer.FixerIncome;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 16/4/8.
 */
public interface FixerIncomeRepo {
    FixerIncome save(Integer fixer, Long repairOrderId, int amount);
    boolean remove(Long id);
    List<FixerIncome> findByFixerIdAndCreateTimeBetween(Integer fixerId, Date startDate, Date endDate, boolean withPaymentInfo);
}
