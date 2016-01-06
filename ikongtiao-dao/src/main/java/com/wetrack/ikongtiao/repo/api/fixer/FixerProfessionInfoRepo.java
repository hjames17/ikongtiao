package com.wetrack.ikongtiao.repo.api.fixer;

import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;

/**
 * Created by zhanghong on 16/1/5.
 */
public interface FixerProfessionInfoRepo {

    FixerProfessionInfo findLatestForFixerIdAndType(Integer fixerId, Integer type) throws Exception;
    FixerProfessionInfo create(Integer type, Integer fixerId, String professNum, String professImg) throws Exception;
    int update(FixerProfessionInfo fixerProfessionInfo) throws Exception;
    int delete(Integer id) throws Exception;
    FixerProfessionInfo findById(Integer id) throws Exception;
}
