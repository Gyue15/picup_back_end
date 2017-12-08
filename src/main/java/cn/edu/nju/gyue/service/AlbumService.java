package cn.edu.nju.gyue.service;

import cn.edu.nju.gyue.models.AlbumModel;
import cn.edu.nju.gyue.util.ResultMessage;

import java.util.List;

public interface AlbumService {
    List<AlbumModel> getAlbum(String userName);

    ResultMessage saveAlbum(AlbumModel albumModel, String username);

    ResultMessage deleteAlbum(String aid);
}
