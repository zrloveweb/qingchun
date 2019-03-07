package com.zr.qingchun.test.proxy.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @ClassName DynamicHandler
 * @Decription TODO
 * @Author Administrator
 * @Date 2019/1/8 0008 18:02
 * Version 1.0
 **/
public class DynamicHandler implements InvocationHandler {

    private Object target;

    public DynamicHandler(Object target) {
        this.target = target;
    }

    public <T> T getProxy(){
        return (T) Proxy.newProxyInstance(target.getClass().getClassLoader() , target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("invoke  begin");
        System.out.println("method: "+ method.getName() + "is invoked!");
        method.invoke(target,args);
        System.out.println("invoke end");
        return null;
    }
}
