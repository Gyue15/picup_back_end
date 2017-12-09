package cn.edu.nju.gyue.repositories;

import cn.edu.nju.gyue.entities.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer>{
    List<Photo> findByGid(Integer gid);

    List<Photo> findByAid(String aid);
}
