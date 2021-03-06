package cn.edu.nju.gyue.service.impl;

import cn.edu.nju.gyue.entities.Album;
import cn.edu.nju.gyue.entities.Photo;
import cn.edu.nju.gyue.entities.User;
import cn.edu.nju.gyue.models.AlbumModel;
import cn.edu.nju.gyue.repositories.AlbumRepository;
import cn.edu.nju.gyue.repositories.PhotoRepository;
import cn.edu.nju.gyue.repositories.UserRepository;
import cn.edu.nju.gyue.service.AlbumService;
import cn.edu.nju.gyue.util.ResultMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlbumServiceImpl implements AlbumService {

    @Autowired
    AlbumRepository albumRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PhotoRepository photoRepository;

    @Override
    public List<AlbumModel> getAlbum(String userName) {
        Integer uid = toUid(userName);
        List<Album> albumList = albumRepository.findByUid(uid);
        List<AlbumModel> albumModelList = new ArrayList<>();
        for (Album album: albumList) {
            albumModelList.add(toAlbumModel(album));
        }
        return albumModelList;
    }

    @Override
    public AlbumModel getAlbumDetail(String aid) {
        Album album = albumRepository.findByAid(aid);
        return toAlbumModel(album);
    }

    @Override
    public ResultMessage saveAlbum(AlbumModel albumModel, String username) {
        Album album = new Album();
        String aid = albumModel.aid;
        if (aid == null) {
            aid = username + albumModel.title;
        }
        album.aid = aid;
        List<User> userList = userRepository.findByUsername(username);
        if (userList.size() == 0) {
            return ResultMessage.FAILURE;
        }
        album.uid = userList.get(0).uid;
        album.title = albumModel.title;
        // 判断重复
        if (albumRepository.findByAid(aid) != null) {
            return ResultMessage.FAILURE;
        }

        albumRepository.saveAndFlush(album);
        return ResultMessage.SUCCESS;
    }

    @Override
    public ResultMessage deleteAlbum(String aid) {
        Album album = albumRepository.findByAid(aid);
        if (album == null) {
            return ResultMessage.FAILURE;
        }
        albumRepository.delete(album);
        return ResultMessage.SUCCESS;
    }

    private AlbumModel toAlbumModel(Album album) {
        AlbumModel albumModel = new AlbumModel();
        if (album == null || album.aid == null) {
            return albumModel;
        }
        albumModel.title = album.title;
        albumModel.aid = album.aid;
        albumModel.uid = album.uid;

        List<Photo> photos = photoRepository.findByAid(album.aid);
        String[] photoUrl = new String[photos.size()];
        for (int i = 0; i < photoUrl.length; i++) {
            photoUrl[i] = photos.get(i).pic;
        }
        albumModel.photos = photoUrl;

        return albumModel;
    }

    private Integer toUid(String userName) {
        return userRepository.findByUsername(userName).get(0).uid;
    }
}
