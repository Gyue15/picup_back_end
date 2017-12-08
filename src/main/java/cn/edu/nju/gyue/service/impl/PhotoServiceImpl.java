package cn.edu.nju.gyue.service.impl;

import cn.edu.nju.gyue.entities.Photo;
import cn.edu.nju.gyue.repositories.PhotoRepository;
import cn.edu.nju.gyue.service.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotoServiceImpl implements PhotoService {

    @Autowired
    private PhotoRepository photoRepository;

    @Override
    public Integer addPhoto(String pic, Integer gid, String aid) {
        Photo photo = new Photo();
        photo.pic = pic;
        photo.gid = gid;
        photo.aid = aid;
        System.out.println(photo.toString());
        photo = photoRepository.saveAndFlush(photo);
        if (photo == null || photo.pid == null) {
            return -1;
        }
        return photo.pid;
    }
}
