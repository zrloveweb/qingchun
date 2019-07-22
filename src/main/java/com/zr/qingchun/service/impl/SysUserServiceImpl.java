package com.zr.qingchun.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zr.qingchun.mapper.SysUserMapper;
import com.zr.qingchun.model.SysUser;
import com.zr.qingchun.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: qingchun
 * @description: SysUserService
 * @author: zhaoRui
 * @create: 2019-07-09 18:19
 **/

@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper userMapper;


    @Override
    public SysUser selectByNameAndPwd(String userName, String passWord) {
        return userMapper.selectByNameAndPwd(userName, passWord);
    }

    @Override
    public IPage<SysUser> selectAllSysUser(int currentSize, int pageNum, String username) {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysUser.DEL_FLAG, SysUser.DEL_FLAG_N);
        if (StringUtils.isNotBlank(username)) {
            queryWrapper.like(SysUser.USERNAME, username);
        }
        Page<SysUser> page = new Page<>(currentSize, pageNum);
        return super.page(page, queryWrapper);
    }

    @Override
    public IPage<SysUser> deleteSysUserById(Integer id) {
        boolean b = super.removeById(id);
        IPage<SysUser> resultPage = null;
        if (b) {
            resultPage = pageMethod();
        }
        return resultPage;
    }

    @Override
    public IPage<SysUser> batchDeleteSysUser(Integer[] ids) {

        List<SysUser> list = new ArrayList<>();
        SysUser sysUser = null;
        for (int i = 0; i < ids.length; i++) {
            sysUser = new SysUser();
            sysUser.setDelFlag(SysUser.DEL_FLAG_Y);
            sysUser.setId(ids[i]);
            list.add(sysUser);
        }
        boolean b = super.updateBatchById(list);
        IPage<SysUser> resultPage = null;
        if (b) {
            resultPage = pageMethod();
        }
        return resultPage;
    }

    private IPage<SysUser> pageMethod() {
        QueryWrapper<SysUser> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq(SysUser.DEL_FLAG, SysUser.DEL_FLAG_N);
        Page<SysUser> page = new Page<>(SysUser.CURRENT_SIZE, SysUser.PAGE_NUM);
        return super.page(page, queryWrapper);
    }

}
