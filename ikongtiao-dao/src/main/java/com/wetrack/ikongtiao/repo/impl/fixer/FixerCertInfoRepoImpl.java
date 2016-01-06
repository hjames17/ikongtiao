package com.wetrack.ikongtiao.repo.impl.fixer;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.Sequence;
import com.wetrack.ikongtiao.domain.fixer.FixerCertInfo;
import com.wetrack.ikongtiao.repo.api.fixer.FixerCertInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by zhanghong on 16/1/5.
 */
@Service
public class FixerCertInfoRepoImpl implements FixerCertInfoRepo {

    @Autowired
    CommonDao commonDao;

    @Override
    public FixerCertInfo findLatestForFixer(Integer fixerId) throws Exception {
        return commonDao.mapper(FixerCertInfo.class).sql("selectLatestForFixer").session().selectOne(fixerId);
    }

    @Override
    public FixerCertInfo create(Integer fixerId, String idNum, String idWithFaceImg, String idImgFront, String idImgBack) throws Exception {
        FixerCertInfo fixerCertInfo = new FixerCertInfo();
        fixerCertInfo.setFixerId(fixerId);
        fixerCertInfo.setIdNum(idNum);
        fixerCertInfo.setCheckState(1);//待审核
        fixerCertInfo.setIdWithFaceImg(idWithFaceImg);
        fixerCertInfo.setIdImgFront(idImgFront);
        fixerCertInfo.setIdImgBack(idImgBack);
        Long id = commonDao.mapper(Sequence.class).sql("getNextVal").session().selectOne(FixerCertInfo.FIXER_CERT_INFO_SEQUENCE);
        fixerCertInfo.setId(id.intValue());
        int i = commonDao.mapper(FixerCertInfo.class).sql("insert").session().insert(fixerCertInfo);
        if(i == 1){
            return fixerCertInfo;
        }else{
            return null;
        }
    }

    @Override
    public int update(FixerCertInfo fixerCertInfo) throws Exception {
        return commonDao.mapper(FixerCertInfo.class).sql("updateByPrimaryKeySelective").session().update(fixerCertInfo);
    }

    @Override
    public int delete(Integer id) throws Exception {
        commonDao.mapper(FixerCertInfo.class).sql("deleteByPrimaryKey").session().delete(id);
        return 0;
    }

    @Override
    public FixerCertInfo findById(Integer id) throws Exception {
        return commonDao.mapper(FixerCertInfo.class).sql("selectByPrimaryKey").session().selectOne(id);
    }
}
