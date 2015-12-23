package com.wetrack.ikongtiao.domain.admin;

/**
 * Created by zhanghong on 15/12/23.
 */
public class User {

    String email;

    String phone;

    //nick name or true name
    String name;

    String password; //md5 hashed

    Role[] roles;

}
