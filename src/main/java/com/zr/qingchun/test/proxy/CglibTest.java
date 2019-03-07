package com.zr.qingchun.test.proxy;

import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @ClassName CglibTest
 * @Decription TODO
 * @Author Administrator
 * @Date 2019/1/9 0009 17:01
 * Version 1.0
 **/
public class CglibTest implements MethodInterceptor {

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        return null;
    }
}
