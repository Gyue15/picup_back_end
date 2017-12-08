package cn.edu.nju.gyue.entities;

import com.alibaba.fastjson.JSON;

import javax.persistence.*;

@Entity
@Table(name = "album")
public class Album {

    @Id
    public String aid;

    public String title;

    public Integer uid;

    @Override
    public boolean equals(Object obj){
        return obj != null && obj.getClass() == this.getClass() && this.aid.equals(((Album)obj).aid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
