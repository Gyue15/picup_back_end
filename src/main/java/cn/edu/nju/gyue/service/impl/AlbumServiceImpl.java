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
    public ResultMessage saveAlbum(AlbumModel albumModel) {
        Album album = new Album();
        String aid = albumModel.getAid();
        if (aid == null) {
            aid = albumModel.getUid()+albumModel.getTitle();
        }
        album.setAid(aid);
        album.setUid(albumModel.getUid());
        album.setTitle(albumModel.getTitle());
        // 判断重复
        if (albumRepository.findByAid(aid) != null) {
            return ResultMessage.FAILURE;
        }

        albumRepository.saveAndFlush(album);
        return ResultMessage.SUCCESS;
    }

    private AlbumModel toAlbumModel(Album album) {
        AlbumModel albumModel = new AlbumModel();
        if (album == null || album.getAid() == null) {
            return albumModel;
        }
        albumModel.setTitle(album.getTitle());
        albumModel.setAid(album.getAid());
        albumModel.setUid(album.getUid());

        return albumModel;
    }

    private Integer toUid(String userName) {
        return userRepository.findByUsername(userName).get(0).getUid();
    }
}
