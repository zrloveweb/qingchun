package com.zr.wechat.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zr.wechat.mapper.UserMapper;
import com.zr.wechat.model.User;
import com.zr.wechat.model.UserExample;
import com.zr.wechat.util.HttpClient;
import com.zr.wechat.util.WXPayUtil;
import com.zr.wechat.wechatEntity.PayEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @ClassName PayController
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/9/25 0025 17:36
 * Version 1.0
 **/
@Controller
public class PayController {
    private final static Logger logger = LoggerFactory.getLogger(PayController.class);

    @Autowired
    private UserMapper userMapper;
    /** 
     *@Author Zr
     *@Description  登录页面
     *@Param [request]
     *@return java.lang.String
     **/
    @RequestMapping("/login")
    public String myqq(HttpServletRequest request){
        logger.info(request.getParameter("accessToken"));
        logger.info(request.getParameter("openId"));
        return "login";
    }
    @RequestMapping("/demo")
    @ResponseBody
    public List demo(){
       /* User user = new User();
        user.setName("小花");
        user.setPhone("1771001992");
        userMapper.insert(user);*/
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andNameEqualTo("小明");
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
