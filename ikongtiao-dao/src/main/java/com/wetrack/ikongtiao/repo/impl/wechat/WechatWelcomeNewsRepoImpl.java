package com.wetrack.ikongtiao.repo.impl.wechat;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.ikongtiao.domain.wechat.WechatWelcomeNews;
import com.wetrack.ikongtiao.repo.api.wechat.WechatWelcomeNewsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 16/3/10.
 */
@Service
public class WechatWelcomeNewsRepoImpl implements WechatWelcomeNewsRepo {
    @Autowired
    CommonDao commonDao;

    @Override
    public WechatWelcomeNews create(WechatWelcomeNews news) throws Exception {
        if(1 == commonDao.mapper(WechatWelcomeNews.class).sql("insertSelective").session().insert(news)){
            return news;
        }else{
            throw new Exception("没有保存成功");
        }
    }

    @Override
    public List<WechatWelcomeNews> findAll() {
        return commonDao.mapper(WechatWelcomeNews.class).sql("findAll").session().selectList();
    }
}
