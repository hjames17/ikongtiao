package com.wetrack.ikongtiao.repo.impl.global;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.MicroCode;
import com.wetrack.ikongtiao.repo.api.global.MicroCodeRepo;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zhangsong on 15/12/10.
 */
@Repository("microCodeRepo")
public class MicroCodeRepoImpl implements MicroCodeRepo{

	@Resource
	protected CommonDao commonDao;

	@Override public MicroCode save(MicroCode microCode) {
		commonDao.mapper(MicroCode.class).sql("insertSelective").session().insert(microCode);
		return microCode;
	}

	@Override public int update(MicroCode microCode) {
		return commonDao.mapper(MicroCode.class).sql("updateSelective").session().update(microCode);
	}

	@Override public MicroCode getByCodeAndKey(String code, String key) {
		MicroCode param = new MicroCode();
		param.setCode(code);
		param.setKey(key);
		return commonDao.mapper(MicroCode.class).sql("selectByCodeAndKey").session().selectOne(param);
	}

	@Override public List<MicroCode> listMicroCodeByCode(String code) {
		return commonDao.mapper(MicroCode.class).sql("selectByCode").session().selectList(code);
	}
}
