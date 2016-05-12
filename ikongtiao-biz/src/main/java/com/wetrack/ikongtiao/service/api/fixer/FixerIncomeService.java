package com.wetrack.ikongtiao.service.api.fixer;

import com.wetrack.ikongtiao.domain.fixer.FixerIncome;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 16/4/8.
 */
public interface FixerIncomeService {
    List<FixerIncome> listIncome(Integer fixerId, Date startDate, Date endDate, boolean withPaymentInfo);
}
