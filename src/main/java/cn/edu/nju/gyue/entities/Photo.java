package cn.edu.nju.gyue.entities;

import com.alibaba.fastjson.JSON;

import javax.persistence.*;

@Entity
@Table(name = "photo")
public class Photo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer pid;

    public String pic;

    public Integer gid;

    public String aid;

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.pid.equals(((Photo)obj).pid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
