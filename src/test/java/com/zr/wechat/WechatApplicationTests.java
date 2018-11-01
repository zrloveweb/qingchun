package com.zr.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.SignatureException;
import java.util.Date;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class WechatApplicationTests {
		//testssssssssssssssssssssss
    @Test
    public void contextLoads() {
        String str = "{\n" +
                "\t\"template_list\": [{\n" +
                "\t\t\"template_id\": \"iPk5sOIt5X_flOVKn5GrTFpncEYTojx6ddbt8WYoV5s\",\n" +
                "\t\t\"title\": \"领取奖金提醒\",\n" +
                "\t\t\"primary_industry\": \"IT科技\",\n" +
                "\t\t\"deputy_industry\": \"互联网|电子商务\",\n" +
                "\t\t\"content\": \"{ {result.DATA} }\\n\\n领奖金额:{ {withdrawMoney.DATA} }\\n领奖  时间:{ {withdrawTime.DATA} }\\n银行信息:{ {cardInfo.DATA} }\\n到账时间:  { {arrivedTime.DATA} }\\n{ {remark.DATA} }\",\n" +
                "\t\t\"example\": \"您已提交领奖申请\\n\\n领奖金额：xxxx元\\n领奖时间：2013-10-10 12:22:22\\n银行信息：xx银行(尾号xxxx)\\n到账时间：预计xxxxxxx\\n\\n预计将于xxxx到达您的银行卡\"\n" +
                "\t}]\n" +
                "}";
        JSONObject str2 = JSONObject.parseObject(str);
        JSONArray jsonArray = str2.getJSONArray("template_list");
        System.out.println(jsonArray.getJSONObject(0));
    }

    private static final String PRIVATE_KEY = "123456789";

    @Test
    public void jwtTest(){
        // 设置3秒后过期
        String jwt = this.buildJwt(DateTime.now().plusSeconds(5).toDate());
        System.out.println(jwt);
        // 验证token是否可用
        boolean isOk = this.isJwtValid(jwt);
        System.out.println(isOk);

    }

    public String buildJwt(Date exp) {
        String jwt = Jwts.builder()
                .signWith(SignatureAlgorithm.HS256, PRIVATE_KEY)//SECRET_KEY是加密算法对应的密钥，这里使用额是HS256加密算法
                .setExpiration(exp)//expTime是过期时间
                .claim("name", "小莉")//该方法是在JWT中加入值为vaule的key字段
                .claim("age", "18")//该方法是在JWT中加入值为vaule的key字段
                .claim("sex", "女")//该方法是在JWT中加入值为vaule的key字段
                .compact();
        return jwt;
    }

    public boolean isJwtValid(String jwt) {
        try {
            //解析JWT字符串中的数据，并进行最基础的验证
            Claims claims = Jwts.parser()
                    .setSigningKey(PRIVATE_KEY)//SECRET_KEY是加密算法对应的密钥，jjwt可以自动判断机密算法
                    .parseClaimsJws(jwt)//jwt是JWT字符串
                    .getBody();
            String vaule = claims.get("name", String.class);//获取自定义字段key
            //判断自定义字段是否正确
            if ("小莉".equals(vaule)) {
                return true;
            } else {
                return false;
            }
        }
        //在解析JWT字符串时，如果密钥不正确，将会解析失败，抛出SignatureException异常，说明该JWT字符串是伪造的
        //在解析JWT字符串时，如果‘过期时间字段’已经早于当前时间，将会抛出ExpiredJwtException异常，说明本次请求已经失效
        catch (ExpiredJwtException e) {
            return false;
        }
    }



}
