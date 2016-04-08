package com.wetrack.ikongtiao.repo.api.admin;

import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.param.AdminQueryForm;

import java.util.List;

/**
 * Created by zhanghong on 16/2/19.
 */
public interface AdminRepo {
    int countWithParams(AdminQueryForm queryForm) throws Exception;

    List<User> listWithParams(AdminQueryForm queryForm) throws Exception;

    User findByEmail(String email);

    User findByEmailOrPhone(String email, String phone);

    User findById(Integer id);

    User create(User user)throws Exception;

    void update(User user)throws Exception;
}
