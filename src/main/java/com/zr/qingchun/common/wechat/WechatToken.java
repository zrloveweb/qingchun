package com.zr.qingchun.common.wechat;

import com.alibaba.fastjson.JSONObject;
import com.zr.qingchun.common.Constant;
import com.zr.qingchun.common.HttpClient;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: qingchun
 * @description: 微信token类
 * @author: zhaoRui
 * @create: 2019-06-21 11:36
 **/

public class WechatToken {

    /**
     * 微信token map
     */
    private static Map<String, String> tokenMap = new HashMap<>();

    /**
     * 获取微信token
     *
     * @return
     */
    public static String getToken() {
        String token = tokenMap.get(Constant.accesstoken);
        if (StringUtils.isEmpty(token)) {
            tokenMap.put(Constant.accesstoken, getAccessToken());
            token = tokenMap.get(Constant.accesstoken);
        }
        return token;
    }


    /**
     * 设置微信token
     *
     * @param accesstoken
     */
    public static void setToken(String accesstoken) {
        tokenMap.put(Constant.accesstoken, accesstoken);
    }

    /**
     * @return java.lang.String
     * @Author Zr
     * @Description 获取公众号的全局唯一接口调用凭据 --- token
     * @Param []
     **/
    private static String getAccessToken() {
        try {
            Map<String, String> mapParam = new HashMap<>();
            mapParam.put("grant_type", "client_credential");
            mapParam.put("appid", Constant.appId);
            mapParam.put("secret", Constant.secretId);
            String tokenStr = HttpClient.get("https://api.weixin.qq.com/cgi-bin/token", mapParam, null);
            JSONObject jsonObject = JSONObject.parseObject(tokenStr);
            String token = jsonObject.getString("access_token");
            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
