package com.zr.qingchun.model;

import java.io.Serializable;

/**
 * @author:zhaorui
 */
public class SysUser extends BaseEntity<SysUser> implements Serializable {



    private String userName;

    private String passWord;

    private String email;



    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord == null ? null : passWord.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }


    @Override
    public String toString() {
        return "SysUser{" +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}