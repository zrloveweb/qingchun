package com.xuemei.weixin.bean;

import com.thoughtworks.xstream.annotations.XStreamAlias;

/**
 * @Title: com.xuemei.weixin.bean.TextMessage
 * @Package com.xuemei.weixin.bean
 * @Description:
 * @author: 薛梅
 * @date: 2017/11/30 20:37
 */
public class TextMessage {

    @XStreamAlias("FromUserName")
    private String fromUserName;

    @XStreamAlias("ToUserName")
    private String toUserName;

    @XStreamAlias("MsgType")
    private String msgType;

    @XStreamAlias("CreateTime")
    private Long createTime;

    @XStreamAlias("Content")
    private String content;

    public String getFromUserName() {
        return fromUserName;
    }

    public void setFromUserName(String fromUserName) {
        this.fromUserName = fromUserName;
    }

    public String getToUserName() {
        return toUserName;
    }

    public void setToUserName(String toUserName) {
        this.toUserName = toUserName;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}