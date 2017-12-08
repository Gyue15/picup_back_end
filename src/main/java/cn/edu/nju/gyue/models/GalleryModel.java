package cn.edu.nju.gyue.models;

import com.alibaba.fastjson.JSON;

import java.util.Date;

public class GalleryModel {

    public Integer gid;

    public String title;

    public String aid;

    public String description;

    public Date date;

    public Integer likeNum;

    public Integer uid;

    public Boolean isLiked;

    public String[] tags;

    public String[] pictures;

    public String userName;

    public String avatar;

    public Boolean isFollowed;

    public String formatDate;

    @Override
    public boolean equals(Object obj) {
        return obj != null && this.gid.equals(((GalleryModel) obj).gid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
