package com.zr.wechat;

import com.zr.qingchun.exception.TestException;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName TestFx
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/12/10 0010 17:12
 * Version 1.0
 **/
public  class  TestFx {


    @Test
    public void adbc(){
        testTemplate();
    }
    public void cca()  {
      throw new TestException("aa","bb");
    }

    public static void main(String[] args) {
        testTemplate();
    }


    @Test
    public static void testTemplate(){
        String tureStr = "TRUE";
        String falseStr = "FALSE";
        System.out.println(Boolean.parseBoolean(tureStr));
        System.out.println(Boolean.parseBoolean(falseStr));
    }
}
