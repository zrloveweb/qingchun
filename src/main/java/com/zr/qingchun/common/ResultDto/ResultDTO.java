package com.zr.qingchun.common.ResultDto;

import java.io.Serializable;

/**
 * @ClassName ResultDTO
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/10/19 0019 10:55
 * Version 1.0
 **/
public class ResultDTO<T> implements Serializable {

    private boolean success;
    private String message;
    private int code;
    private T entityData;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public T getEntityData() {
        return entityData;
    }

    public void setEntityData(T entityData) {
        this.entityData = entityData;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
