package cn.edu.nju.gyue.models;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class MessageModel {

    public Integer mid;

    public String text;

    public Integer owner;

    public Boolean isRead;

    public Integer uid;

    public String username;

    public String avatar;

    public Date date;

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
