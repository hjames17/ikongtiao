package com.wetrack.ikongtiao.repo.impl.fixer;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.Sequence;
import com.wetrack.ikongtiao.domain.fixer.FixerProfessionInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerProfessionInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/1/5.
 */
@Service
public class FixerProfessionInfoRepoImpl implements FixerProfessionInfoRepo {

    @Autowired
    CommonDao commonDao;

    @Override
    public FixerProfessionInfo findLatestForFixerIdAndType(Integer fixerId, Integer type) throws Exception {
        FixerProfessionInfo info = new FixerProfessionInfo();
        info.setFixerId(fixerId);
        info.setProfessType(type);
        return commonDao.mapper(FixerProfessionInfo.class).sql("selectLatestForFixerAndType").session().selectOne(info);
    }

    @Override
    public FixerProfessionInfo create(Integer type, Integer fixerId, String professNum, String professImg) throws Exception {
        FixerProfessionInfo info = new FixerProfessionInfo();
        info.setProfessType(type);
        info.setFixerId(fixerId);
        info.setProfessNum(professNum);
        info.setProfessImg(professImg);
        info.setCheckState(1); //待审核
        Long id = commonDao.mapper(Sequence.class).sql("getNextVal").session().selectOne(FixerProfessionInfo.FIXER_PROFESSION_INFO_SEQUENCE);
        info.setId(id.intValue());
        int i = commonDao.mapper(FixerProfessionInfo.class).sql("insert").session().insert(info);
        if(i == 1){
            return info;
        }else{
            return null;
        }
    }

    @Override
    public int update(FixerProfessionInfo fixerProfessionInfo) throws Exception {
        return commonDao.mapper(FixerProfessionInfo.class).sql("updateByPrimaryKeySelective").session().update(fixerProfessionInfo);
    }


    @Override
    public int delete(Integer id) throws Exception {
        return commonDao.mapper(FixerProfessionInfo.class).sql("deleteByPrimaryKey").session().delete(id);
    }

    @Override
    public FixerProfessionInfo findById(Integer id) throws Exception {
        return commonDao.mapper(FixerProfessionInfo.class).sql("selectByPrimaryKey").session().selectOne(id);
    }
}
