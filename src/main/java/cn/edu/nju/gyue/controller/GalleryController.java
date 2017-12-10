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
    public String likeGallery(String username, Integer gid) {
        return JSON.toJSONString(galleryService.likeGallery(gid, username));
    }

    @PostMapping("/unlikeGallery")
    @ResponseBody
    public String unLikeGallery(String username, Integer gid) {
        return JSON.toJSONString(galleryService.unLikeGallery(gid, username));
    }

    @PostMapping("/searchGallery")
    @ResponseBody
    public String searchGallery(String keyWords, String username) {
        return JSON.toJSONString(galleryService.searchGallery(keyWords, username));
    }

    @PostMapping("/detail")
    @ResponseBody
    public String getGalleryDetail(Integer gid, String username) {
        return JSON.toJSONString(galleryService.getGallery(gid, username));
    }

    /**
     *
     * @param uid 获得uid对应的用户的主页
     * @param visitor 访问这个主页的人的uid
     * @return gallery的list
     */
    @PostMapping("/myGallery")
    @ResponseBody
    public String getMyGallery(Integer uid, Integer visitor) {
        return JSON.toJSONString(galleryService.getMyGallery(uid, visitor));
    }


}
