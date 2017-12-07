package cn.edu.nju.gyue.controller;

import cn.edu.nju.gyue.models.AlbumModel;
import cn.edu.nju.gyue.service.AlbumService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping(value = "/album", produces = "application/json;charset=UTF-8")
@Controller
public class AlbumController {

    @Autowired
    private AlbumService albumService;

    @PostMapping("/getAlbums")
    @ResponseBody
    public String getAlbums(String username) {
        return JSON.toJSONString(albumService.getAlbum(username));
    }

    @PostMapping("/createAlbum")
    @ResponseBody
    public String createNewAlbum(Integer uid, String album) {
        AlbumModel albumModel = new AlbumModel();
        albumModel.setUid(uid);
        albumModel.setTitle(album);

        return JSON.toJSONString(albumService.saveAlbum(albumModel));
    }
}
