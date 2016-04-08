package com.wetrack.ikongtiao.repo.api;

import com.wetrack.ikongtiao.domain.FaultType;

import java.util.List;

/**
 * Created by zhanghong on 16/3/10.
 */
public interface FaultTypeRepo {
    List<FaultType> findAll();

    FaultType create(String name, Integer ordinal);
}
