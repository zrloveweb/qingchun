package com.zr.qingchun.controller;

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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName PayController
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/9/25 0025 17:36
 * Version 1.0
 **/
@Controller
@Api(description = "测试接口")
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private HttpSession session;
    @Autowired
    private UserMapper userMapper;
    @GetMapping(value = "/login")
    public String Login(){
       session.setAttribute("sessionkey",true);
       return "redirect:admin/index.html";
    }


}
