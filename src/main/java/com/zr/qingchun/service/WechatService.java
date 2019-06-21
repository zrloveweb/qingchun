package com.zr.qingchun.service;

import com.alibaba.fastjson.JSONObject;
import com.zr.qingchun.common.Constant;
import com.zr.qingchun.common.HttpClient;
import com.zr.qingchun.common.RestTemplateRequest;
import com.zr.qingchun.common.wechat.WechatToken;
import com.zr.qingchun.util.WechatParseXmlUtil;
import com.zr.qingchun.wechatEntity.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @ClassName WechatService
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/9/21 0021 17:02
 * Version 1.0
 **/

@Service
public class WechatService {

    private final static Logger log = LoggerFactory.getLogger(WechatService.class);

    @Autowired
    private RestTemplateRequest restTemplateRequest;

    /**
     * @return java.lang.String
     * @Author Zr
     * @Description 接收消息，并回复
     * @Param [request]
     **/
    public String processRequest(HttpServletRequest request) {
        Map<String, String> map = null;
        try {
            map = WechatParseXmlUtil.parseXml(request);
        } catch (Exception e) {
        }
        log.info(map.toString());
        // 发送方帐号（一个OpenID）
        String fromUserName = map.get("FromUserName");
        // 开发者微信号
        String toUserName = map.get("ToUserName");
        // 消息类型
        String msgType = map.get("MsgType");
        // 默认回复一个"success"
        String responseMessage = "success";
        // 对消息进行处理
        /// 文本消息
        if (WechatParseXmlUtil.MESSAGE_TEXT.equals(msgType)) {
            TextMessage textMessage = new TextMessage();
            textMessage.setMsgType(WechatParseXmlUtil.MESSAGE_TEXT);
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setContent("我已经受到你发来的消息了");
            responseMessage = WechatParseXmlUtil.textMessageToXml(textMessage);
        }
        log.info("reviced msg : " + responseMessage);
        return responseMessage;

    }

    /**
     * @return java.lang.String
     * @Author Zr
     * @Description 创建行业模板     *@Param []
     */
    public JSONObject setIndustry() {
        String industryStr = "{\n" +
                "          \"industry_id1\":\"1\",\n" +
                "          \"industry_id2\":\"4\"\n" +
                "       }";
        JSONObject resultJson = HttpClient.doPostStr("https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + WechatToken.getToken(), industryStr);
        log.info("创建行业模板： " + resultJson);
        return resultJson;
    }


    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Author Zr
     * @Description 获取行业信息
     * @Param [accessToken]
     **/
    public JSONObject getIndustry() {
        //restTemplate 请求方式
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + WechatToken.getToken();
        ResponseEntity responseEntity = restTemplateRequest.restTemplateGet(requestUrl, String.class);
        log.info("reponse body value :{}", responseEntity.getBody());
        JSONObject resultIndustry = JSONObject.parseObject(responseEntity.getBody().toString());

        return resultIndustry;
    }

    /**
     * @return com.alibaba.fastjson.JSONObject
     * @Author Zr
     * @Description 发送模板
     * @Param [accessToken]
     **/
    public JSONObject sendTemplate() {
        String templateJson="{\n"+
                "\"touser\":\"oDRzd5hG0otYeijHNlsI2-x-M_gw\",\n"+
                "\"template_id\":\"yCqLyegNqX38kxdjdDf2nxocXvI_x486HPnQkOpBvfM\",\n"+
                "\"url\":\"\",\n"+
                "\"miniprogram\":{\n"+
                "\"appid\":\"\",\n"+
                "\"pagepath\":\"\"\n"+
                "},\n"+
                "\"data\":{\n"+
                "\"first\":{\n"+
                "\"value\":\"恭喜你购买成功！\",\n"+
                "\"color\":\"#173177\"\n"+
                "},\n"+
                "\"name\":{\n"+
                "\"value\":\"tom\",\n"+
                "\"color\":\"red\"\n"+
                "},\n"+
                "\"sex\":{\n"+
                "\"DATA\":\"北京\",\n"+
                "\"color\":\"red\"\n"+
                "},\n"+
                "\"demo\":{\n"+
                "\"value\":\"2014年9月22日\",\n"+
                "\"color\":\"#173177\"\n"+
                "},\n"+
                "\"remark\":{\n"+
                "\"value\":\"欢迎再次购买！\",\n"+
                "\"color\":\"#173177\"\n"+
                "}\n"+
                "}\n"+
                "}";
        JSONObject jsonObject = JSONObject.parseObject(templateJson);
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + WechatToken.getToken();
        JSONObject resultJson = restTemplateRequest.restTemplatePost(requestUrl, jsonObject);
        return resultJson;
    }


    /**
     * 获得模板id
     *
     * @return
     */
    public JSONObject getTemplateId() {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/api_add_template?access_token=" + WechatToken.getToken();
        JSONObject data = new JSONObject();
        data.put("template_id_short", "TM00015");
        JSONObject jsonObject = restTemplateRequest.restTemplatePost(requestUrl, data);

        //{"errcode":0,"errmsg":"ok","template_id":"mg709hI7BT8EFTW5XZqVnCAThEHsQ6U2j-aLl9x_RhU"}
        log.info("reponse body value :{}", jsonObject);
        return jsonObject;
    }

    /**
     * 获取模板列表
     *
     * @return
     */
    public JSONObject getTemplateList() {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + WechatToken.getToken();
        ResponseEntity responseEntity = restTemplateRequest.restTemplateGet(requestUrl, String.class);
        JSONObject jsonObject = JSONObject.parseObject(responseEntity.getBody().toString());
        return jsonObject;
    }


    /**
     * 通过模板id 删除模板
     *
     * @return
     */
    public JSONObject deleteTemplate(String templateId) {
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/template/del_private_template?access_token=" + WechatToken.getToken();
        JSONObject data = new JSONObject();
        data.put("template_id", templateId);
        JSONObject resultJson = restTemplateRequest.restTemplatePost(requestUrl, data);
        return resultJson;
    }
}
