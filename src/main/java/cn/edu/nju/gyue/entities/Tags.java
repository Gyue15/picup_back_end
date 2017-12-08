package cn.edu.nju.gyue.entities;


import com.alibaba.fastjson.JSON;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "tags")
public class Tags {

    @Id
    public String tag;

    public Integer usedTime;

    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "tagsList")
    public List<Gallery> galleryList;

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.tag.equals(((Tags)obj).tag);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

}
