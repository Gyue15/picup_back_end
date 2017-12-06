package cn.edu.nju.gyue.controller;

import cn.edu.nju.gyue.configuration.FilePathConfig;
import cn.edu.nju.gyue.models.UserModel;
import cn.edu.nju.gyue.service.UserService;
import cn.edu.nju.gyue.util.ResultMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private AlbumController albumController;

    @PostMapping(value = "/signup")
    @ResponseBody
    public String signUp(String username, String password) {
        UserModel userModel = new UserModel();
        userModel.setAvatar(FilePathConfig.AVATAR_PATH + "default.png");
        userModel.setPassword(password);
        userModel.setUsername(username);
        int res = userService.addUser(userModel);
        if (res > 0) {
            albumController.createNewAlbum(res, "默认专辑");
        }

        return res > 0 ? JSON.toJSONString(ResultMessage.SUCCESS) : JSON.toJSONString(ResultMessage.FAILURE);
    }

    @PostMapping(value = "/login")
    @ResponseBody
    public String logIn(String email, String password) {
        UserModel userModel = userService.getUser(email, password);
        if (userModel == null || userModel.getUid() == null) {
            return JSON.toJSONString(ResultMessage.FAILURE);
        }
        return JSON.toJSONString(userModel);
    }

    @PostMapping("/follow")
    @ResponseBody
    public String follow(String followerUser, String followedUser) {
        return JSON.toJSONString(userService.follow(followerUser, followedUser));
    }

    @PostMapping("/followeduser")
    @ResponseBody
    public String getFollowed(String userName) {
        return JSON.toJSONString(userService.getFollowedUser(userName));
    }

    @PostMapping("/isFollowed")
    @ResponseBody
    public Boolean isFollowed(String followerUser, String followedUser) {
        return userService.isFollowed(followerUser, followedUser);
    }
}
