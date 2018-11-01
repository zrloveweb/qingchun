package com.zr.qingchun.controller;

import com.zr.qingchun.common.ResultDto;
import com.zr.qingchun.mapper.MenuMapper;
import com.zr.qingchun.mapper.UserMapper;
import com.zr.qingchun.model.Menu;
import io.swagger.annotations.Api;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName PayController
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/9/25 0025 17:36
 * Version 1.0
 **/
@RestController
@Api(description = "登录相关接口")
public class LoginController {
    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private HttpSession session;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;
    @GetMapping(value = "/login")
    public String Login(){
       session.setAttribute("sessionkey",true);
       return "redirect:admin/index.html";
    }

    @PostMapping(value = "/selectMenuByUserId")
    public ResultDto<List> selectMenuByUserId(@RequestParam("userId") String userId,@RequestParam("flag") int flag){
        Map<String,Object> map = new HashMap<>();
        map.put("userId",userId);
        map.put("flag",flag);
        return new ResultDto<List>().success("200","查询成功",menuMapper.selectMenuByUserId(map));
    }



}
