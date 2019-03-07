package com.zr.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zr.qingchun.model.User;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.SignatureException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

/*@RunWith(SpringRunner.class)
@SpringBootTest*/
public class WechatApplicationTests {
    @Test
    public void jsonTest(){
        //调用规则引擎返回数据打桩
        Integer maxSignNum = 7;//最大签到次数
        int [] continuitySignIntergral = {10,10,10,20,10,10,100};//连续签到每天奖励琴豆数
        //搜索引擎返回的数据放入json
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("maxSignNum",maxSignNum);
        jsonObject.put("continuitySignIntergral",continuitySignIntergral);
        JSONArray jsonArray = jsonObject.getJSONArray("continuitySignIntergral");
        System.out.println(jsonArray);
       jsonArray.remove(3);
       jsonArray.remove(4L);
        System.out.println(jsonArray);
    }
    public static void main(String[] args) {
      int a = 1;
        System.out.println(a+1);
        System.out.println(a);
    }
    @Test
    public void test04(){
        int maxSignNum = 7;
        int continuePeriodSignNum = 4;
        int signNoneNum  = maxSignNum-continuePeriodSignNum;
        for(int j = 1; j <= continuePeriodSignNum ; j++){
            //当前天数减去 j 推算出已经签到天数

            System.out.println(format(addDate(new Date(),-j),"yyyy.MM.dd"));;
        }

        for( int k=1; k < signNoneNum; k++){
            //当前天数加  k 推算出已经签到天数
             System.out.println(format(addDate(new Date(),k),"yyyy.MM.dd"));
        }

    }

    @Test
    public void listFor() throws ParseException {
        List<User> userList = new ArrayList<>();
        User user1 = new User();
        user1.setName("小明");

        User user2 = new User();
        user2.setName("小名");

        User user3 = new User();
        user3.setName("小明");
        userList.add(user1);
        userList.add(user2);
        userList.add(user3);
        for(User user : userList){
            user.setName("小红");
        }

        System.out.println(userList);
        for(User user : userList){
            System.out.println( user.getName());;
        }
        //日期转为月、日
        String str ="2018.11.09";
        System.out.println("截取"+str.substring(5,str.length()));;

        String sr = formatDateStr("MM.dd","2018.11.9");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM.dd");
       Date d =  simpleDateFormat.parse("2017.04.09");
       String ss = simpleDateFormat.format(d);
        System.out.printf("更改："+ss);
        System.out.println(sr);

        System.out.println(Long.parseLong("19164"));
    }
    @Test
    public void intSum(){
        int i = 1;
        System.out.println(i++);
        System.out.println(++i);
    }
    public static String formatDateStr(String patern, String dateStr) {
        String result = "";
        try {
            Date d = new SimpleDateFormat(patern).parse(dateStr);
            result = new SimpleDateFormat(patern).format(d);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return result;

    }
    @Test
    public void bewttenDay() throws ParseException {
        String str = "20181101";
        String str2 = "20181101";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date beginDate = simpleDateFormat.parse(str);
        Date endDate = simpleDateFormat.parse(str2);;
            long days = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);
        System.out.println(days);

    }
    public static String dateToStr(Date date, String format) {
        if (date == null)
            return null;
        return new SimpleDateFormat(format).format(date);
    }
    @Test
    public void test02(){
        Calendar c = Calendar.getInstance();
        c.setTime(new Date());
        c.add(Calendar.DATE, 30);

        System.out.println(format(c.getTime(),"yyyy.mm.dd"));
    }

    @Test
    public void testTimeStamp() throws ParseException {
        Date date1 = parseDate(format(new Date(),"yyyyMMdd"),"yyyyMMdd");
        Date date2 = parseDate("201811011","yyyyMMdd");
        System.out.println("date1: " + date1.getTime());
        System.out.println("date2: " + date2.getTime());
        System.out.println((date2.getTime() - date1.getTime())/1000/60/60/24);
    }

    @Test
    public void test03(){
        //3
        for(int i = 1; i < 3 ; i++){
            System.out.println(format(addDate(new Date(),-i),"yyyy.MM.dd"));;
        }
        for(int i = 1; i <= 4 ; i++){
            System.out.println(format(addDate(new Date(),i),"yyyy.MM.dd"));;
        }

        System.out.println("----------------");
        //3
        for(int i = 1; i <=0 ; i++){
            System.out.println(format(addDate(new Date(),-i),"yyyy.MM.dd"));;
        }
        for(int i = 1; i < 7 ; i++){
            System.out.println(format(addDate(new Date(),i),"yyyy.MM.dd"));;
        }
    }

    /**
     * 日期加减
     * @param date
     * @param days
     * @return
     */
    public static Date addDate(Date date, int days)
    {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return c.getTime();
    }
    public static String dateStr(Date param) throws ParseException {
        if (param == null)
            return null;
        return new SimpleDateFormat("yyyyMMdd").format(param);
    }
    public static String format(Date date, String patern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sDateFormat = new SimpleDateFormat(patern);
        return sDateFormat.format(date);
    }
    public static Date parseDate(String param,String format) throws ParseException {
        if (param == null) {
            return null;
        }
        return new SimpleDateFormat(format).parse(param);
    }
    @Test
    public void test2(){
      String jsonStr = "{\n" +
              "\t\"code\": \"200\",\n" +
              "\t\"data\": {\n" +
              "\t\t\"creator\": \"admin\",\n" +
              "\t\t\"gmtCreated\": 1541067018000,\n" +
              "\t\t\"gmtModified\": 1541067018000,\n" +
              "\t\t\"id\": 1,\n" +
              "\t\t\"isDeleted\": \"N\",\n" +
              "\t\t\"modifier\": \"admin\",\n" +
              "\t\t\"ruleData\": {\n" +
              "\t\t\t\"maxSignNum\": 7,\n" +
              "\t\t\t\"continuitySignIntergral\": [10, 10, 10, 10, 10, 10, 100]\n" +
              "\t\t},\n" +
              "\t\t\"signInBeginDate\": 1541067018000,\n" +
              "\t\t\"signInEndDate\": 1541067018000\n" +
              "\t},\n" +
              "\t\"message\": \"ok\",\n" +
              "\t\"success\": true\n" +
              "}";
      JSONObject jsonObject = JSONObject.parseObject(jsonStr);
      JSONObject jsonObject1 = jsonObject.getJSONObject("data");
        JSONObject jsonObject2 = jsonObject1.getJSONObject("ruleData");
        System.out.println(jsonObject2.getIntValue("maxSignNum"));
        System.out.println(jsonStr);
    }
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
    public void testJdkNewLabel(){

        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);
        list.add(5);
        list.add(6);
        List<Integer> collect = list.stream().map(data -> {
            if (data > 3) {
                return data;
            } else {
                return null;
            }
        }).collect(Collectors.toList());
        System.out.println(collect);


        collect.stream().forEach(data -> {
            System.out.println(data);
        });

    }

    @Test
    public void listGroup(){
        List<User> list = Lists.newArrayList();
        User user = new User();
        user.setId(1);
        user.setName("lili");
        user.setPsw("00000");

        User user2 = new User();
        user2.setId(2);
        user2.setName("lil2i");
        user2.setPsw("00000");

        User user3 = new User();
        user3.setId(3);
        user3.setName("lili");
        user3.setPsw("00000");

        User user4 = new User();
        user4.setId(4);
        user4.setName("lil2i");
        user4.setPsw("00000");

        list.add(user);
        list.add(user2);
        list.add(user3);
        list.add(user4);

        Map<String, List<User>> collect = list.stream().collect(Collectors.groupingBy(User::getName));
        System.out.println(collect);


        //过滤list
        List<User> lili = list.stream().filter(data -> data.getName().equals("lili")).collect(Collectors.toList());
        lili.stream().forEach(userD -> {
            System.out.println(userD.getName());
        });
    }
}
