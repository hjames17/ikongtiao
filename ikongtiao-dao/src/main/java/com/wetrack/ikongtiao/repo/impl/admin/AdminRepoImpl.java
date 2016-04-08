package com.wetrack.ikongtiao.repo.impl.admin;

import com.wetrack.base.dao.api.CommonDao;
import com.wetrack.base.page.BaseCondition;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.param.AdminQueryForm;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 16/2/19.
 * 关联的是mybatis admin.xml
 */
@Service
public class AdminRepoImpl implements AdminRepo {
    @Autowired
    CommonDao commonDao;

    @Override
    public int countWithParams(AdminQueryForm queryForm) throws Exception {
        if(queryForm.getPhone() != null){
            //加上sql like查询通配符
            queryForm.setPhone("%" + queryForm.getPhone() + "%");
        }
        if(queryForm.getName() != null){
            //加上sql like查询通配符
            queryForm.setName("%" + queryForm.getName() + "%");
        }
        if(queryForm.getEmail() != null){
            //加上sql like查询通配符
            queryForm.setEmail("%" + queryForm.getEmail() + "%");
        }

        BaseCondition baseCondition = commonDao.mapper(User.class).sql("countByQueryParam").session()
                .selectOne(queryForm);
        Integer count = baseCondition == null ? 0 : baseCondition.getTotalSize();
        return count == null ? 0 : count;
    }

    @Override
    public List<User> listWithParams(AdminQueryForm queryForm) throws Exception {
        if(queryForm.getPhone() != null){
            //加上sql like查询通配符
            queryForm.setPhone("%" + queryForm.getPhone() + "%");
        }
        if(queryForm.getName() != null){
            //加上sql like查询通配符
            queryForm.setName("%" + queryForm.getName() + "%");
        }
        if(queryForm.getEmail() != null){
            //加上sql like查询通配符
            queryForm.setEmail("%" + queryForm.getEmail() + "%");
        }
        return commonDao.mapper(User.class).sql("listByQueryParam").session().selectList(queryForm);
    }

    @Override
    public User findByEmail(String email) {
        AdminQueryForm queryForm = new AdminQueryForm();
        queryForm.setEmail(email);
        return commonDao.mapper(User.class).sql("matchByQueryParamOr").session().selectOne(queryForm);
    }

    @Override
    public User findByEmailOrPhone(String email, String phone) {
        AdminQueryForm queryForm = new AdminQueryForm();
        queryForm.setEmail(email);
        queryForm.setPhone(phone);
        return commonDao.mapper(User.class).sql("matchByQueryParamOr").session().selectOne(queryForm);
    }

    @Override
    public User findById(Integer id) {
        return commonDao.mapper(User.class).sql("selectByPrimaryKey").session().selectOne(id);
    }

    @Override
    public User create(User user) throws Exception {
        int count = commonDao.mapper(User.class).sql("insertSelective").session().insert(user);
        if(count != 1){
            throw new Exception("创建,实际创建数量:" + count);
        }
        return user;
    }

    @Override
    public void update(User user) throws Exception {
        commonDao.mapper(User.class).sql("updateByPrimaryKeySelective").session().update(user);
    }
}
