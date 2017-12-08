package cn.edu.nju.gyue.service.impl;

import cn.edu.nju.gyue.entities.Album;
import cn.edu.nju.gyue.models.AlbumModel;
import cn.edu.nju.gyue.repositories.AlbumRepository;
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
    public ResultMessage saveAlbum(AlbumModel albumModel, String username) {
        Album album = new Album();
        String aid = albumModel.aid;
        if (aid == null) {
            aid = albumModel.uid+albumModel.title;
        }
        album.aid = aid;
        if (userRepository.findByUsername(username).size() == 0) {
            return ResultMessage.FAILURE;
        }
        album.uid = userRepository.findByUsername(username).get(0).uid;
        album.title = albumModel.title;
        // 判断重复
        if (albumRepository.findByAid(aid) != null) {
            return ResultMessage.FAILURE;
        }

        albumRepository.saveAndFlush(album);
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

        return albumModel;
    }

    private Integer toUid(String userName) {
        return userRepository.findByUsername(userName).get(0).uid;
    }
}
