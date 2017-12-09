package cn.edu.nju.gyue.controller;

import cn.edu.nju.gyue.entities.Gallery;
import cn.edu.nju.gyue.models.GalleryModel;
import cn.edu.nju.gyue.service.GalleryService;
import cn.edu.nju.gyue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class TestController {

    @Autowired
    GalleryService galleryService;

    @Autowired
    UserService userService;

    @Autowired
    GalleryController galleryController;

    @RequestMapping("/")
    @ResponseBody
    public String test() {

        //galleryService.initDatabase();

        List<GalleryModel> galleryList = galleryService.getInterestGalleryList("test1");

        System.out.println("!!!!!gallery size: " + galleryList.size());
        for (GalleryModel gallery: galleryList) {
            System.out.println(gallery.toString());
        }

        System.out.println(galleryController.searchGallery("猫", "gyue"));

        return "hello";
    }
}
