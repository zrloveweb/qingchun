package com.zr.qingchun.model.vo;

import com.zr.qingchun.model.BaseEntity;
import lombok.Data;

/**
 * @program: qingchun
 * @description: SysUser 前端交互vo
 * @author: zhaoRui
 * @create: 2019-07-17 17:46
 **/

@Data
public class SysUserDto {
    private int id;

    private String userName;

    private String passWord;

    private String email;
}
