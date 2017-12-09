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
        if (userRepository.findByUsername(userModel.username).size() != 0) {
            return -1;
        }

        User user = new User();
        user.avatar = userModel.avatar;
        user.password = userModel.password;
        user.username = userModel.username;
        user = userRepository.saveAndFlush(user);
        return (user == null || user.uid == null) ? -1 : user.uid;
    }

    @Override
    public ResultMessage modifyUser(UserModel userModel) {
        // 判断重复
        if (userRepository.findByUsername(userModel.username).size() == 0) {
            return ResultMessage.FAILURE;
        }

        User user = userRepository.findByUsername(userModel.username).get(0);
        if (userModel.avatar != null) {
            user.avatar = userModel.avatar;
        }
        if (userModel.username != null) {
            user.username = userModel.username;
        }
        if (userModel.password != null) {
            user.password = userModel.password;
        }

        user = userRepository.saveAndFlush(user);
        return (user == null || user.uid == null) ? ResultMessage.FAILURE : ResultMessage.SUCCESS;
    }

    @Override
    public UserModel getUser(String userName, String password) {
        User user = userRepository.findByUsernameAndPassword(userName, password);
        return toUserModel(user);
    }

    @Override
    public List<UserModel> getFollowedUser(String userName) {
        List<User> userList = userRepository.findByFollowers_Uid(userRepository.findByUsername(userName).get(0).uid);
        List<UserModel> userModelList = new ArrayList<>();
        for (User user: userList) {
            UserModel userModel = toUserModel(user);
            if (userModel.uid != null && userModel.uid != 0) {
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
    public ResultMessage unFollow(String followerUser, String followedUser) {
        if (!isFollowed(followerUser, followedUser)) {
            return ResultMessage.FAILURE;
        }
        User follower = userRepository.findByUsername(followerUser).get(0);
        User followed = userRepository.findByUsername(followedUser).get(0);

        System.out.println("============0===================");
        for (int i = 0; i < followed.getFollowers().size(); i++) {
            System.out.println(followed.getFollowers().get(i).toString());
        }

        followed.getFollowers().remove(follower);
        System.out.println("============1===================");
        for (int i = 0; i < followed.getFollowers().size(); i++) {
            System.out.println(followed.getFollowers().get(i).toString());
        }
        followed = userRepository.saveAndFlush(followed);

        System.out.println("============2===================");
        for (int i = 0; i < followed.getFollowers().size(); i++) {
            System.out.println(followed.getFollowers().get(i).toString());
        }

        return ResultMessage.SUCCESS;
    }

    @Override
    public Boolean isFollowed(String followerUser, String followedUser) {
        List<User> userList = userRepository.findByFollowers_Uid(userRepository.findByUsername(followerUser).get(0).uid);
        for (User user: userList) {
            if (user.username.equals(followedUser)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Boolean isFollowed(Integer followerUid, Integer followedUid) {
        List<User> userList = userRepository.findByFollowers_Uid(followerUid);
        for (User user: userList) {
            if (user.uid.equals(followedUid)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Integer usernameToUid(String userName) {
        List<User> user = userRepository.findByUsername(userName);
        if (user.size() == 0) {
            return -1;
        }
        return user.get(0).uid;
    }

    private UserModel toUserModel(User user) {
        UserModel userModel = new UserModel();
        if (user == null) {
            return userModel;
        }
        userModel.avatar = user.avatar;
        userModel.password = user.password;
        userModel.username = user.username;
        userModel.uid = user.uid;
        return userModel;
    }
}
