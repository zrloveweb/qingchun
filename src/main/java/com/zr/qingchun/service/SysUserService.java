package com.zr.qingchun.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zr.qingchun.model.SysUser;
import com.zr.qingchun.service.baservice.BaseService;

import java.util.Collection;
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
     *
     * @param userName
     * @param passWord
     * @return
     */
    SysUser selectByNameAndPwd(String userName, String passWord);


    /**
     * 查询所有用户
     *
     * @param currentSize
     * @return
     */
    IPage<SysUser> selectAllSysUser(int currentSize , int pageNum,String username);
    /**
     * 批量删除用户
     *
     * @param ids
     * @return
     */
    IPage<SysUser> batchDeleteSysUser(Integer[] ids);

    /**
     * 通过id删除用户
     *
     * @param id
     * @return
     */
    IPage<SysUser> deleteSysUserById(Integer id);
}
