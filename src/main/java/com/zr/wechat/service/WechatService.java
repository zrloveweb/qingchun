package com.zr.wechat.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zr.wechat.common.HttpClient;
import com.zr.wechat.util.WechatParseXmlUtil;
import com.zr.wechat.wechatEntity.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    
    /** 
     *@Author Zr
     *@Description  接收消息，并回复
     *@Param [request]
     *@return java.lang.String
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
        if (WechatParseXmlUtil.MESSAGE_TEXT.equals(msgType)) {// 文本消息
            TextMessage textMessage = new TextMessage();
            textMessage.setMsgType(WechatParseXmlUtil.MESSAGE_TEXT);
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(System.currentTimeMillis());
            textMessage.setContent("我已经受到你发来的消息了");
            responseMessage = WechatParseXmlUtil.textMessageToXml(textMessage);
        }
        log.info(responseMessage);
        return responseMessage;

    }
    
    /** 
     *@Author Zr
     *@Description  创建行业模板     *@Param []
     *@return java.lang.String
     *
     * @param accessToken*/
    public JSONObject createTemplate(String accessToken){
        String templateStr  ="{\n" +
                "          \"industry_id1\":\"1\",\n" +
                "          \"industry_id2\":\"4\"\n" +
                "       }";
        JSONObject resultJson = HttpClient.doPostStr("https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+accessToken,templateStr);
        log.info("创建行业模板： " + resultJson);
        return resultJson;
    }

    /** 
     *@Author Zr
     *@Description  获取行业模板
     *@Param [accessToken]
     *@return com.alibaba.fastjson.JSONObject
     **/
    public JSONObject getTemplate(String accessToken) {
        JSONObject resultJson = HttpClient.doPostStr("https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token="+accessToken,"");
        log.info("创建行业模板： " + resultJson);
        JSONArray jsonArray = resultJson.getJSONArray("template_list");
        JSONObject jsonTemplate = jsonArray.getJSONObject(0);
        jsonTemplate.getString("template_id");
        return resultJson;
    }

    /**
     *@Author Zr
     *@Description  发送模板
     *@Param [accessToken]
     *@return com.alibaba.fastjson.JSONObject
     **/
    public JSONObject sendTemplate(String accessToken) {
        String templateStr = "   {\n" +
                "           \"touser\":\"OPENID\",\n" +
                "           \"template_id\":\"ngqIpbwh8bUfcSsECmogfXcV14J0tQlEpBO27izEYtY\",\n" +
                "           \"url\":\"http://weixin.qq.com/download\",  \n" +
                "           \"miniprogram\":{\n" +
                "             \"appid\":\"xiaochengxuappid12345\",\n" +
                "             \"pagepath\":\"index?foo=bar\"\n" +
                "           },          \n" +
                "           \"data\":{\n" +
                "                   \"first\": {\n" +
                "                       \"value\":\"恭喜你购买成功！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword1\":{\n" +
                "                       \"value\":\"巧克力\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword2\": {\n" +
                "                       \"value\":\"39.8元\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"keyword3\": {\n" +
                "                       \"value\":\"2014年9月22日\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"remark\":{\n" +
                "                       \"value\":\"欢迎再次购买！\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        JSONObject resultJson = HttpClient.doPostStr("https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=?access_token="+accessToken,"");
        log.info("创建行业模板： " + resultJson);
        JSONArray jsonArray = resultJson.getJSONArray("template_list");
        JSONObject jsonTemplate = jsonArray.getJSONObject(0);
        jsonTemplate.getString("template_id");
        return resultJson;
    }
}
