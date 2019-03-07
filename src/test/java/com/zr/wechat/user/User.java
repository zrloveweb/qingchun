package com.zr.wechat.user;

import java.io.Serializable;

/**
 * @ClassName User
 * @Decription TODO
 * @Author Administrator
 * @Date 2019/3/7 0007 15:42
 * Version 1.0
 **/
public class User implements Serializable {

    private int age;
    private String name;
    private String gender;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "age=" + age +
                ", name='" + name + '\'' +
                ", gender='" + gender + '\'' +
                '}';
    }
}
