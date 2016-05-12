package com.wetrack.ikongtiao.service.impl.fixer;

import com.wetrack.ikongtiao.domain.fixer.FixerIncome;
import com.wetrack.ikongtiao.repo.api.fixer.FixerIncomeRepo;
import com.wetrack.ikongtiao.service.api.fixer.FixerIncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 16/4/8.
 */
@Service
public class FixerIncomeServiceImpl implements FixerIncomeService {

    @Autowired
    FixerIncomeRepo fixerIncomeRepo;

    @Override
    public List<FixerIncome> listIncome(Integer fixerId, Date startDate, Date endDate, boolean withPaymentInfo) {
        return fixerIncomeRepo.findByFixerIdAndCreateTimeBetween(fixerId, startDate, endDate, withPaymentInfo);
    }
}
