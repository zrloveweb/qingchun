package com.zr.qingchun.controller;

import com.alibaba.fastjson.JSONObject;
import com.zr.qingchun.common.ResultDto;
import com.zr.qingchun.model.SysUser;
import com.zr.qingchun.model.vo.SysUserVo;
import com.zr.qingchun.service.SysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
     * @param loginParam
     * @return
     */
    @RequestMapping("/login")
    public ResultDto<SysUser> login(@RequestBody String loginParam){
        ResultDto<SysUser> resultDto = new ResultDto<>();
        JSONObject jsonObject = JSONObject.parseObject(loginParam);
        String username = jsonObject.getString("username");
        String password = jsonObject.getString("password");
        SysUser sysUser = userService.selectByNameAndPwd(username, password);
        if(sysUser == null){
            resultDto.setCode("10001");
            resultDto.setMessage("密码错误或用户不存在");
        }else{
            resultDto.setCode("10000");
            resultDto.setMessage("登陆成功");
        }
        return resultDto;
    }



    /**
     * 添加系统用户
     * @return
     */
    @RequestMapping( value = "/addSysUser" ,method = RequestMethod.POST)
    public ResultDto<List<SysUser>> addSysUser(@RequestBody SysUser sysUser){
        ResultDto<List<SysUser>> resultDto = new ResultDto<>();
        resultDto.success();
        sysUser.setCreator("admin");
        sysUser.setUpdator("admin");
        boolean b= userService.save(sysUser);
        if(!b){
            resultDto.failure();
        }
        return resultDto;
    }

    /**
     * 查询系统用户
     * @return
     */
    @RequestMapping("/sysUserList")
    public ResultDto<List<SysUserVo>> sysUserList(){
        ResultDto<List<SysUserVo>> resultDto = new ResultDto<>();
        List<SysUser> sysUsers = userService.selectAllSysUser();

        //转成vo类返回出去(以后会创建一个后台系统服务，此服务只提供数据库操作)
        List<SysUserVo> list = sysUsers.stream().map(data -> {
            SysUserVo vo = new SysUserVo();
            BeanUtils.copyProperties(data, vo);
            return vo;
        }).collect(Collectors.toList());
        resultDto.success(list);
        return resultDto;
    }


    /**
     * 查询系统用户
     * @return
     */
    @RequestMapping( value = "/updatesUserById" ,method = RequestMethod.POST)
    public ResultDto<List<SysUser>> updatesUserById(@RequestBody SysUser sysUser){
        ResultDto<List<SysUser>> resultDto = new ResultDto<>();
        resultDto.success();
        boolean b = userService.updateById(sysUser);
        if(!b){
            resultDto.failure();
        }
        return resultDto;
    }


    /**
     * 删除系统用户
     * @return
     */
    @RequestMapping( value = "/delSysUser" ,method = RequestMethod.POST)
    public ResultDto<List<SysUser>> delSysUser(@RequestBody String id){
        ResultDto<List<SysUser>> resultDto = new ResultDto<>();
        resultDto.success();
        boolean b = userService.removeById(id);
        if(!b){
            resultDto.failure();
        }
        return resultDto;
    }



}
