package cn.edu.nju.gyue.models;

import com.alibaba.fastjson.JSON;

public class AlbumModel {

    public String aid;

    public String title;

    public Integer uid;

    public String[] photos;

    @Override
    public boolean equals(Object obj){
        return obj != null && obj.getClass() == this.getClass() && this.aid.equals(((AlbumModel)obj).aid);
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
