package cn.edu.nju.gyue.repositories;

import cn.edu.nju.gyue.entities.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

    List<Message> findByOwnerOrderByDateDesc(Integer owner);

    List<Message> findByOwnerAndIsRead(Integer owner, Boolean isRead);
}
