package com.wetrack.ikongtiao.repo.impl.fixer;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.fixer.FixerIncome;
import com.wetrack.ikongtiao.repo.api.fixer.FixerIncomeRepo;
import com.wetrack.ikongtiao.repo.impl.fixer.query.FixerIncomeQueryForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zhanghong on 16/4/8.
 */
@Service
public class FixerIncomeRepoImpl implements FixerIncomeRepo {

    @Autowired
    CommonDao commonDao;

    @Override
    public FixerIncome save(Integer fixerId, Long repairOrderId, int amount) {
        FixerIncome fixerIncome = new FixerIncome();
        fixerIncome.setFixerId(fixerId);
        fixerIncome.setRepairOrderId(repairOrderId);
        fixerIncome.setAmount(amount);
        int i = commonDao.mapper(FixerIncome.class).sql("insert").session().insert(fixerIncome);
        if(i == 1) {
            return fixerIncome;
        }else{
            return null;
        }
    }

    @Override
    public boolean remove(Long id) {
        return commonDao.mapper(FixerIncome.class).sql("removeByPrimaryKey").session().delete(id) != 0;

    }

    @Override
    public List<FixerIncome> findByFixerIdAndCreateTimeBetween(Integer fixerId, Date startDate, Date endDate, boolean withPaymentInfo) {
        FixerIncomeQueryForm query = new FixerIncomeQueryForm();
        query.setFixerId(fixerId);
        query.setStartDate(startDate);
        query.setEndDate(endDate);
        query.setWithPaymentInfo(withPaymentInfo);
        return commonDao.mapper(FixerIncome.class).sql("findByFixerIdAndCreateTimeBetween").session().selectList(query);
    }



}
