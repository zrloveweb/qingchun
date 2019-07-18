package com.zr.qingchun.service;

import com.zr.qingchun.model.SysUser;
import com.zr.qingchun.service.baservice.BaseService;

import java.util.List;

/**
 * @program: qingchun
 * @description: SysUserService
 * @author: zhaoRui
 * @create: 2019-07-09 18:18
 **/


public interface SysUserService extends BaseService {


    /**
     * 查询用户
     * @param userName
     * @param passWord
     * @return
     */
    SysUser selectByNameAndPwd(String userName , String passWord);

    /**
     * 查询所有用户
     * @return
     */
    List<SysUser> selectAllSysUser();



}
