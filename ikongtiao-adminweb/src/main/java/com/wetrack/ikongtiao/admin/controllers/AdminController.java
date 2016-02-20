package com.wetrack.ikongtiao.admin.controllers;

import com.wetrack.auth.domain.Token;
import com.wetrack.auth.service.TokenService;
import com.wetrack.base.page.PageList;
import com.wetrack.ikongtiao.domain.admin.User;
import com.wetrack.ikongtiao.param.AdminQueryForm;
import com.wetrack.ikongtiao.service.api.admin.AdminService;
import com.wetrack.ikongtiao.socket.NotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Iterator;

/**
 * Created by zhanghong on 15/12/28.
 */

@Controller
public class AdminController {

    static final String BASE_PATH = "/admin";

    @Autowired
    TokenService tokenService;

    @Autowired
    AdminService adminService;

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/list" , method = {RequestMethod.GET})
    public PageList<User> list(@RequestParam(required = false, value = "inService") Boolean inService,
                                @RequestParam(required = false, value = "name") String name,
                                @RequestParam(required = false, value = "email") String email,
                                @RequestParam(required = false, value = "phone") String phone,
                                @RequestParam(required = false, value = "role") String role,
                                @RequestParam(required = false, value = "page") Integer page,
                                @RequestParam(required = false, value = "pageSize") Integer pageSize) throws Exception{
        AdminQueryForm queryForm = new AdminQueryForm();
        queryForm.setPage(page == null ? 0 : page);
        queryForm.setPageSize(pageSize == null ? 10 : pageSize);
        queryForm.setPhone(phone);
        queryForm.setName(name);
        queryForm.setEmail(email);
        queryForm.setInService(inService);
        queryForm.setRole(role);
        return adminService.listWithParams(queryForm);
    }


    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/login", method = RequestMethod.POST)
    LoginOut login(@RequestBody LoginForm loginForm) throws Exception{
        Token token = adminService.login(loginForm.getEmail(), loginForm.getPassword());
        LoginOut out = new LoginOut();
        out.setToken(token.getToken());
        out.setId(token.getUser().getId());
        return out;
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/logout", method = RequestMethod.POST)
    void login(@RequestBody LoginOut form) throws Exception{
        tokenService.logout(form.getToken());
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/add", method = RequestMethod.POST)
    String add(@RequestBody User form) throws Exception{
        User user = adminService.create(form);
        return user.getId().toString();
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/modify", method = RequestMethod.POST)
    void modify(@RequestBody User form) throws Exception{
        if(form.getId() == null){
            throw new Exception("id为空");
        }
        form.setEmail(null);
        adminService.update(form);
    }

    @ResponseBody
    @RequestMapping(value = BASE_PATH + "/delete/{id}", method = RequestMethod.DELETE)
    void delete(@PathVariable(value = "id") Integer id) throws Exception{
        adminService.delete(id);
    }

    @ResponseBody
    @RequestMapping("/socket/test")
    public String test(@RequestParam(value = "name")String name) throws IOException {
        TextMessage textMessage = new TextMessage(name);
        Iterator<WebSocketSession> iterator = NotificationHandler.sessions.iterator();

        while (iterator.hasNext()){
            WebSocketSession session = iterator.next();
            if(session.isOpen()){
                session.sendMessage(textMessage);
            }else{
                iterator.remove();
            }
        }
        return "ok客户端数:" +NotificationHandler.sessions.size() ;
    }

    static class LoginOut {
        String token;
        String id;

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
