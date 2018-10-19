package com.zr.qingchun.controller;

import com.zr.qingchun.common.ResultDto.ResultDTO;
import com.zr.qingchun.mapper.UserMapper;
import com.zr.qingchun.model.User;
import com.zr.qingchun.model.UserExample;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName PayController
 * @Decription TODO
 * @Author Administrator
 * Version 1.0
 **/
@RestController
@Api(description = "测试接口")
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private HttpSession session;

    @Autowired
    private UserMapper userMapper;
    @GetMapping(value = "/login0")
    public String Login0(){
       session.setAttribute("sessionkey",true);
       return "redirect:admin/index.html";
    }

    @ApiOperation("登录接口")
    @ApiImplicitParams({@ApiImplicitParam(name = "username",value = "账号",dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "password",value = "密码",dataType = "String",paramType="query")})
    @RequestMapping(value = "/login")
    @CrossOrigin
    public ResultDTO Login(@RequestParam String username, @RequestParam String password){
        ResultDTO<User> resultDTO = new ResultDTO<>();
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(username);
        criteria.andPswEqualTo(password);
        List<User> user = userMapper.selectByExample(userExample);
        if(user != null && user.size()>0 ){
            resultDTO.setSuccess(true);
            resultDTO.setCode(200);
            resultDTO.setMessage("查询成功");
            resultDTO.setEntityData(user.get(0));
            return resultDTO;
        }
        resultDTO.setSuccess(false);
        resultDTO.setCode(400);
        resultDTO.setMessage("无此用户");
        return resultDTO;
    }


}
