package com.zr.qingchun.model;

import lombok.Data;

import java.io.Serializable;

/**
 * @author:zhaorui
 */
@Data
public class SysUser extends BaseEntity<SysUser> implements Serializable {

    public static final String USERNAME = "user_name";

    private String userName;

    private String passWord;

    private String email;

    private String address;

}
