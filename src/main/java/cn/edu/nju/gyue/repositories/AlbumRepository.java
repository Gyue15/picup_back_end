package cn.edu.nju.gyue.repositories;

import cn.edu.nju.gyue.entities.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlbumRepository extends JpaRepository<Album, Integer>{
    List<Album> findByUid(Integer uid);
    Album findByAid(String aid);
}
