package cn.edu.nju.gyue.repositories;

import cn.edu.nju.gyue.entities.Gallery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
    Gallery findByGid(Integer gid);

    Gallery findByGidAndLikedUser_Uid(Integer gid, Integer uid);

    List<Gallery> findByUid(Integer uid);

    Page<Gallery> findByLikeNumGreaterThanEqual(Integer base, Pageable pageable);

    List<Gallery> findByTitleLikeOrDescriptionLikeOrTagsList_TagLikeOrderByDateDesc(String title, String description, String tag);
}
