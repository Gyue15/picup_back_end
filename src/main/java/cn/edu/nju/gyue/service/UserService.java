package cn.edu.nju.gyue.service;

import cn.edu.nju.gyue.models.UserModel;
import cn.edu.nju.gyue.util.ResultMessage;

import java.util.List;

public interface UserService {

    ResultMessage addUser(UserModel userModel);

    ResultMessage modifyUser(UserModel userModel);

    UserModel getUser(String userName, String password);

    List<UserModel> getFollowedUser(String userName);

    ResultMessage follow(String followerUser, String followedUser);

    Boolean isFollowed(String followerUser, String followedUser);

    Boolean isFollowed(Integer followerUid, Integer followedUid);

    Integer usernameToUid(String userName);
}