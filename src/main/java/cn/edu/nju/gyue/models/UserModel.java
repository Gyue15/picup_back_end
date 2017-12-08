package cn.edu.nju.gyue.models;

import com.alibaba.fastjson.JSON;

public class UserModel {
    public Integer uid;

    public String username;

    public String password;

    public String avatar;

    @Override
    public boolean equals(Object obj) {

        return obj != null && obj.getClass() == this.getClass() && this.uid.equals(((UserModel) obj).uid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
