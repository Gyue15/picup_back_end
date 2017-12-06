package cn.edu.nju.gyue.entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
public class User {

    private String username;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer uid;

    private String password;

    private String avatar;

    @ManyToMany(cascade = CascadeType.REFRESH, mappedBy = "likedUser")
    private List<Gallery> galleryList;

    @ManyToMany(cascade = CascadeType.REFRESH)
    @JoinTable(name = "follow", inverseJoinColumns =
    @JoinColumn(name = "followed_uid"), joinColumns =
    @JoinColumn(name = "follower_uid"))
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

    public String getUsername() {
        return username;
    }

    public Integer getUid() {
        return uid;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setGalleryList(List<Gallery> galleryList) {
        this.galleryList = galleryList;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object obj) {

        return obj != null && obj.getClass() == this.getClass() && this.uid.equals(((User) obj).uid);
    }

    @Override
    public String toString() {
        return "User [name = "
                + username
                + ", uid = "
                + uid
                + ", password = "
                + password
                + ", avatar = "
                + avatar
                + "]";
    }
}
