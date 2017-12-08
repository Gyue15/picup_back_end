package cn.edu.nju.gyue.entities;


import com.alibaba.fastjson.JSON;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gallery")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer gid;

    public String title;

    public String description;

    @Column(name = "`date`")
    @Temporal(TemporalType.TIMESTAMP)
    public Date date;

    public Integer likeNum;

    public String aid;

    public Integer uid;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "like", inverseJoinColumns =
    @JoinColumn(name = "uid"), joinColumns =
    @JoinColumn(name = "gid"))
    private List<User> likedUser;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "tagrelation", inverseJoinColumns =
    @JoinColumn(name = "tag"), joinColumns =
    @JoinColumn(name = "gid"))
    private List<Tags> tagsList;

    public List<User> getLikedUser(){
        if (this.likedUser == null) {
            likedUser = new ArrayList<>();
        }
        return likedUser;
    }

    public List<Tags> getTagsList() {
        if (this.tagsList == null) {
            tagsList = new ArrayList<>();
        }
        return tagsList;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.gid.equals(((Gallery) obj).gid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
