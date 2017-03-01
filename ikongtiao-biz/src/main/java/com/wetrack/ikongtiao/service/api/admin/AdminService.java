package com.wetrack.ikongtiao.service.api.admin;

import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.param.AdminQueryForm;
import studio.wetrack.accountService.auth.domain.Token;

/**
 * Created by zhanghong on 15/12/30.
 */
public interface AdminService {

    PageList<User> listWithParams(AdminQueryForm queryForm) throws Exception;

    User getByEmail(String email);

    User getById(Integer id);

    Token login(String email, String password) throws Exception;

    User create(User form) throws Exception;

    void update(User form) throws Exception;

    void delete(Integer id) throws Exception;

    int getAvailableAdminId();

    void dutyOn(Integer id, boolean on) throws Exception;
}
