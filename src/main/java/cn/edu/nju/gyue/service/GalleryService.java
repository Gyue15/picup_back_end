package cn.edu.nju.gyue.service;

import cn.edu.nju.gyue.models.GalleryModel;
import cn.edu.nju.gyue.util.ResultMessage;

import java.util.List;

public interface GalleryService {

    List<GalleryModel> getInterestGalleryList(String username);

    ResultMessage likeGallery(Integer gid, String username);

    ResultMessage unLikeGallery(Integer gid, String username);

    GalleryModel getGallery(Integer gid, String username);

    Integer saveGallery(GalleryModel showModel);

    List<String> getHotTags();

    List<GalleryModel> getHotGallerys(String userName, int pageNum);

    List<GalleryModel> getLatestGallerys(String userName, int pageNum);

    List<GalleryModel> searchGallery(String tag, String userName);
}
