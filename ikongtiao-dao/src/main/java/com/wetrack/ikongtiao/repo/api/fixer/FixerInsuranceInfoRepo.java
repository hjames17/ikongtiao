package com.wetrack.ikongtiao.repo.api.fixer;

import com.wetrack.ikongtiao.domain.fixer.FixerInsuranceInfo;

import java.util.Date;

/**
 * Created by zhanghong on 16/1/5.
 */
public interface FixerInsuranceInfoRepo {

    FixerInsuranceInfo findLatestForFixer(Integer fixerId) throws Exception;
    FixerInsuranceInfo create(Integer fixerId, String insuranceNum, String insuranceImg, Date insuranceDate, Date expiresAt) throws Exception;
    int update(FixerInsuranceInfo fixerInsuranceInfo) throws Exception;
    int delete(Integer id) throws Exception;
    FixerInsuranceInfo findById(Integer id) throws Exception;
}
