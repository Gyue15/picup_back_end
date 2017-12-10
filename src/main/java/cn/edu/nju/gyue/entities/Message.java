package cn.edu.nju.gyue.entities;

import com.alibaba.fastjson.JSON;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer mid;

    public String text;

    public Integer owner;

    public Boolean isRead;

    public Integer uid;

    public String username;

    public String avatar;

    @Column(name = "`date`")
    @Temporal(TemporalType.TIMESTAMP)
    public Date date;

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.mid.equals(((Message) obj).mid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
