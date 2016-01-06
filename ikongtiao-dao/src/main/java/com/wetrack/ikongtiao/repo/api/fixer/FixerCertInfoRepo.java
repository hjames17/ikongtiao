package com.wetrack.ikongtiao.repo.api.fixer;

import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;

/**
 * Created by zhanghong on 16/1/5.
 */
public interface FixerCertInfoRepo {

    FixerCertInfo findLatestForFixer(Integer fixerId) throws Exception;
    FixerCertInfo create(Integer fixerId, String idNum, String idWithFaceImg, String idImgFront, String IdImgBack) throws Exception;
    int update(FixerCertInfo fixerCertInfo) throws Exception;
    int delete(Integer id) throws Exception;
    FixerCertInfo findById(Integer id) throws Exception;
}
