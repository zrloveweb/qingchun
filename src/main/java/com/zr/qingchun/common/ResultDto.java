package com.zr.qingchun.common;

/**
 * @ClassName ResultDto
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/10/24 0024 14:45
 * Version 1.0
 **/
public class ResultDto<T> {

    private boolean success;
    private String message;
    private String code;
    private T data;

    public ResultDto<T> success() {
        this.success=true;
        this.code="200";
        this.message="ok";
        return this;
    }

    public ResultDto<T> success(T data){
        this.success = true;
        this.message = "ok";
        this.code = "200";
        this.data = data;
        return this;
    }


    public ResultDto<T> success(String code,String message ,T data) {
        this.success=true;
        this.code=code;
        this.message=message;
        this.data = data;
        return this;
    }
    public ResultDto<T> success(String code,String message ) {
        this.success=true;
        this.code=code;
        this.message=message;
        return this;
    }



    public ResultDto<T> failure() {
        this.success=false;
        this.code="500";
        this.message="error";
        return this;
    }

    public ResultDto<T> failure(String message) {
        this.success=false;
        this.code="500";
        this.message=message;
        return this;
    }

    public ResultDto<T> failure(String code,String message) {
        this.success=false;
        this.code=code;
        this.message=message;
        return this;
    }
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
