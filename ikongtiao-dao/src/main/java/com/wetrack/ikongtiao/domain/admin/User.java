package com.wetrack.ikongtiao.domain.admin;

import java.util.Date;

/**
 * Created by zhanghong on 15/12/23.
 */
public class User {

    Integer id;

    String email;

    String phone;

    //nick name or true name
    String name;

    String avatar;

    Boolean inService;
    Boolean deleted;
    Boolean disabled;

    String password; //md5 hashed

    Date createTime;
    Date updateTime;

//    Role[] roles;

    //便于存储
    //格式: kefu;financial
    String rolesString;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role[] getRoles() {
        return parseRolesString();
    }

    public String[] getRolesArrayString(){
        if(rolesString == null || rolesString.isEmpty()){
            return null;
        }

        String[] parts = rolesString.split(DELIMITER);
        return parts;
    }

    public String getRolesString() {
        return rolesString;
    }

    public void setRolesString(String rolesString) {
        this.rolesString = rolesString;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean isInService() {
        return inService;
    }

    public void setInService(Boolean inService) {
        this.inService = inService;
    }

    public Boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(Boolean disabled) {
        this.disabled = disabled;
    }
//    public void setRoles(Role[] roles) {
//        this.roles = roles;
//    }

    static final String DELIMITER = ";";
    private Role[] parseRolesString(){
        if(rolesString == null || rolesString.isEmpty()){
            return null;
        }

        String[] parts = rolesString.split(DELIMITER);
        Role[] roles = new Role[parts.length];
        for(int i = 0; i < parts.length; i++){
            roles[i] = Role.valueOf(parts[i]);
        }

        return roles;
    }


}
