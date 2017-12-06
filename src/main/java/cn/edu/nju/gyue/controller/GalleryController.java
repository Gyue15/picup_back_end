package cn.edu.nju.gyue.controller;

import cn.edu.nju.gyue.models.GalleryModel;
import cn.edu.nju.gyue.service.GalleryService;
import cn.edu.nju.gyue.service.PhotoService;
import cn.edu.nju.gyue.util.FileUtil;
import cn.edu.nju.gyue.util.ResultMessage;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/gallery", produces = "application/json;charset=UTF-8")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;

    @Autowired
    private PhotoService photoService;

    @PostMapping("/upload")
    @ResponseBody
    public void upload(MultipartFile file) {
        FileUtil.upLoad(file);

    }

    @PostMapping("/post")
    @ResponseBody
    public String postPhoto(String[] fileNames, String title, String description, String[] tags, String albumId, Integer uid) {
        Date date = new Date();
        List<String> fileUrls = new ArrayList<>();
        for (Map.Entry<String, String> entry : FileUtil.filePathMap.entrySet()) {
            for (String fileName : fileNames) {
                if (fileName.equals(entry.getKey())) {
                    fileUrls.add(entry.getValue());
                }
            }
        }

        GalleryModel galleryModel = new GalleryModel();
        galleryModel.setTags(tags);
        galleryModel.setDescription(description);
        galleryModel.setUid(uid);
        galleryModel.setLikeNum(0);
        galleryModel.setDate(date);
        galleryModel.setAid(albumId);
        galleryModel.setTitle(title);
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

    @GetMapping("/hottags")
    @ResponseBody
    public String getHotTags() {
        return JSON.toJSONString(galleryService.getHotTags());
    }

    @PostMapping("/hotgallery")
    @ResponseBody
    public String getHotGallery(String username, int pageNum) {
        String res = JSON.toJSONString(galleryService.getHotGallerys(username, pageNum));
        System.out.println(res);
        return res;
    }

    @PostMapping("/interestgallery")
    @ResponseBody
    public String getInterestGallery(String username) {
        return JSON.toJSONString(galleryService.getInterestGalleryList(username));
    }

    @PostMapping("/latestgallery")
    @ResponseBody
    public String getLatestGallery(String username, int pageNum) {
        return JSON.toJSONString(galleryService.getLatestGallerys(username, pageNum));
    }

    @PostMapping("/like")
    @ResponseBody
    public String likeShow(String username, Integer galleryID) {
        return JSON.toJSONString(galleryService.likeGallery(galleryID, username));
    }

    @PostMapping("/search")
    @ResponseBody
    public String searchShow(String tag, String username) {
        return JSON.toJSONString(galleryService.searchGallery(tag, username));
    }
}
