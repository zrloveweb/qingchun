package com.zr.qingchun.service;

import com.alibaba.fastjson.JSONObject;
import com.github.wxpay.sdk.WXPayUtil;
import com.google.zxing.WriterException;
import com.zr.qingchun.common.HttpClient;
import com.zr.qingchun.common.RestTemplateRequest;
import com.zr.qingchun.common.ResultDto;
import com.zr.qingchun.common.wechat.WechatToken;
import com.zr.qingchun.util.FileToBase64;
import com.zr.qingchun.util.QrcodeUtil;
import com.zr.qingchun.util.WechatParseXmlUtil;
import com.zr.qingchun.wechatEntity.TextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

import static com.github.wxpay.sdk.WXPayUtil.generateNonceStr;
import static com.github.wxpay.sdk.WXPayUtil.mapToXml;

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
     * 微信appid
     */
    @Value("${wx.appid}")
    private String appid;

    /**
     * 微信商户号
     */
    @Value("${wx.mch_id}")
    private String mchId;

    /**
     * 微信异步回调地址
     */
    @Value("${wx.notify_url}")
    private String notifyUrl;


    /**
     * 获取行业信息
     */
    @Value("${wxapi.getInsustryUrl}")
    private String getInsustryUrl;

    /**
     * native支付统一下单接口
     */
    @Value("${wxapi.unifieorderUrl}")
    private String unifiedorderUrl;

    /**
     * 发送模板信息
     */
    @Value("${wxapi.sendTemplateUrl}")
    private String sendTemplateUrl;

    /**
     * 创建行业模板
     */
    @Value("${wxapi.apiSetIndustryUrl}")
    private String apiSetIndustryUrl;

    /**
     * 获得模板id
     */
    @Value("${wxapi.apiAddTemplateUrl}")
    private String apiAddTemplateUrl;

    /**
     * 通过模板id 删除模板
     */
    @Value("${wxapi.delPrivateTemplateUrl}")
    private String delPrivateTemplateUrl;

    /**
     * 获取模板列表
     */
    @Value("${wxapi.getAllPrivateTemplateUrl}")
    private String getAllPrivateTemplateUrl;


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
        JSONObject resultJson = HttpClient.doPostStr(apiSetIndustryUrl + "?access_token=" + WechatToken.getToken(), industryStr);
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
        String requestUrl = getInsustryUrl + "?access_token=" + WechatToken.getToken();
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
    public JSONObject sendTemplate(String template) {
        /**
         * 正式开发需要把字符串 按照数据类型定义成类
         */
        String templateJson = "\n" +
                "\n" +
                "\n" +
                "                 {\n" +
                "           \"touser\":\"oDRzd5hG0otYeijHNlsI2-x-M_gw\",\n" +
                "           \"template_id\":\"" + template +
                "\",\n" +
                "           \"url\":\"\",  \n" +
                "           \"miniprogram\":{\n" +
                "             \"appid\":\"\",\n" +
                "             \"pagepath\":\"\"\n" +
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
                "                   },\n" +
                "                   \"sex\":{\n" +
                "                       \"value\":\"男\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   },\n" +
                "                   \"name\":{\n" +
                "                       \"value\":\"赵日天\",\n" +
                "                       \"color\":\"#173177\"\n" +
                "                   }\n" +
                "           }\n" +
                "       }";
        JSONObject jsonObject = JSONObject.parseObject(templateJson);
        String requestUrl = sendTemplateUrl + "?access_token=" + WechatToken.getToken();
        JSONObject resultJson = restTemplateRequest.restTemplatePost(requestUrl, jsonObject);
        return resultJson;
    }


    /**
     * 获得模板id
     *
     * @return
     */
    public JSONObject getTemplateId() {
        String requestUrl = apiAddTemplateUrl + "?access_token=" + WechatToken.getToken();
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
        String requestUrl = getAllPrivateTemplateUrl + "?access_token=" + WechatToken.getToken();
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
        String requestUrl = delPrivateTemplateUrl + "?access_token=" + WechatToken.getToken();
        JSONObject data = new JSONObject();
        data.put("template_id", templateId);
        JSONObject resultJson = restTemplateRequest.restTemplatePost(requestUrl, data);
        return resultJson;
    }


    /**
     * 支付二维码
     *
     * @return
     */
    public ResultDto<String> getPayQrcode() {
        ResultDto<String> resultDto = new ResultDto<>();
        resultDto.success();
        Map<String, String> paramMap = new HashMap<>();
        //交易类型
        paramMap.put("trade_type", "NATIVE");
        //本机的Ip
        paramMap.put("spbill_create_ip", "127.0.0.1");
        // 商户根据自己业务传递的参数 必填
        paramMap.put("product_id", "2019062414060001");
        //描述
        paramMap.put("body", "打火机");
        //商户 后台的贸易单号
        paramMap.put("out_trade_no", "1000012019062400001");
        //金额必须为整数  单位为分
        paramMap.put("total_fee", "" + 3000);
        //支付成功后，回调地址
        paramMap.put("notify_url",notifyUrl);
        //appid
        paramMap.put("appid", appid);
        //商户号
        paramMap.put("mch_id", mchId);
        //随机数
        paramMap.put("nonce_str", generateNonceStr());
        //把参数转换成XML数据格式
        String xmlData = null;
        try {
            //根据微信签名规则，生成签名
            paramMap.put("sign", WXPayUtil.generateSignature(paramMap, "74a4ce90585b44349b562485ee98b832"));
            xmlData = mapToXml(paramMap);
        } catch (Exception e) {
            e.printStackTrace();
        }

        log.info("request wx order xmlData: {}", xmlData);
        System.out.println(xmlData);

        Map<String, String> resultMap = HttpClient.doPostMap(unifiedorderUrl, xmlData);
        log.info("request wx order response data: {}", resultMap);

        try {
            OutputStream os = new FileOutputStream(new File("E:\\weixn.png"));
            log.info("code_url:{}", resultMap.get("code_url"));
            QrcodeUtil.createQrCode(os, resultMap.get("code_url"), 800, "png");


            FileInputStream fileInputStream = new FileInputStream(new File("E:\\weixn.png"));
            String base64FromInputStream = FileToBase64.getBase64FromInputStream(fileInputStream);
            log.info("payBase64 img :{}", base64FromInputStream);
            resultDto.setData(base64FromInputStream);
            return resultDto;
        } catch (WriterException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
