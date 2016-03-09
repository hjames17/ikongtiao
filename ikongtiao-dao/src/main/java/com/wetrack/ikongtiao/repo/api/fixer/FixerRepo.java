package com.wetrack.ikongtiao.repo.api.fixer;

import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.param.FixerQueryForm;

import java.util.List;

/**
 * Created by zhanghong on 15/12/30.
 */
public interface FixerRepo {

    Fixer save(Fixer fixer) throws Exception;

    int update(Fixer fixer) throws Exception;

    int countFixerByQueryParam(FixerQueryForm form) throws Exception;

    List<Fixer> listFixerByQueryParam(FixerQueryForm form) throws Exception;

    //逻辑删除
    int lDelete(Integer id) throws Exception;
    //彻底删除纪录
    int delete(Integer id) throws Exception;

    Fixer getFixerById(Integer fixId);

    Fixer getFixerByPhone(String phone) throws Exception;

    List<Fixer> listFixerOfIds(List<Integer> ids);
}
