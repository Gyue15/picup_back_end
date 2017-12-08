package cn.edu.nju.gyue.controller;

import cn.edu.nju.gyue.models.GalleryModel;
import cn.edu.nju.gyue.service.GalleryService;
import cn.edu.nju.gyue.service.PhotoService;
import cn.edu.nju.gyue.util.FileUtil;
import cn.edu.nju.gyue.util.ResultMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/photo", produces = "application/json;charset=UTF-8")
public class PhotoController {
    @Autowired
    private GalleryService galleryService;

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    @ResponseBody
    public void upload(MultipartFile file) {
        FileUtil.upLoad(file);

    }

    @PostMapping("/postPhoto")
    @ResponseBody
    public String postPhoto(String[] fileNames, String title, String description, String[] tags, String albumId, Integer uid) {
        Date date = new Date();
        List<String> fileUrls = new ArrayList<>();
        for (String fileName : fileNames) {
            fileUrls.add(FileUtil.filePathMap.get(fileName));
        }

        GalleryModel galleryModel = new GalleryModel();
        galleryModel.tags = tags;
        galleryModel.description = description;
        galleryModel.uid = uid;
        galleryModel.likeNum = 0;
        galleryModel.date = date;
        galleryModel.aid = albumId;
        galleryModel.title = title;
        Integer gid = galleryService.saveGallery(galleryModel);
        if (gid <= 0) {
            return JSON.toJSONString(ResultMessage.FAILURE);
        }

        for (int i = 0; i < fileUrls.size(); i++) {
            Integer pid = photoService.addPhoto(fileUrls.get(i), gid, albumId);
            if (pid < 0) {
                return JSON.toJSONString(ResultMessage.FAILURE);
            }
        }
        return JSON.toJSONString(ResultMessage.SUCCESS);
    }
}
