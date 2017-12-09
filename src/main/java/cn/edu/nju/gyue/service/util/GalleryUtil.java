package cn.edu.nju.gyue.service.util;

import cn.edu.nju.gyue.entities.Gallery;
import cn.edu.nju.gyue.entities.Photo;
import cn.edu.nju.gyue.entities.Tags;
import cn.edu.nju.gyue.entities.User;
import cn.edu.nju.gyue.models.GalleryModel;
import cn.edu.nju.gyue.repositories.GalleryRepository;
import cn.edu.nju.gyue.repositories.PhotoRepository;
import cn.edu.nju.gyue.repositories.TagsRepository;
import cn.edu.nju.gyue.repositories.UserRepository;
import cn.edu.nju.gyue.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class GalleryUtil {
    @Autowired
    TagsRepository tagsRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Autowired
    GalleryRepository galleryRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public GalleryModel toGalleryModel(Gallery gallery, Integer uid) {
        GalleryModel galleryModel = new GalleryModel();

        // base
        galleryModel.aid = gallery.aid;
        galleryModel.date = gallery.date;
        galleryModel.description = gallery.description;
        galleryModel.gid = gallery.gid;
        galleryModel.likeNum = gallery.likeNum;
        galleryModel.uid = gallery.uid;
        galleryModel.title = gallery.title;

        // tags
        List<Tags> tagsList = tagsRepository.findByGalleryList_Gid(gallery.gid);
        String[] tags = new String[tagsList.size()];
        for (int j = 0; j < tags.length; j++) {
            tags[j] = tagsList.get(j).tag;
        }
        galleryModel.tags = tags;

        // pics
        List<Photo> photoList = photoRepository.findByGid(gallery.gid);
        String pics[] = new String[photoList.size()];
        for (int i = 0; i < pics.length; i++) {
            pics[i] = photoList.get(i).pic;
        }
        galleryModel.pictures = pics;

        // is liked
        Gallery testGallery = galleryRepository.findByGidAndLikedUser_Uid(gallery.gid, uid);
        galleryModel.isLiked = testGallery != null && testGallery.gid != null;

        // is followed
        galleryModel.isFollowed = userService.isFollowed(uid, gallery.uid);

        // user
        User user = userRepository.findByUid(gallery.uid);

        galleryModel.avatar = user.avatar;
        galleryModel.userName = user.username;


        // time
        galleryModel.formatDate = dateFormat.format(gallery.date);

        return galleryModel;
    }

    public  List<GalleryModel> toGalleryModel(List<Gallery> galleryList, Integer uid) {
        List<GalleryModel> galleryModelList = new ArrayList<>();
        for (Gallery gallery: galleryList) {
            galleryModelList.add(toGalleryModel(gallery, uid));

        }
        return galleryModelList;
    }
}
