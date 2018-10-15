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
public class PayController {
    private final static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private UserMapper userMapper;
    @ApiOperation("用户登陆")
    @ApiImplicitParams({@ApiImplicitParam(name = "userName",value = "用户名",required = true,dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "password",value = "密码",required = true,dataType = "String",paramType="query")})
    @PostMapping(value = "/login1")
    public ResponseEntity Login(@RequestParam String userName, @RequestParam  String password){
        if (userName.equals("admin")&&password.equals("admin")){
            return ResponseEntity.ok("OK");
        }else{
            return ResponseEntity.ok("Fail");
        }
    }

    /** 
     *@Author Zr
     *@Description  登录页面
     *@Param [request]
     *@return java.lang.String
     **/
    @GetMapping("/login")
    public String myqq(HttpServletRequest request){
        logger.info(request.getParameter("accessToken"));
        logger.info(request.getParameter("openId"));
        return "login";
    }

    /**
     *@Author Zr
     *@Description  登录页面
     *@Param [request]
     *@return java.lang.String
     **/
    @GetMapping("/index")
    public String index(){
        return "index";
    }
    @ApiOperation("测试demo方法")
    @ResponseBody
    @GetMapping("/demo")
    @ApiImplicitParams({@ApiImplicitParam(name = "age",value = "年龄",required = true,dataType = "String",paramType="query"),
            @ApiImplicitParam(name = "name",value = "用户名",required = true,dataType = "String",paramType="query")})
    public List demo(@RequestParam String age,@RequestParam String name){
       /* User user = new User();
        user.setName("小花");
        user.setPhone("1771001992");
        userMapper.insert(user);*/
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(name);
        //criteria.andNameLike("小明");
        List<User> list = userMapper.selectByExample(userExample);
        return list;
    }


    @ApiOperation("测试postDemo方法")
    @ResponseBody
    @PostMapping("/demo")
    public List postDemo(String age,String name){
       /* User user = new User();
        user.setName("小花");
        user.setPhone("1771001992");
        userMapper.insert(user);*/
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo(name);
        //criteria.andNameLike("小明");
        List<User> list = userMapper.selectByExample(userExample);
        return list;
    }
    /**
     *@Author Zr
     *@Description  下单接口
     *@Param [req]
     *@return java.lang.String
     **/
  /*  @RequestMapping( value = "/payOrder",method = RequestMethod.GET)
    public String wechatPost(){
        PayEntity payEntity = new PayEntity();

        //1.参数封装
        Map param=new HashMap();
        param.put("appid", PayEntity.appid);//公众账号ID
        param.put("mch_id", PayEntity.partner);//商户
        param.put("nonce_str", "ns64Mu07nTYX2iPZ");//随机字符串
        param.put("body", "pinyougou");
        param.put("out_trade_no", UUID.randomUUID().toString().replace("-",""));//交易订单号
        param.put("total_fee", "100");//金额（分）
        param.put("spbill_create_ip", "127.0.0.1");
        param.put("notify_url", PayEntity.notifyurl);
        param.put("trade_type", "NATIVE");//交易类型
        String xmlParam = "";

        try {
            xmlParam = com.github.wxpay.sdk.WXPayUtil.generateSignedXml(param, PayEntity.partnerkey);
            logger.info(xmlParam    );
            //2.发送请求
            HttpClient httpClient=new HttpClient("https://api.mch.weixin.qq.com/pay/unifiedorder");
            httpClient.setHttps(true);
            httpClient.setXmlParam(xmlParam);
            httpClient.post();
            //3.获取结果
            String xmlResult = httpClient.getContent();
            logger.info("下单数据：" + xmlResult);
        } catch (Exception e) {
            e.printStackTrace();
        }



        return "";
    }*/

    /**
     *@Author Zr
     *@Description  异步返回数据
     *@Param [req]
     *@return java.lang.String
     **/
    @RequestMapping( value = "/WeChatPayNotify",method = RequestMethod.POST)
    public String WeChatPayNotify(HttpServletRequest request){
        return null;
    }
}
