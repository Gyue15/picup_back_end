package cn.edu.nju.gyue.entities;

import com.alibaba.fastjson.JSON;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    public String username;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer uid;

    public String password;

    public String avatar;

    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "likedUser")
    private List<Gallery> galleryList;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "follow", inverseJoinColumns =
    @JoinColumn(name = "follower_uid"), joinColumns =
    @JoinColumn(name = "followed_uid"))
    private List<User> followers;

    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "followers")
    private List<User> followedUsers;

    public List<Gallery> getGalleryList() {
        if (galleryList == null) {
            this.galleryList = new ArrayList<>();
        }
        return galleryList;
    }

    public List<User> getFollowers() {
        if (followers == null) {
            followers = new ArrayList<>();
        }
        return followers;
    }

    public List<User> getFollowedUsers() {
        if (followedUsers == null) {
            followedUsers = new ArrayList<>();
        }
        return followedUsers;
    }

    @Override
    public boolean equals(Object obj) {

        return obj != null && obj.getClass() == this.getClass() && this.uid.equals(((User) obj).uid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
