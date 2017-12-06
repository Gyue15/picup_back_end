package cn.edu.nju.gyue.models;

public class AlbumModel {

    private String aid;

    private String title;

    private Integer uid;

    public String getAid() {
        return aid;
    }

    public String getTitle() {
        return title;
    }

    public Integer getUid() {
        return uid;
    }

    public void setAid(String aid) {
        this.aid = aid;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    @Override
    public boolean equals(Object obj){
        return obj != null && obj.getClass() == this.getClass() && this.aid.equals(((AlbumModel)obj).aid);
    }

    @Override
    public String toString() {
        return "Album [aid = "
                + aid.toString()
                + ", title = "
                + title
                + ", uid = "
                + uid
                + "]";
    }
}
