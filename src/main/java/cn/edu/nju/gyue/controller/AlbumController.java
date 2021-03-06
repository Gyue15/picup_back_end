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

    @PostMapping("/albumDetail")
    @ResponseBody
    public String getAlbumDetail(String aid) {
        return JSON.toJSONString(albumService.getAlbumDetail(aid));
    }

    @PostMapping("/createAlbum")
    @ResponseBody
    public String createNewAlbum(String username, String album) {
        AlbumModel albumModel = new AlbumModel();
        albumModel.title = album;

        return JSON.toJSONString(albumService.saveAlbum(albumModel, username));
    }

    @PostMapping("/deleteAlbum")
    @ResponseBody
    public String deleteAlbum(String aid) {
        return JSON.toJSONString(albumService.deleteAlbum(aid));
    }

}
