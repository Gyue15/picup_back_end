package cn.edu.nju.gyue.models;

public class UserModel {
    private Integer uid;

    private String username;

    private String password;

    private String avatar;

    public Integer getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public boolean equals(Object obj) {

        return obj != null && obj.getClass() == this.getClass() && this.uid.equals(((UserModel) obj).uid);
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
