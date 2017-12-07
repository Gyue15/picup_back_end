package cn.edu.nju.gyue.repositories;

import cn.edu.nju.gyue.entities.Tags;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagsRepository extends JpaRepository<Tags, String> {
    List<Tags> findByGalleryList_Gid(Integer gid);

    Page<Tags> findByUsedTimeGreaterThanEqual(Integer base, Pageable pageable);

    Tags findByTag(String tag);
}
