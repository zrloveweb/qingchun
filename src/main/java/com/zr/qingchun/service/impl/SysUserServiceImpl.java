package com.zr.qingchun.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.qingchun.mapper.SysUserMapper;
import com.zr.qingchun.model.SysUser;
import com.zr.qingchun.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @program: qingchun
 * @description: SysUserService
 * @author: zhaoRui
 * @create: 2019-07-09 18:19
 **/

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper,SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;



    @Override
    public SysUser selectByNameAndPwd(String userName, String passWord) {
        return userMapper.selectByNameAndPwd(userName, passWord);
    }

    @Override
    public List<SysUser> selectAllSysUser() {
        return userMapper.selectAllSysUser();
    }






}
