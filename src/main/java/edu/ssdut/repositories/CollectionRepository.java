package edu.ssdut.repositories;

import edu.ssdut.entities.Collection;
import edu.ssdut.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Gaomj on 2017/7/6.
 */
@Repository
public interface CollectionRepository extends JpaRepository<Collection,Long> {
    List<Collection> findAllByUser(User user);
}
