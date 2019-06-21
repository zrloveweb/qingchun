package com.zr.qingchun.common;

import com.alibaba.fastjson.JSONObject;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

/**
 * @program: qingchun
 * @description: 使用restTemplate请求数据
 * @author: zhaoRui
 * @create: 2019-06-21 13:41
 **/

@Component
public class RestTemplateRequest {
    private static RestTemplate rs;

    static {
        rs = new RestTemplate();
    }


    /**
     * get请求
     *
     * @param requestUrl
     * @param type       class
     * @return
     */
    public  ResponseEntity restTemplateGet(String requestUrl, Class type) {
        rs.getMessageConverters().set(1, new StringHttpMessageConverter(StandardCharsets.UTF_8));
        ResponseEntity<String> forEntity = rs.getForEntity(requestUrl, type);
        return forEntity;
    }


    /**
     * post请求
     *
     * @param requestUrl
     * @param data       请求数据
     * @return
     */
    public  JSONObject restTemplatePost(String requestUrl, JSONObject data) {
        RestTemplate restTemplate = new RestTemplate();
        String body = restTemplate.postForEntity(requestUrl, data, String.class).getBody();
        return JSONObject.parseObject(body);
    }


}
