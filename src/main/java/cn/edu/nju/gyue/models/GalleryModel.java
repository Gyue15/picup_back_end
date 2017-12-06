package cn.edu.nju.gyue.models;

import java.util.Date;

public class GalleryModel {

    private Integer gid;

    private String title;

    private String aid;

    private String description;

    private Date date;

    private Integer likeNum;

    private Integer uid;

    private Boolean isLiked;

    private String[] tags;

    private String[] pictures;

    private String userName;

    private String avatar;

    private Boolean isFollowed;

    public Integer getGid() {
        return gid;
    }

    public String getAid() {
        return aid;
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

    public Integer getUid() {
        return uid;
    }

    public Boolean getLiked() {
        return isLiked;
    }

    public String[] getTags() {
        return tags;
    }

    public String[] getPictures() {
        return pictures;
    }

    public String getTitle() {
        return title;
    }

    public String getUserName() {
        return userName;
    }

    public String getAvatar() {
        return avatar;
    }

    public Boolean getFollowed() {
        return isFollowed;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public void setFollowed(Boolean followed) {
        isFollowed = followed;
    }

    public void setGid(Integer gid) {
        this.gid = gid;
    }

    public void setAid(String aid) {
        this.aid = aid;
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

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setLiked(Boolean liked) {
        isLiked = liked;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public void setPictures(String[] pictures) {
        this.pictures = pictures;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.gid.equals(((GalleryModel) obj).gid);
    }

    @Override
    public String toString() {
        String tags = "[";
        if (this.tags == null) {
            this.tags = new String[0];
        }
        for (int i = 0; i < this.tags.length; i++) {
            tags += this.tags[i] + ", ";
        }
        tags += "\b\b]";

        String pics = "[";
        if (this.pictures == null) {
            this.pictures = new String[0];
        }
        for (int i = 0; i < this.pictures.length; i++) {
            pics += this.pictures[i] + ", ";
        }
        pics += "\b\b]";
        return "Gallery [gid = "
                + gid
                + ", description = "
                + description
                + ", date = "
                + date.toString()
                + ", likeNum = "
                + likeNum
                + ", uid = "
                + uid
                + ", isLiked = "
                + isLiked.toString()
                + ", aid = "
                + aid
                + ", tags = "
                + tags
                + ", pics = "
                + pics
                + "]";
    }
}
