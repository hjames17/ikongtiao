package com.wetrack.ikongtiao.repo.impl.fixer;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.Sequence;
import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerInsuranceInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by zhanghong on 16/1/5.
 */
@Service
public class FixerInsuranceInfoRepoImpl implements FixerInsuranceInfoRepo {

    @Autowired
    CommonDao commonDao;

    @Override
    public FixerInsuranceInfo findLatestForFixer(Integer fixerId) throws Exception {
        return commonDao.mapper(FixerInsuranceInfo.class).sql("selectLatestForFixer").session().selectOne(fixerId);
    }

    @Override
    public FixerInsuranceInfo create(Integer fixerId, String insuranceNum, String insuranceImg, Date insuranceDate, Date expiresAt) throws Exception {
        FixerInsuranceInfo fixerInsuranceInfo = new FixerInsuranceInfo();
        fixerInsuranceInfo.setFixerId(fixerId);
        fixerInsuranceInfo.setInsuranceNum(insuranceNum);
        fixerInsuranceInfo.setInsuranceImg(insuranceImg);
        fixerInsuranceInfo.setInsuranceDate(insuranceDate);
        fixerInsuranceInfo.setCheckState(1); //待审核
        fixerInsuranceInfo.setExpiresAt(expiresAt);
        Long id = commonDao.mapper(Sequence.class).sql("getNextVal").session().selectOne(FixerInsuranceInfo.FIXER_INSURANCE_INFO_SEQUENCE);
        fixerInsuranceInfo.setId(id.intValue());
        int i = commonDao.mapper(FixerInsuranceInfo.class).sql("insert").session().insert(fixerInsuranceInfo);
        if(i == 1){
            return fixerInsuranceInfo;
        }else{
            return null;
        }
    }

    @Override
    public int update(FixerInsuranceInfo fixerInsuranceInfo) throws Exception {
        return commonDao.mapper(FixerInsuranceInfo.class).sql("updateByPrimaryKeySelective").session().update(fixerInsuranceInfo);
    }

    @Override
    public int delete(Integer id) throws Exception {
        commonDao.mapper(FixerInsuranceInfo.class).sql("deleteByPrimaryKey").session().delete(id);
        return 0;
    }

    @Override
    public FixerInsuranceInfo findById(Integer id) throws Exception {
        return commonDao.mapper(FixerInsuranceInfo.class).sql("selectByPrimaryKey").session().selectOne(id);
    }
}
