package cn.edu.nju.gyue.controller;

import cn.edu.nju.gyue.configuration.FilePathConfig;
import cn.edu.nju.gyue.models.UserModel;
import cn.edu.nju.gyue.service.UserService;
import cn.edu.nju.gyue.util.FileUtil;
import cn.edu.nju.gyue.util.ResultMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private AlbumController albumController;


    @PostMapping("/login")
    @ResponseBody
    public String logIn(String username, String password) {
        UserModel userModel = userService.getUser(username, password);
        if (userModel == null || userModel.uid == null) {
            return JSON.toJSONString(ResultMessage.FAILURE);
        }
        return JSON.toJSONString(userModel);
    }

    @PostMapping("/signUp")
    @ResponseBody
    public String signUp(String username, String password) {
        UserModel userModel = new UserModel();
        userModel.avatar = FilePathConfig.AVATAR_URL + "default.jpg";
        userModel.password = password;
        userModel.username = username;
        int res = userService.addUser(userModel);
        if (res > 0) {
            albumController.createNewAlbum(username, "默认相册");
        }

        return res > 0 ? JSON.toJSONString(ResultMessage.SUCCESS) : JSON.toJSONString(ResultMessage.FAILURE);
    }

    @PostMapping("/getUser")
    @ResponseBody
    public String getUser(String username) {
        UserModel userModel = userService.getUser(username);
        userModel.password = null;
        return JSON.toJSONString(userModel);
    }

    @PostMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile file) {
        FileUtil.upLoad(file);
        return JSON.toJSONString(ResultMessage.SUCCESS);
    }

    @PostMapping("/postAvatar")
    @ResponseBody
    public String postAvatar(String avatarFileName, String username) {
        String url = FileUtil.filePathMap.get(avatarFileName);
        UserModel userModel = new UserModel();
        userModel.username = username;
        userModel.avatar = url;
        ResultMessage res = userService.modifyUser(userModel);
        System.out.println("postAvatar:"+res);
        if (res != ResultMessage.SUCCESS) {
            return JSON.toJSONString(res);
        }
        System.out.println("postAvatar:"+url);
        return JSON.toJSONString(url);
    }

    @PostMapping("/changePassword")
    @ResponseBody
    public String changePassword(String username, String oldPassword, String newPassword) {
        UserModel userModel = userService.getUser(username, oldPassword);
        if (userModel == null || userModel.uid == null) {
            return JSON.toJSONString(ResultMessage.FAILURE);
        }
        userModel.username = username;
        userModel.password = newPassword;
        String res = JSON.toJSONString(userService.modifyUser(userModel));
        System.out.println(res);
        return res;
    }


    @PostMapping("/followUser")
    @ResponseBody
    public String followUser(String username, String followedUsername) {
        return JSON.toJSONString(userService.follow(username, followedUsername));
    }

    @PostMapping("/unfollowUser")
    @ResponseBody
    public String unFollowUser(String username, String followedUsername) {
        return JSON.toJSONString(userService.unFollow(username, followedUsername));
    }

    @PostMapping("/unfollowUserList")
    @ResponseBody
    public String unFollowUserList(String username, String[] followedUsernameList) {
        for (String followedUsername: followedUsernameList) {
            ResultMessage res = userService.unFollow(username, followedUsername);
            if (res != ResultMessage.SUCCESS) {
                return JSON.toJSONString(res);
            }
        }
        return JSON.toJSONString(ResultMessage.SUCCESS);
    }

    @PostMapping("/followedUser")
    @ResponseBody
    public String getFollowedUser(String username) {
        return JSON.toJSONString(userService.getFollowedUser(username));
    }

    @PostMapping("/isFollowed")
    @ResponseBody
    public Boolean isFollowed(String followerUser, String followedUser) {
        return userService.isFollowed(followerUser, followedUser);
    }
}
