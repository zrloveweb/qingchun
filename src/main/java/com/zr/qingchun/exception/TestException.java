package com.zr.qingchun.exception;

/**
 * @ClassName TestException
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/12/26 0026 14:28
 * Version 1.0
 **/
public class TestException extends RuntimeException{
    private String message;
    private String errMsg;
    public TestException(String message, String errMsg) {
        super();
        this.message = message;
        this.errMsg = errMsg;
    }
}
