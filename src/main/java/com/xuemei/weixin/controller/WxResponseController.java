package com.xuemei.weixin.controller;

import com.xuemei.weixin.utils.MessageUtils;
import com.xuemei.weixin.utils.SignUtils;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;

/**
 * @Title: com.xuemei.weixin.controller.WxResponseController
 * @Package com.xuemei.weixin.controller
 * @Description:
 * @author: 薛梅
 * @date: 2017/11/30 11:52
 */
@RestController
@RequestMapping("wx")
public class WxResponseController {

    public static WxMpInMemoryConfigStorage config;


    public void init(){

    }

    /**
     * 接收微信服务器发送的4个参数并返回echostr
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "wxCheck")
    public void wxService(HttpServletRequest request, HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/xml;charset=utf-8");
        // 微信加密签名
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串
        String echostr = request.getParameter("echostr");

        PrintWriter out = response.getWriter();

        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (!SignUtils.checkSignature(signature, timestamp, nonce)) {
            return ;
        }

        if(echostr != null){
            out.print(echostr);
            return;
        }

        try {
            Map<String, String> map = MessageUtils.xmlToMap(request);
            String toUserName = map.get("ToUserName");
            String fromUserName = map.get("FromUserName");
            String msgType = map.get("MsgType");
            String content = map.get("Content");

            // 对文本消息进行处理
            if ("text".equals(msgType)) {
                StringBuffer sb = new StringBuffer();
                Date date = new Date();
                sb.append("<xml><ToUserName><![CDATA[");
                sb.append(fromUserName);
                sb.append("]]></ToUserName><FromUserName><![CDATA[");
                sb.append(toUserName);
                sb.append("]]></FromUserName><CreateTime>");
                sb.append(date.getTime());
                sb.append("</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[");
                sb.append(content);
                sb.append("]]></Content><FuncFlag>0</FuncFlag></xml>");
                System.out.println(sb);
                out.write(sb.toString());
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally {
            out.flush();
            out.close();
        }
    }
}