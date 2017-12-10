package cn.edu.nju.gyue.service.impl;

import cn.edu.nju.gyue.entities.Gallery;
import cn.edu.nju.gyue.entities.Tags;
import cn.edu.nju.gyue.entities.User;
import cn.edu.nju.gyue.models.GalleryModel;
import cn.edu.nju.gyue.repositories.GalleryRepository;
import cn.edu.nju.gyue.repositories.TagsRepository;
import cn.edu.nju.gyue.repositories.UserRepository;
import cn.edu.nju.gyue.service.GalleryService;
import cn.edu.nju.gyue.service.MessageService;
import cn.edu.nju.gyue.service.UserService;
import cn.edu.nju.gyue.service.util.GalleryUtil;
import cn.edu.nju.gyue.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
    private UserService userService;

    @Autowired
    private GalleryUtil galleryUtil;

    @Autowired
    private MessageService messageService;

    @Override
    public List<GalleryModel> getInterestGalleryList(String username) {
        Integer uid = userService.usernameToUid(username);
        System.out.println(uid);
        List<User> userList = userRepository.findByFollowers_Uid(uid);
        System.out.println(userList.size());
        List<Gallery> galleryList = new ArrayList<>();

        // 获得所有的Gallery
        // 因为我不会用jpa写jion语句。。。所以就这样凑合着用
        for (User user : userList) {
            galleryList.addAll(galleryRepository.findByUid(user.uid));
        }

        // 按时间排序
        sort(galleryList);

        return galleryUtil.toGalleryModel(galleryList, uid);
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
        if (testGallery != null && testGallery.gid != null) {
            return ResultMessage.FAILURE;
        }

        gallery.getLikedUser().add(user);
        gallery.likeNum = gallery.likeNum + 1;
        gallery = galleryRepository.saveAndFlush(gallery);
        System.out.println("save gallery: " + gallery.toString());

        if (uid.equals(gallery.uid)) {
            return ResultMessage.SUCCESS;
        }

        // 存储消息
        messageService.saveMessage(gallery.uid, "赞了你的动态 " + gallery.title, user);

        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage unLikeGallery(Integer gid, String username) {
        Integer uid = userService.usernameToUid(username);
        User user = userRepository.findByUid(uid);
        Gallery gallery = galleryRepository.findByGid(gid);
        // gid有误
        if (gallery == null) {
            return ResultMessage.FAILURE;
        }

        // 没赞过
        Gallery testGallery = galleryRepository.findByGidAndLikedUser_Uid(gid, uid);
        if (testGallery == null || testGallery.gid == null) {
            return ResultMessage.FAILURE;
        }

        gallery.getLikedUser().remove(user);
        gallery.likeNum = gallery.likeNum - 1;
        gallery = galleryRepository.saveAndFlush(gallery);
        System.out.println("save gallery: " + gallery.toString());

        return ResultMessage.SUCCESS;
    }

    @Override
    public GalleryModel getGallery(Integer gid, String username) {
        Gallery gallery = galleryRepository.findByGid(gid);
        GalleryModel galleryModel = new GalleryModel();
        if (gallery == null || gallery.gid == null || gallery.gid <= 0) {
            return galleryModel;
        }
        galleryModel = galleryUtil.toGalleryModel(gallery, userService.usernameToUid(username));

        return galleryModel;
    }

    @Override
    public Integer saveGallery(GalleryModel galleryModel) {
        Gallery gallery = new Gallery();
        gallery.aid = galleryModel.aid;
        gallery.date = galleryModel.date;
        gallery.description = galleryModel.description;
        gallery.uid = galleryModel.uid;
        gallery.likeNum = galleryModel.likeNum;
        gallery.gid = galleryModel.gid;
        gallery.title = galleryModel.title;

        // 增加tags
        String[] tags = galleryModel.tags;
        for (String tag : tags) {
            // 首先判断该tag是否存在
            Tags tagEntity = tagsRepository.findByTag(tag);
            if (tagEntity != null) {
                // 标签已存在
                tagEntity.usedTime = tagEntity.usedTime + 1;
                tagsRepository.saveAndFlush(tagEntity);
            } else {
                // 标签不存在
                tagEntity = new Tags();
                tagEntity.tag = tag;
                tagEntity.usedTime = 1;
                tagEntity = tagsRepository.saveAndFlush(tagEntity);
            }
            gallery.getTagsList().add(tagEntity);
        }

        gallery = galleryRepository.save(gallery);
        if (gallery == null || gallery.gid == null) {
            return -1;
        }

        // 存储消息
        List<User> owners = userRepository.findByFollowedUsers_Uid(galleryModel.uid);
        List<Integer> ownerUid = new ArrayList<>();
        for (User user : owners) {
            ownerUid.add(user.uid);
        }
        messageService.saveMessageList(ownerUid, "发布了一条新的动态", userRepository.findByUid(gallery.uid));

        return gallery.gid;
    }

    @Override
    public List<String> getHotTags() {
        Pageable pageable = new PageRequest(0, 6, Sort.Direction.DESC, "usedTime");
        Page<Tags> tagsPage = tagsRepository.findByUsedTimeGreaterThanEqual(0, pageable);
        List<Tags> tagsList = tagsPage.getContent();
        List<String> tags = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            tags.add(tagsList.get(i).tag);
        }
        return tags;
    }

    @Override
    public List<GalleryModel> getHotGallerys(String userName, int pageNum) {
        Pageable pageable = new PageRequest(pageNum, 20, Sort.Direction.DESC, "likeNum");
        Page<Gallery> galleryPage = galleryRepository.findByLikeNumGreaterThanEqual(0, pageable);
        List<Gallery> galleryList = galleryPage.getContent();
        return galleryUtil.toGalleryModel(galleryList, userService.usernameToUid(userName));
    }

    @Override
    public List<GalleryModel> getLatestGallerys(String userName, int pageNum) {
        Pageable pageable = new PageRequest(pageNum, 20, Sort.Direction.DESC, "date");
        Page<Gallery> galleryPage = galleryRepository.findByLikeNumGreaterThanEqual(0, pageable);
        List<Gallery> galleryList = galleryPage.getContent();
        return galleryUtil.toGalleryModel(galleryList, userService.usernameToUid(userName));
    }

    @Override
    public List<GalleryModel> searchGallery(String keyWords, String userName) {
        keyWords = "%" + keyWords + "%";
        List<Gallery> galleryList = galleryRepository.
                findByTitleLikeOrDescriptionLikeOrTagsList_TagLikeOrderByDateDesc(keyWords, keyWords, keyWords);
        return galleryUtil.toGalleryModel(galleryList, userService.usernameToUid(userName));
    }

    @Override
    public List<GalleryModel> getMyGallery(Integer uid, Integer visitor) {
        List<Gallery> galleryList = galleryRepository.findByUid(uid);
        // 增加消息
        if (!uid.equals(visitor)) {
            messageService.saveMessage(uid, "访问了你的个人主页", userRepository.findByUid(visitor));
        }
        return galleryUtil.toGalleryModel(galleryList, uid);
    }


    private void sort(List<Gallery> galleryList) {
        for (int i = galleryList.size() - 1; i > 0; i--) {
            for (int j = 0; j < i; j++) {
                if (galleryList.get(j + 1).date.getTime() > galleryList.get(j).date.getTime()) {
                    Gallery temp = galleryList.get(j);
                    galleryList.set(j, galleryList.get(j + 1));
                    galleryList.set(j + 1, temp);
                }
            }
        }
    }

}
