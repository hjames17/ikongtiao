package com.wetrack.ikongtiao.repo.api.fixer;

import com.wetrack.ikongtiao.domain.fixer.GetuiClientId;

/**
 * Created by zhanghong on 16/1/21.
 */
public interface GetuiClientIdRepo {
    GetuiClientId getByUid(Integer uid) throws Exception;

    int update(GetuiClientId getuiClientId) throws Exception;

    GetuiClientId insert(GetuiClientId getuiClientId) throws Exception;
}
