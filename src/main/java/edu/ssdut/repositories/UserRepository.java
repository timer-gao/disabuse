package edu.ssdut.repositories;

import edu.ssdut.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Gaomj on 2017/7/3.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    //boolean existsNickname(String nickname);
    //boolean existsEmail(String email);
    User readByEmail(String email);
    User findByNicknameLike(String name);
    User readByNickname(String name);
}
