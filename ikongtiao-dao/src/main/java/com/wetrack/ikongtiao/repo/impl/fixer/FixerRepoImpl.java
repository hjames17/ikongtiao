package com.wetrack.ikongtiao.repo.impl.fixer;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.Fixer;
import com.wetrack.ikongtiao.domain.Sequence;
import com.wetrack.ikongtiao.param.FixerQueryForm;
import com.wetrack.ikongtiao.repo.api.fixer.FixerRepo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhanghong on 15/12/30.
 */
@Service
public class FixerRepoImpl implements FixerRepo {


    @Resource
    CommonDao commonDao;

    @Override
    public Fixer save(Fixer fixer) throws Exception {
        if(fixer.getId() == null) {
            Long id = commonDao.mapper(Sequence.class).sql("getNextVal").session().selectOne(Fixer.FIXER_SEQUENCE);
            fixer.setId(id.intValue());
        }
        if(1 == commonDao.mapper(Fixer.class).sql("insertSelective").session().insert(fixer)){
            return fixer;
        }else{
            throw new Exception("没有保存成功");
        }
    }

    @Override
    public int update(Fixer fixer) throws Exception {
        return commonDao.mapper(Fixer.class).sql("updateByPrimaryKeySelective").session().update(fixer);
    }

    @Override
    public int countFixerByQueryParam(FixerQueryForm form) throws Exception {
        BaseCondition baseCondition = commonDao.mapper(Fixer.class).sql("countByQueryParam").session()
                .selectOne(form);
        Integer count = baseCondition == null ? 0 : baseCondition.getTotalSize();
        return count == null ? 0 : count;
    }


    @Override
    public List<Fixer> listFixerByQueryParam(FixerQueryForm form) throws Exception {
        return commonDao.mapper(Fixer.class).sql("listByQueryParam").session().selectList(form);
    }

    @Override
    public int lDelete(Integer id) throws Exception {
        return 0;
    }

    @Override
    public int delete(Integer id) throws Exception {
        return 0;
    }

    @Override public Fixer getFixerById(Integer fixId) {
        return commonDao.mapper(Fixer.class).sql("selectByPrimaryKey").session().selectOne(fixId);
    }

    @Override
    public Fixer getFixerByPhone(String phone) throws Exception {
        return commonDao.mapper(Fixer.class).sql("selectByPhone").session().selectOne(phone);
    }
}
