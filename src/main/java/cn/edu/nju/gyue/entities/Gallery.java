package cn.edu.nju.gyue.entities;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "gallery")
public class Gallery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer gid;

    private String title;

    private String description;

    @Column(name = "`date`")
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private Integer likeNum;

    private String aid;

    private Integer uid;

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

    public Integer getGid() {
        return gid;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public String getAid() {
        return this.aid;
    }

    public Integer getUid() {
        return uid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setTagsList(List<Tags> tagsList) {
        this.tagsList = tagsList;
    }

    public void setLikedUser(List<User> likedUser) {
        this.likedUser = likedUser;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && obj.getClass() == this.getClass() && this.gid.equals(((Gallery) obj).gid);
    }

    @Override
    public String toString() {
        return "Gallery [gid = "
                + gid.toString()
                + ", description = "
                + description
                + ", date = "
                + date.toString()
                + ", likeNum = "
                + likeNum
                + ", uid = "
                + uid
                + "]";
    }
}
