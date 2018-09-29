package com.zr.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
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

}
