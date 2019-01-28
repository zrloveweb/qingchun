package com.zr.qingchun.test.ceLueModel;

import org.springframework.stereotype.Component;

/**
 * @ClassName Apple
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/12/27 0027 18:18
 * Version 1.0
 **/
@Component
public class Orange implements Fruit {

    @Override
    public void eat() {
        System.out.println("Orange....");
    }
}
