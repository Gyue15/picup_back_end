package cn.edu.nju.gyue.models;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class MessageModel {

    public Integer mid;

    /**
     * 消息内容
     */
    public String text;

    /**
     * 消息拥有者
     */
    public Integer owner;

    /**
     * true代表已读
     */
    public Boolean isRead;

    /**
     * 消息制造者id
     */
    public Integer uid;

    /**
     * 消息制造者名字
     */
    public String username;

    /**
     * 消息制造者头像
     */
    public String avatar;

    /**
     * 消息发送日期
     */
    public Date date;

    /**
     * 格式化的消息发送日期
     */
    public String formatDate;

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.mid.equals(((MessageModel) obj).mid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
