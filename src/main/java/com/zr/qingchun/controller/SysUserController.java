package com.zr.qingchun.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.zr.qingchun.common.ResultDto;
import com.zr.qingchun.model.SysUser;
import com.zr.qingchun.model.vo.SysUserDto;
import com.zr.qingchun.service.SysUserService;
import org.apache.ibatis.reflection.ArrayUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.ArrayUtils;
import org.thymeleaf.util.ListUtils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @ClassName SysUserController
 * @Decription
 * @Author Administrator
 * @Date 2018/9/20 0020 14:49
 * Version 1.0
 **/
@RestController
@RequestMapping("/sysUser")
public class SysUserController {


    @Autowired
    private SysUserService userService;


    /**
     * 登录
     *
     * @param loginParam
     * @return
     */
    @RequestMapping("/login")
    public ResultDto<SysUser> login(@RequestBody String loginParam) {
        ResultDto<SysUser> resultDto = new ResultDto<>();
        JSONObject jsonObject = JSONObject.parseObject(loginParam);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        SysUser sysUser = userService.selectByNameAndPwd(username, password);
        if (sysUser == null) {
            resultDto.setCode("10001");
            resultDto.setMessage("密码错误或用户不存在");
        } else {
            resultDto.setCode("10000");
            resultDto.setMessage("登陆成功");
        }
        return resultDto;
    }


    /**
     * 添加系统用户
     *
     * @return
     */
    @RequestMapping(value = "/addSysUser", method = RequestMethod.POST)
    public ResultDto<List<SysUser>> addSysUser(@RequestBody SysUser sysUser) {
        ResultDto<List<SysUser>> resultDto = new ResultDto<>();
        resultDto.success();
        sysUser.setCreator("admin");
        sysUser.setUpdator("admin");
        sysUser.setDelFlag(SysUser.DEL_FLAG_N);
        boolean b = userService.save(sysUser);
        if (!b) {
            resultDto.failure();
        }
        return resultDto;
    }

    /**
     * 查询系统用户
     *
     * @return
     */
    @RequestMapping(value = "/sysUserList",method = RequestMethod.GET)
    public ResultDto<IPage<SysUser>> sysUserList(Integer currentSize, Integer pageNum ,String username) {
        ResultDto<IPage<SysUser>> resultDto = new ResultDto<>();
        IPage<SysUser> page = userService.selectAllSysUser(currentSize, pageNum,username);
        return resultDto.success(page);
    }


    /**
     * 查询系统用户
     *
     * @return
     */
    @RequestMapping(value = "/updatesUserById", method = RequestMethod.POST)
    public ResultDto<List<SysUser>> updatesUserById(@RequestBody SysUser sysUser) {
        ResultDto<List<SysUser>> resultDto = new ResultDto<>();
        resultDto.success();
        boolean b = userService.updateById(sysUser);
        if (!b) {
            resultDto.failure();
        }
        return resultDto;
    }


    /**
     * 删除系统用户
     *
     * @return
     */
    @RequestMapping(value = "/delSysUser", method = RequestMethod.POST)
    public ResultDto delSysUser(@RequestBody String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        ResultDto resultDto = new ResultDto<>();
        resultDto.failure();
        IPage<SysUser> page = userService.deleteSysUserById(jsonObject.getInteger("id"));
        if (page != null) {
            resultDto.success(page);
        }
        return resultDto;
    }

    /**
     * 删除系统用户
     *
     * @return
     */
    @RequestMapping(value = "/batchDelSysUser", method = RequestMethod.POST)
    public ResultDto batchDelSysUser(@RequestBody Integer[] ids) {
        ResultDto resultDto = new ResultDto<>();
        resultDto.failure();

        IPage<SysUser> sysUserIPage = userService.batchDeleteSysUser(ids);
        if (sysUserIPage != null) {
            resultDto.success(sysUserIPage);
        }

        return resultDto;
    }


}
