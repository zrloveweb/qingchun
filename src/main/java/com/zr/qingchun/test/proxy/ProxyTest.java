package com.zr.qingchun.test.proxy;

/**
 * @ClassName ProxyTest	
 * @Decription TODO
 * @Author Administrator
 * @Date 2019/1/8 0008 18:28
 * Version 1.0
 **/
public class ProxyTest {

    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles","true");
        //DynamicHandler dynamicHandler = new DynamicHandler(new UserServiceIml());
        //UserService userService = dynamicHandler.getProxy();
       // UserService userService = (UserService)Proxy.newProxyInstance(
          //      ProxyTest.class.getClassLoader(), UserServiceIml.class.getInterfaces(), dynamicHandler);
        //userService.login();
    }
}
