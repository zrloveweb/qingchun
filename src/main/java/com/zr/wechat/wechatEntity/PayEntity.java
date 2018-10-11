package com.zr.wechat.wechatEntity;

import java.math.BigDecimal;

/**
 * @ClassName PayEntity
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/9/25 0025 17:38
 * Version 1.0
 **/
public class PayEntity {
    public static final String appid = "wx8397f8696b538317";
    public static final String partner = "1473426802";
    //签名
    public static final String partnerkey = "8A627A4578ACE384017C997F12D68B23";
    public static final String notifyurl = "http://030d968b.ngrok.io/pay/WeChatPayNotify";

    //随机字符串
    private String nonceStr;
    //商品描述
    private String body;
    //商户订单号
    private String out_trade_no;
    //标价金额(分)
    private String total_fee;
    //终端ip
    private String spbill_create_ip;
    // APP支付 交易类型JSAPI 公众号支付 NATIVE 扫码支付
    private String trade_type;


    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getTotal_fee() {
        return total_fee;
    }

    public void setTotal_fee(String total_fee) {
        this.total_fee = total_fee;
    }

    public String getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(String out_trade_no) {
        this.out_trade_no = out_trade_no;
    }


    public String getSpbill_create_ip() {
        return spbill_create_ip;
    }

    public void setSpbill_create_ip(String spbill_create_ip) {
        this.spbill_create_ip = spbill_create_ip;
    }

    public String getTrade_type() {
        return trade_type;
    }

    public void setTrade_type(String trade_type) {
        this.trade_type = trade_type;
    }
}
