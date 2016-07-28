package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.domain.SimpleGrantedAuthority;
import com.wetrack.auth.domain.Token;
import com.wetrack.auth.exceptions.TokenAuthorizationException;
import com.wetrack.auth.filter.SignTokenAuth;
import com.wetrack.auth.filter.SignTokenAuthInterceptor;
import com.wetrack.auth.service.AuthorizationService;
import com.wetrack.auth.service.TokenService;
import com.wetrack.base.page.PageList;
import com.wetrack.base.utils.encrypt.MD5;
import com.wetrack.ikongtiao.domain.admin.AdminType;
import com.wetrack.ikongtiao.domain.admin.Role;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.exception.BusinessException;
import com.wetrack.ikongtiao.param.AdminQueryForm;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.utils.Util;
import com.wetrack.message.MessageId;
import com.wetrack.message.MessageParamKey;
import com.wetrack.message.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

import static com.wetrack.ikongtiao.domain.admin.AdminType.MANAGER;

/**
 * Created by zhanghong on 15/12/28.
 */

@Controller
@RequestMapping(value = "/admin")
public class AdminController {

//    static final String BASE_PATH = "/admin";

    @Autowired
    TokenService tokenService;

    @Autowired
    AdminService adminService;

    @Autowired
    MessageService messageService;


    @ResponseBody
    @RequestMapping(value = "/list" , method = {RequestMethod.GET})
    public PageList<User> list(@RequestParam(required = false, value = "inService") Boolean inService,
                                @RequestParam(required = false, value = "name") String name,
                                @RequestParam(required = false, value = "email") String email,
                                @RequestParam(required = false, value = "adminType") AdminType adminType,
                                @RequestParam(required = false, value = "phone") String phone,
                                @RequestParam(required = false, value = "page") Integer page,
                                @RequestParam(required = false, value = "pageSize") Integer pageSize) throws Exception{
        AdminQueryForm queryForm = new AdminQueryForm();
        queryForm.setPage(page == null ? 0 : page);
        queryForm.setPageSize(pageSize == null ? 10 : pageSize);
        queryForm.setPhone(phone);
        queryForm.setName(name);
        queryForm.setEmail(email);
        queryForm.setAdminType(adminType);
        queryForm.setInService(inService);
        return adminService.listWithParams(queryForm);
    }

    @ResponseBody
    @RequestMapping(value = "/{id}" , method = {RequestMethod.GET})
    public User get(@PathVariable(value = "id") Integer id) throws Exception{
        User user = adminService.getById(id);
        user.setPassword(null);
        return user;
    }


    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    LoginOut login(@RequestBody LoginForm loginForm) throws Exception{
        Token token = adminService.login(loginForm.getEmail(), loginForm.getPassword());
        LoginOut out = new LoginOut();
        out.setToken(token.getToken());
        out.setId(token.getUser().getId());
        adminService.dutyOn(Integer.valueOf(token.getUser().getId()), true);
        User user = adminService.getByEmail(loginForm.getEmail());
        user.setPassword(null);
        out.setUserInfo(user);
//        out.setRoleString(user.getRolesString());
//        out.setAdminType(user.getAdminType());
        return out;
    }
    @ResponseBody
    @RequestMapping(value = "/loginjk", method = RequestMethod.POST)
    LoginOut loginjk(@RequestBody LoginForm loginForm) throws Exception{
        Token token = adminService.login(loginForm.getEmail(), loginForm.getPassword());
        User user = adminService.getByEmail(loginForm.getEmail());
        if(!user.getAdminType().equals(MANAGER)){
            throw new BusinessException("无效的用户，不允许登录");
        }
        LoginOut out = new LoginOut();
        out.setToken(token.getToken());
        out.setId(token.getUser().getId());
        user.setPassword(null);
        out.setUserInfo(user);
        return out;
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    void login(@RequestBody LoginOut form, HttpServletRequest request) throws Exception{
        tokenService.logout(form.getToken());
        com.wetrack.auth.domain.User user = (com.wetrack.auth.domain.User)request.getAttribute("user");
        adminService.dutyOn(Integer.valueOf(user.getId()), false);
    }

    @SignTokenAuth
    @ResponseBody
    @RequestMapping(value = "/duty", method = RequestMethod.POST)
    void dutyOnOff(@RequestParam(value = "on", required = true) boolean on, HttpServletRequest request) throws Exception{
        com.wetrack.auth.domain.User user = (com.wetrack.auth.domain.User)request.getAttribute("user");
        adminService.dutyOn(Integer.valueOf(user.getId()), on);
    }

//    @ResponseBody
//    @RequestMapping(value = "/add", method = RequestMethod.POST)
//    String add(@RequestBody User form) throws Exception{
//        User user = adminService.create(form);
//        return user.getId().toString();
//    }

    /**
     * @param form
     * @return
     * @throws Exception
     */

    @Autowired
    AuthorizationService authorizationService;

    @ResponseBody
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    String create(@RequestBody User form, HttpServletRequest request) throws Exception{
        String role;
        switch (form.getAdminType()){
            case KEFU:
                role = Role.EDIT_ADMIN_KEFU.getRoleName();
                break;
            case MANAGER:
                role = Role.EDIT_ADMIN_MANAGER.getRoleName();
                break;
            case FINANCIAL:
                role = Role.EDIT_ADMIN_FINANCIAL.getRoleName();
                break;
            case SUPERVISOR:
                role = Role.EDIT_ADMIN_SUPERVISOR.getRoleName();
                break;
            default:
                throw new BusinessException("无效的账号类型: " + form.getAdminType());
        }

        String tokenString = request.getHeader(SignTokenAuthInterceptor.HEADER_CUSTOMER_TOKEN);

        Token token = tokenService.findByTokenString(tokenString);
        if (!authorizationService.grantAccess(token.getToken(), new SimpleGrantedAuthority(role))) {
            throw new TokenAuthorizationException("用户类型没有访问权限");
        }
        if(StringUtils.isEmpty(form.getEmail())){
            throw new BusinessException("email不能为空");
        }else if(StringUtils.isEmpty(form.getName())){
            throw new BusinessException("名字不能为空");
        }else if(form.getAdminType() == null){
            throw new BusinessException("后台账号类型没有指定");
        }

        String initPass = null;
        if(StringUtils.isEmpty(form.getPassword())){
            initPass = Util.createPassword("MGR", 8);
            form.setPassword(MD5.encryptHex(initPass));

        }

        User user = adminService.create(form);

        if(initPass != null) {
            try {
                Map<String, Object> params = new HashMap<String, Object>();
                params.put(MessageParamKey.ADMIN_ID, user.getId());
                params.put(MessageParamKey.PASSWORD, initPass);
                messageService.send(MessageId.ADMIN_INITIAL_PASSWORD, params);
            }catch (Exception e){
                //ignore
            }
        }
        return user.getId().toString();
    }

    @ResponseBody
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    void modify(@RequestBody User form) throws Exception{
        if(form.getId() == null){
            throw new BusinessException("id为空");
        }
        form.setEmail(null);
        adminService.update(form);
    }

    @ResponseBody
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    void delete(@PathVariable(value = "id") Integer id) throws Exception{
        adminService.delete(id);
    }

//    @ResponseBody
//    @RequestMapping("/socket/test")
//    public String test(@RequestParam(value = "name")String name) throws IOException {
//        TextMessage textMessage = new TextMessage(name);
//        Iterator<WebSocketSession> iterator = NotificationHandler.sessions.iterator();
//
//        while (iterator.hasNext()){
//            WebSocketSession session = iterator.next();
//            if(session.isOpen()){
//                session.sendMessage(textMessage);
//            }else{
//                iterator.remove();
//            }
//        }
//        return "ok客户端数:" +NotificationHandler.sessions.size() ;
//    }

    static class LoginOut {
        String token;
        String id;

        public User getUserInfo() {
            return userInfo;
        }

        public void setUserInfo(User userInfo) {
            this.userInfo = userInfo;
        }

        User userInfo;
//        String roleString;
//        String roleName;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

//        public String getRoleString() {
//            return roleString;
//        }
//
//        public void setRoleString(String roleString) {
//            this.roleString = roleString;
//        }

//        public String getAdminType() {
//            return roleName;
//        }
//
//        public void setAdminType(String roleName) {
//            this.roleName = roleName;
//        }
    }

    static class LoginForm{
        String email;
        String password;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
