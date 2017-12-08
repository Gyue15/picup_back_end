package cn.edu.nju.gyue.controller;

import cn.edu.nju.gyue.service.GalleryService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/gallery", produces = "application/json;charset=UTF-8")
public class GalleryController {
    @Autowired
    private GalleryService galleryService;


    @GetMapping("/hotTags")
    @ResponseBody
    public String getHotTags() {
        return JSON.toJSONString(galleryService.getHotTags());
    }

    @PostMapping("/hotGallery")
    @ResponseBody
    public String getHotGallery(String username, int pageNum) {
        return JSON.toJSONString(galleryService.getHotGallerys(username, pageNum));
    }

    @PostMapping("/interestGallery")
    @ResponseBody
    public String getInterestGallery(String username) {
        return JSON.toJSONString(galleryService.getInterestGalleryList(username));
    }

    @PostMapping("/latestGallery")
    @ResponseBody
    public String getLatestGallery(String username, int pageNum) {
        return JSON.toJSONString(galleryService.getLatestGallerys(username, pageNum));
    }

    @PostMapping("/likeGallery")
    @ResponseBody
    public String likeGallery(String username, Integer galleryID) {
        return JSON.toJSONString(galleryService.likeGallery(galleryID, username));
    }

    @PostMapping("/searchGallery")
    @ResponseBody
    public String searchGallery(String tag, String username) {
        return JSON.toJSONString(galleryService.searchGallery(tag, username));
    }

    @PostMapping("/detail")
    @ResponseBody
    public String getGalleryDetail(Integer gid, String username) {
        return JSON.toJSONString(galleryService.getGallery(gid, username));
    }


}
