package cn.edu.nju.gyue.service.impl;

import cn.edu.nju.gyue.entities.Gallery;
import cn.edu.nju.gyue.entities.Photo;
import cn.edu.nju.gyue.entities.Tags;
import cn.edu.nju.gyue.entities.User;
import cn.edu.nju.gyue.models.GalleryModel;
import cn.edu.nju.gyue.repositories.GalleryRepository;
import cn.edu.nju.gyue.repositories.PhotoRepository;
import cn.edu.nju.gyue.repositories.TagsRepository;
import cn.edu.nju.gyue.repositories.UserRepository;
import cn.edu.nju.gyue.service.GalleryService;
import cn.edu.nju.gyue.service.UserService;
import cn.edu.nju.gyue.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class GalleryServiceImpl implements GalleryService {
    @Autowired
    private GalleryRepository galleryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TagsRepository tagsRepository;

    @Autowired
    private PhotoRepository photoRepository;

    @Autowired
    private UserService userService;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public List<GalleryModel> getInterestGalleryList(String username) {
        Integer uid = userService.usernameToUid(username);
        List<User> userList = userRepository.findByFollowers_Uid(uid);
        List<Gallery> galleryList = new ArrayList<>();

        // 获得所有的Gallery
        // 因为我不会用jpa写jion语句。。。所以就这样凑合着用
        for (User user: userList) {
            galleryList.addAll(galleryRepository.findByUid(user.getUid()));
        }

        // 按时间排序
        sort(galleryList);

        return toGalleryModel(galleryRepository.findByLikedUser_Uid(uid), uid);
    }

    @Override
    public ResultMessage likeGallery(Integer gid, String username) {
        Integer uid = userService.usernameToUid(username);
        User user = userRepository.findByUid(uid);
        Gallery gallery = galleryRepository.findByGid(gid);
        // gid有误
        if (gallery == null) {
            return ResultMessage.FAILURE;
        }

        // 已经赞过了
        Gallery testGallery = galleryRepository.findByGidAndLikedUser_Uid(gid, uid);
        if (testGallery != null && testGallery.getGid() != null) {
            return ResultMessage.FAILURE;
        }

        gallery.getLikedUser().add(user);
        gallery.setLikeNum(gallery.getLikeNum() + 1);
        gallery = galleryRepository.saveAndFlush(gallery);
        System.out.println("save gallery: " + gallery.toString());

        return ResultMessage.SUCCESS;
    }

    @Override
    public GalleryModel getGallery(Integer gid) {
        Gallery gallery = galleryRepository.findByGid(gid);
        GalleryModel galleryModel = new GalleryModel();
        if (gallery == null || gallery.getGid() == null || gallery.getGid() <= 0) {
            return galleryModel;
        }
        galleryModel.setLikeNum(gallery.getLikeNum());
        galleryModel.setDate(gallery.getDate());
        galleryModel.setUid(gallery.getUid());
        galleryModel.setDescription(gallery.getDescription());
        galleryModel.setGid(gallery.getGid());
        galleryModel.setAid(gallery.getAid());
        galleryModel.setTitle(gallery.getTitle());

        return galleryModel;
    }

    @Override
    public Integer saveGallery(GalleryModel galleryModel) {
        Gallery gallery = new Gallery();
        gallery.setAid(galleryModel.getAid());
        gallery.setDate(galleryModel.getDate());
        gallery.setDescription(galleryModel.getDescription());
        gallery.setUid(galleryModel.getUid());
        gallery.setLikeNum(galleryModel.getLikeNum());
        gallery.setGid(galleryModel.getGid());
        gallery.setTitle(galleryModel.getTitle());
        gallery = galleryRepository.save(gallery);
        if (gallery == null || gallery.getGid() == null) {
            return -1;
        }
        return gallery.getGid();
    }

    @Override
    public List<String> getHotTags() {
        Pageable pageable = new PageRequest(0, 6, Sort.Direction.DESC, "usedTime");
        Page<Tags> tagsPage = tagsRepository.findByUsedTimeGreaterThanEqual(0, pageable);
        List<Tags> tagsList = tagsPage.getContent();
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            tags.add(tagsList.get(i).getTag());
        }
        return tags;
    }

    @Override
    public List<GalleryModel> getHotGallerys(String userName, int pageNum) {
        Pageable pageable = new PageRequest(pageNum, 20, Sort.Direction.DESC, "likeNum");
        Page<Gallery> galleryPage = galleryRepository.findByLikeNumGreaterThanEqual(0,pageable);
        List<Gallery> galleryList = galleryPage.getContent();
        return this.toGalleryModel(galleryList, userService.usernameToUid(userName));
    }

    @Override
    public List<GalleryModel> getLatestGallerys(String userName, int pageNum) {
        Pageable pageable = new PageRequest(pageNum, 20, Sort.Direction.DESC, "date");
        Page<Gallery> galleryPage = galleryRepository.findByLikeNumGreaterThanEqual(0,pageable);
        List<Gallery> galleryList = galleryPage.getContent();
        return this.toGalleryModel(galleryList, userService.usernameToUid(userName));
    }

    @Override
    public List<GalleryModel> searchGallery(String tag, String userName) {
        List<Gallery> galleryList = galleryRepository.findByTagsList_Tag(tag);
        return toGalleryModel(galleryList, userService.usernameToUid(userName));
    }

    private List<GalleryModel> toGalleryModel(List<Gallery> galleryList, Integer uid) {
        List<GalleryModel> galleryModelList = new ArrayList<>();
        for (Gallery gallery: galleryList) {
            GalleryModel galleryModel = new GalleryModel();

            // base
            galleryModel.setAid(gallery.getAid());
            galleryModel.setDate(gallery.getDate());
            galleryModel.setDescription(gallery.getDescription());
            galleryModel.setGid(gallery.getGid());
            galleryModel.setLikeNum(gallery.getLikeNum());
            galleryModel.setUid(gallery.getUid());
            galleryModel.setTitle(gallery.getTitle());

            // tags
            List<Tags> tagsList = tagsRepository.findByGalleryList_Gid(gallery.getGid());
            String[] tags = new String[tagsList.size()];
            for (int j = 0; j < tags.length; j++) {
                tags[j] = tagsList.get(j).getTag();
            }
            galleryModel.setTags(tags);

            // pics
            List<Photo> photoList = photoRepository.findByGid(gallery.getGid());
            String pics[] = new String[photoList.size()];
            for (int i = 0; i < pics.length; i++) {
                pics[i] = photoList.get(i).getPic();
            }
            galleryModel.setPictures(pics);

            // is liked
            Gallery testGallery = galleryRepository.findByGidAndLikedUser_Uid(gallery.getGid(), uid);
            galleryModel.setLiked(testGallery != null && testGallery.getGid() != null);

            // is followed
            galleryModel.setFollowed(userService.isFollowed(uid, gallery.getUid()));

            // user
            User user = userRepository.findByUid(gallery.getUid());

            galleryModel.setAvatar(user.getAvatar());
            galleryModel.setUserName(user.getUsername());


            // time
            galleryModel.setFormatDate(dateFormat.format(gallery.getDate()));

            // add
            galleryModelList.add(galleryModel);

        }
        return galleryModelList;
    }

    private void sort(List<Gallery> galleryList) {
        for (int i = galleryList.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (galleryList.get(j + 1).getDate().getTime() > galleryList.get(j).getDate().getTime()) {
                    Gallery temp = galleryList.get(j);
                    galleryList.set(j, galleryList.get(j + 1));
                    galleryList.set(j + 1, temp);
                }
            }
        }
    }

}
