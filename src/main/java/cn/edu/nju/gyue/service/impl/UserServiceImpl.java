package cn.edu.nju.gyue.service.impl;

import cn.edu.nju.gyue.entities.User;
import cn.edu.nju.gyue.models.UserModel;
import cn.edu.nju.gyue.repositories.UserRepository;
import cn.edu.nju.gyue.service.UserService;
import cn.edu.nju.gyue.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Integer addUser(UserModel userModel) {
        // 判断重复
        if (userRepository.findByUsername(userModel.getUsername()).size() != 0) {
            return -1;
        }

        User user = new User();
        user.setAvatar(userModel.getAvatar());
        user.setPassword(userModel.getPassword());
        user.setUsername(userModel.getUsername());
        user = userRepository.saveAndFlush(user);
        return (user == null || user.getUid() == null) ? -1 : user.getUid();
    }

    @Override
    public ResultMessage modifyUser(UserModel userModel) {
        // 判断重复
        if (userRepository.findByUsername(userModel.getUsername()).size() == 0) {
            return ResultMessage.FAILURE;
        }

        User user = new User();
        user.setAvatar(userModel.getAvatar());
        user.setPassword(userModel.getPassword());
        user.setUsername(userModel.getUsername());
        user = userRepository.saveAndFlush(user);
        return (user == null || user.getUid() == null) ? ResultMessage.FAILURE : ResultMessage.SUCCESS;
    }

    @Override
    public UserModel getUser(String userName, String password) {
        User user = userRepository.findByUsernameAndPassword(userName, password);
        return toUserModel(user);
    }

    @Override
    public List<UserModel> getFollowedUser(String userName) {
        List<User> userList = userRepository.findByFollowers_Uid(userRepository.findByUsername(userName).get(0).getUid());
        List<UserModel> userModelList = new ArrayList<>();
        for (User user: userList) {
            UserModel userModel = toUserModel(user);
            if (userModel.getUid() != null && userModel.getUid() != 0) {
                userModelList.add(userModel);
            }
        }
        return userModelList;
    }

    @Override
    public ResultMessage follow(String followerUser, String followedUser) {
        if (isFollowed(followerUser, followedUser)) {
            return ResultMessage.FAILURE;
        }
        User follower = userRepository.findByUsername(followerUser).get(0);
        User followed = userRepository.findByUsername(followedUser).get(0);

        followed.getFollowers().add(follower);
        userRepository.saveAndFlush(followed);

        return ResultMessage.SUCCESS;
    }

    @Override
    public Boolean isFollowed(String followerUser, String followedUser) {
        List<User> userList = userRepository.findByFollowers_Uid(userRepository.findByUsername(followerUser).get(0).getUid());
        for (User user: userList) {
            if (user.getUsername().equals(followedUser)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isFollowed(Integer followerUid, Integer followedUid) {
        List<User> userList = userRepository.findByFollowers_Uid(followedUid);
        for (User user: userList) {
            if (user.getUid().equals(followedUid)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer usernameToUid(String userName) {
        return userRepository.findByUsername(userName).get(0).getUid();
    }

    private UserModel toUserModel(User user) {
        UserModel userModel = new UserModel();
        if (user == null) {
            return userModel;
        }
        userModel.setAvatar(user.getAvatar());
        userModel.setPassword(user.getPassword());
        userModel.setUsername(user.getUsername());
        userModel.setUid(user.getUid());
        return userModel;
    }
}
