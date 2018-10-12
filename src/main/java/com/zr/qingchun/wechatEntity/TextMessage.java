package com.zr.qingchun.wechatEntity;

/**
 * @ClassName TextMessage
 * @Decription TODO
 * @Author Administrator
 * @Date 2018/9/21 0021 17:07
 * Version 1.0
 **/
public class TextMessage {

    /**
     * 开发者微信号
     */
    public String ToUserName;
    /**
     * 发送方帐号（一个OpenID）
     */
    public String FromUserName;
    /**
     * 消息创建时间 （整型）
     */
    public long CreateTime;
    /**
     * text
     */
    public String MsgType ;

    /**
     * 文本消息内容
     */
    public String Content;
    /**
     * 消息id，64位整型
     */
    public long MsgId ;

    public String getToUserName() {
        return ToUserName;
    }

    public void setToUserName(String toUserName) {
        ToUserName = toUserName;
    }

    public String getFromUserName() {
        return FromUserName;
    }

    public void setFromUserName(String fromUserName) {
        FromUserName = fromUserName;
    }

    public long getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(long createTime) {
        CreateTime = createTime;
    }

    public String getMsgType() {
        return MsgType;
    }

    public void setMsgType(String msgType) {
        MsgType = msgType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public long getMsgId() {
        return MsgId;
    }

    public void setMsgId(long msgId) {
        MsgId = msgId;
    }
}
