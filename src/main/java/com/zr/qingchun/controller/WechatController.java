package com.zr.qingchun.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zr.qingchun.common.Constant;
import com.zr.qingchun.common.HttpClient;
import com.zr.qingchun.common.wechat.WechatToken;
import com.zr.qingchun.service.WechatService;
import com.zr.qingchun.util.CheckUtil;
import com.zr.qingchun.util.WechatParseXmlUtil;
import com.zr.qingchun.wechatEntity.ClickButton;
import com.zr.qingchun.wechatEntity.ViewButton;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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


    @RequestMapping(value = "wechat", method = RequestMethod.GET)
    public String wechatGet(HttpServletRequest req) {
        try {
            Map<String, String> openId = WechatParseXmlUtil.parseXml(req);
            logger.info("openId: {}", openId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Map<String, String> map = WechatParseXmlUtil.parseXml(req);
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
        String accesstoken = WechatToken.getToken();
        logger.info("token:" + accesstoken);
        // 请求校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (CheckUtil.checkSignature(signature, timestamp, nonce)) {
            return echostr;
        }
        return null;
    }


    /**
     * @return java.lang.String
     * @Author Zr
     * @Description wechat Post请求
     * @Param [req]
     **/
    @RequestMapping(value = "/wechat", method = RequestMethod.POST)
    public String wechatPost(HttpServletRequest req) {
        String result = wechatService.processRequest(req);
        logger.info("wechat post wechat : {}", result);
        return result;
    }

    /**
     * @return JSONObject
     * @Author Zr
     * @Description 设置所属行业
     * @Param []
     **/
    @RequestMapping(value = "/setIndustry", method = RequestMethod.GET)
    public JSONObject setIndustry() {
        return wechatService.setIndustry();
    }

    /**
     * @return JSONObject
     * @Author Zr
     * @Description 获取行业信息
     * @Param []
     **/
    @RequestMapping(value = "/getIndustry", method = RequestMethod.GET)
    public JSONObject getIndustry() {
        return wechatService.getIndustry();
    }

    /**
     * @return JSONObject
     * @Author Zr
     * @Description 获取模板id
     * @Param []
     **/
    @RequestMapping(value = "/getTemplateId", method = RequestMethod.GET)
    public JSONObject getTemplateId() {
        return wechatService.getTemplateId();
    }


    /**
     * @return JSONObject
     * @Author Zr
     * @Description 获取模板列表
     * @Param []
     **/
    @RequestMapping(value = "/getTemplateList", method = RequestMethod.GET)
    public JSONObject getTemplateList() {
        return wechatService.getTemplateList();
    }

    /**
     * @return JSONObject
     * @Author Zr
     * @Description  通过模板id 删除模板
     * @Param []
     **/
    @RequestMapping(value = "/deleteTemplate", method = RequestMethod.GET)
    public JSONObject deleteTemplate(@RequestParam("templateId") String templateId) {
        return wechatService.deleteTemplate(templateId);
    }


    /**
     * @return JSONObject
     * @Author Zr
     * @Description  发送模板消息
     * @Param []
     **/
    @RequestMapping(value = "/sendTemplate", method = RequestMethod.GET)
    public JSONObject sendTemplate(String template) {
        return wechatService.sendTemplate(template);
    }


    /**
     * @return java.lang.String
     * @Author Zr
     * @Description 获取微信用户基本信息
     * @Param []
     **/
    @RequestMapping("/getUserWechatInfo")
    public String getUserWechatInfo() {
        return null;
    }


    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Author Zr
     * @Description 创建菜单
     * @Param []
     **/
    @RequestMapping("/click")
    public JSONObject click() {
        return createMenu();
    }

    /**
     * 删除菜单
     *
     * @return
     */
    @RequestMapping("/deleteMenu")
    public String deleteMenu() {
        return deleteMenuMethod();
    }

    /**
     * 添加菜单
     *
     * @return
     */
    public JSONObject createMenu() {

        ClickButton cbt = new ClickButton();
        cbt.setKey("image");
        cbt.setName("时尚新闻");
        cbt.setType("click");
        ClickButton cbt2 = new ClickButton();
        cbt2.setKey("image");
        cbt2.setName("历史新闻");
        cbt2.setType("click");

        ViewButton vbt = new ViewButton();
        vbt.setUrl("http://www.baidu.com");
        vbt.setName("百度");
        vbt.setType("view");

        ViewButton vbt2 = new ViewButton();
        vbt2.setUrl("http://www.pandaTv.com");
        vbt2.setName("panda");
        vbt2.setType("view");
        JSONArray sub_button = new JSONArray();
        sub_button.add(vbt);
        sub_button.add(vbt2);


        JSONObject buttonOne = new JSONObject();
        buttonOne.put("name", "菜单");
        buttonOne.put("sub_button", sub_button);

        JSONArray button = new JSONArray();
        button.add(buttonOne);
        button.add(cbt);
        button.add(cbt2);

        JSONObject menujson = new JSONObject();
        menujson.put("button", button);
        System.out.println(menujson);
        //这里为请求接口的url +号后面的是token，这里就不做过多对token获取的方法解释
        JSONObject rs = null;
        try {
            rs = HttpClient.doPostStr("https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + WechatToken.getToken(), menujson.toString());
        } catch (Exception e) {
            System.out.println("请求错误！");
        }
        return rs;
    }


    /**
     * 删除菜单
     *
     * @return
     */
    public String deleteMenuMethod() {
        HashMap<String, String> hashMapParam = new HashMap<>(4);
        hashMapParam.put("access_token", WechatToken.getToken());
        String rs = null;
        try {
            rs = HttpClient.get("https://api.weixin.qq.com/cgi-bin/menu/delete", hashMapParam, null);
            logger.info("删除菜单返回 ： " + rs);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("请求错误！");
        }
        return rs;
    }


}
