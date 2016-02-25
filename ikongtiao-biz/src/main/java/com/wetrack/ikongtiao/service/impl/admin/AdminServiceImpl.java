package com.wetrack.ikongtiao.service.impl.admin;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.service.TokenService;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.param.AdminQueryForm;
import com.wetrack.ikongtiao.repo.api.admin.AdminRepo;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.utils.RegExUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by zhanghong on 15/12/30.
 */
@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    TokenService tokenService;

    @Autowired
    AdminRepo adminRepo;

    @Override
    public PageList<User> listWithParams(AdminQueryForm queryForm) throws Exception {
        PageList<User> page = new PageList<User>();
        page.setPage(queryForm.getPage());
        page.setPageSize(queryForm.getPageSize());
        queryForm.setStart(page.getStart());
        // 设置总数量
        page.setTotalSize(adminRepo.countWithParams(queryForm));
        // 获取内容
        List<User> list = adminRepo.listWithParams(queryForm);
        page.setData(list);
        return page;
    }

    @Override
    public Token login(String email, String password) throws Exception {

        User user = adminRepo.findByEmail(email);
        if(user == null){
            throw new Exception("用户邮箱不存在");
        }
        if(user.getPassword().equals(password)){
            com.wetrack.auth.domain.User tokenUser = new com.wetrack.auth.domain.User
                    (user.getId().toString(), user.getPassword(), com.wetrack.auth.domain.User.NEVER_EXPIRED, user.getRolesArrayString());

            return tokenService.login(tokenUser);
//            return tokenService.login(user.getId().toString(), password);
        }

        throw new Exception("密码错误!");
    }

    @Override
    public User create(User form) throws Exception {
        if(StringUtils.isEmpty(form.getEmail())){
            throw new Exception("邮箱不能为空");
        }
        if(!RegExUtil.isValidEmail(form.getEmail())){
            throw new Exception("无效的邮箱地址");
        }
        if(StringUtils.isEmpty(form.getPassword())){
            throw new Exception("密码不能为空");
        }

        User exist = adminRepo.findByEmailOrPhone(form.getEmail(), form.getPhone());
        if(exist != null){
            throw new Exception("重复注册的邮箱或者手机号码！");
        }

        return adminRepo.create(form);
    }

    @Override
    public void update(User form) throws Exception {
        if(form.getId() == null){
            throw new Exception("没有指定id");
        }

        adminRepo.update(form);

    }

    @Override
    public void delete(Integer id) throws Exception {
        User user = new User();
        user.setId(id);
        user.setDeleted(true);
        adminRepo.update(user);
    }
}
