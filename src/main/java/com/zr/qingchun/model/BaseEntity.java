package com.zr.qingchun.model;

import com.baomidou.mybatisplus.extension.activerecord.Model;

import java.util.Date;

/**
 * @program: qingchun
 * @description: 实体基类
 * @author: zhaoRui
 * @create: 2019-07-15 13:37
 **/

public class BaseEntity<T extends Model> extends Model {

    /**
     * 删除常量字段
     */
    public static final String DEL_FLAG = "del_flag";

    /**
     * 未删除标记
     */
    public static final String DEL_FLAG_N = "0";
    /**
     * 删除标记
     */
    public static final String DEL_FLAG_Y = "1";
    /**
     * 当前页
     */
    public static final int  CURRENT_SIZE= 1;
    /**
     * 每页个数
     */
    public static final int PAGE_NUM = 5;


    private int id;

    private String delFlag;

    private Date createDate;

    private Date updateDate;

    private String creator;

    private String updator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(String delFlag) {
        this.delFlag = delFlag;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdator() {
        return updator;
    }

    public void setUpdator(String updator) {
        this.updator = updator;
    }
}
