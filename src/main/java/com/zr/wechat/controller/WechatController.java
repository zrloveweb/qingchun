package com.zr.wechat.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zr.wechat.common.Constant;
import com.zr.wechat.common.HttpClient;
import com.zr.wechat.service.WechatService;
import com.zr.wechat.util.CheckUtil;
import com.zr.wechat.util.WechatParseXmlUtil;
import com.zr.wechat.wechatEntity.ClickButton;
import com.zr.wechat.wechatEntity.ViewButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName WechatController
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/9/20 0020 14:49
 * Version 1.0
 **/
@RestController
public class WechatController {

    @Autowired
    private WechatService wechatService;

    private final static Logger logger = LoggerFactory.getLogger(WechatController.class);
    //全局token
    public static String accesstoken = null;
    @RequestMapping(value = "wechat",method = RequestMethod.GET)
    public String wechatGet(HttpServletRequest req){
        try {
            Map<String, String> openId = WechatParseXmlUtil.parseXml(req);
            System.out.println(openId.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String,String> map = WechatParseXmlUtil.parseXml(req);
            logger.info(map.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 微信加密签名
        String signature = req.getParameter("signature");
        // 时间戳
        String timestamp = req.getParameter("timestamp");
        // 随机数
        String nonce = req.getParameter("nonce");
        // 随机字符串
        String echostr = req.getParameter("echostr");
        accesstoken  = getAccessToken();
        logger.info("token:" + accesstoken);
        // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }

    
    /** 
     *@Author Zr
     *@Description  wechat Post请求
     *@Param [req]
     *@return java.lang.String
     **/
    @RequestMapping( value = "/wechat",method = RequestMethod.POST)
    public String wechatPost(HttpServletRequest req){
        String result = wechatService.processRequest(req);
        logger.info(result);
        return result;
    }

    /**
     *@Author Zr
     *@Description  设置行业模板
     *@Param []
     *@return java.lang.String
     **/
    @RequestMapping(value = "/createTemplate",method = RequestMethod.GET)
    public JSONObject createTemplate(){
        return wechatService.createTemplate(accesstoken);
    }

    /**
     *@Author Zr
     *@Description  获取行业模板
     *@Param []
     *@return java.lang.String
     **/
    @RequestMapping(value = "/getTemplate",method = RequestMethod.GET)
    public JSONObject getTemplate(){
        return wechatService.getTemplate(accesstoken);
    }
    /** 
     *@Author Zr
     *@Description  获取微信用户基本信息
     *@Param []
     *@return java.lang.String
     **/
    @RequestMapping("/getUserWechatInfo")
    public String getUserWechatInfo(){
        return null;
    }
    
    /** 
     *@Author Zr
     *@Description  重新生成token
     *@Param []
     *@return java.lang.String
     **/
    @RequestMapping("/againToken")
    public String getAgainToken(){
        accesstoken = getAccessToken();
        return  accesstoken;
    }
    /** 
     *@Author Zr
     *@Description  创建菜单
     *@Param []
     *@return com.alibaba.fastjson.JSONObject
     **/
    @RequestMapping("/click")
    public JSONObject click(){
        return createMenu();
    }

    /**
     * 删除菜单
     * @return
     */
    @RequestMapping("/deleteMenu")
    public String deleteMenu(){
        return deleteMenuMethod();
    }
    /**
     * 添加菜单
     * @return
     */
    public JSONObject createMenu(){

        ClickButton cbt=new ClickButton();
        cbt.setKey("image");
        cbt.setName("时尚新闻");
        cbt.setType("click");
        ClickButton cbt2=new ClickButton();
        cbt2.setKey("image");
        cbt2.setName("历史新闻");
        cbt2.setType("click");

        ViewButton vbt=new ViewButton();
        vbt.setUrl("http://www.baidu.com");
        vbt.setName("百度");
        vbt.setType("view");

        ViewButton vbt2=new ViewButton();
        vbt2.setUrl("http://www.pandaTv.com");
        vbt2.setName("panda");
        vbt2.setType("view");
        JSONArray sub_button=new JSONArray();
        sub_button.add(vbt);
        sub_button.add(vbt2);


        JSONObject buttonOne=new JSONObject();
        buttonOne.put("name", "菜单");
        buttonOne.put("sub_button", sub_button);

        JSONArray button=new JSONArray();
        button.add(buttonOne);
        button.add(cbt);
        button.add(cbt2);

        JSONObject menujson=new JSONObject();
        menujson.put("button", button);
        System.out.println(menujson);
        //这里为请求接口的url +号后面的是token，这里就不做过多对token获取的方法解释
        JSONObject rs = null;
        try{
            rs = HttpClient.doPostStr("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" +accesstoken, menujson.toString());
        }catch(Exception e){
            System.out.println("请求错误！");
        }
        return rs;
    }

    /**
     * 删除菜单
     * @return
     */
    public String deleteMenuMethod(){
        HashMap<String,String> hashMapParam = new HashMap<>();
        hashMapParam.put("access_token",accesstoken);
        String rs = null;
        try{
             rs = HttpClient.get("https://api.weixin.qq.com/cgi-bin/menu/delete" ,hashMapParam,null);
             logger.info("删除菜单返回 ： "+rs);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("请求错误！");
        }
        return rs;
    }
    /** 
     *@Author Zr
     *@Description  获取公众号的全局唯一接口调用凭据 --- token
     *@Param []
     *@return java.lang.String
     **/
    public String getAccessToken(){
        try {
            Map<String,String> mapParam = new HashMap<>();
            mapParam.put("grant_type","client_credential");
            mapParam.put("appid", Constant.appId);
            mapParam.put("secret",Constant.secretId);
            String tokenStr = HttpClient.get("https://api.weixin.qq.com/cgi-bin/token",mapParam,null);
            JSONObject jsonObject = JSONObject.parseObject(tokenStr);
            String token = jsonObject.getString("access_token");
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



}
