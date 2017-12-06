package cn.edu.nju.gyue.repositories;

import cn.edu.nju.gyue.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer>{
    User findByUid(Integer uid);

    List<User> findByFollowers_Uid(Integer uid);

    List<User> findByUsername(String username);

    User findByUsernameAndPassword(String username, String password);

}
